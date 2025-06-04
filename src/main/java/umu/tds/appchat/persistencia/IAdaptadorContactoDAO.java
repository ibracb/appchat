package umu.tds.appchat.persistencia;

import java.util.Map;
import umu.tds.appchat.models.ContactoIndividual;

public interface IAdaptadorContactoDAO {

	public boolean registrarContacto(ContactoIndividual contacto);

	public ContactoIndividual recuperarContacto(int id);

	public boolean eliminarContacto(ContactoIndividual contacto);
	
	public Map<String, ContactoIndividual> recuperarTodosContactos();

	public boolean contactoYaRegistrado(ContactoIndividual c);

	ContactoIndividual recuperarContactoTelefono(String tlf);

	}
