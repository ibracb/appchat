package umu.tds.apps.servicios.descargas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ProveedorRutaDescargasWindows es una implementación de ProveedorRutaDescargas
 * que obtiene la ruta de la carpeta de descargas en sistemas operativos Windows.
 */
public class ProveedorRutaDescargasWindows implements ProveedorRutaDescargas {
	
	/**
	 * Contrabarra doble utilizada para construir la ruta de descargas. 
	 */
	private static final String DOBLE_CONTRABARRA = "\\";
	
	/**
	 * Comando de PowerShell utilizado para obtener la ruta de la carpeta de descargas.
	 */
	private static final String POWERSHELL = "powershell";
	
	/**
	 * Parámetro para PowerShell que indica que no se debe cargar el perfil del usuario.
	 */
	private static final String NO_PROFILE = "-NoProfile";
	
	/**
	 * Comando de PowerShell que se utiliza para ejecutar el script que obtiene la ruta de la carpeta de descargas.
	 */
	private static final String COMMAND = "-command";
	
	/**
	 * Comando de PowerShell que obtiene la ruta de la carpeta de descargas.
	 * Utiliza el objeto Shell.Application para acceder a la carpeta de descargas.
	 */
	private static final String GUID = "(New-Object -ComObject Shell.Application).NameSpace('shell:Downloads').Self.Path";
	
	/**
	 * Identificador del sistema operativo Windows.
	 * Se utiliza para verificar si el proveedor es compatible con el sistema operativo actual.
	 */
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
