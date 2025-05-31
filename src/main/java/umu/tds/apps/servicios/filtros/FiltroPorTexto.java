package umu.tds.apps.servicios.filtros;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.Usuario;

public class FiltroPorTexto implements Filtro {
	
	private String texto;
	
	public FiltroPorTexto(String texto) {
		this.texto = texto;
	}
	
	@Override
	public Set<Mensaje> filtrar(Usuario usuario) {
		return usuario.getContactosIndividuales().stream()
				.flatMap(contacto -> contacto.getMensajes().stream())
				.filter(mensaje -> mensaje.getTexto().toLowerCase().contains(texto.toLowerCase()))
				.collect(Collectors.toCollection(TreeSet::new));
	}

	@Override
	public boolean seFiltra() {
		return texto != null && !texto.isEmpty() && !texto.isBlank();
	}

}
