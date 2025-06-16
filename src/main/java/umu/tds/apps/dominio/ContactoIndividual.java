package umu.tds.apps.dominio;

import java.util.Objects;

/**
 * Representación de un contacto individual. 
 */
public class ContactoIndividual extends Contacto {
	
	/**
	 * Usuario al que hace referencia el nuevo contacto individual.
	 */
	private Usuario usuario;
	
	/**
	 * Cadena de texto que representa la cadena vacía y, por lo tanto, no tiene nombre el contacto individual.
	 */
	public static final String NOMBRE_NULL = "";
	
	/**
	 * Contructor de un contacto individual.
	 * @param nombre - Nombre asignado al contacto.
	 * @param usuario - Usuario que crea el contacto.
	 */
	public ContactoIndividual(String nombre, Usuario usuario) {
		super(nombre);
		this.usuario = usuario;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactoIndividual other = (ContactoIndividual) obj;
		return Objects.equals(usuario, other.usuario);
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * Devuelve el número de teléfono móvil del contacto individual. 
	 * @return el movil del contacto individual.
	 */
	public String getMovil() {
		return usuario.getMovil();
	}
	
	public String getSaludo() {
		return usuario.getSaludo();
	}
	
	public boolean isAñadido() {
		return !getNombre().matches("\\d+");
	}
	
	
}