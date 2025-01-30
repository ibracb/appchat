package dominio.descuentos;

import java.util.List;
import dominio.Usuario;

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
	 * Devuelve la única instancia de FactoriaDescuentos.
	 * @return la única instancia FactoriaDescuentos
	 */
	public static FactoriaDescuentos getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Lista de los posibles descuentos especiales que se pueden aplicar a un usuario de AppChat. 
	 */
	private static final List<Descuento> descuentos = List.of(
			new DescuentoPorMensaje(),
			new DescuentoPorFecha()
		);
	
	/**
	 * Devuelve el descuento a aplicar al usuario.
	 * @param usuario - Usuario al que aplicarle el descuento.
	 * @return el descuento correspondiente.
	 */
	public Descuento createDescuento(Usuario usuario) {
		return descuentos.stream()
			.filter(descuento -> descuento.isAplicable(usuario))
			.findFirst()
			.orElse(new DescuentoNull());
	}
	
}