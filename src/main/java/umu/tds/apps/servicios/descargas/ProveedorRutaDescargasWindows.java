package umu.tds.apps.servicios.descargas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProveedorRutaDescargasWindows implements ProveedorRutaDescargas {
	
	private static final String DOBLE_CONTRABARRA = "\\";
	private static final String POWERSHELL = "powershell";
	private static final String NO_PROFILE = "-NoProfile";
	private static final String COMMAND = "-command";
	private static final String GUID = "(New-Object -ComObject Shell.Application).NameSpace('shell:Downloads').Self.Path";
	private static final String WIN = "win";
	
	@Override
	public String getRutaDescargas() {
		try {
			ProcessBuilder builder = new ProcessBuilder(
				POWERSHELL,
				NO_PROFILE,
				COMMAND,
				GUID
			);
			builder.redirectErrorStream(true);
			Process proceso = builder.start();
			try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(proceso.getInputStream()))) {
				return reader.readLine().trim();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return System.getProperty(DescargasUtils.HOME) + DOBLE_CONTRABARRA + DescargasUtils.DOWNLOADS;
	}

	@Override
	public boolean isCompatible() {
		return System.getProperty(DescargasUtils.SISTEMA_OPERATIVO).toLowerCase().contains(WIN);
	}

}
