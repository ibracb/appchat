package dominio.descuentos;

public class DescuentoNull implements Descuento {

	@Override
	public double getDescuento(double precio) {
		return 0;
	}

}
