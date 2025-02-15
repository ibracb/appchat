package umu.tds.apps.controlador;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import umu.tds.apps.dominio.ContactoIndividual;
import umu.tds.apps.dominio.Grupo;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.RepositorioUsuarios;
import umu.tds.apps.dominio.TipoMensaje;
import umu.tds.apps.dominio.Usuario;
import umu.tds.apps.persistencia.ContactoIndividualDAO;
import umu.tds.apps.persistencia.DAOException;
import umu.tds.apps.persistencia.FactoriaDAO;
import umu.tds.apps.persistencia.GrupoDAO;
import umu.tds.apps.persistencia.MensajeDAO;
import umu.tds.apps.persistencia.UsuarioDAO;
import umu.tds.apps.utils.Utils;

/**
 * Coordina la lógica de la aplicación, y maneja los eventos capturados por la interfaz de usuario.
 */
public class AppChat {
	
	/**
	 * Punto de acceso global al Controlador AppChat.
	 */
	private static AppChat INSTANCE;
	
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
	 * Mensaje actualmente manejado por el controlador.
	 */
	private Mensaje mensajeActual;
	
	/**
	 * Constructor privado del controlador AppChat.
	 */
	private AppChat() {
		initializeAdaptadores();
		initializeRepositorioUsuarios();
	}
	
	/**
	 * Devuelve la única instancia que representa el controlador.
	 * @return la única instancia.
	 */
	public static AppChat getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AppChat();
		}
		return INSTANCE;
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
	public void registrarUsuario(String nombre, LocalDate fechaNacimiento, String email, String imagen, String movil, String contraseña, String saludo) {
		Usuario usuario = new Usuario(nombre, fechaNacimiento, email, imagen, movil, contraseña, saludo);
		adaptadorUsuario.create(usuario);
		repositorioUsuarios.addUsuario(usuario);
	}
	
	/**
	 * Inicia sesión a un usuario.
	 * @param movil - El teléfono móvil del usuario a iniciar sesión.
	 * @param contraseña - La contraseña del usuario a iniciar sesión.
	 */
	public void loginUsuario(String movil, String contraseña) {
		Usuario usuario = repositorioUsuarios.findUsuario(movil);
		if(usuario != null && usuario.getContraseña().equals(contraseña)) {
			usuarioActual = usuario;
		}
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
	 * @param movilUsuario - móvil del usuario que añade el contacto.
	 * @param nombre - Nombre del contacto a registrar.
	 * @param movilContacto - Móvil de contacto a registrar.
	 */
	public void registrarContactoIndividual(String movilUsuario, String nombre, String movilContacto) {
		Usuario usuario = repositorioUsuarios.findUsuario(movilUsuario);
		contactoIndividualActual.setNombre(nombre);
		contactoIndividualActual.setUsuario(usuario);
		contactoIndividualActual.setMovil(movilContacto);
		contactoIndividualActual.setMensajes(new TreeSet<Mensaje>());
		adaptadorContactoIndividual.create(contactoIndividualActual);
		usuario.addContacto(nombre, movilContacto);
		adaptadorUsuario.update(usuario);
	}
	
	/**
	 * Llamada al adaptador de contactos individuales para borrar un contacto individual de la base de datos.
	 * @param contacto - Contacto individual a borrar.
	 */
	public void borrarContactoIndividual(ContactoIndividual contacto) {
		adaptadorContactoIndividual.delete(contacto);
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
		grupoActual.setUsuario(usuario);
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
	}
	
	/**
	 * Método para registrar un mensaje con solo texto en la base de datos.
	 * @param emisor - Móvil del emisor del mensaje.
	 * @param receptor - Móvil del receptor de mensaje.
	 * @param texto - Texto empleado en el mensaje.
	 */
	public void registrarMensaje(String emisor, String receptor, String texto) {
		ContactoIndividual userEmisor = repositorioUsuarios.findContactoIndividual(receptor, emisor);
		ContactoIndividual userReceptor = repositorioUsuarios.findContactoIndividual(emisor, receptor);
		mensajeActual.setEmisor(userEmisor.getMovil());
		mensajeActual.setReceptor(userReceptor.getMovil());
		mensajeActual.setMomentoEnvio(Utils.FECHA_ACTUAL);
		mensajeActual.setTexto(texto);
		mensajeActual.setEmoticono(Mensaje.ICONO_NULL);
		mensajeActual.setTipo(TipoMensaje.ENVIADO);
		adaptadorMensaje.create(mensajeActual);
		userEmisor.addMensaje(mensajeActual);
		adaptadorContactoIndividual.update(userEmisor);
		mensajeActual.setTipo(TipoMensaje.RECIBIDO);
		adaptadorMensaje.create(mensajeActual);
		userReceptor.addMensaje(mensajeActual);
		adaptadorContactoIndividual.update(userReceptor);
	}
	
	/**
	 * Registra un mensaje con solo un emoticono.
	 * @param emisor - Móvil del emisor del mensaje.
	 * @param receptor - Móvil del receptor de mensaje.
	 * @param emoticono - Emoticono empleado en el mensaje.
	 */
	public void registrarMensaje(String emisor, String receptor, int emoticono) {
		ContactoIndividual userEmisor = repositorioUsuarios.findContactoIndividual(receptor, emisor);
		ContactoIndividual userReceptor = repositorioUsuarios.findContactoIndividual(emisor, receptor);
		mensajeActual.setEmisor(userEmisor.getMovil());
		mensajeActual.setReceptor(userReceptor.getMovil());
		mensajeActual.setMomentoEnvio(Utils.FECHA_ACTUAL);
		mensajeActual.setTexto(Mensaje.TEXTO_NULL);
		mensajeActual.setEmoticono(emoticono);
		mensajeActual.setTipo(TipoMensaje.ENVIADO);
		adaptadorMensaje.create(mensajeActual);
		userEmisor.addMensaje(mensajeActual);
		adaptadorContactoIndividual.update(userEmisor);
		mensajeActual.setTipo(TipoMensaje.RECIBIDO);
		adaptadorMensaje.create(mensajeActual);
		userReceptor.addMensaje(mensajeActual);
		adaptadorContactoIndividual.update(userReceptor);
	}
	
	/**
	 * Llamada al adaptador de mensajes para borrar un mensaje de la base de datos.
	 * @param mensaje - Mensaje a borrar.
	 */
	public void borrarMensaje(Mensaje mensaje) {
		adaptadorMensaje.delete(mensaje);
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
		repositorioUsuarios = RepositorioUsuarios.getInstance();
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
	
}
