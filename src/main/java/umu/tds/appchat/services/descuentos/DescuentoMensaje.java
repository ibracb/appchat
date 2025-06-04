package umu.tds.appchat.services.descuentos;

public class DescuentoMensaje extends Descuento {

	private final static double DESCUENTO_FIDELIDAD = 0.25;
	
	@Override
	protected double calcularDescuento(double precio) {
		
		return (1.0 - DESCUENTO_FIDELIDAD) * precio ;
	
	}

}
