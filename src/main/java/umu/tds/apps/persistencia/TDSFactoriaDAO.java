package umu.tds.apps.persistencia;

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
