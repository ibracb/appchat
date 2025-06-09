package umu.tds.apps.servicios.filtros;

import java.util.HashMap;
import java.util.Map;

import java.util.stream.Collectors;

import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.Usuario;

public class FiltroPorTexto implements Filtro {
	
	private String texto;
	
	public FiltroPorTexto(String texto) {
		this.texto = texto;
	}
	
	@Override	 
	public Map<Mensaje, Contacto> filtrar(Usuario usuario) {
	    return usuario.getContactosIndividuales().stream()
	    		.flatMap(contacto -> contacto.getMensajes().stream()
	            .filter(mensaje -> mensaje.getTexto().toLowerCase().contains(texto.toLowerCase()))
	            .map(mensaje -> Map.entry(mensaje, contacto)))
	    		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (m1, m2) -> m1, HashMap::new));
	}

	@Override
	public boolean seFiltra() {
		return texto != null && !texto.isEmpty() && !texto.isBlank();
	}

}
