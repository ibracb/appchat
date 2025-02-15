package umu.tds.apps.dominio;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import umu.tds.apps.utils.Utils;

/**
 * Clase que define un contacto de un usuario.
 */
public class Contacto implements Comparable<Contacto> {

	/**
	 * Identificador del contacto.
	 */
	private int id;
	
	/**
	 * Nombre del contacto.
	 */
	private String nombre;
	
	/**
	 * Usuario que crea el contacto.
	 */
	private Usuario usuario;
	
	/**
	 * Mensajes intercambiados entre el usuario y el contacto.
	 */
	private Set<Mensaje> mensajes;
	
	/**
	 * Constructor de Contacto, a partir de un nombre y un usuario.
	 * @param nombre -  Nombre a asignar al contacto.
	 * @param usuario - Usuario que crea y maneja el contacto.
	 */
	public Contacto(String nombre, Usuario usuario) {
		this.id = Utils.ID_DEFAULT;
		this.nombre = nombre;
		this.usuario = usuario;
		this.mensajes = new TreeSet<Mensaje>();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contacto other = (Contacto) obj;
		return Objects.equals(nombre, other.nombre);
	}
	
	/**
	 * Añade un mensaje a la lista de mensajes intercambiados entre usuario y contacto.
	 * @param mensaje - El mensaje a añadir
	 */
	public void addMensaje(Mensaje mensaje) {
		mensajes.add(mensaje);
	}
	
	/**
	 * Devuelve el identificador que tiene el contacto.
	 * @return el id asociado al contacto.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Establece un id al contacto.
	 * @param id - Identificador a establecer al contacto.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Devuelve el nombre introducido al contacto.
	 * @return el nombre del contacto.
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Establece un nombre al contacto.
	 * @param nombre - Nombre a establecer al contacto.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Devuelve el usuario que gestiona el contacto.
	 * @return el usuario correspondiente.
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	
	/**
	 * Establece un usuario al contacto.
	 * @param usuario - usuario a asociar al contacto.
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * Devuelve los mensajes que el usuario ha intercambiado con el contacto.
	 * @return los mensajes intercambiados.
	 */
	public Set<Mensaje> getMensajes() {
		return Collections.unmodifiableSet(mensajes);
	}
	
	/**
	 * Establece una determinada colección de mensajes al contacto, intercambiados con el usuario.
	 * @param mensajes - Colección de mensajes a asociar al contacto.
	 */
	public void setMensajes(Set<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	@Override
	public int compareTo(Contacto o) {
		return this.getNombre().compareTo(o.getNombre());
	}
	
}
