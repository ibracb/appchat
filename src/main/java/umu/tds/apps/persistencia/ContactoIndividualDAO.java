package umu.tds.apps.persistencia;

import java.util.Set;

import umu.tds.apps.dominio.ContactoIndividual;

/**
 * Interfaz para el manejo de la persistencia de los contactos individuales de AppChat.
 */
public interface ContactoIndividualDAO {
	
	/**
	 * Método para registrar un contacto individual en la base de datos.
	 * @param contacto - El contacto individual a registrar.
	 */
	void create(ContactoIndividual contacto);
	
	/**
	 * Método para eliminar un contacto inidividual la base de datos.
	 * @param contacto - Contacto individual a eliminar.
	 */
	void delete(ContactoIndividual contacto);
	
	/**
	 * Método para modificar un contacto individual de la base de datos.
	 * @param contacto - Contacto individual a modificar.
	 */
	void update(ContactoIndividual contacto);
	
	/**
	 * Método para recuperar un contacto individual de la base de datos.
	 * @param id - Identificador del contacto individual a recuperar.
	 * @return el contacto individual a recuperar.
	 */
	ContactoIndividual get(int id);
	
	/**
	 * Método para recuperar todos los contactos individuales de la base de datos.
	 * @return todos los contactos individuales.
	 */
	Set<ContactoIndividual> getAll();
	
}
