package umu.tds.appchat.services.filtros;

import java.util.List;
import java.util.stream.Collectors;

import umu.tds.appchat.models.Mensaje;


public class FiltroTelefono extends FiltroDecorator {
	
	private String filterer;
	
	public FiltroTelefono(Filtro filtro, String filterer) {
		super(filtro);
		this.filterer = filterer;
	}

	public List<Mensaje> filtrar(List<Mensaje> lista){
		List<Mensaje> l = lista.stream()
				.filter(m -> m.getEmisor().getTelefono().equals(filterer) || m.getReceptor().getTelefono().equals(filterer))
				.collect(Collectors.toList());
		return super.filtrar(l);
	}

}