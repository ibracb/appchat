package umu.tds.appchat.services.filtros;

import java.util.List;
import umu.tds.appchat.models.Mensaje;

public class FiltroDecorator implements Filtro {
	
	private Filtro filtro;
	
	public FiltroDecorator(Filtro filtro) {
		super();
		this.filtro = filtro;
	}

	public List<Mensaje> filtrar(List<Mensaje> lista){
		return filtro.filtrar(lista);
	}

}