package umu.tds.appchat.persistencia;

import java.util.List;

import umu.tds.appchat.models.Mensaje;

public interface IAdaptadorMensajeDAO {
	
	public boolean registrarMensaje(Mensaje mensaje);
	public Mensaje recuperarMensaje(int id);
	public List<Mensaje> recuperarTodasMensajes();

}
