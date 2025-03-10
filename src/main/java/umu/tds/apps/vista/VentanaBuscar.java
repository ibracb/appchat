package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ImageIcon;

public class VentanaBuscar {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaBuscar window = new VentanaBuscar();
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
	public VentanaBuscar() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 798, 529);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\imagenes\\iconoPestanas.PNG"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelBuscar = new JPanel();
		panelBuscar.setBackground(new Color(242, 216, 245));
		frame.getContentPane().add(panelBuscar, BorderLayout.NORTH);
		GridBagLayout gbl_panelBuscar = new GridBagLayout();
		gbl_panelBuscar.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelBuscar.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panelBuscar.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelBuscar.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelBuscar.setLayout(gbl_panelBuscar);
		
		JLabel ImagenBuscar = new JLabel("");
		ImagenBuscar.setIcon(new ImageIcon("src\\main\\resources\\imagenes\\buscar.png"));
		GridBagConstraints gbc_ImagenBuscar = new GridBagConstraints();
		gbc_ImagenBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_ImagenBuscar.gridx = 4;
		gbc_ImagenBuscar.gridy = 2;
		panelBuscar.add(ImagenBuscar, gbc_ImagenBuscar);
		
		JPanel panelCentral = new JPanel();
		panelCentral.setBackground(new Color(242, 216, 245));
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		
	}

}
