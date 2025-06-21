package umu.tds.apps.servicios.filtros;

import java.util.Map;

import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.Usuario;

/**
 * Interfaz que define un filtro para mensajes de un usuario.
 * Implementaciones concretas deben proporcionar la lógica de filtrado.
 */
public interface Filtro {
	
	/**
	 * Filtra los mensajes de un usuario y devuelve un mapa de mensajes filtrados
	 * asociados a sus respectivos contactos.
	 *
	 * @param usuario el usuario cuyos mensajes se van a filtrar
	 * @return un mapa donde las claves son los mensajes filtrados y los valores
	 *         son los contactos asociados
	 */
	Map<Mensaje, Contacto> filtrar(Usuario usuario);
	
	/**
	 * Indica si el filtro se aplica o no.
	 *
	 * @return true si el filtro se aplica, false en caso contrario
	 */
	boolean seFiltra();
	
}