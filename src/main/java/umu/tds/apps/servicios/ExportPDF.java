package umu.tds.apps.servicios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
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

			PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

			Paragraph titulo = new Paragraph("Historial de conversación con " + contacto.getNombre())
				.setFont(fontBold)
				.setTextAlignment(TextAlignment.CENTER)
				.setFontSize(18)
				.setMarginBottom(10);
			document.add(titulo);

			Paragraph intro = new Paragraph("Gracias por confiar en AppChat y apostar por el modo Premium. " +
				"Nos alegra acompañarte en tu experiencia de comunicación.")
				.setTextAlignment(TextAlignment.CENTER)
				.setFontSize(12)
				.setMarginBottom(30);
			document.add(intro);

			List<Mensaje> mensajes = new ArrayList<>(contacto.getMensajes());
			mensajes.sort(Comparator.comparing(Mensaje::getMomentoEnvio));

			LocalDate ultimaFecha = null;
			DateTimeFormatter horaFormat = DateTimeFormatter.ofPattern("HH:mm");

			for (Mensaje mensaje : mensajes) {
				LocalDate fechaMensaje = mensaje.getMomentoEnvio().toLocalDate();

				if (ultimaFecha == null || !fechaMensaje.equals(ultimaFecha)) {
					String etiquetaFecha = getEtiquetaFecha(fechaMensaje);
					Paragraph separador = new Paragraph(etiquetaFecha)
						.setFont(fontBold)
						.setTextAlignment(TextAlignment.CENTER)
						.setFontSize(10)
						.setMarginTop(10)
						.setMarginBottom(10);
					document.add(separador);
					ultimaFecha = fechaMensaje;
				}

				Div burbuja = new Div();
				Paragraph texto = new Paragraph(mensaje.getTexto())
					.setFontSize(12)
					.setMargin(0);

				Paragraph hora = new Paragraph(mensaje.getMomentoEnvio().format(horaFormat))
					.setFontSize(8)
					.setMarginTop(5)
					.setMarginBottom(0)
					.setTextAlignment(TextAlignment.RIGHT);

				burbuja.add(texto);
				burbuja.add(hora);
				burbuja.setWidth(UnitValue.createPercentValue(60));
				burbuja.setPadding(10);
				burbuja.setMarginBottom(10);

				if (mensaje.getTipo() == TipoMensaje.ENVIADO) {
					burbuja.setHorizontalAlignment(HorizontalAlignment.RIGHT);
				} else {
					burbuja.setHorizontalAlignment(HorizontalAlignment.LEFT);
				}

				document.add(burbuja);
			}

			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private String getEtiquetaFecha(LocalDate fecha) {
	    return fecha.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
	}
	
}
