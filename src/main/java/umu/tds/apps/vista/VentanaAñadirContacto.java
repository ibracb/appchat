package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaAñadirContacto {

	private JFrame frame;
	private JTextField textFieldNombre;
	private JTextField textFieldTelefono;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAñadirContacto window = new VentanaAñadirContacto();
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
	public VentanaAñadirContacto() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\imagenes\\iconoPestanas.PNG"));
		frame.setTitle("Añadir contacto");
		frame.setBounds(100, 100, 451, 245);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelCentral = new JPanel();
		panelCentral.setBackground(new Color(242, 216, 245));
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panelCentral.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelCentral.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panelCentral.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelCentral.setLayout(gbl_panelCentral);
		
		JLabel labelFoto = new JLabel("");
		GridBagConstraints gbc_labelFoto = new GridBagConstraints();
		gbc_labelFoto.insets = new Insets(0, 0, 5, 5);
		gbc_labelFoto.gridx = 2;
		gbc_labelFoto.gridy = 3;
		panelCentral.add(labelFoto, gbc_labelFoto);
		
		JLabel labelMensaje = new JLabel("Introduzca nombre y teléfono al contacto:");
		labelMensaje.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_labelMensaje = new GridBagConstraints();
		gbc_labelMensaje.insets = new Insets(0, 0, 5, 5);
		gbc_labelMensaje.gridx = 3;
		gbc_labelMensaje.gridy = 3;
		panelCentral.add(labelMensaje, gbc_labelMensaje);
		
		JLabel labelNombre = new JLabel("nombre");
		labelNombre.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_labelNombre = new GridBagConstraints();
		gbc_labelNombre.anchor = GridBagConstraints.EAST;
		gbc_labelNombre.insets = new Insets(0, 0, 5, 5);
		gbc_labelNombre.gridx = 2;
		gbc_labelNombre.gridy = 5;
		panelCentral.add(labelNombre, gbc_labelNombre);
		
		textFieldNombre = new JTextField();
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.gridx = 3;
		gbc_textFieldNombre.gridy = 5;
		panelCentral.add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(32);
		
		JLabel labelTelefono = new JLabel("teléfono");
		labelTelefono.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_labelTelefono = new GridBagConstraints();
		gbc_labelTelefono.anchor = GridBagConstraints.EAST;
		gbc_labelTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_labelTelefono.gridx = 2;
		gbc_labelTelefono.gridy = 7;
		panelCentral.add(labelTelefono, gbc_labelTelefono);
		
		textFieldTelefono = new JTextField();
		GridBagConstraints gbc_textFieldTelefono = new GridBagConstraints();
		gbc_textFieldTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTelefono.gridx = 3;
		gbc_textFieldTelefono.gridy = 7;
		panelCentral.add(textFieldTelefono, gbc_textFieldTelefono);
		textFieldTelefono.setColumns(32);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBackground(new Color(242, 216, 245));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 4;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.gridx = 2;
		gbc_panel_1.gridy = 8;
		panelCentral.add(panelBotones, gbc_panel_1);
		panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Georgia", Font.BOLD, 12));
		btnAceptar.setPreferredSize(new Dimension(100, 30)); // Establece el tamaño preferido
		panelBotones.add(btnAceptar);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panelBotones.add(horizontalStrut);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Georgia", Font.BOLD, 12));
		btnCancelar.setPreferredSize(new Dimension(100, 30)); // Establece el tamaño preferido
		panelBotones.add(btnCancelar);
	}

}
