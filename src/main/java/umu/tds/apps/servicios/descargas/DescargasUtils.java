package umu.tds.apps.servicios.descargas;

/**
 * Clase utilitaria para manejar descargas y rutas de archivos.
 * Proporciona constantes y métodos comunes para la gestión de descargas.
 */
public class DescargasUtils {
	
	/**
	 * Constante para recuperar el directorio de inicio del usuario en el sistema operativo.
	 */
	protected static final String HOME = "user.home";
	
	/**
	 * Nombre del directorio de descargas en el sistema operativo.
	 * Por defecto, se asume que es "Downloads", pero puede variar según la configuración.
	 */
	protected static final String DOWNLOADS = "Downloads";
	
	/**
	 * Barra utilizada para separar directorios en rutas de archivos.
	 */
	protected static final String BARRA = "/";
	
	/**
	 * Constante para recuperar el nombre del sistema operativo.
	 */
	protected static final String SISTEMA_OPERATIVO = "os.name";
	
}
