package umu.tds.apps.servicios.filtros;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.Usuario;

public class FiltroPorMovil implements Filtro {
	
	private String movil;
	
	public FiltroPorMovil(String movil) {
		this.movil = movil;
	}
	
	@Override
	public Set<Mensaje> filtrar(Usuario usuario) {
		return usuario.getContactosIndividuales().stream()
				.filter(contacto -> contacto.getMovil().equals(movil))
				.flatMap(contacto -> contacto.getMensajes().stream())
				.collect(Collectors.toCollection(TreeSet::new));
	}

	@Override
	public boolean seFiltra() {
		return movil != null && !movil.isEmpty() && !movil.isBlank();
	}
	
}
