package umu.tds.appchat.services.descuentos;

public class DescuentoFecha extends Descuento{
	
	private final static double DESCUENTO_BIENVENIDA = 0.20;

	@Override
	public double calcularDescuento(double precio) {
		return (1.0 - DESCUENTO_BIENVENIDA) * precio ;

	}
	
}
