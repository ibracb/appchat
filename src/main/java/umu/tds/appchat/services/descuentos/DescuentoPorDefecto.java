package umu.tds.appchat.services.descuentos;

public class DescuentoPorDefecto extends Descuento {

	@Override
	protected double calcularDescuento(double precio) {
		return precio;
	}
}
