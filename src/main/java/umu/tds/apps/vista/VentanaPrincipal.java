package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import tds.BubbleText;
import umu.tds.apps.controlador.Controlador;
import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.ContactoIndividual;
import umu.tds.apps.dominio.Grupo;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.TipoMensaje;
import umu.tds.apps.dominio.Usuario;

/**
 * Ventana principal de la aplicación que muestra el chat y los contactos.
 * Permite enviar mensajes, cambiar imagen de perfil, gestionar contactos,
 * buscar usuarios y generar PDFs.
 */
public class VentanaPrincipal extends JFrame {
	/**
	 * Serial version UID para la serialización de la clase.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Paneles que contienen el chat y los contactos.
	 */
	private JPanel chat, contactos = new JPanel();
	/**
	 * Componentes para el espaciado horizontal en la barra de menú.
	 */
	private Component horizontalGlue, horizontalGlue_1;
	/**
	 * Barra de menú que contiene opciones como cerrar sesión, cambiar imagen de
	 * perfil y gestionar contactos.
	 */
	private JMenuBar menuBar;
	/**
	 * Menú que contiene el nombre del usuario actual y opciones como cerrar sesión
	 */
	private JMenu MTuContacto, mnContactos;
	/*
	 * Menu items para cerrar sesión, cambiar imagen de perfil y gestionar contactos.
     */
	private JMenuItem MCerrarSesion, MCambiarImagenPerfil, mntmIndividuales, mntmGrupos, MBuscar;
	/**
	 * Imagenes para los botones de búsqueda y premium.
	 */
	private ImageIcon imagenBuscar, imagenPremium;
	/**
	 * Panel que contiene los componentes de la ventana principal.
	 */
	private JPanel panel, panelCentral, panelInfo, panelEnviarMensaje;
	/**
	 * Botones para funcionalidades adicionales como Premium, PDF de listado y PDF
	 * del chat.
	 */
	private JButton btnPremium, btnPdfListado, btnPdfChat, btnEnviar;
	/**
	 * Panel de desplazamiento que contiene el chat.
	 */
	private JScrollPane scrollPane;
	/**
	 * Etiqueta que muestra el contacto actual en el chat.
	 */
	private JLabel lblContactoChat, lblImagenUsuario;
	/**
	 * Área de texto para ingresar mensajes.
	 */
	private JTextArea textArea;
	/**
	 * Contacto actual con el que se está chateando. Puede ser un contacto
	 * individual o un grupo.
	 */
	private Contacto contacto;

	/**
	 * Constructor por defecto de la ventana principal. Inicializa los componentes y
	 * configura la ventana.
	 */
	protected VentanaPrincipal() {
		initialize();
	}

	/**
	 * Constructor que recibe un contacto individual o grupo para iniciar la ventana
	 * principal con ese contacto seleccionado.
	 * 
	 * @param contacto Contacto individual o grupo con el que se inicia la ventana.
	 */
	protected VentanaPrincipal(Contacto contacto) {
		this.contacto = contacto;
		initialize();
		recuperarMensajes();
	}

	/**
	 * Constructor que recibe un grupo para iniciar la ventana principal con ese
	 * grupo seleccionado.
	 * 
	 * @param grupo Grupo con el que se inicia la ventana.
	 */
	protected VentanaPrincipal(Grupo grupo) {
		this.contacto = grupo;
		initialize();
		recuperarMensajes();
	}

	/**
	 * Método para mostrar la ventana principal con un tamaño y ubicación
	 * específicos.
	 * 
	 * @param tam Tamaño de la ventana.
	 * @param ubi Ubicación de la ventana.
	 */
	protected void mostrarVentanaPrincipal(Dimension tam, Point ubi) {
		setVisible(true);
		setSize(tam);
		setLocation(ubi);
	}

	/**
	 * Método que inicializa los componentes de la ventana principal.
	 */
	private void initialize() {
		setBounds(100, 100, 601, 449);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("/imagenes/iconoPestanas.png")).getImage());
		setTitle("AppChat");
		getContentPane().setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		menuBar = new JMenuBar();
		panel.add(menuBar);

		MTuContacto = new JMenu(Controlador.INSTANCE.getNombreUsuarioActual());
		MTuContacto.setFont(new Font("Georgia", Font.BOLD, 12));
		MTuContacto.setPreferredSize(new Dimension(170, 30));
		MTuContacto.setAlignmentX(Component.LEFT_ALIGNMENT);
		refrescarImagen();

		lblImagenUsuario = new JLabel("");
		cargarImagenPerfilUsuario();
		menuBar.add(lblImagenUsuario);
		menuBar.add(MTuContacto);

		MCambiarImagenPerfil = new JMenuItem("Cambiar imagen de perfil");
		MCambiarImagenPerfil.setFont(new Font("Georgia", Font.PLAIN, 12));
		MCambiarImagenPerfil.addActionListener(e -> cambiarImagen());
		MTuContacto.add(MCambiarImagenPerfil);

		MCerrarSesion = new JMenuItem("Cerrar sesión");
		MCerrarSesion.setFont(new Font("Georgia", Font.PLAIN, 12));
		MCerrarSesion.addActionListener(e -> cerrarSesion());
		MTuContacto.add(MCerrarSesion);

		mnContactos = new JMenu("Gestión de Contactos");
		mnContactos.setFont(new Font("Georgia", Font.BOLD, 12));
		menuBar.add(mnContactos);

		mntmIndividuales = new JMenuItem("Individuales");
		mntmIndividuales.addActionListener(e -> abrirIndividuales());
		mnContactos.add(mntmIndividuales);

		mntmGrupos = new JMenuItem("Grupos");
		mntmGrupos.addActionListener(e -> abrirGrupos());
		mnContactos.add(mntmGrupos);

		horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);

		horizontalGlue_1 = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue_1);

		MBuscar = new JMenuItem("Buscar");
		MBuscar.setFont(new Font("Georgia", Font.BOLD, 12));
		imagenBuscar = new ImageIcon(getClass().getResource("/imagenes/buscar.png"));
		MBuscar.setIcon(imagenBuscar);
		MBuscar.setMaximumSize(new Dimension(128, 128));
		MBuscar.addActionListener(e -> abrirBuscar());
		menuBar.add(MBuscar);

		imagenPremium = new ImageIcon(getClass().getResource("/imagenes/premiumTick.png"));
		btnPremium = new JButton("Premium", imagenPremium);
		btnPremium.setFont(new Font("Georgia", Font.BOLD, 12));
		btnPremium.addActionListener(e -> abrirPremium());
		menuBar.add(btnPremium);

		btnPdfListado = new JButton("PDF Listado");
		btnPdfListado.setFont(new Font("Georgia", Font.BOLD, 12));
		btnPdfListado.addActionListener(e -> gestionarPdfListado());
		menuBar.add(btnPdfListado);

		contactos.setMaximumSize(new Dimension(200, Integer.MAX_VALUE));
		contactos.setPreferredSize(new Dimension(200, 0));
		contactos.setLayout(new BoxLayout(contactos, BoxLayout.Y_AXIS));
		JScrollPane scrollContactos = new JScrollPane(contactos);
		scrollContactos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollContactos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollContactos.setPreferredSize(new Dimension(200, 700));

		getContentPane().add(scrollContactos, BorderLayout.WEST);

		panelCentral = new JPanel();
		getContentPane().add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));

		panelInfo = new JPanel();
		panelCentral.add(panelInfo, BorderLayout.NORTH);

		if (contacto != null) {
			lblContactoChat = new JLabel(Controlador.INSTANCE.getNombreContacto(contacto));
		} else {
			lblContactoChat = new JLabel("");
		}
		panelInfo.add(lblContactoChat);

		btnPdfChat = new JButton("PDF Chat");
		btnPdfChat.addActionListener(e -> gestionarPdfChat());
		btnPdfChat.setFont(new Font("Georgia", Font.BOLD, 12));
		panelInfo.add(btnPdfChat);

		chat = new JPanel();
		chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
		scrollPane = new JScrollPane(chat);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelCentral.add(scrollPane, BorderLayout.CENTER);

		panelEnviarMensaje = new JPanel();
		panelCentral.add(panelEnviarMensaje, BorderLayout.SOUTH);
		GridBagLayout gbl_panelEnviarMensaje = new GridBagLayout();
		gbl_panelEnviarMensaje.columnWidths = new int[] { 0, 0 };
		gbl_panelEnviarMensaje.rowHeights = new int[] { 0 };
		gbl_panelEnviarMensaje.columnWeights = new double[] { 1.0, 0.0 };
		gbl_panelEnviarMensaje.rowWeights = new double[] { 0.0 };
		panelEnviarMensaje.setLayout(gbl_panelEnviarMensaje);

		ImageIcon originalIcon = BubbleText.getEmoji(16);
		Image imgEmoji = originalIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		ImageIcon iconEmoji = new ImageIcon(imgEmoji);

		JButton btnEmoji = new JButton();
		btnEmoji.setMargin(new Insets(0, 0, 0, 0));
		btnEmoji.setIcon(iconEmoji);

		btnEmoji.addActionListener(e -> mostrarPanelEmojis());
		GridBagConstraints gbc_btnEmoji = new GridBagConstraints();
		gbc_btnEmoji.insets = new Insets(5, 5, 5, 5);
		gbc_btnEmoji.gridx = 0;
		gbc_btnEmoji.gridy = 0;
		panelEnviarMensaje.add(btnEmoji, gbc_btnEmoji);

		textArea = new JTextArea(2, 20);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.fill = GridBagConstraints.HORIZONTAL;
		gbc_textArea.insets = new Insets(5, 5, 5, 5);
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 0;
		gbc_textArea.weightx = 1.0;
		panelEnviarMensaje.add(textArea, gbc_textArea);

		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(e -> enviarTexto());
		GridBagConstraints gbc_btnEnviar = new GridBagConstraints();
		gbc_btnEnviar.insets = new Insets(5, 5, 5, 5);
		gbc_btnEnviar.gridx = 2;
		gbc_btnEnviar.gridy = 0;
		panelEnviarMensaje.add(btnEnviar, gbc_btnEnviar);

		refrescarPanelContactos();

	}

	/**
	 * Muestra un panel con botones de emojis para seleccionar y enviar. Al
	 * seleccionar un emoji, se envía al contacto actual.
	 */
	private void mostrarPanelEmojis() {
		JDialog emojiDialog = new JDialog(this, "Selecciona un emoji", true);

		// Panel que contendrá todos los botones de emojis
		JPanel panelEmojis = new JPanel();
		panelEmojis.setLayout(new GridLayout(0, 5, 5, 5)); // 5 columnas, filas automáticas

		for (int i = 0; i <= BubbleText.MAXICONO; i++) {
			JButton emojiButton = new JButton(BubbleText.getEmoji(i));
			final int emojiIndex = i;
			emojiButton.setPreferredSize(new Dimension(50, 50));
			emojiButton.addActionListener(e -> {
				enviarEmoji(emojiIndex);
				emojiDialog.dispose();
			});
			panelEmojis.add(emojiButton);
		}

		// ScrollPane que envuelve el panel de emojis
		JScrollPane scrollPane = new JScrollPane(panelEmojis);
		scrollPane.setPreferredSize(new Dimension(300, 300)); // ajusta el tamaño visible

		emojiDialog.getContentPane().add(scrollPane);
		emojiDialog.pack();
		emojiDialog.setLocationRelativeTo(this);
		emojiDialog.setVisible(true);
	}

	/**
	 * Envía el emoji seleccionado al contacto actual y actualiza la vista del chat.
	 * 
	 * @param emojiIndex Índice del emoji seleccionado.
	 */
	private void enviarEmoji(int emojiIndex) {
		if (contacto == null)
			return;
		else if (Controlador.INSTANCE.isContactoIndividual(contacto)) {
			Controlador.INSTANCE.registrarMensajeContacto((ContactoIndividual) contacto, "", emojiIndex);
		} else if (Controlador.INSTANCE.isGrupo(contacto)) {
			Controlador.INSTANCE.registrarMensajeGrupo((Grupo) contacto, "", emojiIndex);
		}
		recuperarMensajes();
		refrescarPanelContactos();
	}

	/**
	 * Cierra la sesión del usuario actual y muestra la ventana de inicio de sesión.
	 */
	private void cerrarSesion() {
		Controlador.INSTANCE.cerrarSesion();
		VentanaLogin ventanaLogin = new VentanaLogin();
		dispose();
		ventanaLogin.mostrarLogin(this.getSize(), this.getLocation());
	}

	/**
	 * Cambia la imagen del usuario actual solicitando una URL al usuario.
	 */
	private void cambiarImagen() {
		String url = JOptionPane.showInputDialog(this, "Introduce la URL de la imagen:",
				"Seleccionar imagen desde internet", JOptionPane.PLAIN_MESSAGE);

		if (url != null && !url.trim().isEmpty()) {
			cargarImagenDesdeURL(url.trim());
		}
	}

	/**
	 * Carga una imagen desde una URL proporcionada por el usuario. Si la imagen es
	 * válida, actualiza la imagen del usuario actual y la muestra en la interfaz.
	 * Si hay un error, muestra un mensaje de error y permite volver a intentar.
	 * 
	 * @param urlString URL de la imagen a cargar.
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
				cambiarImagen(); // Volver a intentar
				return;
			}

			// Cambiar la imagen del usuario actual usando el controlador
			Controlador.INSTANCE.cambiarImagenUsuarioActual(urlString);

			// Actualizar la imagen visualmente en la interfaz
			actualizarImagenEnInterfaz(image);

			JOptionPane.showMessageDialog(this,
					"Imagen de " + Controlador.INSTANCE.getNombreUsuarioActual() + " modificada", "Cambio de imagen OK",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"Error al cargar la imagen desde la URL:\n" + e.getMessage() + "\n\nVerifica que:\n"
							+ "- La URL sea correcta\n" + "- Tengas conexión a internet\n"
							+ "- El enlace apunte a una imagen válida (PNG, JPG, GIF, etc.)",
					"Error al cargar imagen", JOptionPane.ERROR_MESSAGE);
			cambiarImagen(); // Volver a intentar
		}
	}

	/**
	 * Actualiza la imagen del usuario en la interfaz gráfica. Escala la imagen a un
	 * tamaño fijo (16x16) y la establece en el JLabel correspondiente.
	 * 
	 * @param image Imagen a mostrar.
	 */
	private void actualizarImagenEnInterfaz(BufferedImage image) {
		int anchoDeseado = 16; // altura/ancho fija que quieres para la barra
		int altoDeseado = 16;

		Image imagenEscalada = image.getScaledInstance(anchoDeseado, altoDeseado, Image.SCALE_SMOOTH);
		lblImagenUsuario.setIcon(new ImageIcon(imagenEscalada));

		// Establece tamaño fijo al JLabel para que no expanda la barra
		lblImagenUsuario.setPreferredSize(new Dimension(anchoDeseado, altoDeseado));

		lblImagenUsuario.setMaximumSize(new Dimension(anchoDeseado, altoDeseado));
		lblImagenUsuario.setMinimumSize(new Dimension(anchoDeseado, altoDeseado));

		lblImagenUsuario.revalidate();
		lblImagenUsuario.repaint();
	}

	/**
	 * Carga la imagen del perfil del usuario actual desde la URL almacenada en el
	 * controlador. Si no hay imagen, muestra un mensaje de error.
	 */
	@SuppressWarnings("deprecation")
	private void cargarImagenPerfilUsuario() {
		String rutaImagen = Controlador.INSTANCE.getImagenUsuarioActual();
		if (rutaImagen != null && !rutaImagen.isEmpty()) {
			try {
				URL url = new URL(rutaImagen);
				BufferedImage image = ImageIO.read(url);
				if (image != null) {
					actualizarImagenEnInterfaz(image);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "No se cargó la imagen. Una lástima...");
			}
		}
	}

	/**
	 * Refresca la imagen del usuario actual en la barra de menú. Se llama al
	 * iniciar la ventana y después de cambiar la imagen.
	 */
	private void refrescarImagen() {
		String rutaImagen = Controlador.INSTANCE.getImagenUsuarioActual();
		ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
		Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
		MTuContacto.setIcon(iconoEscalado);
	}

	/**
	 * Genera un PDF con el listado de contactos del usuario actual.
	 */
	private void gestionarPdfListado() {
		if (Controlador.INSTANCE.isPremiumUsuarioActual()) {
			if (Controlador.INSTANCE.generarPdfListado()) {
				JOptionPane.showMessageDialog(this,
						"Se ha generado el pdf exitosamente, en la carpeta de Descargas. Disfrútalo", "Pdf ok",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "Error al generar el pdf. Prueba de nuevo", "Pdf mal",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "No eres premium. ESPABILA", "No premium", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Genera un PDF del chat actual con el contacto seleccionado.
	 */
	private void gestionarPdfChat() {
		if (Controlador.INSTANCE.isPremiumUsuarioActual()) {
			if (contacto != null && Controlador.INSTANCE.isContactoIndividual(contacto)) {
				if (Controlador.INSTANCE.generarPdfChat((ContactoIndividual) contacto)) {
					JOptionPane.showMessageDialog(this,
							"Se ha generado el pdf exitosamente, en la carpeta de Descargas. Disfrútalo", "Pdf ok",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Error al generar el pdf. Prueba de nuevo", "Pdf mal",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "No hay ningún contacto seleccionado para generar el PDF", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "No eres Premium, si quieres estos beneficios hazte Usuario Premium",
					"Espabila", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Abre el diálogo de gestión de premium. Permite activar o desactivar la
	 * suscripción premium del usuario actual.
	 */
	private void abrirPremium() {
		if (!Controlador.INSTANCE.isPremiumUsuarioActual()) {
			int respuestaActivar = JOptionPane.showConfirmDialog(this, "¿Desea activar premium?", "Gestión premium",
					JOptionPane.YES_NO_OPTION);
			if (respuestaActivar == JOptionPane.YES_OPTION) {
				Controlador.INSTANCE.activarPremiumUsuarioActual();
				JOptionPane.showMessageDialog(this,
						"Premium activado. Gracias por esos "
								+ (Usuario.PRECIO_INICIAL - Controlador.INSTANCE.getDescuentoCalculadoUsuarioActual())
								+ " euros",
						"Gestión premium", JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			int respuestaDesactivar = JOptionPane.showConfirmDialog(this, "¿Desea desactivar premium?",
					"Gestión premium", JOptionPane.YES_NO_OPTION);
			if (respuestaDesactivar == JOptionPane.YES_OPTION) {
				Controlador.INSTANCE.desactivarPremiumUsuarioActual();
				JOptionPane.showMessageDialog(this, "Premium desactivado", "Gestión premium",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * Abre la ventana de contactos individuales.
	 */
	private void abrirIndividuales() {
		VentanaContactos vContactos = new VentanaContactos();
		dispose();
		vContactos.mostrarVentanaContactos(this.getSize(), this.getLocation());
	}

	/**
	 * Abre la ventana de grupos.
	 */
	private void abrirGrupos() {
		VentanaGrupos v = new VentanaGrupos();
		dispose();
		v.mostrarVentanaGrupos(this.getSize(), this.getLocation());
	}

	/**
	 * Abre la ventana de búsqueda de usuarios.
	 */
	private void abrirBuscar() {
		VentanaBuscar v = new VentanaBuscar();
		dispose();
		v.mostrarVentanaBuscar(this.getSize(), this.getLocation());
	}

	/**
	 * Crea un mensaje en el chat con el texto y el usuario con fecha
	 * 
	 * @param mensaje         - Texto del mensaje a mostrar.
	 * @param usuarioConFecha - Texto del usuario con la fecha del mensaje.
	 * @param tipo            - Tipo de mensaje (enviado o recibido).
	 * @param emojiIndex      - Índice del emoji asociado al mensaje. Si es
	 *                        Mensaje.ICONO_NULL, no se muestra emoji.
	 */
	private void crearMensaje(String mensaje, String usuarioConFecha, int tipo, int emojiIndex) {
		Color color = (tipo == BubbleText.RECEIVED) ? Color.PINK : Color.CYAN;

		if (mensaje.isEmpty() && emojiIndex != Mensaje.ICONO_NULL) {
			// Usar el emoji que se pasa como parámetro, no el último mensaje
			BubbleText burbuja = new BubbleText(chat, emojiIndex, color, usuarioConFecha, tipo, 12);
			chat.add(burbuja);
		} else {
			BubbleText burbuja = new BubbleText(chat, mensaje, color, usuarioConFecha, tipo);
			chat.add(burbuja);
		}

		chat.revalidate();
		chat.repaint();
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
	}

	/**
	 * Recupera los mensajes del contacto seleccionado y los muestra en el panel de
	 * chat.
	 */
	private void recuperarMensajes() {
		chat.removeAll();
		chat.revalidate();
		chat.repaint();

		Controlador.INSTANCE.getMensajesInvertidos(contacto).forEach(mensaje -> {
			String textoMensaje = Controlador.INSTANCE.getTextoMensaje(mensaje);
			int emojiMensaje = Controlador.INSTANCE.getEmojiMensaje(mensaje); // Obtener el emoji de ESTE mensaje
																				// específico

			if (Controlador.INSTANCE.getTipoMensaje(mensaje).equals(TipoMensaje.ENVIADO)) {
				crearMensaje(textoMensaje, Controlador.INSTANCE.getNombreUsuarioActual(), BubbleText.SENT,
						emojiMensaje);
			} else if (Controlador.INSTANCE.getTipoMensaje(mensaje).equals(TipoMensaje.RECIBIDO)) {
				crearMensaje(textoMensaje, Controlador.INSTANCE.getNombreContacto(contacto), BubbleText.RECEIVED,
						emojiMensaje);
			}
		});

		SwingUtilities.invokeLater(() -> {
			scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
		});

		refrescarPanelContactos();

	}

	/**
	 * Envía el texto ingresado en el área de texto al contacto seleccionado creando
	 * un mensaje.
	 */
	private void enviarTexto() {
		String texto = textArea.getText().trim();
		if (texto.isEmpty()) {
			return;
		} else if (Controlador.INSTANCE.isContactoIndividual(contacto)) {
			Controlador.INSTANCE.registrarMensajeContacto((ContactoIndividual) contacto, texto, Mensaje.ICONO_NULL);
		} else if (Controlador.INSTANCE.isGrupo(contacto)) {
			Controlador.INSTANCE.registrarMensajeGrupo((Grupo) contacto, texto, Mensaje.ICONO_NULL);
		}
		textArea.setText("");
		recuperarMensajes();
		refrescarPanelContactos();
	}

	/**
	 * Crea los contenedores para los contactos en la lista de contactos.
	 * 
	 * @param contacto - El contacto para el cual se crea el contenedor.
	 */
	@SuppressWarnings("deprecation")
	private void crearContenedoresContactos(Contacto contacto) {
		Boolean isAnadido = true;
		if (contacto instanceof ContactoIndividual) {
			isAnadido = Controlador.INSTANCE.isContactoIndividualAñadido((ContactoIndividual) contacto);
		}
		Mensaje msg = Controlador.INSTANCE.getUltimoMensaje(contacto);
		boolean tieneMensaje = msg != null;

		JButton contenedor = new JButton();
		contenedor.setLayout(new GridBagLayout());
		contenedor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
		contenedor.setBackground(new Color(242, 216, 245));
		contenedor.setBorder(BorderFactory.createLineBorder(new Color(135, 0, 146)));

		contenedor.setFocusPainted(false);
		contenedor.setContentAreaFilled(false);
		contenedor.setOpaque(true);

		contenedor.addActionListener(e -> {
			this.contacto = contacto;
			lblContactoChat.setText(Controlador.INSTANCE.getNombreContacto(contacto));
			recuperarMensajes();
		});

		GridBagConstraints gbc_contenedor = new GridBagConstraints();
		gbc_contenedor.insets = new Insets(5, 5, 5, 5);
		gbc_contenedor.gridy = 0;
		gbc_contenedor.gridx = 0;
		gbc_contenedor.anchor = GridBagConstraints.WEST;
		JLabel lblFotoContacto = new JLabel("");
		String urlImagen = Controlador.INSTANCE.getImagenContacto(contacto);
		if (urlImagen != null && !urlImagen.isEmpty()) {
			try {
				BufferedImage imagen = ImageIO.read(new URL(urlImagen));
				if (imagen != null) {
					int ancho = 20;
					int alto = 20;
					Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
					lblFotoContacto.setIcon(new ImageIcon(imagenEscalada));
					lblFotoContacto.setPreferredSize(new Dimension(ancho, alto));
					lblFotoContacto.setMaximumSize(new Dimension(ancho, alto));
					lblFotoContacto.setMinimumSize(new Dimension(ancho, alto));

				}
			} catch (Exception e) {
				// Podrías poner una imagen por defecto aquí si quieres
			}
		}

		contenedor.add(lblFotoContacto, gbc_contenedor);

		gbc_contenedor.gridx = 1;
		gbc_contenedor.anchor = GridBagConstraints.CENTER;
		contenedor.add(new JLabel(Controlador.INSTANCE.getNombreContacto(contacto)), gbc_contenedor);

		gbc_contenedor.gridx = 2;
		gbc_contenedor.anchor = GridBagConstraints.EAST;
		JLabel lblFecha = new JLabel();
		if (tieneMensaje && msg.getMomentoEnvio() != null) {
			lblFecha.setText(Controlador.INSTANCE.getMomentoEnvioMensaje(msg));
		}
		contenedor.add(lblFecha, gbc_contenedor);

		gbc_contenedor.gridx = 0;
		gbc_contenedor.gridy = 1;
		gbc_contenedor.gridwidth = 3; // ocupar toda la fila
		gbc_contenedor.weightx = 1.0;
		gbc_contenedor.fill = GridBagConstraints.HORIZONTAL;

		// Panel horizontal para contener el texto y el botón
		JPanel panelTextoBoton = new JPanel();
		panelTextoBoton.setLayout(new BoxLayout(panelTextoBoton, BoxLayout.X_AXIS));
		panelTextoBoton.setOpaque(false); // para heredar el fondo del contenedor

		if (tieneMensaje) {
			if (Controlador.INSTANCE.getEmojiMensaje(msg) != Mensaje.ICONO_NULL) {
				ImageIcon original = BubbleText.getEmoji(Controlador.INSTANCE.getEmojiMensaje(msg));
				if (original != null) {
					Image scaledImage = original.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					ImageIcon scaledIcon = new ImageIcon(scaledImage);

					JLabel lblEmoji = new JLabel(scaledIcon);
					lblEmoji.setOpaque(true);
					lblEmoji.setBackground(new Color(180, 159, 185));
					lblEmoji.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					lblEmoji.setAlignmentX(Component.LEFT_ALIGNMENT);

					// Hacer que llene el ancho restante
					lblEmoji.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
					panelTextoBoton.add(lblEmoji);
				}
			} else {
				JTextArea texto = new JTextArea(Controlador.INSTANCE.getTextoMensaje(msg));
				texto.setLineWrap(true);
				texto.setWrapStyleWord(true);
				texto.setEditable(false);
				texto.setBackground(new Color(180, 159, 185));
				texto.setMargin(new Insets(5, 5, 5, 5));
				panelTextoBoton.add(texto);
			}
		}

		// Si no está añadido, añado el botón
		if (!isAnadido) {
			JButton btnMas = new JButton();
			btnMas.setPreferredSize(new Dimension(30, 30)); // cuadrado pequeño
			btnMas.setMaximumSize(new Dimension(30, 30));
			btnMas.setText("+");
			btnMas.setFocusable(false);
			btnMas.setHorizontalAlignment(JButton.CENTER);
			btnMas.setVerticalAlignment(JButton.CENTER);
			btnMas.setMargin(new Insets(0, 0, 0, 0));
			btnMas.setContentAreaFilled(false);
			btnMas.setOpaque(true);
			panelTextoBoton.add(Box.createHorizontalStrut(5)); // espacio entre texto y botón
			panelTextoBoton.add(btnMas);

			btnMas.addActionListener(e -> anadirContacto((ContactoIndividual) contacto));

		}

		contenedor.add(panelTextoBoton, gbc_contenedor);

		contactos.add(contenedor);
		contactos.revalidate();
		contactos.repaint();
	}

	/**
	 * Refresca el panel de contactos, ordenando y mostrando los contactos del
	 * usuario actual. Los contactos con mensajes recientes se muestran primero,
	 * seguidos de los que no tienen mensajes.
	 */
	private void refrescarPanelContactos() {
		contactos.removeAll();

		Usuario u = Controlador.INSTANCE.getUsuarioActual();

		// Obtener todos los contactos del usuario (grupos + individuales)
		List<Contacto> listaContactos = new ArrayList<>(u.getContactos());

		// Separar contactos con y sin mensajes
		List<Contacto> conMensajes = new ArrayList<>();
		List<Contacto> sinMensajes = new ArrayList<>();

		for (Contacto c : listaContactos) {
			Mensaje m = Controlador.INSTANCE.getUltimoMensaje(c);
			if (m != null && m.getMomentoEnvio() != null) {
				conMensajes.add(c);
			} else {
				sinMensajes.add(c);
			}
		}

		// Ordenar los contactos con mensajes por fecha (más recientes primero)
		conMensajes.sort((c1, c2) -> {
			LocalDateTime tiempo1 = Controlador.INSTANCE.getUltimoMensaje(c1).getMomentoEnvio();
			LocalDateTime tiempo2 = Controlador.INSTANCE.getUltimoMensaje(c2).getMomentoEnvio();
			return tiempo2.compareTo(tiempo1); // Más reciente primero
		});

		// Crear los contenedores en orden
		for (Contacto c : conMensajes) {
			crearContenedoresContactos(c);
		}
		for (Contacto c : sinMensajes) {
			crearContenedoresContactos(c);
		}

		contactos.revalidate();
		contactos.repaint();
	}

	/**
	 * Añade un nuevo contacto individual al usuario actual. Solicita el nombre y lo
	 * añade a la lista de contactos.
	 * 
	 * @param contacto - Contacto individual que se va a añadir.
	 */
	private void anadirContacto(ContactoIndividual contacto) {
		JTextField campoNombre = new JTextField();
		JTextField campoMovil = new JTextField();

		// Prellenar campoMovil con el nombre del contacto, que actúa como número
		campoMovil.setText(Controlador.INSTANCE.getNombreContacto(contacto));
		campoMovil.setEditable(false);

		Object[] campos = { "Nombre:", campoNombre, "Móvil:", campoMovil };

		int resultado = JOptionPane.showConfirmDialog(this, campos, "Nuevo Contacto", JOptionPane.OK_CANCEL_OPTION);

		if (resultado == JOptionPane.OK_OPTION) {
			String nuevoNombre = campoNombre.getText().trim();

			if (!nuevoNombre.isEmpty()) {
				Controlador.INSTANCE.cambiarNombreContactoIndividual(contacto, nuevoNombre);

				JOptionPane.showMessageDialog(this, "Contacto agregado correctamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);

				refrescarPanelContactos();
			} else {
				JOptionPane.showMessageDialog(this, "Debes introducir un nombre válido.", "Nombre vacío",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

}
