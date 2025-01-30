package dominio.descuentos;

import java.time.temporal.ChronoUnit;

import dominio.Usuario;
import utils.Utils;

/**
 * 
 */
public class DescuentoPorFecha implements Descuento {
	
	/**
	 * 10% de descuento que se le aplica al precio inicial, tras cumplirse 1 año de registro en AppChat.
	 */
	private static final double DESCUENTO_FIJO_FECHA = 0.1;
	
	/**
	 * 1% de descuento que se le aplica al precio inicial por cada año de registro cumplido, tras haberse aplicado el descuento fijo.
	 */
	private static final double DESCUENTO_ADICIONAL_POR_FECHA = 0.01;
	
	/**
	 * Entero que representa el número de días que hay en un año estándar.
	 */
	private static final int NUM_DIAS_AÑO = 365;
	
	@Override
	public boolean isAplicable(Usuario usuario) {
		return ChronoUnit.DAYS.between(usuario.getFechaRegistro(), Utils.FECHA_ACTUAL) >= NUM_DIAS_AÑO;
	}
	
	@Override
	public int getNumDescuentosAdicionales(Usuario usuario) {
		return (int) ChronoUnit.DAYS.between(usuario.getFechaRegistro(), Utils.FECHA_ACTUAL) / NUM_DIAS_AÑO;
	}
	
	@Override
	public double getDescuento(double precio, Usuario usuario) {
		return precio*(DESCUENTO_FIJO_FECHA + DESCUENTO_ADICIONAL_POR_FECHA * getNumDescuentosAdicionales(usuario));
	}

}