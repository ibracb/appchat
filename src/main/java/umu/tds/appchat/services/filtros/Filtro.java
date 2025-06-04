package umu.tds.appchat.services.filtros;

import java.util.List;
import umu.tds.appchat.models.Mensaje;

public interface Filtro {
	
	public List<Mensaje> filtrar(List<Mensaje> lista);

}
