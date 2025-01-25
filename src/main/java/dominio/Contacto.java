package dominio;

import java.time.Month;
import java.time.Year;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Contacto implements Comparable<Contacto> {
	
	private static final int ID_CONTACTO_DEFAULT = 0;
	
	private int id;
	private String nombre;
	private final Usuario usuario;
	private Set<Mensaje> mensajes;
	
	public Contacto(String nombre, Usuario usuario) {
		this.id = ID_CONTACTO_DEFAULT;
		this.nombre = nombre;
		this.usuario = usuario;
		this.mensajes = new TreeSet<Mensaje>();
	}
	
	public Set<Mensaje> getMensajesPorTexto(String texto) {
		return mensajes.stream()
				.filter(mensaje -> mensaje.getTexto().contains(texto))
				.sorted()
				.collect(Collectors.toSet());
	}
	
	public Set<Mensaje> getMensajesPorMovil(String movil) {
		return mensajes.stream()
				.filter(mensaje -> mensaje.getEmisor().equals(movil) || mensaje.getReceptor().equals(movil))
				.sorted()
				.collect(Collectors.toSet());
	}
		
	public Set<Mensaje> getMensajesPorFecha(int dia, Month mes, int año) {
		return mensajes.stream()
				.filter(mensaje -> mensaje.getMomentoEnvio().getDayOfYear()==dia && mensaje.getMomentoEnvio().getMonth().equals(mes)
					&& mensaje.getMomentoEnvio().getYear()==año)
				.sorted()
				.collect(Collectors.toSet());
	}
	
	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public Set<Mensaje> getMensajes() {
		return Collections.unmodifiableSet(mensajes);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public int compareTo(Contacto o) {
		return this.getNombre().compareTo(o.getNombre());
	}
	
}
