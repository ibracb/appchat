package umu.tds.apps.servicios.descargas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProveedorRutaDescargasLinux implements ProveedorRutaDescargas {
	
	private static final String XDG_USER_DIR = "xdg-user-dir";
	private static final String DOWNLOAD = "DOWNLOAD";
	private static final String NIX = "nix";
	private static final String NUX = "nux";
	
	@Override
	public String getRutaDescargas() {
		try {
			ProcessBuilder builder = new ProcessBuilder(XDG_USER_DIR, DOWNLOAD);
			builder.redirectErrorStream(true);
			Process proceso = builder.start();
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(proceso.getInputStream()))) {
				return reader.readLine().trim();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return System.getProperty(DescargasUtils.HOME) + DescargasUtils.BARRA + DescargasUtils.DOWNLOADS;
	}

	@Override
	public boolean isCompatible() {
		String os = System.getProperty(DescargasUtils.SISTEMA_OPERATIVO).toLowerCase();
		return os.contains(NIX) || os.contains(NUX);
	}

}
