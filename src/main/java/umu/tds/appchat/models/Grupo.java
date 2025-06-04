package umu.tds.appchat.models;

import java.util.LinkedList;
import java.util.List;

public class Grupo extends Contacto{
	
	private List<ContactoIndividual> contactos;
	
	public Grupo(String nombre, int usuario, String imagen) {
		super(nombre, usuario, imagen);
		contactos = new LinkedList<>();
	}
	
	public Grupo(String nombre, int usuario, List<ContactoIndividual> contactos, String imagen ) {
		this(nombre,usuario, imagen);
		this.contactos = contactos;
	}

	public List<ContactoIndividual> getContactos() {
		return contactos;
	}

	public boolean addContacto(ContactoIndividual contacto) {
		return contactos.add(contacto);
	}	
	
	public boolean remContacto(ContactoIndividual contacto) {
		return contactos.remove(contacto);
	}
	
	public int getNumContactos () {
		return contactos.size();
	}
	
}
