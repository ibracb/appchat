package umu.tds.apps.servicios.descargas;

/**
 * ProveedorRutaDescargasMac es una implementación de ProveedorRutaDescargas que obtiene la ruta de descargas en sistemas operativos Mac.
 */
public class ProveedorRutaDescargasMac implements ProveedorRutaDescargas {
	
	/**
	 * Identificador de sistemas operativos Mac.
	 */
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
