package umu.tds.apps.servicios.filtros;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.Usuario;

/**
 * Clase que representa un filtro compuesto que combina múltiples filtros.
 * Este filtro solo devuelve los mensajes que cumplen con todos los filtros
 * individuales aplicados.
 */
public class FiltroCompuesto implements Filtro {
	
	/**
	 * Conjunto de filtros que componen este filtro compuesto.
	 * Se filtran los filtros que no se aplican (seFiltra() devuelve false).
	 */
	private final Set<Filtro> filtros;

	/**
	 * Constructor que inicializa el filtro compuesto con un conjunto de filtros.
	 * 
	 * @param filtros Conjunto de filtros a aplicar.
	 */
	public FiltroCompuesto(Set<Filtro> filtros) {
		this.filtros = filtros.stream()
							.filter(Filtro::seFiltra)
							.collect(Collectors.toSet());
	}
	
	@Override
	public Map<Mensaje, Contacto> filtrar(Usuario usuario) {
		Map<Mensaje, Contacto>  resultado = new TreeMap<>();
		boolean primerFiltro = true;
	    for (Filtro filtro : filtros) {
	        Map<Mensaje, Contacto> actual = filtro.filtrar(usuario);

	        if (primerFiltro) {
	            resultado.putAll(actual);
	            primerFiltro = false;
	        } else {
	            // Mantener solo las entradas que también están en `actual`
	            resultado.entrySet().removeIf(entry -> !actual.containsKey(entry.getKey()));
	        }
	    }
		return resultado;
	}
	
	@Override
	public boolean seFiltra() {
		return !filtros.isEmpty();
	}
	
}
