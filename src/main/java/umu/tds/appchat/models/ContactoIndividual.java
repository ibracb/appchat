package umu.tds.appchat.models;

import java.util.Objects;

public class ContactoIndividual extends Contacto {

	private String telefono; 
	
	public ContactoIndividual(String nombre, String telefono, int usuario) {
		super(nombre, usuario);
		this.telefono = telefono;

	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true; // Son el mismo objeto

		if (obj == null || getClass() != obj.getClass())
			return false; // Clases diferentes

		if (!super.equals(obj))
			return false; // Verificar igualdad en la superclase
		ContactoIndividual other = (ContactoIndividual) obj;
		// Comparar atributos específicos de ContactoIndividual
		return this.telefono.equals(other.telefono);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), telefono);
	}

}
