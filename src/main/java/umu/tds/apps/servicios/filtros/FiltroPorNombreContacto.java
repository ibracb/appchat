package umu.tds.apps.servicios.filtros;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.Usuario;

public class FiltroPorNombreContacto implements Filtro {
	
	private String nombreContacto;
	
	public FiltroPorNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}
	
	@Override
	public Set<Mensaje> filtrar(Usuario usuario) {
		return usuario.getContactosIndividuales().stream()
				.filter(contacto -> contacto.getNombre().equalsIgnoreCase(nombreContacto))
				.flatMap(contacto -> contacto.getMensajes().stream())
				.collect(Collectors.toCollection(TreeSet::new));
	}

	@Override
	public boolean seFiltra() {
		return nombreContacto != null && !nombreContacto.isEmpty() && !nombreContacto.isBlank();
	}
	
}
