package umu.tds.apps.persistencia;

import java.util.HashMap;

/**
 * Lugar donde se almacenan todas las instancias a persistir.
 */
public enum PoolDAO {
	
	/**
	 * Punto de acceso global al pool.
	 */
	INSTANCE;
	
	/**
	 * Tabla de dispersión donde se almacenan las instancias.
	 */
	private HashMap<Integer, Object> pool;
	
	/**
	 * Constructor privado del pool.
	 */
	private PoolDAO() {
		pool = new HashMap<Integer, Object>();
	}
	
	/**
	 * Devuelve el pool de la aplicación.
	 * @return la única instancia del pool
	 */
	public static PoolDAO getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Añade al pool la instancia que tiene el id especificado.
	 * @param id - el identificador de la instancia a querer añadir del pool.
	 * @param object la instancia a añadir al pool
	 */
	public void addObject(int id, Object object) {
		pool.put(id, object);
	}
	
	/**
	 * Borra del pool la instancia que tiene el id especificado.
	 * @param id - el identificador de la instancia a querer borrar del pool.
	 */
	public void removeObject(int id) {
		pool.remove(id);
	}
	
	/**
	 * Devuelve la instancia que tiene el id especificado.
	 * @param id - el identificador de la instancia a querer obtener.
	 * @return la instancia correspondiente contenida en el pool.
	 */
	public Object getObject(int id) {
		return pool.get(id);
	}
	
	/**
	 * Comprueba si hay alguna instancia en el pool con el id pasado como parámetro.
	 * @param id - el identificador de la instancia a comrpbar si está en el pool.
	 * @return true si el pool contiene la instancia con el id indicado, false si no es así.
	 */
	public boolean contains(int id) {
		return pool.containsKey(id);
	}
	
}