package umu.tds.apps.dominio.descuentos;

import umu.tds.apps.dominio.RepositorioUsuarios;
import umu.tds.apps.dominio.Usuario;

/**
 * 
 */
public class DescuentoPorMensaje implements Descuento {
	
	/**
	 * 10% de descuento que se le aplica al precio inicial, tras haber alcanzado los 200 mensajes en el mes actual.
	 */
	private static final double DESCUENTO_FIJO_MENSAJE = 0.1;
	
	/**
	 * 10% de descuento que se le aplica al precio inicial, por cada 100 mensajes enviados una vez aplicado anteriormente el descuento fijo.
	 */
	private static final double DESCUENTO_ADICIONAL_POR_MENSAJE = 0.05;
	
	/**
	 * Número de mensajes por los que se aplica el descuento fijo.
	 */
	private static final int NUM_MENSAJES_DESCUENTO_FIJO = 200;
	
	@Override
	public boolean isAplicable(Usuario usuario) {
		return RepositorioUsuarios.getInstance().getTotalMensajesEnviadosUltimoMes(usuario) >= NUM_MENSAJES_DESCUENTO_FIJO;
	}
	
	@Override
	public int getNumDescuentosAdicionales(Usuario usuario) {
		return (RepositorioUsuarios.getInstance().getTotalMensajesEnviadosUltimoMes(usuario) - NUM_MENSAJES_DESCUENTO_FIJO) / 100; 
	}
	
	@Override
	public double getDescuento(double precio, Usuario usuario) {
		return precio*(DESCUENTO_FIJO_MENSAJE + DESCUENTO_ADICIONAL_POR_MENSAJE * getNumDescuentosAdicionales(usuario));
	}

}