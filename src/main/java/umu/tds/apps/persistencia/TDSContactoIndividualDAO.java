package umu.tds.apps.persistencia;

import java.util.Set;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.dominio.ContactoIndividual;

/**
 * Clase para manejo de persistencia de contactos individuales en TDS.
 */
public class TDSContactoIndividualDAO implements ContactoIndividualDAO {
	
	/**
	 * Servicio de Persistencia global propuesto desde la asignatura de TDS.
	 */
	private static ServicioPersistencia servPersistencia;
	
	/**
	 * Instancia global de TDSContactoIndividualDAO.
	 */
	private static TDSContactoIndividualDAO INSTANCE;
	
	/**
	 * Devuelve la instancia global de TDSContactoIndividualDAO. Si es null, la inicializa.
	 * @return el único TDSContactoIndividualDAO.
	 */
	public static TDSContactoIndividualDAO getInstance() {
		if (INSTANCE == null) {
			return new TDSContactoIndividualDAO();
		}
		else {
			return INSTANCE;
		}
	}
	
	/**
	 * Inicialización privada de TDSContactoIndividualDAO.
	 */
	private TDSContactoIndividualDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	@Override
	public void create(ContactoIndividual contacto) {
	}

	@Override
	public void delete(ContactoIndividual contacto) {
	}

	@Override
	public void update(ContactoIndividual contacto) {
	}

	@Override
	public ContactoIndividual get(int id) {
		return null;
	}

	@Override
	public Set<ContactoIndividual> getAll() {
		return null;
	}

}
