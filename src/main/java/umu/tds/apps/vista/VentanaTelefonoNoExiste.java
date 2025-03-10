package umu.tds.apps.vista;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;

public class VentanaTelefonoNoExiste {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaTelefonoNoExiste window = new VentanaTelefonoNoExiste();
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
	public VentanaTelefonoNoExiste() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\imagenes\\iconoPestanas.PNG"));
		frame.setTitle("Teléfono inexistente!!");
		frame.setBounds(100, 100, 451, 245);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelCentral = new JPanel();
		panelCentral.setBackground(new Color(242, 216, 245));
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelCentral.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panelCentral.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelCentral.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelCentral.setLayout(gbl_panelCentral);
		
		JLabel labelFoto = new JLabel("");
		ImageIcon imagen = new ImageIcon(new ImageIcon("src\\main\\resources\\imagenes\\senalAdvertencia.png").getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
		labelFoto.setIcon(imagen);
		GridBagConstraints gbc_labelFoto = new GridBagConstraints();
		gbc_labelFoto.gridheight = 2;
		gbc_labelFoto.insets = new Insets(0, 0, 5, 5);
		gbc_labelFoto.gridx = 3;
		gbc_labelFoto.gridy = 1;
		panelCentral.add(labelFoto, gbc_labelFoto);
		
		JLabel labelMensaje = new JLabel("¡El teléfono indicado no existe!");
		labelMensaje.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_labelMensaje = new GridBagConstraints();
		gbc_labelMensaje.gridwidth = 2;
		gbc_labelMensaje.insets = new Insets(0, 0, 5, 5);
		gbc_labelMensaje.gridx = 4;
		gbc_labelMensaje.gridy = 1;
		panelCentral.add(labelMensaje, gbc_labelMensaje);
		

		JButton okButton = new JButton("Aceptar");
		okButton.setFont(new Font("Georgia", Font.BOLD, 12));
		okButton.setActionCommand("Aceptar");
		GridBagConstraints gbc_okButton = new GridBagConstraints();
		gbc_okButton.anchor = GridBagConstraints.EAST;
		gbc_okButton.insets = new Insets(0, 0, 5, 5);
		gbc_okButton.gridx = 5;
		gbc_okButton.gridy = 2;
		panelCentral.add(okButton, gbc_okButton);
	}

}
