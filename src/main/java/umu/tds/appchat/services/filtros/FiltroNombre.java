package umu.tds.appchat.services.filtros;

import java.util.List;
import umu.tds.appchat.models.Mensaje;
import java.util.stream.Collectors;

public class FiltroNombre extends FiltroDecorator {

	private String filterer;

	public FiltroNombre(Filtro filtro, String filterer) {
		super(filtro);
		this.filterer = filterer;
	}

	@Override
	public List<Mensaje> filtrar(List<Mensaje> lista) {
		List<Mensaje> l = lista.stream()
				.filter(m -> m.getEmisor().getNombre().equals(filterer) || m.getReceptor().getNombre().equals(filterer))
				.collect(Collectors.toList());
		return super.filtrar(l);
	}

}
