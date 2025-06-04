package umu.tds.appchat.windows.vista;

import java.time.ZoneId;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import com.toedter.calendar.JDateChooser;
import umu.tds.appchat.controllers.ControladorAppChat;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class VentanaRegistro extends JFrame implements VentanaCambiaImagenes {

	private static final long serialVersionUID = 1L;

	private static final File IMAGEN_POR_DEFECTO = null;
	private static final int DEFAUL_HEIGHT_AND_WIDTH = 150;

	private JTextField nameField;
	private JTextField LastNameField;
	private JTextField numberField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JDateChooser dateChooser;
	private JLabel lblImagen;
	private JLabel lblSaludd;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JTextArea areaSaludo;
	private JScrollPane PanelSaludo;
	private JPanel panel_1;
	private JButton btnCancelar;
	private JButton btnRegistrar;
	private Component horizontalGlue;
	private JLabel lblNewLabel_1;
	VentanaLogin ventanaLogin;
	URL url;
	private File destinationFile = IMAGEN_POR_DEFECTO;

	public VentanaRegistro(VentanaLogin parent) {
		ventanaLogin = parent;
		initialize();
	}

	private void initialize() {

		this.setBounds(100, 100, 552, 430);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// cierra la ventana cuando cancelas

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 3.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		this.getContentPane().setLayout(gridBagLayout);

		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		this.getContentPane().add(lblNombre, gbc_lblNombre);

		nameField = new JTextField(20); // aqui se almacena el texto introducido por el usuario
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 3;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		this.getContentPane().add(nameField, gbc_textField);
		nameField.setColumns(10);

		JLabel lblApellidos = new JLabel("Apellidos:");
		GridBagConstraints gbc_lblApellidos = new GridBagConstraints();
		gbc_lblApellidos.anchor = GridBagConstraints.EAST;
		gbc_lblApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidos.gridx = 1;
		gbc_lblApellidos.gridy = 2;
		this.getContentPane().add(lblApellidos, gbc_lblApellidos);

		LastNameField = new JTextField(50); // TODO: Numero magico cambiar
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 3;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 2;
		this.getContentPane().add(LastNameField, gbc_textField_1);
		LastNameField.setColumns(10);

		lblNewLabel_1 = new JLabel("telefono:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 3;
		this.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

		numberField = new JTextField(9); // TODO: Constante
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 3;
		this.getContentPane().add(numberField, gbc_textField_2);
		numberField.setColumns(10);

		JLabel lblContrasea = new JLabel("Contraseña:");
		GridBagConstraints gbc_lblContrasea = new GridBagConstraints();
		gbc_lblContrasea.anchor = GridBagConstraints.EAST;
		gbc_lblContrasea.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasea.gridx = 1;
		gbc_lblContrasea.gridy = 4;
		this.getContentPane().add(lblContrasea, gbc_lblContrasea);

		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 4;
		this.getContentPane().add(passwordField, gbc_passwordField);

		JLabel lblContrasea_1 = new JLabel("Contraseña:");
		GridBagConstraints gbc_lblContrasea_1 = new GridBagConstraints();
		gbc_lblContrasea_1.anchor = GridBagConstraints.EAST;
		gbc_lblContrasea_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasea_1.gridx = 3;
		gbc_lblContrasea_1.gridy = 4;
		this.getContentPane().add(lblContrasea_1, gbc_lblContrasea_1);

		passwordField_1 = new JPasswordField();
		passwordField_1.setColumns(15);
		GridBagConstraints gbc_passwordField_1 = new GridBagConstraints();
		gbc_passwordField_1.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_1.gridx = 4;
		gbc_passwordField_1.gridy = 4;
		this.getContentPane().add(passwordField_1, gbc_passwordField_1);

		JLabel lblFechaNac = new JLabel("Fecha Nac:");
		GridBagConstraints gbc_lblFechaNac = new GridBagConstraints();
		gbc_lblFechaNac.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaNac.gridx = 1;
		gbc_lblFechaNac.gridy = 5;
		this.getContentPane().add(lblFechaNac, gbc_lblFechaNac);

		dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 2;
		gbc_dateChooser.gridy = 5;
		this.getContentPane().add(dateChooser, gbc_dateChooser);

		lblSaludd = new JLabel("Saludo:");
		GridBagConstraints gbc_lblSaludd = new GridBagConstraints();
		gbc_lblSaludd.anchor = GridBagConstraints.EAST;
		gbc_lblSaludd.insets = new Insets(0, 0, 5, 5);
		gbc_lblSaludd.gridx = 1;
		gbc_lblSaludd.gridy = 6;
		this.getContentPane().add(lblSaludd, gbc_lblSaludd);

		PanelSaludo = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 6;
		this.getContentPane().add(PanelSaludo, gbc_scrollPane);

		areaSaludo = new JTextArea();
		PanelSaludo.setViewportView(areaSaludo);

		lblImagen = new JLabel("Imagen:");
		GridBagConstraints gbc_lblImagen = new GridBagConstraints();
		gbc_lblImagen.anchor = GridBagConstraints.EAST;
		gbc_lblImagen.insets = new Insets(0, 0, 5, 5);
		gbc_lblImagen.gridx = 3;
		gbc_lblImagen.gridy = 6;
		this.getContentPane().add(lblImagen, gbc_lblImagen);

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(ControladorAppChat.getInstancia().getScaledDefaultImage(DEFAUL_HEIGHT_AND_WIDTH));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridheight = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 4;
		gbc_lblNewLabel.gridy = 6;
		this.getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 8;
		this.getContentPane().add(panel, gbc_panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		panel_1 = new JPanel();
		panel.add(panel_1);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setHorizontalAlignment(SwingConstants.LEFT);

		// Accion cuando pulsas btn cancelar
		btnCancelar.addActionListener(e -> {
			ventanaLogin.setVisible(true);
			this.setVisible(false);
		});
		panel_1.add(btnCancelar);

		horizontalGlue = Box.createHorizontalGlue();
		panel_1.add(horizontalGlue);

		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setHorizontalAlignment(SwingConstants.RIGHT);
		dateChooser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnRegistrar.doClick(); // Simula un clic en el botón
				}
			}
		});
		// CONTROLADOR DE EVENTOS

		btnRegistrar.addActionListener(e -> manejarRegistro());
		panel_1.add(btnRegistrar);

		JButton btnCambiarImagen = new JButton("Cambiar Imagen");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 8;
		getContentPane().add(btnCambiarImagen, gbc_btnNewButton);
		btnCambiarImagen.addActionListener(e -> abrirVentanaCambioImagen());
	}

	private void abrirVentanaCambioImagen() {
		String telefono = numberField.getText();

		// Validar que el teléfono no esté vacío
		if (telefono.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor ingrese todos los datos antes de cambiar la imagen.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		VentanaCambioImagen vci = new VentanaCambioImagen(this);
		vci.setVisible(true);
	}

	public void setIcon() {
		String path = destinationFile.getAbsolutePath();
		lblNewLabel.setIcon(
				ControladorAppChat.getInstancia().getScaledImage(new ImageIcon(path), DEFAUL_HEIGHT_AND_WIDTH));
	}

	public void setIcon(ImageIcon imageIcon, URL url) {
		if (url != null) {
			this.url = url;
			imageIcon = new ImageIcon(url);
		} else if (destinationFile != null) {
			String path = destinationFile.getAbsolutePath();
			imageIcon = new ImageIcon(path);
		}
		lblNewLabel.setIcon(ControladorAppChat.getInstancia().getScaledImage(imageIcon, DEFAUL_HEIGHT_AND_WIDTH));
	}

	public String getTelefono() {
		return numberField.getText();
	}

	private void manejarRegistro() {
		String nombre, apellidos, telefono, passwd1, passwd2, saludo, imagePath;
		nombre = nameField.getText();
		apellidos = LastNameField.getText();
		telefono = numberField.getText();
		passwd1 = new String(passwordField.getPassword());
		passwd2 = new String(passwordField_1.getPassword());
		saludo = areaSaludo.getText();

		if (dateChooser.getDate() == null) {
			JOptionPane.showMessageDialog(null, "El campo fecha es nulo", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		java.util.Date fecha = dateChooser.getDate();
		LocalDate fNaci = fecha.toInstant() // Convierte Date a Instant
				.atZone(ZoneId.systemDefault()) // Obtiene el ZoneId por defecto
				.toLocalDate();

		if (hasRequiredFields(nombre, apellidos, telefono, passwd1, passwd2, fNaci)) {
			JOptionPane.showMessageDialog(null, "Faltan campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!nombre.matches("[a-zA-Z0-9]+")) {
			JOptionPane.showMessageDialog(null, "El nombre contiene caracteres no permitidos.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!passwd1.equals(passwd2)) {
			JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		boolean result = ControladorAppChat.getInstancia().registrarUsuario(nombre, apellidos, telefono, passwd1, fNaci,
				telefono);

		if (!result) {
			JOptionPane.showMessageDialog(null, "Ya estas registrado o ha ocurrido un error", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			if (!saludo.isEmpty()) {
				ControladorAppChat.getInstancia().setSaludo(saludo);
			}
			if (destinationFile != null) {
				imagePath = destinationFile.getAbsolutePath();
				ControladorAppChat.getInstancia().setImagen(imagePath);
			} else if (url != null) {
				ControladorAppChat.getInstancia().setImagen(url.toString());
			}

			JOptionPane.showMessageDialog(null, "Sus datos han sido guardados correctamente", "Conseguido",
					JOptionPane.PLAIN_MESSAGE);
			VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(ventanaLogin);
			ventanaLogin.dispose();
			ventanaPrincipal.setVisible(true);
			this.dispose();
		}

	}

	public void setDestinationFile(File d) {
		destinationFile = d;
	}

	private boolean hasRequiredFields(String nombre, String apellidos, String telefono, String passwd1, String passwd2,
			LocalDate fNaci) {
		return nombre.isEmpty() || telefono.isEmpty() || apellidos.isEmpty() || passwd1.isEmpty() || passwd2.isEmpty()
				|| fNaci == null;
	}

}
