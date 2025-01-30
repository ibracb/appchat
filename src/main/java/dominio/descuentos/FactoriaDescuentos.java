package dominio.descuentos;

import java.util.List;
import dominio.Usuario;

/**
 * Tiene la responsabilidad de asignar el descuento correspondiente al usuario.
 */
public class FactoriaDescuentos {
	
	/**
	 * Colección de los posibles descuentos especiales a aplicar.
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
	public static Descuento createDescuento(Usuario usuario) {
		return descuentos.stream()
			.filter(descuento -> descuento.isAplicable(usuario))
			.findFirst()
			.orElse(new DescuentoNull());
	}
}