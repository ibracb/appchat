package umu.tds.appchat.persistencia;

import java.util.Map;

import umu.tds.appchat.models.Usuario;

public interface IAdaptadorUsuarioDAO {
	
	public boolean registrarUsuario(Usuario usuario);

	public boolean modificarImagen(Usuario user);

	public Usuario recuperarUsuario(int id);
	
	public Usuario recuperarUsuarioTelefono(String tlf);

	Map<String,Usuario> recuperarTodosUsuarios();

	boolean addContactoIndividual(Usuario user);
	
	boolean añadirMensajeEnviado(Usuario user);

	boolean añadirMensajeRecibido(Usuario user);
	
	boolean actualizarGrupos(Usuario user); 



}
