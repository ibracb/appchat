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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class VentanaLogin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private VentanaRegistro vRegistro;
	private JTextField textField_Telefono;
	private JPasswordField passwordField;
	private JLabel etiquetaTelefono;
	private JLabel etiquetaContrasena;
	private JLabel imagenAppChat;
	private ImageIcon imagenLogo;
	private GridBagLayout gbl_panelCentral;
	private GridBagConstraints gbc_imagenAppChat;
	private GridBagConstraints gbc_etiquetaTelefono;
	private GridBagConstraints gbc_textField_Telefono;
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
		// this.vRegistro = vRegistro;
		initialize();
	}
	public void mostrarLogin() {
		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\imagenes\\iconoPestanas.PNG"));
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
		imagenLogo = new ImageIcon(new ImageIcon("src\\main\\resources\\imagenes\\appchatLogoGrande3.PNG").getImage());
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

		textField_Telefono = new JTextField();
		gbc_textField_Telefono = new GridBagConstraints();
		gbc_textField_Telefono.gridwidth = 4;
		gbc_textField_Telefono.insets = new Insets(10, 10, 10, 10);
		gbc_textField_Telefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Telefono.gridx = 3;
		gbc_textField_Telefono.gridy = 3;
		panelCentral.add(textField_Telefono, gbc_textField_Telefono);
		textField_Telefono.setColumns(10);

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
		// botonRegistrar.addActionListener(e -> accederRegistro());
		panelCentral.add(botonRegistrar, gbc_botonRegistrar);

		botonAceptar = new JButton("Aceptar");
		botonAceptar.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_botonAceptar = new GridBagConstraints();
		gbc_botonAceptar.anchor = GridBagConstraints.EAST;
		gbc_botonAceptar.insets = new Insets(10, 10, 10, 10);
		gbc_botonAceptar.gridx = 6;
		gbc_botonAceptar.gridy = 5;
		botonAceptar.setPreferredSize(new Dimension(100, 30));
		panelCentral.add(botonAceptar, gbc_botonAceptar);

		horizontalGlue = Box.createHorizontalGlue();
		gbc_horizontalGlue = new GridBagConstraints();
		gbc_horizontalGlue.insets = new Insets(10, 10, 10, 10);
		gbc_horizontalGlue.gridx = 4;
		gbc_horizontalGlue.gridy = 7;
		panelCentral.add(horizontalGlue, gbc_horizontalGlue);

		botonRegistrar.addActionListener(this);
		botonAceptar.addActionListener(this);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == botonRegistrar) {
			VentanaRegistro registro = new VentanaRegistro();
			dispose();
			registro.mostrarRegistro();
		} else { // botonAceptar
			// Aqui hay que llamar al controlador para que compruebe en el repositorio de
			// usuarios si existe alguien con el nombre y contraseña que hay en los
			// textfield
			dispose();
		}

	}

	/*
	 * private void accederRegistro() { this.setVisible(false); if(vRegistro ==
	 * null) { vRegistro = new VentanaRegistro(this); }
	 * VistaUtils.transicionar(this, vRegistro); }
	 */

}
