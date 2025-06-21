package umu.tds.apps.dominio.descuentos;

import umu.tds.apps.dominio.Usuario;

/**
 * Clase que modela un tipo de descuento que se aplica en función de la cantidad de mensajes enviados.
 */
public class DescuentoPorMensaje implements Descuento {
	
	/**
	 * Nombre del tipo de descuento por mensaje.
	 */
	private static final String NOMBRE_DESCUENTO = "Descuento por Mensaje";
	
	/**
	 * 10% de descuento que se le aplica al precio inicial, tras haber alcanzado los 16 mensajes en el mes actual.
	 */
	private static final double DESCUENTO_FIJO_MENSAJE = 0.1;
	
	/**
	 * 10% de descuento que se le aplica al precio inicial, por cada 100 mensajes enviados una vez aplicado anteriormente el descuento fijo.
	 */
	private static final double DESCUENTO_ADICIONAL_POR_MENSAJE = 0.05;
	
	/**
	 * Número de mensajes por los que se aplica el descuento fijo.
	 */
	private static final int NUM_MENSAJES_DESCUENTO_FIJO = 16;
	
	@Override
	public boolean isAplicable(Usuario usuario) {
		return usuario.getTotalMensajesEnviadosUltimoMes() >= NUM_MENSAJES_DESCUENTO_FIJO;
	}
	
	@Override
	public int getNumDescuentosAdicionales(Usuario usuario) {
		return (usuario.getTotalMensajesEnviadosUltimoMes() - NUM_MENSAJES_DESCUENTO_FIJO) / 100; 
	}
	
	@Override
	public double getDescuento(double precio, Usuario usuario) {
		return precio*(DESCUENTO_FIJO_MENSAJE + DESCUENTO_ADICIONAL_POR_MENSAJE * getNumDescuentosAdicionales(usuario));
	}

	@Override
	public String getNombreDescuento() {
		return NOMBRE_DESCUENTO;
	}

}