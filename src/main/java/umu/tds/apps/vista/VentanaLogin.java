package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import umu.tds.apps.controlador.Controlador;


public class VentanaLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField textFieldTelefono;
	private JPasswordField passwordField;
	private JLabel etiquetaTelefono;
	private JLabel etiquetaContrasena;
	private JLabel imagenAppChat;
	private ImageIcon imagenLogo;
	private GridBagLayout gbl_panelCentral;
	private GridBagConstraints gbc_imagenAppChat;
	private GridBagConstraints gbc_etiquetaTelefono;
	private GridBagConstraints gbc_textFieldTelefono;
	private GridBagConstraints gbc_etiquetaContrasena;
	private GridBagConstraints gbc_passwordField;
	private GridBagConstraints gbc_botonRegistrar;
	private GridBagConstraints gbc_botonAceptar;
	private GridBagConstraints gbc_horizontalGlue;
	private Component horizontalGlue;
	private JPanel panelCentral;
	private JButton botonRegistrar;
	private JButton botonAceptar;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin window = new VentanaLogin();
					window.setVisible(true);
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
	public void mostrarLogin(Dimension tam, Point ubi) {
		setVisible(true);
		setSize(tam);
		setLocation(ubi);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setIconImage(new ImageIcon(getClass().getResource("/imagenes/iconoPestanas.PNG")).getImage());
		setTitle("AppChat");
		setBounds(100, 100, 613, 464);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		panelCentral = new JPanel();
		panelCentral.setBackground(new Color(242, 216, 245));
		getContentPane().add(panelCentral, BorderLayout.CENTER);
		gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[] { Integer.MIN_VALUE, 0, 90, 0, 0, 0, 128, 62, 0, 0, 0 };
		gbl_panelCentral.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panelCentral.columnWeights = new double[] { Double.MIN_VALUE, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panelCentral.rowWeights = new double[] { 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panelCentral.setLayout(gbl_panelCentral);

		imagenAppChat = new JLabel("");
		imagenAppChat.setMaximumSize(new Dimension(200, 200));
		imagenLogo = new ImageIcon(getClass().getResource("/imagenes/appchatLogoGrande3.PNG"));
		imagenAppChat.setIcon(imagenLogo);
		gbc_imagenAppChat = new GridBagConstraints();
		gbc_imagenAppChat.gridwidth = 5;
		gbc_imagenAppChat.insets = new Insets(10, 10, 10, 10);
		gbc_imagenAppChat.gridx = 2;
		gbc_imagenAppChat.gridy = 1;
		panelCentral.add(imagenAppChat, gbc_imagenAppChat);

		etiquetaTelefono = new JLabel("Teléfono");
		etiquetaTelefono.setFont(new Font("Georgia", Font.BOLD, 12));
		etiquetaTelefono.setHorizontalAlignment(SwingConstants.TRAILING);
		gbc_etiquetaTelefono = new GridBagConstraints();
		gbc_etiquetaTelefono.anchor = GridBagConstraints.EAST;
		gbc_etiquetaTelefono.insets = new Insets(10, 10, 10, 10);
		gbc_etiquetaTelefono.gridx = 2;
		gbc_etiquetaTelefono.gridy = 3;
		panelCentral.add(etiquetaTelefono, gbc_etiquetaTelefono);

		textFieldTelefono = new JTextField();
		gbc_textFieldTelefono = new GridBagConstraints();
		gbc_textFieldTelefono.gridwidth = 4;
		gbc_textFieldTelefono.insets = new Insets(10, 10, 10, 10);
		gbc_textFieldTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTelefono.gridx = 3;
		gbc_textFieldTelefono.gridy = 3;
		panelCentral.add(textFieldTelefono, gbc_textFieldTelefono);
		textFieldTelefono.setColumns(10);

		etiquetaContrasena = new JLabel("Contraseña");
		etiquetaContrasena.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_etiquetaContrasena = new GridBagConstraints();
		gbc_etiquetaContrasena.insets = new Insets(10, 10, 10, 10);
		gbc_etiquetaContrasena.anchor = GridBagConstraints.EAST;
		gbc_etiquetaContrasena.gridx = 2;
		gbc_etiquetaContrasena.gridy = 4;
		panelCentral.add(etiquetaContrasena, gbc_etiquetaContrasena);

		passwordField = new JPasswordField();
		gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 4;
		gbc_passwordField.insets = new Insets(10, 10, 10, 10);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 3;
		gbc_passwordField.gridy = 4;
		panelCentral.add(passwordField, gbc_passwordField);

		botonRegistrar = new JButton("Registrar");
		botonRegistrar.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_botonRegistrar = new GridBagConstraints();
		gbc_botonRegistrar.insets = new Insets(10, 10, 10, 10);
		gbc_botonRegistrar.gridx = 3;
		gbc_botonRegistrar.gridy = 5;
		botonRegistrar.setPreferredSize(new Dimension(100, 30));
		botonRegistrar.addActionListener(e -> accederRegistro());
		panelCentral.add(botonRegistrar, gbc_botonRegistrar);

		botonAceptar = new JButton("Aceptar");
		botonAceptar.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_botonAceptar = new GridBagConstraints();
		gbc_botonAceptar.anchor = GridBagConstraints.EAST;
		gbc_botonAceptar.insets = new Insets(10, 10, 10, 10);
		gbc_botonAceptar.gridx = 6;
		gbc_botonAceptar.gridy = 5;
		botonAceptar.setPreferredSize(new Dimension(100, 30));
		botonAceptar.addActionListener(e -> accederLogin());
		panelCentral.add(botonAceptar, gbc_botonAceptar);

		horizontalGlue = Box.createHorizontalGlue();
		gbc_horizontalGlue = new GridBagConstraints();
		gbc_horizontalGlue.insets = new Insets(10, 10, 10, 10);
		gbc_horizontalGlue.gridx = 4;
		gbc_horizontalGlue.gridy = 7;
		panelCentral.add(horizontalGlue, gbc_horizontalGlue);

	}
	
	private void accederLogin() {
		String movil = textFieldTelefono.getText();
		String contrasena = new String(passwordField.getPassword());
		if (movil.isEmpty() || contrasena.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Falta algún campo por completar: teléfono y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
		if(Controlador.INSTANCE.loginUsuario(movil, contrasena)) {
			JOptionPane.showMessageDialog(this, "¡Hola de nuevo, " + Controlador.INSTANCE.getUsuarioActual().getNombre() + "!");
			VentanaPrincipal vPrincipal = new VentanaPrincipal();
			dispose();
			vPrincipal.mostrarVentanaPrincipal(this.getSize(), this.getLocation());;
		}
		else {
			JOptionPane.showMessageDialog(this, "Login fallido. Inténtelo de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void accederRegistro() {
		VentanaRegistro registro = new VentanaRegistro();
		dispose();
		registro.mostrarRegistro(this.getSize(), this.getLocation());;
	}

}
