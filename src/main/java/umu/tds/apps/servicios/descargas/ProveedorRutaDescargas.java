package umu.tds.apps.servicios.descargas;

/**
 * Interfaz para proveedores de rutas de descargas.
 * Permite obtener la ruta de descargas.
 */
public interface ProveedorRutaDescargas {
	
	/**
	 * Obtiene la ruta de descargas.
	 * 
	 * @return La ruta de descargas como una cadena de texto.
	 */
	String getRutaDescargas();
	
	/**
	 * Comprueba si el proveedor es compatible con el sistema operativo donde se ejecuta AppChat.
	 * 
	 * @return true si es compatible, false en caso contrario.
	 */
	boolean isCompatible();
}
