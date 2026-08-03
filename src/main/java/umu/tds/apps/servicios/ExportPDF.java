package umu.tds.apps.servicios;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import umu.tds.apps.modelo.ContactoIndividual;
import umu.tds.apps.modelo.Mensaje;
import umu.tds.apps.modelo.TipoMensaje;
import umu.tds.apps.modelo.Usuario;
import umu.tds.apps.servicios.descargas.FactoriaProveedorRutaDescargas;

/**
 * Clase encargada de exportar los datos de un usuario a un archivo PDF.
 * Incluye listados de contactos individuales y grupos, así como el historial de chat con un contacto específico.
 */
public enum ExportPDF {

	/**
	 * Instancia única de la clase ExportPDF (Singleton).
	 */
	INSTANCE;

	/**
	 * Tamaño del título en el PDF.
	 */
	private static final int TITULO_TAMAÑO = 32;
	
	/**
	 * Prefijo para los nombres de los archivos PDF generados.
	 */
	private static final String INICIO_NOMBRE_PDF = "AppChat_";
	
	/**
	 * Extensión de los archivos PDF generados.
	 */
	private static final String PDF_EXTENSION = ".pdf";
	
	/**
	 * Título del PDF que se generará.
	 */
	private static final String TITULO_PDF = "Contactos añadidos en AppChat de ";
	
	/**
	 * Mensajes inicial que se incluirá en el PDF.
	 */
	private static final String MENSAJE_INICIAL = "¡Hola! Antes de nada, le agradecemos su máxima confianza depositada en AppChat. "
			+ "En primer lugar, he aquí un listado de sus contactos individuales, con sus respectivos números de teléfono móvil.";
	
	/**
	 * Mensaje que se mostrará después del listado de contactos.
	 */
	private static final String MENSAJE_SEGUNDO = "En segundo lugar, le mostramos un listado de sus grupos, con cada uno de los integrantes que añadió.";

	/**
	 * Constructor privado para evitar instanciación externa.
	 */
	private ExportPDF() {}

	/**
	 * Crea un archivo PDF con el listado de contactos y grupos del usuario.
	 *
	 * @param usuario El usuario cuyos datos se exportarán al PDF.
	 * @return true si el PDF se creó correctamente, false en caso contrario.
	 */
	public boolean createPdfListado(Usuario usuario) {
		File fichero = new File(FactoriaProveedorRutaDescargas.INSTANCE.getRutaDescargas(),
				INICIO_NOMBRE_PDF + usuario.getNombre() + PDF_EXTENSION);
		try (PdfWriter pdfWriter = new PdfWriter(fichero)) {
			PdfDocument pdfDocument = new PdfDocument(pdfWriter);
			Document document = new Document(pdfDocument);

			agregarTitulo(document, usuario.getNombre());
			agregarListadoContactos(document, usuario.getContactosIndividualesAñadidos());
			agregarListadoGrupos(document, usuario);

			document.close();
			pdfDocument.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Agrega el título al documento PDF.
	 *
	 * @param document El documento PDF al que se añadirá el título.
	 * @param nombreUsuario El nombre del usuario que se mostrará en el título.
	 * @throws IOException Si ocurre un error al crear la fuente del título.
	 */
	private void agregarTitulo(Document document, String nombreUsuario) throws IOException {
		PdfFont fuenteTitulo = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
		Paragraph titulo = new Paragraph(TITULO_PDF + nombreUsuario)
				.setFont(fuenteTitulo)
				.setFontSize(TITULO_TAMAÑO)
				.setTextAlignment(TextAlignment.CENTER);
		document.add(titulo);
		document.add(new Paragraph(MENSAJE_INICIAL));
	}

	/**
	 * Agrega un listado de contactos individuales al documento PDF.
	 *
	 * @param document El documento PDF al que se añadirá el listado.
	 * @param contactos El conjunto de contactos individuales a incluir en el listado.
	 */
	private void agregarListadoContactos(Document document, Set<ContactoIndividual> contactos) {
		AtomicInteger contador = new AtomicInteger(1);
		contactos.forEach(contacto -> {
			Paragraph p = new Paragraph(contador.getAndIncrement() + ". " + contacto.getNombre() + ": " + contacto.getMovil());
			document.add(p);
		});
	}

	/**
	 * Agrega un listado de grupos al documento PDF.
	 *
	 * @param document El documento PDF al que se añadirá el listado de grupos.
	 * @param usuario El usuario cuyos grupos se incluirán en el listado.
	 */
	private void agregarListadoGrupos(Document document, Usuario usuario) {
		document.add(new Paragraph(MENSAJE_SEGUNDO));
		AtomicInteger contador = new AtomicInteger(1);
		usuario.getGrupos().forEach(grupo -> {
			Paragraph p = new Paragraph(contador.getAndIncrement() + ". " + grupo.getNombre() + ":\n");
			grupo.getMiembros().forEach(miembro -> {
				p.add("  - " + miembro.getNombre() + ": " + miembro.getMovil() + "\n");
			});
			document.add(p);
		});
	}

	/**
	 * Crea un archivo PDF con el historial de chat de un contacto individual.
	 *
	 * @param contacto El contacto individual cuyo historial de chat se exportará al PDF.
	 * @return true si el PDF se creó correctamente, false en caso contrario.
	 */
	public boolean createPdfChat(ContactoIndividual contacto) {
		File fichero = new File(FactoriaProveedorRutaDescargas.INSTANCE.getRutaDescargas(),
				"Historial_" + contacto.getNombre() + ".pdf");

		try (PdfWriter writer = new PdfWriter(fichero);
			 PdfDocument pdfDoc = new PdfDocument(writer);
			 Document document = new Document(pdfDoc)) {

			document.setMargins(15, 15, 15, 15);

			Color colorVerde = new DeviceRgb(37, 211, 102);
			Color colorGris = new DeviceRgb(233, 237, 239);
			Color colorFondo = new DeviceRgb(240, 242, 245);
			Color colorTextoSecundario = new DeviceRgb(134, 142, 150);
			Color colorBorde = new DeviceRgb(220, 224, 226);

			PdfFont fontRegular = PdfFontFactory.createFont(StandardFonts.HELVETICA);
			PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

			dibujarFondoPagina(pdfDoc, colorFondo);
			agregarEncabezadoChat(document, contacto, fontBold, fontRegular, colorVerde);
			agregarIntro(document, fontRegular, colorTextoSecundario);

			List<Mensaje> mensajes = new ArrayList<>(contacto.getMensajes());
			mensajes.sort(Comparator.comparing(Mensaje::getMomentoEnvio));

			procesarMensajes(document, mensajes, fontRegular, colorGris, colorVerde, colorTextoSecundario, colorBorde);

			Paragraph footer = new Paragraph("Generado el " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
					.setFont(fontRegular)
					.setFontSize(8)
					.setFontColor(colorTextoSecundario)
					.setTextAlignment(TextAlignment.CENTER)
					.setMarginTop(30);
			document.add(footer);

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Dibuja el fondo de la página del PDF.
	 *
	 * @param pdfDoc El documento PDF donde se dibujará el fondo.
	 * @param fondo El color de fondo a aplicar.
	 */
	private void dibujarFondoPagina(PdfDocument pdfDoc, Color fondo) {
		Rectangle pageSize = pdfDoc.getDefaultPageSize();
		PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage().newContentStreamBefore(), pdfDoc.getFirstPage().getResources(), pdfDoc);
		canvas.setFillColor(fondo).rectangle(0, 0, pageSize.getWidth(), pageSize.getHeight()).fill();
	}

	/**
	 * Agrega el encabezado del chat al documento PDF.
	 *
	 * @param document El documento PDF al que se añadirá el encabezado.
	 * @param contacto El contacto individual cuyo historial de chat se exportará.
	 * @param fontBold Fuente para el texto en negrita.
	 * @param fontRegular Fuente para el texto normal.
	 * @param fondoHeader Color de fondo del encabezado.
	 */
	private void agregarEncabezadoChat(Document document, ContactoIndividual contacto, PdfFont fontBold, PdfFont fontRegular, Color fondoHeader) {
		Div header = new Div()
				.setBackgroundColor(fondoHeader)
				.setPadding(15)
				.setMarginBottom(10);

		header.add(new Paragraph(contacto.getNombre())
				.setFont(fontBold)
				.setFontColor(ColorConstants.WHITE)
				.setFontSize(18)
				.setMargin(0));

		header.add(new Paragraph("Historial de conversación")
				.setFont(fontRegular)
				.setFontColor(ColorConstants.WHITE)
				.setFontSize(12)
				.setMargin(0)
				.setOpacity(0.8f));

		document.add(header);
	}

	/**
	 * Agrega una introducción al documento PDF.
	 *
	 * @param document El documento PDF al que se añadirá la introducción.
	 * @param fontRegular Fuente para el texto normal.
	 * @param colorTextoSecundario Color del texto secundario.
	 */
	private void agregarIntro(Document document, PdfFont fontRegular, Color colorTextoSecundario) {
		document.add(new Paragraph("💬 Exportado desde AppChat Premium")
				.setFont(fontRegular)
				.setTextAlignment(TextAlignment.CENTER)
				.setFontSize(10)
				.setFontColor(colorTextoSecundario)
				.setMarginBottom(20));
	}

	/**
	 * Procesa y agrega los mensajes al documento PDF.
	 *
	 * @param document El documento PDF al que se añadirán los mensajes.
	 * @param mensajes La lista de mensajes a procesar.
	 * @param fontRegular Fuente para el texto normal.
	 * @param colorGris Color de fondo para los mensajes recibidos.
	 * @param colorVerde Color de fondo para los mensajes enviados.
	 * @param colorTextoSecundario Color del texto secundario (hora).
	 * @param colorBorde Color del borde del separador de fecha.
	 */
	private void procesarMensajes(Document document, List<Mensaje> mensajes, PdfFont fontRegular,
								   Color colorGris, Color colorVerde, Color colorTextoSecundario, Color colorBorde) {
		LocalDate ultimaFecha = null;
		DateTimeFormatter horaFormat = DateTimeFormatter.ofPattern("HH:mm");

		for (Mensaje mensaje : mensajes) {
			LocalDate fechaActual = mensaje.getMomentoEnvio().toLocalDate();
			if (ultimaFecha == null || !fechaActual.equals(ultimaFecha)) {
				agregarSeparadorFecha(document, getEtiquetaFecha(fechaActual), fontRegular, colorBorde);
				ultimaFecha = fechaActual;
			}
			agregarBurbujaMensaje(document, mensaje, fontRegular, colorGris, colorVerde, colorTextoSecundario, horaFormat);
		}
	}

	/**
	 * Agrega un separador de fecha al documento PDF.
	 *
	 * @param document El documento PDF al que se añadirá el separador.
	 * @param textoFecha El texto que se mostrará en el separador (fecha).
	 * @param font Fuente para el texto del separador.
	 * @param borde Color del borde del separador.
	 */
	private void agregarSeparadorFecha(Document document, String textoFecha, PdfFont font, Color borde) {
		Div fechaContainer = new Div().setTextAlignment(TextAlignment.CENTER)
				.setMarginTop(15).setMarginBottom(15);

		Paragraph separadorFecha = new Paragraph(textoFecha)
				.setFont(font)
				.setFontSize(11)
				.setFontColor(ColorConstants.GRAY)
				.setBackgroundColor(ColorConstants.WHITE)
				.setPadding(6)
				.setPaddingLeft(12)
				.setPaddingRight(12)
				.setBorder(new SolidBorder(borde, 1))
				.setMargin(0);

		fechaContainer.add(separadorFecha);
		document.add(fechaContainer);
	}

	/**
	 * Agrega una burbuja de mensaje al documento PDF.
	 *
	 * @param document El documento PDF al que se añadirá la burbuja de mensaje.
	 * @param mensaje El mensaje a mostrar en la burbuja.
	 * @param font Fuente para el texto del mensaje.
	 * @param colorGris Color de fondo para los mensajes recibidos.
	 * @param colorVerde Color de fondo para los mensajes enviados.
	 * @param colorTextoSecundario Color del texto secundario (hora).
	 * @param horaFormat Formato para la hora del mensaje.
	 */
	private void agregarBurbujaMensaje(Document document, Mensaje mensaje, PdfFont font, Color colorGris, Color colorVerde,
									   Color colorTextoSecundario, DateTimeFormatter horaFormat) {
		boolean esEnviado = mensaje.getTipo() == TipoMensaje.ENVIADO;

		Div contenedor = new Div().setWidth(UnitValue.createPercentValue(100)).setMarginBottom(8);
		Div burbuja = new Div()
				.setWidth(UnitValue.createPercentValue(75))
				.setPadding(12)
				.setPaddingTop(8)
				.setPaddingBottom(8)
				.setHorizontalAlignment(esEnviado ? HorizontalAlignment.RIGHT : HorizontalAlignment.LEFT);
		contenedor.setTextAlignment(esEnviado ? TextAlignment.RIGHT : TextAlignment.LEFT);

		Paragraph texto = new Paragraph(mensaje.getTexto())
				.setFont(font)
				.setFontSize(12)
				.setFontColor(esEnviado ? ColorConstants.WHITE : ColorConstants.BLACK)
				.setMargin(0)
				.setMarginBottom(4);

		String horaTexto = obtenerHoraMensaje(mensaje, horaFormat);

		Paragraph hora = new Paragraph(horaTexto)
				.setFont(font)
				.setFontSize(9)
				.setFontColor(esEnviado ? ColorConstants.WHITE : colorTextoSecundario)
				.setTextAlignment(TextAlignment.RIGHT)
				.setMargin(0)
				.setOpacity(0.7f);

		if (esEnviado) {
			hora.add(new Tab()).add("✓✓");
			burbuja.setBackgroundColor(colorVerde);
		} else {
			burbuja.setBackgroundColor(colorGris);
		}

		burbuja.add(texto);
		burbuja.add(hora);
		contenedor.add(burbuja);
		document.add(contenedor);
	}

	/**
	 * Obtiene la hora del mensaje en formato "HH:mm".
	 *
	 * @param mensaje El mensaje del cual se extraerá la hora.
	 * @param formato El formato de fecha y hora a utilizar.
	 * @return La hora del mensaje formateada como "HH:mm".
	 */
	private String obtenerHoraMensaje(Mensaje mensaje, DateTimeFormatter formato) {
		try {
			Object m = mensaje.getMomentoEnvio();
			if (m instanceof LocalDateTime ldt) return ldt.format(formato);
			if (m instanceof java.sql.Timestamp ts) return ts.toLocalDateTime().format(formato);
			if (m instanceof java.util.Date date) return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(formato);
			if (m instanceof java.time.Instant instant) return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).format(formato);
			String s = m.toString();
			for (String p : s.split(" ")) {
				if (p.contains(":") && p.length() >= 5) return p.substring(0, 5);
			}
		} catch (Exception e) {
			// Ignorar y devolver por defecto
		}
		return "00:00";
	}

	/**
	 * Obtiene una etiqueta de fecha para mostrar en el PDF.
	 * Dependiendo de la fecha, puede devolver "Hoy", "Ayer", el nombre del día de la semana o la fecha en formato "dd/MM/yyyy".
	 *
	 * @param fecha La fecha a formatear.
	 * @return Una cadena representando la etiqueta de fecha.
	 */
	@SuppressWarnings("deprecation")
	private String getEtiquetaFecha(LocalDate fecha) {
		LocalDate hoy = LocalDate.now();
		if (fecha.equals(hoy)) return "Hoy";
		if (fecha.equals(hoy.minusDays(1))) return "Ayer";
		if (fecha.isAfter(hoy.minusDays(7))) {
			return fecha.format(DateTimeFormatter.ofPattern("EEEE", new Locale("es", "ES")));
		}
		return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
}
