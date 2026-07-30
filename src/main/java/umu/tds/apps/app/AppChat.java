package umu.tds.apps.app;

import java.awt.EventQueue;

import umu.tds.apps.vista.VentanaLogin;

public class AppChat {

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
