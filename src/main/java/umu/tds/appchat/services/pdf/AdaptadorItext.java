package umu.tds.appchat.services.pdf;

import umu.tds.appchat.models.Mensaje;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AdaptadorItext implements IGeneradorPDF {

	@Override
	public boolean exportarConversacion(List<Mensaje> mensajes, String ruta, String nombreContacto) {

        // Ordenar los mensajes por fecha (de más antiguo a más reciente)
        List<Mensaje> mensajesOrdenados = mensajes.stream()
                .sorted((m1, m2) -> m1.getFechaHora().compareTo(m2.getFechaHora()))  // Ordenar por fecha
                .collect(Collectors.toList());

        // Crear el documento PDF
        Document document = new Document();
        try {
            // Crear el escritor de PDF (PdfWriter)
        	
            PdfWriter.getInstance(document, new FileOutputStream(ruta));
            
            // Abrir el documento
            document.open();
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph titulo = new Paragraph("Historial de mensajes - " + nombreContacto + "\n", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);

            document.add(titulo);

            // Añadir los mensajes al PDF, cada mensaje en una línea
            for (Mensaje mensaje : mensajesOrdenados) {
                String contenidoMensaje = String.format("%s (%s): %s", 
                        mensaje.getEmisor().getNombre(), 
                        mensaje.getFechaHora().toLocalDate().toString(),
                        mensaje.getTexto());
                
                // Añadir el mensaje al documento como una nueva frase
                document.add(new Phrase(contenidoMensaje + "\n"));
            }
            document.close();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }

		
}
