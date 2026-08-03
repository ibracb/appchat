package umu.tds.apps.servicios.filtros;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import umu.tds.apps.modelo.Contacto;
import umu.tds.apps.modelo.Mensaje;
import umu.tds.apps.modelo.Usuario;

/**
 * Filtro que permite filtrar mensajes por el número de móvil del contacto.
 * 
 * 
 */
public class FiltroPorMovil implements Filtro {
	
	/**
	 * Número de móvil del contacto por el que se filtran los mensajes.
	 */
	private String movil;
	
	/**
	 * Constructor del filtro que recibe el número de móvil.
	 * 
	 * @param movil Número de móvil del contacto por el que se filtran los mensajes.
	 */
	public FiltroPorMovil(String movil) {
		this.movil = movil;
	}
	
	@Override
	public Map<Mensaje, Contacto> filtrar(Usuario usuario) {
		return usuario.getContactosIndividuales().stream()
				.filter(contacto -> contacto.getMovil().equals(movil))
				.flatMap(contacto -> contacto.getMensajes().stream()
				.map(mensaje -> Map.entry(mensaje, contacto)))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (m1, m2) -> m1, TreeMap::new));
	}

	@Override
	public boolean seFiltra() {
		return movil != null && !movil.isEmpty() && !movil.isBlank();
	}
	
}
