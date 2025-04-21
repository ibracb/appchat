package umu.tds.apps.repositorios;

import java.time.Month;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.ContactoIndividual;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.TipoMensaje;
import umu.tds.apps.dominio.Usuario;
import umu.tds.apps.persistencia.DAOException;
import umu.tds.apps.persistencia.FactoriaDAO;
import umu.tds.apps.persistencia.UsuarioDAO;
import umu.tds.apps.utils.Utils;

/**
 * Punto global que colecciona a todos los usuarios del sistema AppChat.
 */
public enum RepositorioUsuarios {
	
	/**
	 * Instancia global que representa el repositorio de usuarios.
	 */
	INSTANCE;
	
	/**
	 * Tabla de dispersión que asocia un usuario a un ID.
	 */
	private Map<Integer, Usuario> usuariosPorID;
	
	/**
	 * Tabla de dispersión que asocia un usuario a un número móvil.
	 */
	private Map<String, Usuario> usuariosPorMovil;
	
	/**
	 * Campo donde acceder a la FactoriaDAO global del sistema.
	 */
	private FactoriaDAO factoriaDAO;
	
	/**
	 * Acceso al adaptador de usuarios.
	 */
	private UsuarioDAO adaptadorUsuarioDAO;
	
	/**
	 * Constructor privado de RepositorioUsuarios.
	 */
	private RepositorioUsuarios() {
		try {
			factoriaDAO = FactoriaDAO.getInstance();
			adaptadorUsuarioDAO = factoriaDAO.getUsuarioDAO();
			usuariosPorID = new HashMap<Integer, Usuario>();
			usuariosPorMovil = new HashMap<String, Usuario>();
			loadRepositorioUsuarios();
		}
		catch(DAOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Devuelve una colección con todos los usuarios registrados en el sistema.
	 * @return todos los usuarios de AppChat.
	 */
	public Set<Usuario> getUsuarios() {
		return Collections.unmodifiableSet(new HashSet<>(usuariosPorID.values()));
	}
	
	/**
	 * Busca un usuadrio dado un id.
	 * @param id - Identificador del usuario a encontrar.
	 * @return el usuario.
	 */
	public Usuario findUsuario(int id) {
		return usuariosPorID.get(id);
	}
	
	/**
	 * Busca un usuario dado un número de teléfono móvil.
	 * @param movil - Móvil del usuario a encontrar.
	 * @return el usuario.
	 */
	public Usuario findUsuario(String movil) {
		return usuariosPorMovil.get(movil);
	}
	
	/**
	 * Busca un contacto individual dado el móvil del usuario y del contacto individual.
	 * @param movilUsuario - el móvil del usuario.
	 * @param movilContacto - el móvil del contacto.
	 * @return el contacto individual.
	 */
	public ContactoIndividual findContactoIndividual(String movilUsuario, String movilContacto) {
		return findUsuario(movilUsuario).getContactos().stream()
				.filter(contacto -> contacto instanceof ContactoIndividual)
				.map(contacto -> (ContactoIndividual) contacto)
				.findFirst()
				.orElse(null);
	}
	
	/**
	 * Recupera todos los usuarios para trabajar con ellos en memoria.
	 * @throws DAOException - El método lanza una DAOException.
	 */
	private void loadRepositorioUsuarios() throws DAOException {
		adaptadorUsuarioDAO.getAll().forEach(usuario -> {
			usuariosPorID.put(usuario.getId(), usuario);
			usuariosPorMovil.put(usuario.getMovil(), usuario);
		});
	}
	
	/**
	 * Añadir un usuario al repositorio.
	 * @param usuario - usuario a añadir.
	 */
	public boolean addUsuario(Usuario usuario) {
		if (usuario == null) {
	        return false;
	    }
	    if (usuariosPorID.containsKey(usuario.getId()) || usuariosPorMovil.containsKey(usuario.getMovil())) {
	        return false;
	    }
	    usuariosPorID.put(usuario.getId(), usuario);
	    usuariosPorMovil.put(usuario.getMovil(), usuario);
	    return true;
	}
	
	/**
	 * Eliminar un usuario del repositorio.
	 * @param usuario - usuario a eliminar.
	 */
	public boolean removeUsuario(Usuario usuario) {
		if(!usuariosPorID.containsKey(usuario.getId()) || !usuariosPorMovil.containsKey(usuario.getMovil())) {
			return false;
		}
		usuariosPorID.remove(usuario.getId());
		usuariosPorMovil.remove(usuario.getMovil());
		return true;
	}
	
	/**
	 * Devuelve todos los contactos de un determinado usuario.
	 * @param usuario - Usuario del que obtener sus contactos.
	 * @return los contactos
	 */
	public Set<Contacto> getAllContactos(Usuario usuario){
		return usuario.getContactos();
	}
	
	/**
	 * Dado un número de teléfono móvil, devuelve los mensajes en los que el emisor o el receptor coincide con ese mismo teléfono móvil.
	 * @param contacto - el contacto de donde extraer los mensajes.
	 * @param texto - El texto que deben contener los mensajes.
	 * @return los mensajes coincidentes.
	 */
	public Set<Mensaje> getMensajesPorTexto(Contacto contacto, String texto){
		return contacto.getMensajes().stream()
			.filter(mensaje -> mensaje.getTexto().contains(texto))
			.collect(Collectors.toCollection(TreeSet::new));
	}
	
	/**
	 * Dada una fecha, devuelve los mensajes que un contacto intercambió con el usuario en esa misma fecha.
	 * @param contacto - el contacto de donde extraer los mensajes.
	 * @param dia - el día coincidente.
	 * @param mes - el mes coincidente.
	 * @param año - el año coincidente.
	 * @return los mensajes coincidentes.
	 */
	public Set<Mensaje> getMensajesPorFecha(Contacto contacto, int dia, Month mes, int año) {
		return contacto.getMensajes().stream()
				.filter(mensaje -> mensaje.getMomentoEnvio().getDayOfYear()==dia && mensaje.getMomentoEnvio().getMonth().equals(mes)
					&& mensaje.getMomentoEnvio().getYear()==año)
				.collect(Collectors.toCollection(TreeSet::new));
	}
	
	/**
	 * Devuelve los mensajes que el usuario envió un contacto en el mes actual.
	 * @param contacto - el contacto que recibe los mensajes.
	 * @return la cantidad de mensajes enviados.
	 */
	public int getSubTotalMensajesEnviadosUltimoMes(Contacto contacto) {
		return (int) contacto.getMensajes().stream()
				.filter(mensaje -> mensaje.getMomentoEnvio().getMonth().equals(Utils.FECHA_ACTUAL.getMonth())
						&& mensaje.getMomentoEnvio().getYear()==Utils.FECHA_ACTUAL.getYear() && mensaje.getTipo().equals(TipoMensaje.ENVIADO))
				.count();
	}
	
	/**
	 * Dado un usuario, busca aquellos mensajes que contienen un texto.
	 * @param usuario - el usuario donde filtrar los mensajes.
	 * @param texto - el texto que deben contener los mensajes.
	 * @return los mensajes coincidentes.
	 */
	public Set<Mensaje> searchMensajesPorTexto(Usuario usuario, String texto) {
		return getAllContactos(usuario).stream()
				.flatMap(contacto -> getMensajesPorTexto(contacto, texto).stream())
				.collect(Collectors.toCollection(TreeSet::new));
	}
	
	/**
	 * Dado un usuario, busca aquellos mensajes cuyo emisor o receptor es un contacto con el nombre pasado como asrgumento.
	 * @param usuario - usuario del que obtener los mensajes.
	 * @param nombre - nombre del contacto que nos intersa.
	 * @return los mensajes coincidentes con el patrón indicado.
	 */
	public Set<Mensaje> searchMensajesPorNombreContacto(Usuario usuario, String nombre) {
		return getAllContactos(usuario).stream()
				.filter(contacto -> contacto.getNombre().equals(nombre))
				.flatMap(contacto -> contacto.getMensajes().stream())
				.collect(Collectors.toCollection(TreeSet::new));
	}
	
	/**
	 * Dado un usuario, busca aquellos mensaje donde el emisor o el receptor tiene un determinado número de teléfono móvil.
	 * @param usuario - el usuario del que extraer los mensajes.
	 * @param movil - teléfono móvil del emisor o receptor.
	 * @return los mensajes que cumplen.
	 */
	public Set<Mensaje> searchMensajesPorMovil(Usuario usuario, String movil) {
		return usuario.getContactos().stream()
				.filter(contacto -> contacto instanceof ContactoIndividual)
				.map(contacto -> (ContactoIndividual) contacto)
				.filter(contacto -> contacto.getMovil().equals(movil))
				.flatMap(contacto -> contacto.getMensajes().stream())
				.collect(Collectors.toCollection(TreeSet::new));
	}
	
	/**
	 * Busca todos aquellos mensajes en los que participa un usuario, y se cumple una determinada fecha.
	 * @param usuario - el usuario donde filtrar.
	 * @param dia - dia coincidente.
	 * @param mes - mes coincidente.
	 * @param año - año coincidente.
	 * @return los mensajes que cumplen con el patrón
	 */
	public Set<Mensaje> searchMensajesPorFecha(Usuario usuario, int dia, Month mes, int año) {
		return getAllContactos(usuario).stream()
				.flatMap(contacto -> getMensajesPorFecha(contacto, dia, mes, año).stream())
				.collect(Collectors.toCollection(TreeSet::new));
	}
	
	/**
	 * Devuelve cuántos mensajes un usuario envió en el mes actual.
	 * @param usuario - el usuario que nos interesa conocer la información.
	 * @return la cantidad todtal de mensajes.
	 */
	public int getTotalMensajesEnviadosUltimoMes(Usuario usuario) {
		return getAllContactos(usuario).stream()
				.map(contacto -> getSubTotalMensajesEnviadosUltimoMes(contacto))
				.reduce(0, Integer::sum);
	}
	
}
