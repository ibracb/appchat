package umu.tds.apps.servicios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
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

import umu.tds.apps.dominio.ContactoIndividual;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.TipoMensaje;
import umu.tds.apps.dominio.Usuario;
import umu.tds.apps.servicios.descargas.FactoriaProveedorRutaDescargas;

/**
 * Clase que tiene la responsabilidad de generar los PDF correspondientes en la aplicación.
 */
public enum ExportPDF {
	
	/**
	 * Punto de acceso global al único generador de ficheros PDF existente en la aplicación.
	 */
	INSTANCE;
	
	private static final int TITULO_TAMAÑO = 32;
	private static final String INICIO_NOMBRE_PDF = "AppChat_";
	private static final String PDF_EXTENSION = ".pdf";
	private static final String TITULO_PDF = "Contactos añadidos en AppChat de ";
	private static final String MENSAJE_INICIAL = "¡Hola! Antes de nada, le agradecemos su máxima confianza depositada en AppChat. "
			+ "En primer lugar, he aquí un listado de sus contactos individuales, con sus respectivos números de teléfono móvil.";
	private static final String MENSAJE_SEGUNDO = "En segundo lugar, le mostramos un listado de sus grupos, con cada uno de los integrantes que añadió.";
	
	/**
	 * Constructor privado de ExportPDF.
	 */
	private ExportPDF() {}
	
	public boolean createPdfListado(Usuario usuario) {
		File fichero = new File(FactoriaProveedorRutaDescargas.INSTANCE.getProveedor().getRutaDescargas(),
				INICIO_NOMBRE_PDF + usuario.getNombre() + PDF_EXTENSION);
		try (PdfWriter pdfWriter = new PdfWriter(fichero)) {
			PdfDocument pdfDocument = new PdfDocument(pdfWriter);
			Document document = new Document(pdfDocument);
			PdfFont fuenteTitulo = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
			Paragraph titulo = new Paragraph(TITULO_PDF + usuario.getNombre())
					.setFont(fuenteTitulo)
					.setFontSize(TITULO_TAMAÑO)
					.setTextAlignment(TextAlignment.CENTER);
			document.add(titulo);
			Paragraph parrafoInicial = new Paragraph(MENSAJE_INICIAL);
			document.add(parrafoInicial);
			AtomicInteger contador1 = new AtomicInteger(1);
			usuario.getContactosIndividuales().forEach(contacto -> {
				Paragraph parrafo = new Paragraph(contador1.getAndIncrement() + ". " + contacto.getNombre() + ": " + contacto.getMovil());
				document.add(parrafo);
			});
			Paragraph parrafoGrupos = new Paragraph(MENSAJE_SEGUNDO);
			document.add(parrafoGrupos);
			AtomicInteger contador2 = new AtomicInteger(1);
			usuario.getGrupos().forEach(grupo -> {
				Paragraph parrafo = new Paragraph(contador2.getAndIncrement() + ". " + grupo.getNombre() + ":\n");
				grupo.getMiembros().forEach(miembro -> {
					parrafo.add("  - " + miembro.getNombre() + ": " + miembro.getMovil() + "\n");
				});
				document.add(parrafo);
			});
			document.close();
			pdfDocument.close();		
			return true;
		} catch(FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean createPdfChat(ContactoIndividual contacto) {
		File fichero = new File(FactoriaProveedorRutaDescargas.INSTANCE.getProveedor().getRutaDescargas(),
				"Historial_" + contacto.getNombre() + ".pdf");

		try (PdfWriter writer = new PdfWriter(fichero);
			 PdfDocument pdfDoc = new PdfDocument(writer);
			 Document document = new Document(pdfDoc)) {

			// Configurar márgenes más pequeños para aprovechar mejor el espacio
			document.setMargins(15, 15, 15, 15);

			// Colores WhatsApp
			Color colorVerde = new DeviceRgb(37, 211, 102);		// Verde WhatsApp
			Color colorGris = new DeviceRgb(233, 237, 239);		// Gris claro para mensajes recibidos
			Color colorFondo = new DeviceRgb(240, 242, 245);	// Fondo general
			Color colorTextoSecundario = new DeviceRgb(134, 142, 150);
			Color colorBordeRedondeado = new DeviceRgb(220, 224, 226);

			// Fuentes
			PdfFont fontRegular = PdfFontFactory.createFont(StandardFonts.HELVETICA);
			PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

			// Fondo de la página
			Rectangle pageSize = pdfDoc.getDefaultPageSize();
			PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage().newContentStreamBefore(), pdfDoc.getFirstPage().getResources(), pdfDoc);
			canvas.setFillColor(colorFondo)
				  .rectangle(0, 0, pageSize.getWidth(), pageSize.getHeight())
				  .fill();

			// Header estilo WhatsApp
			Div header = new Div();
			header.setBackgroundColor(colorVerde)
				  .setPadding(15)
				  .setMarginBottom(10);

			Paragraph nombreContacto = new Paragraph(contacto.getNombre())
					.setFont(fontBold)
					.setFontColor(ColorConstants.WHITE)
					.setFontSize(18)
					.setMargin(0);

			Paragraph subtitulo = new Paragraph("Historial de conversación")
					.setFont(fontRegular)
					.setFontColor(ColorConstants.WHITE)
					.setFontSize(12)
					.setMargin(0)
					.setOpacity(0.8f);

			header.add(nombreContacto);
			header.add(subtitulo);
			document.add(header);

			// Mensaje de agradecimiento más discreto
			Paragraph intro = new Paragraph("💬 Exportado desde AppChat Premium")
					.setFont(fontRegular)
					.setTextAlignment(TextAlignment.CENTER)
					.setFontSize(10)
					.setFontColor(colorTextoSecundario)
					.setMarginBottom(20);
			document.add(intro);

			// Procesar mensajes
			List<Mensaje> mensajes = new ArrayList<>(contacto.getMensajes());
			mensajes.sort(Comparator.comparing(Mensaje::getMomentoEnvio));

			LocalDate ultimaFecha = null;
			DateTimeFormatter horaFormat = DateTimeFormatter.ofPattern("HH:mm");

			for (Mensaje mensaje : mensajes) {
				LocalDate fechaMensaje = mensaje.getMomentoEnvio().toLocalDate();

				// Separador de fecha estilo WhatsApp
				if (ultimaFecha == null || !fechaMensaje.equals(ultimaFecha)) {
					String etiquetaFecha = getEtiquetaFecha(fechaMensaje);
					
					Div fechaContainer = new Div();
					fechaContainer.setTextAlignment(TextAlignment.CENTER)
								 .setMarginTop(15)
								 .setMarginBottom(15);

					Paragraph separadorFecha = new Paragraph(etiquetaFecha)
							.setFont(fontRegular)
							.setFontSize(11)
							.setFontColor(ColorConstants.GRAY)
							.setBackgroundColor(ColorConstants.WHITE)
							.setPadding(6)
							.setPaddingLeft(12)
							.setPaddingRight(12)
							.setBorder(new SolidBorder(colorBordeRedondeado, 1))
							.setMargin(0);

					fechaContainer.add(separadorFecha);
					document.add(fechaContainer);
					ultimaFecha = fechaMensaje;
				}

				// Crear burbuja de mensaje
				boolean esMensajeEnviado = (mensaje.getTipo() == TipoMensaje.ENVIADO);
				
				Div contenedorMensaje = new Div();
				contenedorMensaje.setWidth(UnitValue.createPercentValue(100))
								.setMarginBottom(8);

				Div burbuja = new Div();
				burbuja.setWidth(UnitValue.createPercentValue(75))
					   .setPadding(12)
					   .setPaddingTop(8)
					   .setPaddingBottom(8);

				if (esMensajeEnviado) {
					// Mensaje enviado - verde, alineado a la derecha
					burbuja.setBackgroundColor(colorVerde)
						   .setHorizontalAlignment(HorizontalAlignment.RIGHT);
					contenedorMensaje.setTextAlignment(TextAlignment.RIGHT);
				} else {
					// Mensaje recibido - gris, alineado a la izquierda
					burbuja.setBackgroundColor(colorGris)
						   .setHorizontalAlignment(HorizontalAlignment.LEFT);
					contenedorMensaje.setTextAlignment(TextAlignment.LEFT);
				}

				// Texto del mensaje
				Paragraph textoMensaje = new Paragraph(mensaje.getTexto())
						.setFont(fontRegular)
						.setFontSize(12)
						.setFontColor(esMensajeEnviado ? ColorConstants.WHITE : ColorConstants.BLACK)
						.setMargin(0)
						.setMarginBottom(4);

				// Hora del mensaje - manejo robusto de diferentes tipos
				String horaTexto = "00:00"; // valor por defecto
				
				try {
					Object momentoEnvio = mensaje.getMomentoEnvio();
				
					if (momentoEnvio != null) {
						// Diferentes casos según el tipo de dato
						if (momentoEnvio instanceof LocalDateTime) {
							LocalDateTime ldt = (LocalDateTime) momentoEnvio;
							horaTexto = ldt.format(horaFormat);
						} 
						else if (momentoEnvio instanceof java.sql.Timestamp) {
							java.sql.Timestamp ts = (java.sql.Timestamp) momentoEnvio;
							LocalDateTime ldt = ts.toLocalDateTime();
							horaTexto = ldt.format(horaFormat);
						}
						else if (momentoEnvio instanceof java.util.Date) {
							java.util.Date date = (java.util.Date) momentoEnvio;
							LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), java.time.ZoneId.systemDefault());
							horaTexto = ldt.format(horaFormat);
						}
						else if (momentoEnvio instanceof java.time.Instant) {
							java.time.Instant instant = (java.time.Instant) momentoEnvio;
							LocalDateTime ldt = LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault());
							horaTexto = ldt.format(horaFormat);
						}
						else {
							// Intentar parsear como string
							String strFecha = momentoEnvio.toString();
							if (strFecha.contains(":")) {
								// Extraer solo la parte de la hora si existe
								String[] partes = strFecha.split(" ");
								for (String parte : partes) {
									if (parte.contains(":") && parte.length() >= 5) {
										horaTexto = parte.substring(0, 5); // HH:mm
										break;
									}
								}
							}
						}
					}
				} catch (Exception e) {
					return false;
				}
				
				Paragraph horaMensaje = new Paragraph(horaTexto)
						.setFont(fontRegular)
						.setFontSize(9)
						.setFontColor(esMensajeEnviado ? ColorConstants.WHITE : colorTextoSecundario)
						.setTextAlignment(TextAlignment.RIGHT)
						.setMargin(0)
						.setOpacity(0.7f);

				// Añadir check marks para mensajes enviados
				if (esMensajeEnviado) {
					horaMensaje.add(new Tab()).add("✓✓");
				}

				burbuja.add(textoMensaje);
				burbuja.add(horaMensaje);
				
				contenedorMensaje.add(burbuja);
				document.add(contenedorMensaje);
			}

			// Footer discreto
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

	@SuppressWarnings("deprecation")
	private String getEtiquetaFecha(LocalDate fecha) {
		LocalDate hoy = LocalDate.now();
		LocalDate ayer = hoy.minusDays(1);
		
		if (fecha.equals(hoy)) {
			return "Hoy";
		} else if (fecha.equals(ayer)) {
			return "Ayer";
		} else if (fecha.isAfter(hoy.minusDays(7))) {
			// Mostrar día de la semana para la última semana
			return fecha.format(DateTimeFormatter.ofPattern("EEEE", new Locale("es", "ES")));
		} else {
			return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
	}
	
}
