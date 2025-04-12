package umu.tds.apps.vista;

import javax.swing.JFrame;

public final class VistaUtils {
	
	protected static void transicionar(JFrame actual, JFrame objetivo) {
		objetivo.setBounds(actual.getBounds());
		objetivo.setExtendedState(actual.getExtendedState());
		objetivo.setVisible(true);
	}
	
}
