package dominio;

import java.util.Objects;

public class ContactoIndividual extends Contacto {
	
	private final String movil;
	
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

	public String getMovil() {
		return movil;
	}
	
}