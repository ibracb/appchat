package dominio;

import java.time.Month;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import utils.Utils;

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
	private final Usuario usuario;
	
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
	 * Filtra los mensajes intercambiados con el usuario, según un texto
	 * @param texto - El texto a contener
	 * @return los mensajes que contengan el texto.
	 */
	public Set<Mensaje> getMensajesPorTexto(String texto) {
		return mensajes.stream()
				.filter(mensaje -> mensaje.getTexto().contains(texto))
				.collect(Collectors.toCollection(TreeSet::new));
	}
	
	/**
	 * Dado un número de teléfono móvil, devuelve los mensajes en los que el emisor o el receptor coincide con ese mismo teléfono móvil.
	 * @param movil - El teléfono móvil , ya sea emisor o receptor, a coincidir.
	 * @return los mensajes con algún participante con el número de teléfono móvil introducido como argumento.
	 */
	public Set<Mensaje> getMensajesPorMovil(String movil) {
		return mensajes.stream()
				.filter(mensaje -> mensaje.getEmisor().equals(movil) || mensaje.getReceptor().equals(movil))
				.collect(Collectors.toCollection(TreeSet::new));
	}
	
	/**
	 * Dada una fecha, devuelve los mensajes que el contacto intercambió con el usuario en esa misma fecha.
	 * @param dia - Día del mes con el que se quieren filtrar los mensajes.
	 * @param mes - Mes del año con el que se quieren filtrar los mensajes.
	 * @param año - Año con el que se quieren filtrar los mensajes.
	 * @return los mensajes con la fecha de envío coincidente con los argumentos.
	 */
	public Set<Mensaje> getMensajesPorFecha(int dia, Month mes, int año) {
		return mensajes.stream()
				.filter(mensaje -> mensaje.getMomentoEnvio().getDayOfYear()==dia && mensaje.getMomentoEnvio().getMonth().equals(mes)
					&& mensaje.getMomentoEnvio().getYear()==año)
				.collect(Collectors.toCollection(TreeSet::new));
	}
	
	/**
	 * Devuelve los mensajes que el usuario envió al contacto en el mes actual.
	 * @return los mensajes enviados en forma de colección.
	 */
	public int getSubTotalMensajesEnviadosUltimoMes() {
		return (int) mensajes.stream()
				.filter(mensaje -> mensaje.getMomentoEnvio().getMonth().equals(Utils.FECHA_ACTUAL.getMonth())
						&& mensaje.getMomentoEnvio().getYear()==Utils.FECHA_ACTUAL.getYear() && mensaje.getTipo().equals(TipoMensaje.ENVIADO))
				.count();	
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
	 * Devuelve los mensajes que el usuario ha intercambiado con el contacto.
	 * @return los mensajes intercambiados.
	 */
	public Set<Mensaje> getMensajes() {
		return Collections.unmodifiableSet(mensajes);
	}
	
	@Override
	public int compareTo(Contacto o) {
		return this.getNombre().compareTo(o.getNombre());
	}
	
}
