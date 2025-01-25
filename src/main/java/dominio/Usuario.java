package dominio;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Usuario implements Comparable<Usuario>{
	
	private static final int ID_USUARIO_DEFAULT = 0;
	
	private int id;
	private final String nombre;
	private final LocalDate fechaNacimiento;
	private final String email;
	private String imagen;
	private final String movil;
	private final String contraseña;
	private Optional<String> saludo;
	private boolean premium;
	private Set<Contacto> contactos;
	
	public Usuario(String nombre, LocalDate fechaNacimiento, String email, String imagen, String movil, String contraseña, String saludo) {
		this.id = ID_USUARIO_DEFAULT;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.imagen = imagen;
		this.movil = movil;
		this.contraseña = contraseña;
		setSaludo(saludo);
		this.premium = false;
		this.contactos = new TreeSet<Contacto>();
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, movil);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(email, other.email) || Objects.equals(movil, other.movil);
	}
	
	public Grupo createGrupo(String nombre, String imagen, ContactoIndividual...miembros) {
		Grupo grupo = new Grupo(nombre, this, imagen, miembros);
		contactos.add(grupo);
		return grupo;
	}
	
	public boolean addContacto(ContactoIndividual contacto) {
		return contactos.add(contacto);
	}
	
	public boolean removeContacto(ContactoIndividual contacto) {
		return contactos.remove(contacto);
	}
	
	public boolean addContacto(ContactoIndividual contacto, Grupo grupo) {
		return grupo.addContacto(contacto);
	}
	
	public boolean removeContacto(ContactoIndividual contacto, Grupo grupo) {
		return grupo.removeContacto(contacto);
	}
	
	public Set<Mensaje> findMensajesPorTexto(String texto) {
		return contactos.stream()
				.flatMap(contacto -> contacto.getMensajesPorTexto(texto).stream())
				.collect(Collectors.toSet());
	}
	
	public Set<Mensaje> findMensajesPorNombreContacto(String nombre) {
		return contactos.stream()
				.filter(contacto -> contacto.getNombre().equals(nombre))
				.flatMap(contacto -> contacto.getMensajes().stream())
				.collect(Collectors.toSet());
	}
	
	public Set<Mensaje> findMensajesPorMovil(String movil) {
		return contactos.stream()
				.flatMap(contacto -> contacto.getMensajesPorMovil(movil).stream())
				.collect(Collectors.toSet());
				
	}
	
	public Set<Mensaje> findMensajesPorFecha(int dia, Month mes, int año) {
		return contactos.stream()
				.flatMap(contacto -> contacto.getMensajesPorFecha(dia, mes, año).stream())
				.collect(Collectors.toSet());
	}
	
	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public String getImagen() {
		return imagen;
	}

	public String getMovil() {
		return movil;
	}

	public String getContraseña() {
		return contraseña;
	}

	public Optional<String> getSaludo() {
		return saludo;
	}

	public boolean isPremium() {
		return premium;
	}

	public Set<Contacto> getContactos() {
		return Collections.unmodifiableSet(contactos);
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	public void setSaludo(String saludo) {
		this.saludo = Optional.ofNullable(saludo);
	}
	
	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	@Override
	public int compareTo(Usuario o) {
		return this.getMovil().compareTo(o.getMovil());
	}
	
}
