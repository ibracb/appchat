package umu.tds.apps.persistencia;

import java.util.Set;

import umu.tds.apps.dominio.Mensaje;

/**
 * Interfaz para el manejo de la persistencia de los mensajes de AppChat.
 */
public interface MensajeDAO {
	
	/**
	 * Método para registrar un mensaje en la base de datos.
	 * @param mensaje
	 */
	void create(Mensaje mensaje);
	
	/**
	 * Método para eliminar un mensaje de la base de datos.
	 * @param mensaje
	 */
	void delete(Mensaje mensaje);
	
	/**
	 * Método para modificar un mensaje de la base de datos.
	 * @param usuario - Mensaje a modificar.
	 */
	void update(Mensaje mensaje);
	
	/**
	 *	Método para recuperar un mensaje de la base de datos.
	 * @param id - Identificador del mensaje a recuperar.
	 * @return
	 */
	Mensaje get(int id);
	
	/**
	 * Método para recuperar todos los mensajes de la base de datos.
	 * @return todos los grupos registrados.
	 */
	Set<Mensaje> getAll();
	
}
