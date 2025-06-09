package umu.tds.apps.servicios.filtros;

import java.util.Map;

import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.Usuario;

public interface Filtro {
	
	Map<Mensaje, Contacto> filtrar(Usuario usuario);
	
	boolean seFiltra();
	
}