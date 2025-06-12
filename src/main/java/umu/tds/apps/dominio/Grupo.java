package umu.tds.apps.dominio;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 * Representación de una colección de contactos individuales del seleccionada por el usuario.
 */
public class Grupo extends Contacto {
	
	/**
	 * Imagen por defecto que tiene un grupo si no tiene asignada una imagen
	 */
	private static final String IMAGEN_DEFAULT = "src/main/resources/imagenes/perfil_grupo.png";
	
	/**
	 * Ruta que representa la imagen de perfil del grupo.
	 */
	private Optional<String> imagen;
	
	/**
	 * Colección que almacena a los miembros del grupo añadidos por el usuario.
	 */
	private Set<ContactoIndividual> miembros;
	
	/**
	 * Método que otorga un determinado estado inicial a un grupo.
	 * @param nombre - Nombre del grupo
	 * @param imagen - Ruta de la imagen a asignar al grupo.
	 * @param miembros - Miembros a añadir al grupo.
	 */
	public Grupo(String nombre, String imagen) {
		super(nombre);
		setImagen(imagen);
		this.miembros = new TreeSet<ContactoIndividual>();
	}
	
	public Grupo(String nombre, String imagen, Set<ContactoIndividual> miembros) {
		this(nombre, imagen);
		this.miembros = miembros;
	}
	
	/**
	 * Método que añade un contacto individual al grupo.
	 * @param miembro - El miembro que se desea añadir al grupo.
	 * @return true si se ha añadido al miembro satisfactoriamente; false si no ha sido así.
	 */
	public boolean addMiembro(ContactoIndividual miembro) {
		return miembros.add(miembro);
	}
	
	/**
	 * Método que elimina un contacto individual del grupo.
	 * @param miembro - El miembro que se desea eliminar del grupo.
	 * @return true si se ha eliminado el miembro satisfactoriamente; false si no ha sido así.
	 */
	public boolean removeMiembro(ContactoIndividual miembro) {
		return miembros.remove(miembro);
	}
	
	/**
	 * Devuelve la ruta de la imagen de perfil del grupo.
	 * @return la ruta correspondiente, o una por defecto si no tiene.
	 */
	public String getImagen() {
		return imagen.orElse(IMAGEN_DEFAULT);
	}

	/**
	 * Asigna la imagen que se desea poner como perfi, mediante una ruta.
	 * @param imagen - La imagen a poner con foto de perfil del grupo.
	 */
	public void setImagen(String imagen) {
		this.imagen = Optional.ofNullable(imagen);
	}

	/**
	 * Devuelve la colección de contactos individuales, los cuales el usuario añadió al grupo.
	 * @return la colección de miembros.
	 */
	public Set<ContactoIndividual> getMiembros() {
		return Collections.unmodifiableSet(miembros);
	}
	
}
