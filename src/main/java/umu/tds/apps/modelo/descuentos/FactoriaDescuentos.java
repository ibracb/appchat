package umu.tds.apps.modelo.descuentos;

import java.util.List;
import java.util.function.Supplier;

import umu.tds.apps.modelo.Usuario;

/**
 * Tiene la responsabilidad de asignar el descuento correspondiente al usuario.
 */
public enum FactoriaDescuentos {
	
	/**
	 * Punto de acceso global. Única instancia que crea los descuentos.
	 */
	INSTANCE;
	
	/**
	 * Constructor privado de FactoriaDescuentos.
	 */
	private FactoriaDescuentos() {}
	
	/**
	 * Lista de los posibles descuentos especiales que se pueden aplicar a un usuario de AppChat. 
	 */
	private static final List<Supplier<Descuento>> DESCUENTOS = List.of(
			DescuentoPorMensaje::new,
			DescuentoPorFecha::new
			);
	
	/**
	 * Devuelve el descuento a aplicar al usuario.
	 * @param usuario - Usuario al que aplicarle el descuento.
	 * @return el descuento correspondiente.
	 */
	public Descuento createDescuento(Usuario usuario) {
		return DESCUENTOS.stream()
				.map(Supplier::get)
				.filter(descuento -> descuento.isAplicable(usuario))
				.findFirst()
				.orElseGet(DescuentoNull::new);
	}
	
}