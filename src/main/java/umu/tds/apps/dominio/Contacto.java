package umu.tds.apps.dominio;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import umu.tds.apps.utils.Utils;

/**
 * Clase que define un contacto de un usuario puede ser individual o grupo.
 */
public abstract class Contacto implements Comparable<Contacto> {

	/**
	 * Identificador del contacto.
	 */
	private int id;
	
	/**
	 * Nombre del contacto.
	 */
	private String nombre;
	
	/**
	 * Mensajes intercambiados entre el usuario y el contacto.
	 */
	private Set<Mensaje> mensajes;
	
	/**
	 * Constructor de Contacto, a partir de un nombre y un usuario.
	 * @param nombre -  Nombre a asignar al contacto.
	 */
	public Contacto(String nombre) {
		this.id = Utils.ID_DEFAULT;
		this.nombre = nombre;
		this.mensajes = new TreeSet<Mensaje>();
	}
	
	@Override

	public int hashCode() {
		return Objects.hash(nombre+id);
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
	
	/**
	 * Crea un nuevo mensaje a partir de un texto, un emoticono y un tipo de mensaje.
	 * @param texto - Texto a incluir en el mensaje.
	 * @param emoticono - Emoticono a incluir en el mensaje.
	 * @param tipo - Tipo de mensaje a crear.
	 * @return el mensaje creado.
	 */
	public Mensaje nuevoMensaje(String texto, int emoticono, TipoMensaje tipo) {
		Mensaje mensaje = new Mensaje(texto, emoticono, tipo);
		addMensaje(mensaje);
		return mensaje;
	}
	
	public int getSubTotalMensajesEnviadosUltimoMes() {
		return (int) getMensajes().stream()
				.filter(mensaje -> mensaje.getMomentoEnvio().getMonth().equals(Utils.FECHA_ACTUAL.getMonth())
						&& mensaje.getMomentoEnvio().getYear()==Utils.FECHA_ACTUAL.getYear() && mensaje.getTipo().equals(TipoMensaje.ENVIADO))
				.count();
	}
	
	public Mensaje getUltimoMensaje() {
	    if (mensajes.isEmpty()) return null;
	    TreeSet<Mensaje> ordenados = new TreeSet<>(mensajes);
	    return ordenados.first();
	}
	
	
	@Override
	public int compareTo(Contacto o) {
		if (this.getNombre() == null && o.getNombre() == null) return 0;
		if (this.getNombre() == null) return -1;
		if (o.getNombre() == null) return 1;
		return this.getNombre().compareTo(o.getNombre());
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
	
}
