package umu.tds.apps.servicios.filtros;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.Usuario;

public class FiltroPorNombreContacto implements Filtro {
	
	private String nombreContacto;
	
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
