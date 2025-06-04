package umu.tds.appchat.services.pdf;

import java.util.List;

import umu.tds.appchat.models.Mensaje;

public interface IGeneradorPDF {
	
	public boolean exportarConversacion(List<Mensaje> mensajes, String ruta, String titulo);
}
