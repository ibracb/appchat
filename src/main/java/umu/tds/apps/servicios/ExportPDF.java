package umu.tds.apps.servicios;

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
	
}
