package umu.tds.apps.dominio.descuentos;

import java.util.List;
import java.util.function.Supplier;

import umu.tds.apps.dominio.Usuario;

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
	private static final List<Supplier<Descuento>> descuentos = List.of(
			DescuentoPorMensaje::new,
			DescuentoPorFecha::new
			);
	
	/**
	 * Devuelve el descuento a aplicar al usuario.
	 * @param usuario - Usuario al que aplicarle el descuento.
	 * @return el descuento correspondiente.
	 */
	public Descuento createDescuento(Usuario usuario) {
		return descuentos.stream()
				.map(Supplier::get)
				.filter(descuento -> descuento.isAplicable(usuario))
				.findFirst()
				.orElseGet(DescuentoNull::new);
	}
	
}