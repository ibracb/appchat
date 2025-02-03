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
	 * 
	 */
	private HashMap<Integer, Object> pool;
	
	/**
	 * 
	 */
	private PoolDAO() {
		pool = new HashMap<Integer, Object>();
	}
	
	/**
	 * 
	 * @return
	 */
	public static PoolDAO getInstance() {
		return INSTANCE;
	}
	
	/**
	 * 
	 * @param id
	 * @param object
	 */
	public void addObject(int id, Object object) {
		pool.put(id, object);
	}
	
	/**
	 * 
	 * @param id
	 */
	public void removeObject(int id) {
		pool.remove(id);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Object getObject(int id) {
		return pool.get(id);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean contains(int id) {
		return pool.containsKey(id);
	}
	
}