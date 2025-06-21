package umu.tds.apps.servicios.filtros;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.Usuario;

/**
 * Filtro que permite filtrar mensajes por el nombre de un contacto específico.
 * 
 * 
 */
public class FiltroPorNombreContacto implements Filtro {
	
	/**
	 * Nombre del contacto por el cual se filtrarán los mensajes.
	 */
	private String nombreContacto;
	
	/**
	 * Constructor que inicializa el filtro con el nombre del contacto.
	 * 
	 * @param nombreContacto Nombre del contacto por el cual se filtrarán los mensajes.
	 */
	public FiltroPorNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}
	
	@Override
	public Map<Mensaje, Contacto> filtrar(Usuario usuario) {
	    return usuario.getContactosIndividuales().stream()
	        .filter(contacto -> contacto.getNombre().equalsIgnoreCase(nombreContacto))
	        .flatMap(contacto -> contacto.getMensajes().stream()
	        .map(mensaje -> Map.entry(mensaje, contacto)))
	        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (m1, m2) -> m1, TreeMap::new));
	}

	@Override
	public boolean seFiltra() {
		return nombreContacto != null && !nombreContacto.isEmpty() && !nombreContacto.isBlank();
	}
	
}
