package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
	 * Serial version UID para la serialización de la clase.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Campo de texto para introducir el teléfono del usuario.
	 */
	private JTextField textFieldTelefono;
	/**
	 * Campo de contraseña para introducir la contraseña del usuario.
	 */
	private JPasswordField passwordField;
	/**
	 * Etiquetas que señalan los datos que se deben introducir para iniciar sesion.
	 */
	private JLabel etiquetaTelefono, etiquetaContrasena, imagenAppChat;
	/**
	 * Imagen que representa el logo de la aplicación.
	 */
	private ImageIcon imagenLogo;
	/**
	 * Layout y restricciones para el panel central de la ventana.
	 */
	private GridBagLayout gbl_panelCentral;
	private GridBagConstraints gbc_imagenAppChat, gbc_etiquetaTelefono, gbc_textFieldTelefono, gbc_etiquetaContrasena,
			gbc_passwordField, gbc_botonRegistrar, gbc_botonAceptar, gbc_horizontalGlue;
	/**
	 * Componente de pegamento horizontal para alinear los botones en el panel.
	 */
	private Component horizontalGlue;
	/**
	 * Panel central de la ventana, donde se colocan los componentes.
	 */
	private JPanel panelCentral;
	/**
	 * Botones para registrar un nuevo usuario o aceptar el inicio de sesión.
	 */
	private JButton botonRegistrar, botonAceptar;

	/**
	 * Crea la ventanaLogin.
	 */
	public VentanaLogin() {
		initialize();
	}
	
	/**
	 * Muestra la ventana de login con el tamaño y ubicación especificados.
	 * 
	 * @param tam - Tamaño de la ventana.
	 * @param ubi - Ubicación de la ventana.
	 */
	protected void mostrarLogin(Dimension tam, Point ubi) {
		setVisible(true);
		setSize(tam);
		setLocation(ubi);
	}

	/**
	 * Inicializa el frame de la ventana.
	 */
	private void initialize() {
		setIconImage(new ImageIcon(getClass().getResource("/imagenes/iconoPestanas.png")).getImage());
		setTitle("AppChat");
		setBounds(100, 100, 750, 600);
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
	
	/**
	 * Método que se ejecuta al pulsar el botón de aceptar. Comprueba si los campos
	 * están completos y si lo están, intenta iniciar sesión.
	 */
	private void accederLogin() {
		String movil = textFieldTelefono.getText();
		String contrasena = new String(passwordField.getPassword());
		if (movil.isEmpty() || contrasena.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Falta algún campo por completar: teléfono y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
		int loginExitoso = Controlador.INSTANCE.loginUsuario(movil, contrasena);
		if (loginExitoso == 0) {
			JOptionPane.showMessageDialog(this, "¡Hola de nuevo, " + Controlador.INSTANCE.getNombreUsuarioActual() + "!");
			VentanaPrincipal vPrincipal = new VentanaPrincipal();
			dispose();
			vPrincipal.mostrarVentanaPrincipal(this.getSize(), this.getLocation());
		} else if (loginExitoso == -1) {
			JOptionPane.showMessageDialog(this, "La contraseña no es correcta", "Error", JOptionPane.ERROR_MESSAGE);
		} else if (loginExitoso == -2) {
			JOptionPane.showMessageDialog(this, "El usuario no existe. Regístrese primero.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Método que se ejecuta al pulsar el botón de registrar. Abre la ventana de
	 * registro.
	 */
	private void accederRegistro() {
		VentanaRegistro registro = new VentanaRegistro();
		dispose();
		registro.mostrarRegistro(this.getSize(), this.getLocation());
	}

}
