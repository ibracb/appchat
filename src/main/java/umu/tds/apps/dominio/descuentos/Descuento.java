package umu.tds.apps.dominio.descuentos;

import umu.tds.apps.dominio.Usuario;

/**
 * Interfaz que declara las funcionalidades genéricas de un Descuento.
 */
public interface Descuento {
	
	/**
	 * Comprueba si a un usuario se le puede aplicar el descuento.
	 * @param usuario - El usuario al que se le aplicará (o no) el descuento.
	 * @return true si el usuario cumple las condiciones para aplicarle el descuento, false si no las cumple.
	 */
	public boolean isAplicable(Usuario usuario);
	
	/**
	 * Devuelve cuántos descuentos adicionales se le pueden aplicar al usuario
	 * @param usuario - El usuario al que se le aplicarían los descuentos adicionales
	 * @return El número de descuentos adicionales finalmente aplicados
	 */
	public int getNumDescuentosAdicionales(Usuario usuario);
	
	/**
	 * Devuelve el valor descontado al precio inicial, dadas las condiciones del usuario.
	 * @param precio - El precio inicial
	 * @param usuario - El usuario al que se le aplica el descuento
	 * @return El descuento finalmente aplicado al usuario
	 */
	public double getDescuento(double precio, Usuario usuario);
}
