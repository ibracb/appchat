package umu.tds.apps.persistencia;

/**
 * Excepción que se lanza cuando ocurre un error en la capa de acceso a datos.
 * 
 * @author TDS
 */
@SuppressWarnings("serial")
public class DAOException extends Exception {

	/**
	 * Constructor por defecto de la excepción DAOException.
	 */
	public DAOException(final String mensaje) {
		super(mensaje);
	}
	
}