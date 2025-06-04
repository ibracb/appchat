package umu.tds.appchat.windows.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import umu.tds.appchat.controllers.ControladorAppChat;

public class VentanaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JPasswordField passwordField;
	
	public VentanaLogin() {
		this.setTitle("AppChat");
		this.setBounds(300, 300, 450, 261);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panelSur = new JPanel();
		this.getContentPane().add(panelSur, BorderLayout.SOUTH);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setForeground(Color.RED);
		btnRegistrar.setIcon(new ImageIcon(getClass().getResource("/nuevo.png")));
		btnRegistrar.setPreferredSize(new Dimension(115, 23));
		panelSur.add(btnRegistrar);

		// Controlar evento boton registrar
		btnRegistrar.addActionListener(e -> abrirVentanaRegistro());

		JButton btnLogin = new JButton("Login");
		btnLogin.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLogin.setIcon(new ImageIcon(getClass().getResource("/contrasena.png")));
		btnLogin.setPreferredSize(new Dimension(115, 23));
		panelSur.add(btnLogin);

		// Controlad Evento boton login
		btnLogin.addActionListener(e -> comprobarUsuario());

		JPanel panelCentro = new JPanel();
		panelCentro.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		GridBagLayout gbl_panelCentro = new GridBagLayout();
		gbl_panelCentro.columnWidths = new int[] { 20, 0, 0, 20, 0 };
		gbl_panelCentro.rowHeights = new int[] { 20, 0, 0, 20, 0 };
		gbl_panelCentro.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelCentro.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelCentro.setLayout(gbl_panelCentro);

		JLabel lblTelefono = new JLabel("tel\u00E9fono:");
		GridBagConstraints gbc_lblTelefono = new GridBagConstraints();
		gbc_lblTelefono.anchor = GridBagConstraints.EAST;
		gbc_lblTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefono.gridx = 1;
		gbc_lblTelefono.gridy = 1;
		panelCentro.add(lblTelefono, gbc_lblTelefono);


		textField = new JTextField();
		textField.setColumns(20);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		textField.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            btnLogin.doClick();
		        }
		    }
		});
		panelCentro.add(textField, gbc_textField);
		

		JLabel lblContrasea = new JLabel("contrase\u00F1a:");
		GridBagConstraints gbc_lblContrasea = new GridBagConstraints();
		gbc_lblContrasea.anchor = GridBagConstraints.EAST;
		gbc_lblContrasea.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasea.gridx = 1;
		gbc_lblContrasea.gridy = 2;
		panelCentro.add(lblContrasea, gbc_lblContrasea);

		passwordField = new JPasswordField();
		passwordField.setColumns(20);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 2;
		passwordField.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            btnLogin.doClick(); // Simula un clic en el botón
		        }
		    }
		});
		panelCentro.add(passwordField, gbc_passwordField);

		JPanel panel = new JPanel();
		this.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWeights = new double[] { 1.0 };
		gbl_panel.columnWidths = new int[] { 0 };
		panel.setLayout(gbl_panel);
		panel.add(panelCentro);

		JLabel logo = new JLabel("");
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(logo, BorderLayout.NORTH);

	}
	
	private void abrirVentanaRegistro() {
		// Crear una instancia de VentanaRegistro y hacerla visible
		VentanaRegistro ventanaRegistro = new VentanaRegistro(this);
		ventanaRegistro.setVisible(true);
		this.dispose();
	}

	public void comprobarUsuario() {
		String telefono = textField.getText();
		char[] passwd = passwordField.getPassword();
		textField.setText("");
		passwordField.setText("");
        String passwordString = new String(passwd);
		if (telefono.isEmpty() || passwordString.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Los campos estan vacíos, por favor escribe tu nombre y telefono", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			if (ControladorAppChat.getInstancia().comprobarUsuario(telefono,passwordString)) {
				this.dispose();
				VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(this);
				ventanaPrincipal.setVisible(true);
			}
			else {
				JOptionPane.showMessageDialog(null, "Fallo en el inicio de sesion", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
