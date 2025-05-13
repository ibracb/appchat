package umu.tds.apps.servicios.descargas;

public class ProveedorRutaDescargasMac implements ProveedorRutaDescargas {
	
	private static final String MAC = "mac";
	
	@Override
	public String getRutaDescargas() {
		return (System.getProperty(DescargasUtils.HOME) + DescargasUtils.BARRA + DescargasUtils.DOWNLOADS).trim();
	}

	@Override
	public boolean isCompatible() {
		return System.getProperty(DescargasUtils.SISTEMA_OPERATIVO).toLowerCase().contains(MAC);
	}

}
