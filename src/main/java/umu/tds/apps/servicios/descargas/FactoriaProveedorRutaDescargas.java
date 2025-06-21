package umu.tds.apps.servicios.descargas;

import java.util.Set;
import java.util.function.Supplier;

public enum FactoriaProveedorRutaDescargas {
	
	INSTANCE;
	
	private FactoriaProveedorRutaDescargas() {};
	
	private static final Set<Supplier<ProveedorRutaDescargas>> PROVEEDORES = Set.of(
			ProveedorRutaDescargasLinux::new,
			ProveedorRutaDescargasWindows::new,
			ProveedorRutaDescargasMac::new
			);
	
	private ProveedorRutaDescargas getProveedor() {
		return PROVEEDORES.stream()
				.map(Supplier::get)
				.filter(proveedor -> proveedor.isCompatible())
				.findFirst()
				.orElse(null);
	}
	
	public String getRutaDescargas() {
		return getProveedor().getRutaDescargas();
	}
	
}
