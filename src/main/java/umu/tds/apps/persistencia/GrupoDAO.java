package umu.tds.apps.persistencia;

import java.util.Set;

import umu.tds.apps.dominio.Grupo;

/**
 * Interfaz para el manejo de la persistencia de los grupos de AppChat.
 */
public interface GrupoDAO {
	
	/**
	 * Método para registrar un grupo en el sistema.
	 * @param grupo - Grupo a registrar.
	 */
	void create(Grupo grupo);
	
	/**
	 * Método para eliminar a un usuario del sistema.
	 * @param grupo - Grupo a eliminar.
	 */
	void delete(Grupo grupo);
	
	/**
	 * Método para modificar un grupo del sistema.
	 * @param grupo - Grupo a modificar.
	 */
	void update(Grupo grupo);
	
	/**
	 * Método para recuperar un grupo del sistema.
	 * @param id - Identificador del grupo a recuperar.
	 * @return el grupo a recuperar.
	 */
	Grupo get(int id);
	
	/**
	 * Método para recuperar todos los grupos del sistema.
	 * @return todos los grupos registrados.
	 */
	Set<Grupo> getAll();
	
}
