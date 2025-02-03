package umu.tds.apps.dominio;

/**
 * Punto global que colecciona a todos los usuarios del sistema AppChat.
 */
public enum RepositorioUsuarios {
	
	/**
	 * Punto de acceso global al repositorio de usuarios de AppChat.
	 */
	INSTANCE;
	
	/**
	 * Devuelve la única instancia que representa al repositorio de usuarios.
	 * @return el único RepositorioUsuarios.
	 */
	public static RepositorioUsuarios getInstance() {
		return INSTANCE;
	}
	
}
