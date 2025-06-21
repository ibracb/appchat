package umu.tds.apps.servicios.filtros;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.Usuario;

/**
 * Filtro que permite filtrar mensajes por un texto específico.
 * 
 * Este filtro busca en los mensajes de los contactos del usuario aquellos que contienen
 * el texto especificado, ignorando mayúsculas y minúsculas.
 */
public class FiltroPorTexto implements Filtro {
	
	/**
	 * Texto a buscar en los mensajes.
	 */
	private String texto;
	
	/**
	 * Constructor del filtro que recibe el texto a buscar.
	 * 
	 * @param texto Texto que se utilizará para filtrar los mensajes.
	 */
	public FiltroPorTexto(String texto) {
		this.texto = texto;
	}
	
	@Override	 
	public Map<Mensaje, Contacto> filtrar(Usuario usuario) {
	    return usuario.getContactosIndividuales().stream()
	    		.flatMap(contacto -> contacto.getMensajes().stream()
	            .filter(mensaje -> mensaje.getTexto().toLowerCase().contains(texto.toLowerCase()))
	            .map(mensaje -> Map.entry(mensaje, contacto)))
	    		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (m1, m2) -> m1, TreeMap::new));
	}

	@Override
	public boolean seFiltra() {
		return texto != null && !texto.isEmpty() && !texto.isBlank();
	}

}
