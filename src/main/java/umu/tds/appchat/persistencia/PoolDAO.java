package umu.tds.appchat.persistencia;

import java.util.Hashtable;

public enum PoolDAO {
	INSTANCE;

	private Hashtable<Integer,Object> pool;

	private PoolDAO() {
		// Esto es lo que pones en las transparencias
		pool = new Hashtable<Integer, Object>();
	}

	public void addObject(int id, Object object) {
		pool.put(id, object);
	}

	public Object getObject(int id) {
		return pool.get(id);
	}

	public boolean contains(int id) {
		return pool.containsKey(id);
	}
}