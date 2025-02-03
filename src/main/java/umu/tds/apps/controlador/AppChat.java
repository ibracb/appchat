package umu.tds.apps.controlador;

/**
 * Coordina la lógica de la aplicación, y maneja los eventos capturados por la interfaz de usuario.
 */
public enum AppChat {
	
	/**
	 * Punto de acceso global al Controlador AppChat.
	 */
	INSTANCE;
	
	/**
	 * Devuelve la única instancia AppChat.
	 * @return el único Appchat.
	 */
	public static AppChat getInstance() {
		return INSTANCE;
	}
	
}
