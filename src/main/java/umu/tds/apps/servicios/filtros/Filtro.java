package umu.tds.apps.servicios.filtros;

import java.util.Set;

import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.Usuario;

public interface Filtro {
	
	Set<Mensaje> filtrar(Usuario usuario);
	
	boolean seFiltra();
	
}