package dominio.descuentos;

import dominio.Usuario;

/**
 * 
 */
public class DescuentoNull implements Descuento {
	
	/**
	 * Entero que representa que un usuario no disfrutará de ningún descuento adicional.
	 */
	private static final int CERO_DESCUENTOS_ADICIONALES = 0;
	
	/**
	 * Número que representa una cantidad nula de descuento asignado al usuario.
	 */
	private static final double DESCUENTO_INEXISTENTE = 0.0;
	
	@Override
	public boolean isAplicable(Usuario usuario) {
		return true;
	}
	
	@Override
	public int getNumDescuentosAdicionales(Usuario usuario) {
		return CERO_DESCUENTOS_ADICIONALES;
	}
	
	@Override
	public double getDescuento(double precio, Usuario usuario) {
		return DESCUENTO_INEXISTENTE;
	}

}