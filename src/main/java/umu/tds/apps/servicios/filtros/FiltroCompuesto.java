package umu.tds.apps.servicios.filtros;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.Usuario;

public class FiltroCompuesto implements Filtro {
	
	private final Set<Filtro> filtros;

	public FiltroCompuesto(Set<Filtro> filtros) {
		this.filtros = filtros.stream()
							.filter(Filtro::seFiltra)
							.collect(Collectors.toSet());
	}
	
	@Override
	public Map<Mensaje, Contacto> filtrar(Usuario usuario) {
		Map<Mensaje, Contacto>  resultado = new HashMap<>();
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
