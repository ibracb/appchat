package umu.tds.apps.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;

public class VentanaPrincipal {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 601, 449);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		panel.add(menuBar);
		
		
		
		JMenu MTuContacto = new JMenu("Nombre");
		// Hacer que el nombre sea el nombre del usuario logeado + apellidos
		MTuContacto.setFont(new Font("Georgia", Font.BOLD, 12));
		MTuContacto.setMaximumSize(new Dimension(200, 200));
		MTuContacto.setAlignmentX(Component.LEFT_ALIGNMENT);
		//MTuContacto.setIcon(new ImageIcon("FOTO QUE NOS HAN PASADO COMO FOTO DE PERFIL"));
		menuBar.add(MTuContacto);
		
		JMenuItem MCambiarImagenPerfil = new JMenuItem("Cambiar imagen de perfil");
		MCambiarImagenPerfil.setFont(new Font("Georgia", Font.PLAIN, 12));
		MTuContacto.add(MCambiarImagenPerfil);
		
		JMenuItem MCerrarSesion = new JMenuItem("Cerrar sesión");
		MCerrarSesion.setFont(new Font("Georgia", Font.PLAIN, 12));
		MTuContacto.add(MCerrarSesion);
		
		JMenuItem MContactos = new JMenuItem("Contactos");
		MContactos.setFont(new Font("Georgia", Font.BOLD, 12));
		MContactos.setMaximumSize(new Dimension(128, 128));
		MContactos.setIcon(new ImageIcon("C:\\Users\\maria\\OneDrive\\Escritorio\\UNI\\3 Carrera\\1 Cuatrimestre\\TDS\\ProjectoTDS\\AppChat\\imagenes\\login.png"));
		menuBar.add(MContactos);
		
		JMenuItem MBuscar = new JMenuItem("Buscar");
		MBuscar.setFont(new Font("Georgia", Font.BOLD, 12));
		MBuscar.setIcon(new ImageIcon("C:\\Users\\maria\\OneDrive\\Escritorio\\UNI\\3 Carrera\\1 Cuatrimestre\\TDS\\ProjectoTDS\\AppChat\\imagenes\\buscar.png"));
		MBuscar.setMaximumSize(new Dimension(128, 128));
		menuBar.add(MBuscar);
		
		JMenuItem MPremium = new JMenuItem("Premium");
		MPremium.setFont(new Font("Georgia", Font.BOLD, 12));
		MPremium.setMaximumSize(new Dimension(128, 128));
		MPremium.setAlignmentX(Component.RIGHT_ALIGNMENT);
		MPremium.setIcon(new ImageIcon("C:\\Users\\maria\\OneDrive\\Escritorio\\UNI\\3 Carrera\\1 Cuatrimestre\\TDS\\ProjectoTDS\\AppChat\\imagenes\\premiumTick.png"));
		MPremium.setActionCommand("MPremium");
		menuBar.add(MPremium);
		
		
	}

}
