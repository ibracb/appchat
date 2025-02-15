package umu.tds.apps.persistencia;

import java.text.SimpleDateFormat;
import java.util.Set;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.dominio.Grupo;

/**
 * Clase para manejo de persistencia de grupos en TDS.
 */
public class TDSGrupoDAO implements GrupoDAO {
	
	/**
	 * Servicio de Persistencia global propuesto desde la asignatura de TDS.
	 */
	private static ServicioPersistencia servPersistencia;
	
	/**
	 * Instancia global de TDSGrupoDAO.
	 */
	private static TDSGrupoDAO INSTANCE;
	
	/**
	 * Devuelve la instancia global de TDSGrupoDAO. Si es null, la inicializa.
	 * @return el único TDSGrupoDAO.
	 */
	public static TDSGrupoDAO getInstance() {
		if (INSTANCE == null) {
			return new TDSGrupoDAO();
		}
		else {
			return INSTANCE;
		}
	}
	
	/**
	 * Inicialización privada de TDSGrupoDAO.
	 */
	private TDSGrupoDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	@Override
	public void create(Grupo grupo) {
	}

	@Override
	public void delete(Grupo grupo) {
	}

	@Override
	public void update(Grupo grupo) {
	}

	@Override
	public Grupo get(int id) {
		return null;
	}

	@Override
	public Set<Grupo> getAll() {
		return null;
	}

}
