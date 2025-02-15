package umu.tds.apps.persistencia;

import java.text.SimpleDateFormat;
import java.util.Set;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.dominio.Mensaje;

/**
 * Clase para manejo de persistencia de manejos en TDS.
 */
public class TDSMensajeDAO implements MensajeDAO {
	
	/**
	 * Servicio de Persistencia global propuesto desde la asignatura de TDS.
	 */
	private static ServicioPersistencia servPersistencia;
	
	/**
	 * Formateador de fechas para la base de datos.
	 */
	private SimpleDateFormat dateFormat;
	
	/**
	 * Instancia global de TDSMensajeDAO.
	 */
	private static TDSMensajeDAO INSTANCE;
	
	/**
	 * Devuelve la instancia global de TDSMensajeDAO. Si es null, la inicializa.
	 * @return el único TDSMensajeDAO.
	 */
	public static TDSMensajeDAO getInstance() {
		if (INSTANCE == null) {
			return new TDSMensajeDAO();
		}
		else {
			return INSTANCE;
		}
	}
	
	/**
	 * Inicialización privada de TDSMensajeDAO.
	 */
	private TDSMensajeDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	@Override
	public void create(Mensaje mensaje) {
	}
	
	@Override
	public void delete(Mensaje mensaje) {
	}
	
	@Override
	public void update(Mensaje mensaje) {
	}
	
	@Override
	public Mensaje get(int id) {
		return null;
	}

	@Override
	public Set<Mensaje> getAll() {
		return null;
	}

}
