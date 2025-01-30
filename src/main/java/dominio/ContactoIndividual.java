package dominio;

import java.util.Objects;

/**
 * 
 */
public class ContactoIndividual extends Contacto {
	
	/**
	 * Número de teléfono móvil del contacto.
	 */
	private final String movil;
	
	/**
	 * Contructor de un contacto individual.
	 * @param nombre - Nombre asignado al contacto.
	 * @param usuario - Usuario que crea el contacto.
	 * @param movil - Teléfono móvil del contacto creado.
	 */
	public ContactoIndividual(String nombre, Usuario usuario, String movil) {
		super(nombre, usuario);
		this.movil = movil;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(movil);
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
		return Objects.equals(movil, other.movil);
	}
	
	/**
	 * Devuelve el número de teléfono móvil del contacto. 
	 * @return
	 */
	public String getMovil() {
		return movil;
	}
	
}