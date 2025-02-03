package umu.tds.apps.pdf;

/**
 * Clase que tiene la responsabilidad de generar los PDF correspondientes en la aplicación.
 */
public enum ExportPDF {
	
	/**
	 * Punto de acceso global al único generador de ficheros PDF existente en la aplicación.
	 */
	INSTANCE;
	
	/**
	 * Constructor privado de ExportPDF.
	 */
	private ExportPDF() {}
	
	/**
	 * Devuelve la instancia generadora de ficheros PDF. 
	 * @return la única instancia.
	 */
	public static ExportPDF getInstance() {
		return INSTANCE;
	}
	
}
