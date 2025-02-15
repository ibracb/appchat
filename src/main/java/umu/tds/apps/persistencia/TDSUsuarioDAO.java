package umu.tds.apps.persistencia;

import java.text.SimpleDateFormat;
import java.util.Set;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.dominio.Usuario;

/**
 * Clase para manejo de persistencia de usuarios en TDS.
 */
public class TDSUsuarioDAO implements UsuarioDAO {
	
	/**
	 * Servicio de Persistencia global propuesto desde la asignatura de TDS.
	 */
	private static ServicioPersistencia servPersistencia;
	
	/**
	 * Formateador de fechas para la base de datos.
	 */
	private SimpleDateFormat dateFormat;
	
	/**
	 * Instancia global de TDSUsuarioaDAO.
	 */
	private static TDSUsuarioDAO INSTANCE;
	
	/**
	 * Devuelve la instancia global de TDSUsuarioaDAO. Si es null, la inicializa.
	 * @return el único TDSUsuarioDAO.
	 */
	public static TDSUsuarioDAO getInstance() {
		if (INSTANCE == null) {
			return new TDSUsuarioDAO();
		}
		else {
			return INSTANCE;
		}
	}
	
	/**
	 * Inicialización privada de TDSUsuarioDAO.
	 */
	private TDSUsuarioDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	@Override
	public void create(Usuario usuario) {
	}

	@Override
	public void delete(Usuario usuario) {
	}

	@Override
	public void update(Usuario usuario) {
	}

	@Override
	public Usuario get(int id) {
		return null;
	}

	@Override
	public Set<Usuario> getAll() {
		return null;
	}

}
