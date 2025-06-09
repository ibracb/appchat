package umu.tds.apps.persistencia.tdsimpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.persistencia.MensajeDAO;

public final class TDSContactosUtilsDAO {
	
	/**
	 * Cadena de texto que representa un espacio en blanco.
	 */
	public static final String ESPACIO_EN_BLANCO = " ";
	
	/**
	 * Nombre de la propiedad id en la base de datos.
	 */
	public static final String PROPIEDAD_ID = "id";
	
	/**
	 * Nombre de la propiedad nombre en la base de datos.
	 */
	public static final String PROPIEDAD_NOMBRE = "nombre";
	
	/**
	 * Nombre de la propiedad usuario en la base de datos.
	 */
	public static final String PROPIEDAD_USUARIO = "usuario";
	
	/**
	 * Nombre de la propiedad mensajes en la base de datos.
	 */
	public static final String PROPIEDAD_MENSAJES = "mensajes";
	
	/**
	 * Convierte un conjunto de mensajes a una cadena de texto con los ids de los mensajes.
	 * @param mensajes Conjunto de mensajes a convertir.
	 * @return Cadena de texto con los ids de los mensajes separados por espacios.
	 */
	public static String getIdsMensajes(Set<Mensaje> mensajes) {
		return mensajes.stream()
				.map(mensaje -> String.valueOf(mensaje.getId()))
				.collect(Collectors.joining(ESPACIO_EN_BLANCO));
	}
	
	/**
	 * Convierte una cadena de texto con los ids de los mensajes a un conjunto de mensajes.
	 * @param lineas Cadena de texto con los ids de los mensajes separados por espacios.
	 * @return Conjunto de mensajes.
	 */
	public static Set<Mensaje> getMensajesFromIds(String lineas) {
		MensajeDAO adaptadorMensaje = TDSMensajeDAO.getInstance();
	    
	    if (lineas == null || lineas.isBlank()) {
	        return new HashSet<>();
	    }

	    return Arrays.stream(lineas.split(ESPACIO_EN_BLANCO))
	        .filter(id -> id != null && !id.isEmpty())
	        .map(Integer::valueOf)
	        .map(adaptadorMensaje::get)
	        .filter(Objects::nonNull)
	        .collect(Collectors.toSet());
	}
	
}
