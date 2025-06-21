package umu.tds.apps.aplicacion;

import java.awt.EventQueue;

import umu.tds.apps.vista.VentanaLogin;

/**
 * Aplicación de chat.
 * 
 * Esta aplicación permite a los usuarios iniciar sesión y participar en un chat.
 * Utiliza una interfaz gráfica para la interacción del usuario.
 * 
 */
public class AppChat {
	
	/**
	 * Método principal que inicia la aplicación.
	 * Carga los datos iniciales y muestra la ventana de inicio de sesión.
	 * 
	 * @param args Argumentos de línea de comandos (no se utilizan en esta aplicación).
	 */
	public static void main(final String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// Cargar datos iniciales
				DataLoader.cargarDatosIniciales();
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