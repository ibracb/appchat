package umu.tds.apps.controlador;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.ContactoIndividual;
import umu.tds.apps.dominio.Grupo;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.TipoMensaje;
import umu.tds.apps.dominio.Usuario;
import umu.tds.apps.persistencia.ContactoIndividualDAO;
import umu.tds.apps.persistencia.DAOException;
import umu.tds.apps.persistencia.FactoriaDAO;
import umu.tds.apps.persistencia.GrupoDAO;
import umu.tds.apps.persistencia.MensajeDAO;
import umu.tds.apps.persistencia.UsuarioDAO;
import umu.tds.apps.repositorios.RepositorioUsuarios;

/**
 * Coordina la lógica de la aplicación, y maneja los eventos capturados por la interfaz de usuario.
 */
public enum AppChat {
	
	/**
	 * Punto de acceso global al Controlador AppChat.
	 */
	INSTANCE;
	
	/**
	 * Campo para acceder al adaptador de usuarios para TDS.
	 */
	private UsuarioDAO adaptadorUsuario;
	
	/**
	 * Campo para acceder al adaptador de contactos individuales para TDS.
	 */
	private ContactoIndividualDAO adaptadorContactoIndividual;
	
	/**
	 * Campo para acceder al adaptador de grupos para TDS.
	 */
	private GrupoDAO adaptadorGrupo;
	
	/**
	 * Campo para acceder al adaptador de mensajes para TDS.
	 */
	private MensajeDAO adaptadorMensaje;
	
	/**
	 * Campo para acceder a la instancia global del repositorio de usuarios .
	 */
	private RepositorioUsuarios repositorioUsuarios;
	
	/**
	 * Usuario actualmente manejado por el controlador.
	 */
	private Usuario usuarioActual;
	
	/**
	 * Contacto individual actualmente manejado por el controlador.
	 */
	private ContactoIndividual contactoIndividualActual;
	
	/**
	 * Grupo actualmente manejado por el controlador.
	 */
	private Grupo grupoActual;
	
	/**
	 * Constructor privado del controlador AppChat.
	 */
	private AppChat() {
		initializeAdaptadores();
		initializeRepositorioUsuarios();
	}
	
	/**
	 * Registra un nuevo usuario en el sistema de AppChat.
	 * @param nombre - El nombre del usuario a registrar.
	 * @param fechaNacimiento - La fecha de nacimiento del usuario a registrar.
	 * @param email - El email del usuario a registrar.
	 * @param imagen - La foto de perfil del usuario a registrar.
	 * @param movil - El número de teléfono móvil del usuario a registrar.
	 * @param contraseña - La contraseña del usuario a registrar.
	 * @param saludo - El saludo del usuario a registrar.
	 */
	public boolean registrarUsuario(String nombre, LocalDate fechaNacimiento, String email, String imagen, String movil, String contraseña, String saludo) {
		if(repositorioUsuarios.findUsuario(movil) != null) {
			return false;
		}
		Usuario usuario = new Usuario(nombre, fechaNacimiento, email, imagen, movil, contraseña, saludo);
		return adaptadorUsuario.create(usuario) && repositorioUsuarios.addUsuario(usuario);
	}
	
	/**
	 * Inicia sesión a un usuario.
	 * @param movil - El teléfono móvil del usuario a iniciar sesión.
	 * @param contraseña - La contraseña del usuario a iniciar sesión.
	 */
	public boolean loginUsuario(String movil, String contraseña) {
		Usuario usuario = repositorioUsuarios.findUsuario(movil);
		if(usuario != null && usuario.getContraseña().equals(contraseña)) {
			usuarioActual = usuario;
			return true;
		}
		return false;
	}
	
	/**
	 * Borra un usuario de la base de datos y del repositorio.
	 * @param usuario - Usuario a borrar.
	 */
	public void borrarUsuario(Usuario usuario) {
		adaptadorUsuario.delete(usuario);
		repositorioUsuarios.removeUsuario(usuario);
	}
	
	/**
	 * Llamada al adaptador de contactos individuales para registrar un contacto individual en la base de datos.
	 * @param nombre - Nombre del contacto a registrar.
	 * @param movilContacto - Móvil de contacto a registrar.
	 */
	public boolean registrarContactoIndividual(String nombre, String movilContacto) {
		if(usuarioActual.getContactoIndividual(movilContacto) != null) {
			return false;
		}
		contactoIndividualActual.setNombre(nombre);
		contactoIndividualActual.setUsuario(repositorioUsuarios.findUsuario(movilContacto));
		contactoIndividualActual.setMensajes(new TreeSet<Mensaje>());
		adaptadorContactoIndividual.create(contactoIndividualActual);
		usuarioActual.addContacto(nombre, movilContacto);
		adaptadorUsuario.update(usuarioActual);
		return true;
	}
	
	/**
	 * Llamada al adaptador de contactos individuales para borrar un contacto individual de la base de datos.
	 * @param contacto - Contacto individual a borrar.
	 */
	public void borrarContactoIndividual(ContactoIndividual contacto) {
		adaptadorContactoIndividual.delete(contacto);
		adaptadorUsuario.update(repositorioUsuarios.findUsuario(contacto.getUsuario().getMovil()));
	}
	
	/**
	 * Llamada al adaptador de grupos para registrar un grupo en la base de datos.
	 * @param movilUsuario - Móvil del usuario que crea el grupo.
	 * @param nombre - Nombre del grupo.
	 * @param imagen - Foto de perfil del grupo.
	 * @param miembros - Miembros añadidos al crear el grupo.
	 */
	public void registrarGrupo(String movilUsuario, String nombre, String imagen, ContactoIndividual... miembros) {
		Usuario usuario = repositorioUsuarios.findUsuario(movilUsuario);
		grupoActual.setNombre(nombre);
		grupoActual.setImagen(imagen);
		grupoActual.setMensajes(new TreeSet<Mensaje>());
		adaptadorGrupo.create(grupoActual);
		usuario.createGrupo(nombre, imagen, miembros);
		adaptadorUsuario.update(usuario);
	}
	
	/**
	 * Llamada al adaptador de grupos para que borre un grupo de la base de datos.
	 * @param grupo - Grupo a borrar.
	 */
	public void borrarGrupo(Grupo grupo) {
		adaptadorGrupo.delete(grupo);
		grupo.getMiembros().forEach(miembro -> {
			adaptadorUsuario.update(miembro.getUsuario());
		});
	}
	
	/**
	 * Método para registrar en la base de datos un mensaje enviado a un contacto individual.
	 * @param emisor - Móvil del emisor del mensaje.
	 * @param receptor - Móvil del receptor de mensaje.
	 * @param texto - Texto empleado en el mensaje.
	 * @param emoticono - Emoticono empleado en el mensaje.
	 */
	public void registrarMensajeContacto(ContactoIndividual contacto, String texto, int emoticono, TipoMensaje tipo) {
		Mensaje mensaje = contacto.nuevoMensaje(texto, emoticono, tipo);
		adaptadorMensaje.create(mensaje);
		adaptadorContactoIndividual.update(contacto);
		Usuario usuarioReceptor = contacto.getUsuario();
		ContactoIndividual contactoInverso = usuarioReceptor.getContactoIndividual(usuarioActual.getMovil());
		if(contactoInverso == null) {
			contactoInverso = new ContactoIndividual(usuarioActual.getNombre(), usuarioReceptor, usuarioActual.getMovil());
			adaptadorContactoIndividual.create(contactoInverso);
			adaptadorUsuario.update(usuarioReceptor);
		}
		Mensaje mensajeRecibido = contactoInverso.nuevoMensaje(texto, emoticono, tipo);
		adaptadorMensaje.create(mensajeRecibido);
		adaptadorContactoIndividual.update(contactoInverso);
	}
	
	/**
	 * Método para registrar en la base de datos un mensaje enviado a un grupo.
	 * @param grupo - Grupo al que se envía el mensaje.
	 * @param texto - Texto empleado en el mensaje.
	 * @param emoticono - Emoticono empleado en el mensaje.
	 */
	public void registrarMensajeGrupo(Grupo grupo, String texto, int emoticono, TipoMensaje tipo) {
		Mensaje mensaje = grupo.nuevoMensaje(texto, emoticono, tipo);
		adaptadorMensaje.create(mensaje);
		adaptadorGrupo.update(grupo);
		grupo.getMiembros().forEach(miembro -> registrarMensajeContacto(miembro, texto, emoticono, tipo));
	}
	
	/**
	 * Llamada al adaptador de mensajes para borrar un mensaje de la base de datos.
	 * @param mensaje - Mensaje a borrar.
	 */
	public void borrarMensaje(Mensaje mensaje) {
		adaptadorMensaje.delete(mensaje);
		adaptadorContactoIndividual.update(contactoIndividualActual);
	}
	
	/**
	 * Inicializa los adaptadores, en particular para TDS.
	 */
	private void initializeAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstance(FactoriaDAO.DAO_TDS);
		}
		catch(DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorContactoIndividual = factoria.getContactoIndividualDAO();
		adaptadorGrupo = factoria.getGrupoDAO();
		adaptadorMensaje = factoria.getMensajeDAO();
	}
	
	/**
	 * Inicializa el repositorio de usuarios.
	 */
	private void initializeRepositorioUsuarios() {
		repositorioUsuarios = RepositorioUsuarios.INSTANCE;
	}
	
	/**
	 * Devuelve todos los usuarios almacenados en el repositorio.
	 * @return los usuarios que hay en el repositorio
	 */
	public Set<Usuario> getUsuarios() {
		return repositorioUsuarios.getUsuarios();
	}
	
	/**
	 * Devuelve el usuario que actualmente maneja el controlador.
	 * @return el usuario actual.
	 */
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	public Set<Contacto> getContactosIndividualesUsuarioActual(){
		return getUsuarioActual().getContactos().stream()
				.filter(contacto -> contacto instanceof ContactoIndividual)
				.collect(Collectors.toCollection(TreeSet::new));
	}
	
}
