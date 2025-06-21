package umu.tds.apps.persistencia;

import java.util.Set;

import umu.tds.apps.dominio.Usuario;

/**
 * Interfaz para el manejo de la persistencia de los usuarios de AppChat.
 */
public interface UsuarioDAO {
	
	/**
	 * Método para registrar un usuario en la base de datos.
	 * @param usuario - Usuario a registrar.
	 */
	void create(Usuario usuario);
	
	/**
	 * Método para eliminar un usuario de la base de datos.
	 * @param usuario - Usuario a eliminar.
	 */
	void delete(Usuario usuario);
	
	/**
	 * Método para modificar un usuario de la base de datos.
	 * @param usuario - Usuario a modificar.
	 */
	void update(Usuario usuario);
	
	/**
	 * Método para recuperar un usuario de la base de datos.
	 * @param id - Identificador del usuario a recuperar.
	 * @return el usuario a recuperar.
	 */
	Usuario get(int id);
	
	/**
	 * Método para recuperar todos los usuarios registrados en la base de datos.
	 * @return todos los usuarios registrados.
	 */
	Set<Usuario> getAll();
	
}
