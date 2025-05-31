package umu.tds.apps.servicios.filtros;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
	public Set<Mensaje> filtrar(Usuario usuario) {
		Set<Mensaje> resultado = new TreeSet<>(usuario.getAllMensajes());
		for (Filtro filtro : filtros) {
			resultado.retainAll(filtro.filtrar(usuario));
		}
		return resultado;
	}
	
	@Override
	public boolean seFiltra() {
		return !filtros.isEmpty();
	}
	
}
