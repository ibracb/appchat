package umu.tds.apps.persistencia;

import java.util.Set;

import umu.tds.apps.dominio.ContactoIndividual;

/**
 * Interfaz para el manejo de la persistencia de los contactos individuales de AppChat.
 */
public interface ContactoIndividualDAO {
	
	/**
	 * Método para registrar un contacto individual en el sistema.
	 * @param contacto - El contacto individual a registrar.
	 */
	void create(ContactoIndividual contacto);
	
	/**
	 * Método para eliminar un contacto inidividual del sistema.
	 * @param contacto - Contacto individual a eliminar.
	 */
	void delete(ContactoIndividual contacto);
	
	/**
	 * Método para modificar un contacto individual del sistema.
	 * @param contacto - Contacto individual a modificar.
	 */
	void update(ContactoIndividual contacto);
	
	/**
	 * Método para recuperar un contacto individual del sistema.
	 * @param id - Identificador del contacto individual a recuperar.
	 * @return el contacto individual a recuperar.
	 */
	ContactoIndividual get(int id);
	
	/**
	 * Método para recuperar todos los contactos individuales del sistema.
	 * @return todos los contactos individuales.
	 */
	Set<ContactoIndividual> getAll();
	
}
