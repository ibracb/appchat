package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import umu.tds.apps.controlador.Controlador;

/**
 * Ventana de registro de usuario. Permite al usuario registrarse en la
 * aplicación.
 */
public class VentanaRegistro extends JFrame {

	/**
	 * Serial version UID para la serialización de la clase.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Etiquetas que señalan los datos que se deben introducir para el
	 * registro.
	 */
	private JLabel etiquetaImagen, etiquetaNombre, etiquetaApellidos, etiquetaTelefono, etiquetaContrasena1,
			etiquetaContrasena2, etiquetaFechaNacimiento, etiquetaSaludo, lblEmail, lblPerfil;
	/**
	 * Campos de texto donde el usuario introduce los datos necesarios para
	 * registrarse.
	 */
	private JTextField textFieldNombre, textFieldApellidos, textFieldTelefono, textFieldEmail;
	/**
	 * Campos de contraseña donde el usuario introduce la contraseña y su
	 * confirmación.
	 */
	private JPasswordField passwordField, passwordFieldOk;
	/**
	 * Botones que permiten al usuario aceptar o cancelar el registro.
	 */
	private JButton btnAceptar, btnCancelar;
	/**
	 * Componente de selección de fecha de nacimiento.
	 */
	private JDateChooser dateChooser;
	/**
	 * Componente que permite al usuario seleccionar una imagen de perfil.
	 */
	private Component horizontalStrut;
	/**
	 * Paneles que organizan los componentes de la ventana.
	 */
	private JPanel panel, panelBotones;
	/**
	 * Área de texto donde el usuario puede introducir un saludo.
	 */
	private JTextArea textArea;
	/**
	 * Botón que permite al usuario seleccionar una imagen de perfil desde una URL.
	 */
	private JButton btnPerfil;
	/**
	 * Icono de imagen que se muestra en la ventana.
	 */
	private ImageIcon imagen;
	/**
	 * Imagen escalada que se muestra en el label de perfil.
	 */
	private Image imagenEscalada;
	/**
	 * Panel de desplazamiento que contiene el área de texto para el saludo.
	 */
	private JScrollPane scrollPane;
	/**
	 * Layout y restricciones de diseño para organizar los componentes
	 */
	private GridBagLayout gridBagLayout;
	private GridBagConstraints gbc_etiquetaNombre, gbc_textFieldNombre, gbc_etiquetaApellidos, gbc_textFieldApellidos,
			gbc_etiquetaTelefono, gbc_textFieldTelefono, gbc_etiquetaContrasena1, gbc_passwordField,
			gbc_etiquetaContrasena2, gbc_passwordFieldOk, gbc_etiquetaFechaNacimiento, gbc_dateChooser,
			gbc_etiquetaImagen, gbc_btnPerfil, gbc_etiquetaSaludo, gbc_scrollPane, gbc_lblPerfil, gbc_panel_1;
	/**
	 * Ruta de la imagen seleccionada por el usuario.
	 */
	private String rutaImagenSeleccionada;

	/**
	 * Crea una nueva instancia de la ventana de registro.
	 */
	protected VentanaRegistro() {
		initialize();
	}

	/**
	 * Mostrar la ventana de registro
	 * @param tam - Tamaño de la ventana.
	 * @param ubi - Ubicación de la ventana en la pantalla.
	 */
	protected void mostrarRegistro(Dimension tam, Point ubi) {
		setVisible(true);
		setSize(tam);
		setLocation(ubi);
	}

	/**
	 * Inicializa el contenido de la ventana.
	 */
	private void initialize() {
		getContentPane().setBackground(new Color(242, 216, 245));
		setBounds(100, 100, 613, 464);
		setIconImage(new ImageIcon(getClass().getResource("/imagenes/iconoPestanas.PNG")).getImage());
		setTitle("Registrarse en AppChat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setBackground(new Color(242, 216, 245));
		getContentPane().add(panel, BorderLayout.CENTER);
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 110, 0, 0, 110, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 34, 40, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0 };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0 };
		panel.setLayout(gridBagLayout);

		etiquetaNombre = new JLabel("Nombre");
		etiquetaNombre.setFont(new Font("Georgia", Font.BOLD, 12));
		etiquetaNombre.setHorizontalAlignment(SwingConstants.TRAILING);
		gbc_etiquetaNombre = new GridBagConstraints();
		gbc_etiquetaNombre.anchor = GridBagConstraints.EAST;
		gbc_etiquetaNombre.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaNombre.gridx = 1;
		gbc_etiquetaNombre.gridy = 0;
		panel.add(etiquetaNombre, gbc_etiquetaNombre);

		textFieldNombre = new JTextField();
		gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.gridwidth = 4;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.gridx = 2;
		gbc_textFieldNombre.gridy = 0;
		panel.add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);

		etiquetaApellidos = new JLabel("Apellidos");
		etiquetaApellidos.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_etiquetaApellidos = new GridBagConstraints();
		gbc_etiquetaApellidos.anchor = GridBagConstraints.EAST;
		gbc_etiquetaApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaApellidos.gridx = 1;
		gbc_etiquetaApellidos.gridy = 1;
		panel.add(etiquetaApellidos, gbc_etiquetaApellidos);

		textFieldApellidos = new JTextField();
		gbc_textFieldApellidos = new GridBagConstraints();
		gbc_textFieldApellidos.gridwidth = 4;
		gbc_textFieldApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldApellidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldApellidos.gridx = 2;
		gbc_textFieldApellidos.gridy = 1;
		panel.add(textFieldApellidos, gbc_textFieldApellidos);
		textFieldApellidos.setColumns(10);

		etiquetaTelefono = new JLabel("Teléfono");
		etiquetaTelefono.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_etiquetaTelefono = new GridBagConstraints();
		gbc_etiquetaTelefono.anchor = GridBagConstraints.EAST;
		gbc_etiquetaTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaTelefono.gridx = 1;
		gbc_etiquetaTelefono.gridy = 2;
		panel.add(etiquetaTelefono, gbc_etiquetaTelefono);

		textFieldTelefono = new JTextField();
		gbc_textFieldTelefono = new GridBagConstraints();
		gbc_textFieldTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTelefono.gridx = 2;
		gbc_textFieldTelefono.gridy = 2;
		panel.add(textFieldTelefono, gbc_textFieldTelefono);
		textFieldTelefono.setColumns(10);

		lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 3;
		panel.add(lblEmail, gbc_lblEmail);

		textFieldEmail = new JTextField();
		GridBagConstraints gbc_textFieldEmail = new GridBagConstraints();
		gbc_textFieldEmail.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEmail.gridx = 2;
		gbc_textFieldEmail.gridy = 3;
		panel.add(textFieldEmail, gbc_textFieldEmail);
		textFieldEmail.setColumns(10);

		etiquetaContrasena1 = new JLabel("Contraseña");
		etiquetaContrasena1.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_etiquetaContrasena1 = new GridBagConstraints();
		gbc_etiquetaContrasena1.anchor = GridBagConstraints.EAST;
		gbc_etiquetaContrasena1.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaContrasena1.gridx = 1;
		gbc_etiquetaContrasena1.gridy = 4;
		panel.add(etiquetaContrasena1, gbc_etiquetaContrasena1);

		passwordField = new JPasswordField();
		gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 4;
		panel.add(passwordField, gbc_passwordField);

		etiquetaContrasena2 = new JLabel("Contraseña");
		etiquetaContrasena2.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_etiquetaContrasena2 = new GridBagConstraints();
		gbc_etiquetaContrasena2.anchor = GridBagConstraints.EAST;
		gbc_etiquetaContrasena2.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaContrasena2.gridx = 4;
		gbc_etiquetaContrasena2.gridy = 4;
		panel.add(etiquetaContrasena2, gbc_etiquetaContrasena2);

		passwordFieldOk = new JPasswordField();
		gbc_passwordFieldOk = new GridBagConstraints();
		gbc_passwordFieldOk.insets = new Insets(0, 0, 5, 5);
		gbc_passwordFieldOk.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordFieldOk.gridx = 5;
		gbc_passwordFieldOk.gridy = 4;
		panel.add(passwordFieldOk, gbc_passwordFieldOk);

		etiquetaFechaNacimiento = new JLabel("Fecha de nacimiento");
		etiquetaFechaNacimiento.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_etiquetaFechaNacimiento = new GridBagConstraints();
		gbc_etiquetaFechaNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaFechaNacimiento.gridx = 1;
		gbc_etiquetaFechaNacimiento.gridy = 5;
		panel.add(etiquetaFechaNacimiento, gbc_etiquetaFechaNacimiento);

		dateChooser = new JDateChooser();
		gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 2;
		gbc_dateChooser.gridy = 5;
		panel.add(dateChooser, gbc_dateChooser);

		etiquetaImagen = new JLabel("Imagen");
		etiquetaImagen.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_etiquetaImagen = new GridBagConstraints();
		gbc_etiquetaImagen.anchor = GridBagConstraints.EAST;
		gbc_etiquetaImagen.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaImagen.gridx = 4;
		gbc_etiquetaImagen.gridy = 5;
		panel.add(etiquetaImagen, gbc_etiquetaImagen);

		btnPerfil = new JButton("Selecciona una imagen de perfil");
		gbc_btnPerfil = new GridBagConstraints();
		gbc_btnPerfil.anchor = GridBagConstraints.WEST;
		gbc_btnPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_btnPerfil.gridx = 5;
		gbc_btnPerfil.gridy = 5;
		btnPerfil.addActionListener(e -> seleccionarImagen());
		panel.add(btnPerfil, gbc_btnPerfil);

		etiquetaSaludo = new JLabel("Saludo");
		etiquetaSaludo.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_etiquetaSaludo = new GridBagConstraints();
		gbc_etiquetaSaludo.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaSaludo.gridx = 1;
		gbc_etiquetaSaludo.gridy = 6;
		panel.add(etiquetaSaludo, gbc_etiquetaSaludo);

		scrollPane = new JScrollPane();
		gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 6;
		panel.add(scrollPane, gbc_scrollPane);

		textArea = new JTextArea();
		textArea.setLineWrap(true); // Habilitar ajuste de línea
		textArea.setWrapStyleWord(true); // Habilitar ajuste por palabra
		scrollPane.setViewportView(textArea);

		lblPerfil = new JLabel("");
		lblPerfil.setMinimumSize(new Dimension(120, 120));
		lblPerfil.setPreferredSize(new Dimension(120, 120));
		lblPerfil.setMaximumSize(new Dimension(120, 120));
		imagen = new ImageIcon(getClass().getResource("/imagenes/usuario_perfil_defecto.png"));
		imagenEscalada = imagen.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
		lblPerfil.setIcon(new ImageIcon(imagenEscalada));
		gbc_lblPerfil = new GridBagConstraints();
		gbc_lblPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_lblPerfil.gridx = 5;
		gbc_lblPerfil.gridy = 6;
		panel.add(lblPerfil, gbc_lblPerfil);

		panelBotones = new JPanel();
		panelBotones.setBackground(new Color(242, 216, 245));
		gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 4;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.gridx = 2;
		gbc_panel_1.gridy = 7;
		panel.add(panelBotones, gbc_panel_1);
		panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Georgia", Font.BOLD, 12));
		btnAceptar.setPreferredSize(new Dimension(100, 30)); // Establece el tamaño preferido
		btnAceptar.addActionListener(e -> registrarUsuario());
		panelBotones.add(btnAceptar);

		horizontalStrut = Box.createHorizontalStrut(20);
		panelBotones.add(horizontalStrut);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Georgia", Font.BOLD, 12));
		btnCancelar.setPreferredSize(new Dimension(100, 30)); // Establece el tamaño preferido
		btnCancelar.addActionListener(e -> volverLogin());
		panelBotones.add(btnCancelar);
	}

	/**
	 * Registra al usuario con los datos introducidos en la ventana de registro.
	 * Valida los campos obligatorios y muestra mensajes de error si es necesario.
	 */
	private void registrarUsuario() {
		String nombre = textFieldNombre.getText() + " " + textFieldApellidos.getText();
		LocalDate fechaNacimiento = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String email = textFieldEmail.getText();
		String imagen = rutaImagenSeleccionada;
		String movil = textFieldTelefono.getText();
		String contraseña = new String(passwordField.getPassword());
		String contraseñaOk = new String(passwordFieldOk.getPassword());
		String saludo = textArea.getText();
		if (nombre.isEmpty() || fechaNacimiento == null || email.isEmpty() || movil.isEmpty() || contraseña.isEmpty()
				|| contraseñaOk.isEmpty()) {
			JOptionPane.showMessageDialog(this,
					"Por favor, completa todos los campos obligatorios. Son: nombre, apellidos, email, móvil, contraseñas y fecha de nacimiento.",
					"Campos incompletos", JOptionPane.WARNING_MESSAGE);
			return;
		} else if (fechaNacimiento.isAfter(LocalDate.now())) {
			JOptionPane.showMessageDialog(this, "La fecha de nacimiento no puede ser futura.",
					"Fecha naciemiento futura", JOptionPane.WARNING_MESSAGE);
			// Limpia el campo de fecha de nacimiento
			dateChooser.setDate(null);
			return;
		} else if (!email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
			JOptionPane.showMessageDialog(this, "El email introducido no es válido.", "Email no valido",
					JOptionPane.WARNING_MESSAGE);
			// Limpia el campo de email
			textFieldEmail.setText("");
			return;
		} else if (!(movil.length() == 9) && !(movil.matches("\\d+"))) {
			JOptionPane.showMessageDialog(this, "El número de móvil debe ser de 9 digitos.",
					"Número de telefono no valido", JOptionPane.WARNING_MESSAGE);
			// Limpia el campo de móvil
			textFieldTelefono.setText("");
			return;
		} else if (!contraseña.equals(contraseñaOk)) {
			JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Las contraseñas no coinciden",
					JOptionPane.WARNING_MESSAGE);
			// Limpia los campos de contraseña
			passwordField.setText("");
			passwordFieldOk.setText("");
			return;
		}
		Boolean confirmacionRegistro = false;
		if (imagen == null || imagen.isEmpty()) {
			confirmacionRegistro = Controlador.INSTANCE.registrarUsuario(nombre, fechaNacimiento, email, movil,
					contraseña, saludo);
		} else {
			confirmacionRegistro = Controlador.INSTANCE.registrarUsuario(nombre, fechaNacimiento, email, imagen, movil,
					contraseña, saludo);
		}
		if (confirmacionRegistro) {
			dispose();
			JOptionPane.showMessageDialog(this, "¡Bienvenido a AppChat, " + nombre + "!");
			VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
			ventanaPrincipal.mostrarVentanaPrincipal(getSize(), getLocation());
		} else {
			JOptionPane.showMessageDialog(this, "Error en el registro, inténtalo de nuevo...");
		}
	}

	/**
	 * Acción del boton "Volver" que hacer que se muestre la ventana de login.
	 */
	private void volverLogin() {
		VentanaLogin ventanaLogin = new VentanaLogin();
		dispose();
		ventanaLogin.mostrarLogin(this.getSize(), this.getLocation());
	}

	/**
	 * Permite al usuario seleccionar una imagen de perfil desde una URL. Muestra un
	 * diálogo para introducir la URL y carga la imagen.
	 */
	private void seleccionarImagen() {
		String url = JOptionPane.showInputDialog(this, "Introduce la URL de la imagen:",
				"Seleccionar imagen desde internet", JOptionPane.PLAIN_MESSAGE);

		if (url != null && !url.trim().isEmpty()) {
			cargarImagenDesdeURL(url.trim());
		}
	}

	/**
	 * Carga una imagen desde una URL proporcionada por el usuario. Escala la imagen
	 * para ajustarla al tamaño del label de perfil y maneja errores en la carga.
	 *
	 * @param urlString La URL de la imagen a cargar.
	 */
	@SuppressWarnings("deprecation")
	private void cargarImagenDesdeURL(String urlString) {
		try {
			URL url = new URL(urlString);
			BufferedImage image = ImageIO.read(url);

			if (image == null) {
				JOptionPane.showMessageDialog(this,
						"El enlace no corresponde a una imagen válida.\nPor favor, introduce una URL de imagen válida.",
						"Imagen no válida", JOptionPane.ERROR_MESSAGE);
				seleccionarImagen(); // Volver a intentar
				return;
			}

			// Guarda la URL como ruta de imagen seleccionada
			rutaImagenSeleccionada = urlString;

			// Crea ImageIcon desde BufferedImage
			ImageIcon icono = new ImageIcon(image);

			// Escala la imagen
			int width = lblPerfil.getWidth();
			int height = lblPerfil.getHeight();
			int escalaAncho = (width > 0) ? width : 120;
			int escalaAlto = (height > 0) ? height : 120;
			Image imagenEscalada = icono.getImage().getScaledInstance(escalaAncho, escalaAlto, Image.SCALE_SMOOTH);

			// Establece la imagen en el label
			lblPerfil.setIcon(new ImageIcon(imagenEscalada));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"Error al cargar la imagen desde la URL:\n" + e.getMessage() + "\n\nVerifica que:\n"
							+ "- La URL sea correcta\n" + "- Tengas conexión a internet\n"
							+ "- El enlace apunte a una imagen válida (PNG, JPG, GIF, etc.)",
					"Error al cargar imagen", JOptionPane.ERROR_MESSAGE);
			seleccionarImagen(); // Volver a intentar
		}
	}
}