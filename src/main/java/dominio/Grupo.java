package dominio;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class Grupo extends Contacto {
	
	private Optional<String> imagen;
	private Set<ContactoIndividual> miembros;
	
	public Grupo(String nombre, Usuario usuario, String imagen, ContactoIndividual...miembros) {
		super(nombre, usuario);
		setImagen(imagen);
		this.miembros = new TreeSet<ContactoIndividual>();
		Collections.addAll(this.miembros, miembros);
	}
	
	public boolean addContacto(ContactoIndividual conacto) {
		return miembros.add(conacto);
	}
	
	public boolean removeContacto(ContactoIndividual conacto) {
		return miembros.remove(conacto);
	}
	
	public Optional<String> getImagen() {
		return imagen;
	}
	
	public Set<ContactoIndividual> getMiembros() {
		return Collections.unmodifiableSet(miembros);
	}
	
	public void setImagen(String imagen) {
		this.imagen = Optional.ofNullable(imagen);
	}
	
}
