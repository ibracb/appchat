package umu.tds.apps.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JPasswordField;
import java.awt.Dimension;
import java.awt.Color;

public class VentanaLogin {

	private JFrame frameLogin;
	private JTextField textFieldTelefono;
	private JLabel EtiquetaContrasena;
	private JTextField textField;
	private JTextField textField_Telefono;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin window = new VentanaLogin();
					window.frameLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaLogin() {
		initialize();
	}
	
	/**
	 * 
	 */
	public void show() {
		frameLogin.setLocationRelativeTo(null);
		frameLogin.setVisible(true);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameLogin = new JFrame();
		frameLogin.setTitle("AppChat");
		frameLogin.setBounds(100, 100, 613, 464);
		frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameLogin.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelCentral = new JPanel();
		panelCentral.setBackground(new Color(242, 216, 245));
		frameLogin.getContentPane().add(panelCentral, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[]{Integer.MIN_VALUE, 0, 90, 0, 0, 0, 128, 62, 0, 0, 0};
		gbl_panelCentral.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelCentral.columnWeights = new double[]{Double.MIN_VALUE, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelCentral.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panelCentral.setLayout(gbl_panelCentral);
		
		JLabel imagenAppChat = new JLabel("");
		imagenAppChat.setMaximumSize(new Dimension(200, 200));
		imagenAppChat.setIcon(new ImageIcon("C:\\Users\\maria\\OneDrive\\Escritorio\\UNI\\3 Carrera\\1 Cuatrimestre\\TDS\\ProjectoTDS\\AppChat\\imagenes\\appchatLogoGrande3.PNG"));
		GridBagConstraints gbc_imagenAppChat = new GridBagConstraints();
		gbc_imagenAppChat.gridwidth = 5;
		gbc_imagenAppChat.insets = new Insets(10, 10, 10, 10);
		gbc_imagenAppChat.gridx = 2;
		gbc_imagenAppChat.gridy = 1;
		panelCentral.add(imagenAppChat, gbc_imagenAppChat);
		
		JLabel etiquetaTelefono = new JLabel("Teléfono");
		etiquetaTelefono.setFont(new Font("Georgia", Font.BOLD, 12));
		etiquetaTelefono.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_etiquetaTelefono = new GridBagConstraints();
		gbc_etiquetaTelefono.anchor = GridBagConstraints.EAST;
		gbc_etiquetaTelefono.insets = new Insets(10, 10, 10, 10);
		gbc_etiquetaTelefono.gridx = 2;
		gbc_etiquetaTelefono.gridy = 3;
		panelCentral.add(etiquetaTelefono, gbc_etiquetaTelefono);
		
		textField_Telefono = new JTextField();
		GridBagConstraints gbc_textField_Telefono = new GridBagConstraints();
		gbc_textField_Telefono.gridwidth = 4;
		gbc_textField_Telefono.insets = new Insets(10, 10, 10, 10);
		gbc_textField_Telefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Telefono.gridx = 3;
		gbc_textField_Telefono.gridy = 3;
		panelCentral.add(textField_Telefono, gbc_textField_Telefono);
		textField_Telefono.setColumns(10);
		
		JLabel etiquetaContrasena = new JLabel("Contraseña");
		etiquetaContrasena.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_etiquetaContrasena = new GridBagConstraints();
		gbc_etiquetaContrasena.insets = new Insets(10, 10, 10, 10);
		gbc_etiquetaContrasena.anchor = GridBagConstraints.EAST;
		gbc_etiquetaContrasena.gridx = 2;
		gbc_etiquetaContrasena.gridy = 4;
		panelCentral.add(etiquetaContrasena, gbc_etiquetaContrasena);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 4;
		gbc_passwordField.insets = new Insets(10, 10, 10, 10);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 3;
		gbc_passwordField.gridy = 4;
		panelCentral.add(passwordField, gbc_passwordField);
		
		JButton botonRegistrar = new JButton("Registrar");
		botonRegistrar.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_botonRegistrar = new GridBagConstraints();
		gbc_botonRegistrar.insets = new Insets(10, 10, 10, 10);
		gbc_botonRegistrar.gridx = 3;
		gbc_botonRegistrar.gridy = 5;
		botonRegistrar.setPreferredSize(new Dimension(100, 30));
		panelCentral.add(botonRegistrar, gbc_botonRegistrar);
		
		JButton botonAceptar = new JButton("Aceptar");
		botonAceptar.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_botonAceptar = new GridBagConstraints();
		gbc_botonAceptar.anchor = GridBagConstraints.EAST;
		gbc_botonAceptar.insets = new Insets(10, 10, 10, 10);
		gbc_botonAceptar.gridx = 6;
		gbc_botonAceptar.gridy = 5;
		botonAceptar.setPreferredSize(new Dimension(100, 30));
		panelCentral.add(botonAceptar, gbc_botonAceptar);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		GridBagConstraints gbc_horizontalGlue = new GridBagConstraints();
		gbc_horizontalGlue.insets = new Insets(10, 10, 10, 10);
		gbc_horizontalGlue.gridx = 4;
		gbc_horizontalGlue.gridy = 7;
		panelCentral.add(horizontalGlue, gbc_horizontalGlue);
		
		
		
		
		
	}

}
