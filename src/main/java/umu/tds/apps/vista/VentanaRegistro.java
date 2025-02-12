package umu.tds.apps.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

import com.toedter.calendar.JDateChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JDesktopPane;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JRadioButton;
import java.awt.Component;
import javax.swing.Box;

public class VentanaRegistro {

	private JFrame frameRegistro;
	private JLabel etiquetaImagen;
	private JTextField textFieldNombre;
	private JTextField textFieldApellidos;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JTextField textFieldImagenPerfil;
	private JTextField textFieldTelefono;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRegistro window = new VentanaRegistro();
					window.frameRegistro.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaRegistro() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameRegistro = new JFrame();
		frameRegistro.getContentPane().setBackground(new Color(242, 216, 245));
		frameRegistro.setBounds(100, 100, 613, 464);
		frameRegistro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(242, 216, 245));
		frameRegistro.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 110, 0, 0, 110, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 34, 40, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0};
		panel.setLayout(gridBagLayout);
		
		JLabel etiquetaNombre = new JLabel("Nombre");
		etiquetaNombre.setFont(new Font("Georgia", Font.BOLD, 12));
		etiquetaNombre.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_etiquetaNombre = new GridBagConstraints();
		gbc_etiquetaNombre.anchor = GridBagConstraints.EAST;
		gbc_etiquetaNombre.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaNombre.gridx = 1;
		gbc_etiquetaNombre.gridy = 1;
		panel.add(etiquetaNombre, gbc_etiquetaNombre);
		
		textFieldNombre = new JTextField();
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.gridwidth = 4;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.gridx = 2;
		gbc_textFieldNombre.gridy = 1;
		panel.add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel etiquetaApellidos = new JLabel("Apellidos");
		etiquetaApellidos.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_etiquetaApellidos = new GridBagConstraints();
		gbc_etiquetaApellidos.anchor = GridBagConstraints.EAST;
		gbc_etiquetaApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaApellidos.gridx = 1;
		gbc_etiquetaApellidos.gridy = 2;
		panel.add(etiquetaApellidos, gbc_etiquetaApellidos);
		
		textFieldApellidos = new JTextField();
		GridBagConstraints gbc_textFieldApellidos = new GridBagConstraints();
		gbc_textFieldApellidos.gridwidth = 4;
		gbc_textFieldApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldApellidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldApellidos.gridx = 2;
		gbc_textFieldApellidos.gridy = 2;
		panel.add(textFieldApellidos, gbc_textFieldApellidos);
		textFieldApellidos.setColumns(10);
		
		JLabel etiquetaTelefono = new JLabel("Teléfono");
		etiquetaTelefono.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_etiquetaTelefono = new GridBagConstraints();
		gbc_etiquetaTelefono.anchor = GridBagConstraints.EAST;
		gbc_etiquetaTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaTelefono.gridx = 1;
		gbc_etiquetaTelefono.gridy = 3;
		panel.add(etiquetaTelefono, gbc_etiquetaTelefono);
		
		textFieldTelefono = new JTextField();
		GridBagConstraints gbc_textFieldTelefono = new GridBagConstraints();
		gbc_textFieldTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTelefono.gridx = 2;
		gbc_textFieldTelefono.gridy = 3;
		panel.add(textFieldTelefono, gbc_textFieldTelefono);
		textFieldTelefono.setColumns(10);
		
		JLabel etiquetaContrasena1 = new JLabel("Contraseña");
		etiquetaContrasena1.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_etiquetaContrasena1 = new GridBagConstraints();
		gbc_etiquetaContrasena1.anchor = GridBagConstraints.EAST;
		gbc_etiquetaContrasena1.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaContrasena1.gridx = 1;
		gbc_etiquetaContrasena1.gridy = 4;
		panel.add(etiquetaContrasena1, gbc_etiquetaContrasena1);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 4;
		panel.add(passwordField, gbc_passwordField);
		
		JLabel etiquetaContrasena2 = new JLabel("Contraseña");
		etiquetaContrasena2.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_etiquetaContrasena2 = new GridBagConstraints();
		gbc_etiquetaContrasena2.anchor = GridBagConstraints.EAST;
		gbc_etiquetaContrasena2.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaContrasena2.gridx = 4;
		gbc_etiquetaContrasena2.gridy = 4;
		panel.add(etiquetaContrasena2, gbc_etiquetaContrasena2);
		
		passwordField_1 = new JPasswordField();
		GridBagConstraints gbc_passwordField_1 = new GridBagConstraints();
		gbc_passwordField_1.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_1.gridx = 5;
		gbc_passwordField_1.gridy = 4;
		panel.add(passwordField_1, gbc_passwordField_1);
		
		JLabel etiquetaFechaNacimiento = new JLabel("Fecha de nacimiento");
		etiquetaFechaNacimiento.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_etiquetaFechaNacimiento = new GridBagConstraints();
		gbc_etiquetaFechaNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaFechaNacimiento.gridx = 1;
		gbc_etiquetaFechaNacimiento.gridy = 5;
		panel.add(etiquetaFechaNacimiento, gbc_etiquetaFechaNacimiento);
		
		JDateChooser dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 2;
		gbc_dateChooser.gridy = 5;
		panel.add(dateChooser, gbc_dateChooser);
		
		etiquetaImagen = new JLabel("Imagen");
		etiquetaImagen.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_etiquetaImagen = new GridBagConstraints();
		gbc_etiquetaImagen.anchor = GridBagConstraints.EAST;
		gbc_etiquetaImagen.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaImagen.gridx = 4;
		gbc_etiquetaImagen.gridy = 5;
		panel.add(etiquetaImagen, gbc_etiquetaImagen);
		
		textFieldImagenPerfil = new JTextField();
		GridBagConstraints gbc_textFieldImagenPerfil = new GridBagConstraints();
		gbc_textFieldImagenPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldImagenPerfil.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldImagenPerfil.gridx = 5;
		gbc_textFieldImagenPerfil.gridy = 5;
		panel.add(textFieldImagenPerfil, gbc_textFieldImagenPerfil);
		textFieldImagenPerfil.setColumns(10);
		
		JLabel etiquetaSaludo = new JLabel("Saludo");
		etiquetaSaludo.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_etiquetaSaludo = new GridBagConstraints();
		gbc_etiquetaSaludo.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaSaludo.gridx = 1;
		gbc_etiquetaSaludo.gridy = 6;
		panel.add(etiquetaSaludo, gbc_etiquetaSaludo);
		
		
		//TODO Mirar el saludo a ver como hacer un scroll panel
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 6;
		panel.add(scrollPane, gbc_scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setMinimumSize(new Dimension(170, 170));
		lblNewLabel.setMaximumSize(new Dimension(200, 200));
		ImageIcon imagen = new ImageIcon(new ImageIcon("src\\main\\resources\\imagenes\\appchatLogoGrande3.PNG").getImage());
		lblNewLabel.setIcon(imagen);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 5;
		gbc_lblNewLabel.gridy = 6;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBackground(new Color(242, 216, 245));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 4;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.gridx = 2;
		gbc_panel_1.gridy = 7;
		panel.add(panelBotones, gbc_panel_1);
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
