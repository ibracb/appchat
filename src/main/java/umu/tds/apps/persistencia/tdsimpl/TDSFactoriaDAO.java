package umu.tds.apps.persistencia.tdsimpl;

import umu.tds.apps.persistencia.ContactoIndividualDAO;
import umu.tds.apps.persistencia.FactoriaDAO;
import umu.tds.apps.persistencia.GrupoDAO;
import umu.tds.apps.persistencia.MensajeDAO;
import umu.tds.apps.persistencia.UsuarioDAO;

/**
 * Factoria concreta DAO para el Servidor de Persistencia de la asignatura TDS.
 */
public class TDSFactoriaDAO extends FactoriaDAO {
	
	/**
	 * Constructor de TDSFactoriaDAO.
	 */
	public TDSFactoriaDAO() {}
	
	@Override
	public UsuarioDAO getUsuarioDAO() {
		return TDSUsuarioDAO.getInstance();
	}

	@Override
	public ContactoIndividualDAO getContactoIndividualDAO() {
		return TDSContactoIndividualDAO.getInstance();
	}

	@Override
	public GrupoDAO getGrupoDAO() {
		return TDSGrupoDAO.getInstance();
	}

	@Override
	public MensajeDAO getMensajeDAO() {
		return TDSMensajeDAO.getInstance();
	}

}
