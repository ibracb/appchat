package umu.tds.apps.aplicacion;

import java.awt.EventQueue;

import umu.tds.apps.vista.VentanaLogin;

/**
 * Clase a la que se le delega la responsabilidad de iniciar la aplicación.
 */
public class AppChat {
	
	/**
	 * Método que arranca AppChat.
	 * @param args - Almacena los argumentos que se pasan al ejecutar el programa desde la línea de comandos.
	 */
	public static void main(final String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin ventana = new VentanaLogin();
					ventana.setVisible(true);
					ventana.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}