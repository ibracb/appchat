package umu.tds.apps.persistencia;

/**
 * Clase donde se obtienen instancias DAO de la aplicación. 
 */
public abstract class FactoriaDAO {
	
	/**
	 * Instancia global de FactoriaDAO.
	 */
	private static FactoriaDAO INSTANCE;
	
	/**
	 * Cadena para instanciar TDSFactoriaDAO.
	 */
	public static final String DAO_TDS = "umu.tds.apps.persistencia.TDSFactoriaDAO";
	
	/**
	 * Método para instanciar un adaptador FactoriaDAO.
	 * @param nombre - Cadena para indicar qué clase instanciar.
	 * @return la clase instanciada, si no lo estaba, o la clase instanciada anteriormente.
	 * @throws DAOException lanza excepción relacionada con el patrón DAO.
	 */
	public static FactoriaDAO getInstance(String nombre) throws DAOException {
		if(INSTANCE==null) {
			try {
				INSTANCE = (FactoriaDAO) Class.forName(nombre).getDeclaredConstructor().newInstance();
			} catch(Exception e) {
				throw new DAOException(e.getMessage());
			}
		}
		return INSTANCE;
	}
	
	/**
	 * Devuelve la instancia de TDSFctoriaDAO.
	 * @return la instancia TDSFactoriaDAO.
	 * @throws DAOException lanza excepción relacionada con el patrón DAO.
	 */
	public static FactoriaDAO getInstance() throws DAOException {
		return getInstance(DAO_TDS);
	}
	
	/**
	 * Constructor de FactoriaDAO.
	 */
	protected FactoriaDAO() {}
	
	/**
	 * Devuelve un adaptador de UsuarioDAO para la asignatura de TDS.
	 * @return un adaptador UsuarioDAO de TDS.
	 */
	public abstract UsuarioDAO getUsuarioDAO();
	
	/**
	 * Devuelve un adaptador de ContactoIndividualDAO para la asignatura de TDS.
	 * @return un adaptador ContactoIndividualDAO de TDS.
	 */
	public abstract ContactoIndividualDAO getContactoIndividualDAO();
	
	/**
	 * Devuelve un adaptador de GrupoDAO para la asignatura de TDS.
	 * @return un adaptador GrupoDAO de TDS.
	 */
	public abstract GrupoDAO getGrupoDAO();
	
	/**
	 * Devuelve un adaptador de MensajeDAO para la asignatura de TDS.
	 * @return un adaptador MensajeDAO de TDS.
	 */
	public abstract MensajeDAO getMensajeDAO();
	
}
