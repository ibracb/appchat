package umu.tds.apps.repositorios;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.ContactoIndividual;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.Usuario;
import umu.tds.apps.persistencia.DAOException;
import umu.tds.apps.persistencia.FactoriaDAO;
import umu.tds.apps.persistencia.UsuarioDAO;

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
	
	public ContactoIndividual findContactoIndividual(String movilUsuario, String movilContacto) {
		Usuario usuario = findUsuario(movilUsuario);
		if (usuario == null) return null;

		return usuario.getContactos().stream()
				.filter(contacto -> contacto instanceof ContactoIndividual)
				.map(contacto -> (ContactoIndividual) contacto)
				.filter(contacto -> movilContacto.equals(contacto.getMovil()))
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
	public void addUsuario(Usuario usuario) {
	    usuariosPorID.put(usuario.getId(), usuario);
	    usuariosPorMovil.put(usuario.getMovil(), usuario);
	}
	
	/**
	 * Eliminar un usuario del repositorio.
	 * @param usuario - usuario a eliminar.
	 */
	public void removeUsuario(Usuario usuario) {
		usuariosPorID.remove(usuario.getId());
		usuariosPorMovil.remove(usuario.getMovil());
	}
	
	/**
	 * Devuelve todos los contactos de un determinado usuario.
	 * @param usuario - Usuario del que obtener sus contactos.
	 * @return los contactos
	 */
	public Set<Contacto> getAllContactos(Usuario usuario){
		return usuario.getContactos();
	}
	
	public ContactoIndividual findContacto(Usuario usuario, Mensaje mensaje) {
		return (ContactoIndividual) usuario.getContactos().stream()
				.filter(contacto -> contacto instanceof ContactoIndividual && contacto.getMensajes().contains(mensaje))
				.findFirst()
				.get();
	}
	
}
