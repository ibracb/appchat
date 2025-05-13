package umu.tds.apps.servicios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

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
	
	public boolean createPDF(Usuario usuario) {
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
			usuario.getContactosIndividuales().forEach(contacto -> {
				int contador = 0;
				Paragraph parrafo = new Paragraph(contador + contacto.getNombre() + ": " + contacto.getMovil());
				document.add(parrafo);
				contador++;
			});
			Paragraph parrafoGrupos = new Paragraph(MENSAJE_SEGUNDO);
			usuario.getGrupos().forEach(grupo -> {
				int contador = 0;
				Paragraph parrafo = new Paragraph(contador + grupo.getNombre() + ":\n");
				grupo.getMiembros().forEach(miembro -> {
					parrafo.add("\t- " + miembro.getNombre() + ": " + miembro.getMovil());
				});
				document.add(parrafo);
				contador++;
			});
			document.add(parrafoGrupos);
			document.close();
			pdfDocument.close();		
			return true;
		} catch(FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
	
}
