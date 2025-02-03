package umu.tds.apps.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * Clase de utilidades.
 */
public class Utils {
	
	/**
	 * Identificador por defecto (0) para cualquier entidad del dominio nada más ser creada.
	 */
	public static final int ID_DEFAULT = 0;	
	
	/**
	 * Representación de la fecha actual.
	 */
	public static final LocalDateTime FECHA_ACTUAL = LocalDateTime.now();
	
	/**
	 * 
	 * @param archivoImagen
	 * @return
	 */
	public static String getRutaResourceFromFile(File archivoImagen) {	//Lo cogí de recursos de AV, pero de momento no sé usarlo para lo de las imágnes
		// Define la ruta base del proyecto que debe apuntar a "src/main/resources"
		Path rutaBase = Paths.get("src/main/resources").toAbsolutePath();

		// Obtén la ruta absoluta del archivo
		Path rutaArchivo = archivoImagen.toPath().toAbsolutePath();

		// Calcula la ruta relativa desde "src/main/resources" hasta el archivo
		Path rutaRelativa = rutaBase.relativize(rutaArchivo);

		// Devuelve la ruta en formato compatible con getResource()
		return "/" + rutaRelativa.toString().replace("\\", "/");
	}
	
	/**
	 * 
	 * @param source
	 * @return
	 */
	public static String getRutaResourceFromString (String source) {	//Lo cogí de recursos de AV, pero de momento no sé usarlo para lo de las imágnes
		String target = "";
		if (source.contains("src\\main\\resources\\")) {
			target = source.substring(source.indexOf("src\\main\\resources\\") + "src\\main\\resources\\".length());
			// Cambia las barras de Windows (\) por barras de URL (/)
			target = "/" + target.replace("\\", "/");
		}
		return target;
	}
	
}
