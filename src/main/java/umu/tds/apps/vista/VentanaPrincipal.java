package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import tds.BubbleText;
import umu.tds.apps.controlador.Controlador;
import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.ContactoIndividual;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.TipoMensaje;
import umu.tds.apps.dominio.Usuario;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel chat = new JPanel();
	private JPanel contactos = new JPanel();
	private Component horizontalGlue;
	private Component horizontalGlue_1;
	private JMenuBar menuBar;
	private JMenu MTuContacto;
	private JMenuItem MCerrarSesion;
	private JMenuItem MCambiarImagenPerfil;
	private JMenuItem MBuscar;
	private ImageIcon imagenBuscar;
	private ImageIcon imagenPremium;
	private JPanel panel;
	private JButton btnPremium;
	private JButton btnPdfListado;
	private JMenu mnContactos;
	private JMenuItem mntmIndividuales;
	private JMenuItem mntmGrupos;
	private JPanel panelCentral;
	private JPanel panelInfo;
	private JScrollPane scrollPane;
	private JLabel lblContactoChat;
	private JButton btnPdfChat;
	private JPanel panelEnviarMensaje;
	private JButton btnEnviar;
	private JTextArea textArea;
	private JLabel lblImagenUsuario;
	private ContactoIndividual contacto;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				VentanaPrincipal window = new VentanaPrincipal();
				window.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	protected VentanaPrincipal() {
		initialize();
	}
	
	protected VentanaPrincipal(ContactoIndividual contacto) {
		this.contacto = contacto;
		initialize();
		recuperarMensajes();
	}

	protected void mostrarVentanaPrincipal(Dimension tam, Point ubi) {
		setVisible(true);
		setSize(tam);
		setLocation(ubi);
	}
	protected void mostrarVentanaPrincipal(Dimension tam, Point ubi, Contacto contacto) {
		setVisible(true);
		setSize(tam);
		setLocation(ubi);
	}

	private void initialize() {
		setBounds(100, 100, 601, 449);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("/imagenes/iconoPestanas.PNG")).getImage());
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

		contactos.setPreferredSize(new Dimension(200, 700));
		contactos.setLayout(new BoxLayout(contactos, BoxLayout.Y_AXIS));
		getContentPane().add(contactos, BorderLayout.WEST);
		
		panelCentral = new JPanel();
		getContentPane().add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));
		
		panelInfo = new JPanel();
		panelCentral.add(panelInfo, BorderLayout.NORTH);
		
		if(contacto != null) {
			lblContactoChat = new JLabel(Controlador.INSTANCE.getNombreContacto(contacto));
		}
		else {
			lblContactoChat = new JLabel("Nombre contacto");
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
		gbl_panelEnviarMensaje.columnWidths = new int[]{0, 0};
		gbl_panelEnviarMensaje.rowHeights = new int[]{0};
		gbl_panelEnviarMensaje.columnWeights = new double[]{1.0, 0.0}; 
		gbl_panelEnviarMensaje.rowWeights = new double[]{0.0};
		panelEnviarMensaje.setLayout(gbl_panelEnviarMensaje);

		textArea = new JTextArea(2, 20);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.fill = GridBagConstraints.HORIZONTAL;
		gbc_textArea.insets = new Insets(5, 5, 5, 5);
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 0;
		gbc_textArea.weightx = 1.0;
		panelEnviarMensaje.add(textArea, gbc_textArea);

		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(e -> enviarTexto());
		GridBagConstraints gbc_btnEnviar = new GridBagConstraints();
		gbc_btnEnviar.insets = new Insets(5, 5, 5, 5);
		gbc_btnEnviar.gridx = 1;
		gbc_btnEnviar.gridy = 0;
		panelEnviarMensaje.add(btnEnviar, gbc_btnEnviar);
		
		Usuario u = Controlador.INSTANCE.getUsuarioActual();
		for (ContactoIndividual c : Controlador.INSTANCE.getContactosIndividuales(u)) {
			crearContenedoresContactos(c);
		}
		
	}

	private void cerrarSesion() {
		Controlador.INSTANCE.cerrarSesion();
		VentanaLogin ventanaLogin = new VentanaLogin();
		dispose();
		ventanaLogin.mostrarLogin(this.getSize(), this.getLocation());
	}

	private void cambiarImagen() {
		String url = JOptionPane.showInputDialog(this, 
			"Introduce la URL de la imagen:", 
			"Seleccionar imagen desde internet", 
			JOptionPane.PLAIN_MESSAGE);
		
		if (url != null && !url.trim().isEmpty()) {
			cargarImagenDesdeURL(url.trim());
		}
	}

	@SuppressWarnings("deprecation")
	private void cargarImagenDesdeURL(String urlString) {
		try {
			URL url = new URL(urlString);
			BufferedImage image = ImageIO.read(url);
			
			if (image == null) {
				JOptionPane.showMessageDialog(this, 
					"El enlace no corresponde a una imagen válida.\nPor favor, introduce una URL de imagen válida.", 
					"Imagen no válida", 
					JOptionPane.ERROR_MESSAGE);
				cambiarImagen(); // Volver a intentar
				return;
			}
			
			// Cambiar la imagen del usuario actual usando el controlador
			Controlador.INSTANCE.cambiarImagenUsuarioActual(urlString);
			
			// Actualizar la imagen visualmente en la interfaz
			actualizarImagenEnInterfaz(image);
			
			JOptionPane.showMessageDialog(this,
				"Imagen de " + Controlador.INSTANCE.getNombreUsuarioActual() + " modificada",
				"Cambio de imagen OK", JOptionPane.INFORMATION_MESSAGE);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, 
				"Error al cargar la imagen desde la URL:\n" + e.getMessage() + 
				"\n\nVerifica que:\n" +
				"- La URL sea correcta\n" +
				"- Tengas conexión a internet\n" +
				"- El enlace apunte a una imagen válida (PNG, JPG, GIF, etc.)", 
				"Error al cargar imagen", 
				JOptionPane.ERROR_MESSAGE);
			cambiarImagen(); // Volver a intentar
		}
	}
	
	private void actualizarImagenEnInterfaz(BufferedImage image) {
		int anchoDeseado = 16;  // altura/ancho fija que quieres para la barra
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
	            // Manejar error (por ejemplo, mostrar imagen por defecto)
	            System.err.println("No se pudo cargar la imagen del perfil: " + e.getMessage());
	        }
	    }
	}
	
	private void refrescarImagen() {
		String rutaImagen = Controlador.INSTANCE.getImagenUsuarioActual();
		ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
		Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
		MTuContacto.setIcon(iconoEscalado);
	}

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

	private void gestionarPdfChat() {
		if (Controlador.INSTANCE.isPremiumUsuarioActual()) {
			if (Controlador.INSTANCE.generarPdfChat(contacto)) {
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

	private void abrirIndividuales() {
		VentanaContactos vContactos = new VentanaContactos();
		dispose();
		vContactos.mostrarVentanaContactos(this.getSize(), this.getLocation());
	}

	private void abrirGrupos() {
		VentanaGrupos v = new VentanaGrupos();
		dispose();
		v.mostrarVentanaGrupos(this.getSize(), this.getLocation());
	}

	private void abrirBuscar() {
		VentanaBuscar v = new VentanaBuscar();
		dispose();
		v.mostrarVentanaBuscar(this.getSize(), this.getLocation());
	}

	private void crearMensaje(String mensaje, String usuarioConFecha, int tipo) {
		Color color = (tipo == BubbleText.RECEIVED) ? Color.PINK : Color.CYAN;
		
		BubbleText burbuja = new BubbleText(chat, mensaje, color, usuarioConFecha, tipo);
		burbuja.setMaximumSize(new Dimension(380, Integer.MAX_VALUE));
		chat.add(burbuja);
		chat.revalidate();
		chat.repaint();
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
	}
	
	private void recuperarMensajes() {
		chat.removeAll();
	    chat.revalidate();
	    chat.repaint();
	    Controlador.INSTANCE.getMensajesInvertidos(contacto).forEach(mensaje -> {
	        if(Controlador.INSTANCE.getTipoMensaje(mensaje).equals(TipoMensaje.ENVIADO)) {
	            crearMensaje(Controlador.INSTANCE.getTextoMensaje(mensaje), Controlador.INSTANCE.getNombreUsuarioActual(), BubbleText.SENT);
	        }
	        else if(Controlador.INSTANCE.getTipoMensaje(mensaje).equals(TipoMensaje.RECIBIDO)) {
	            crearMensaje(Controlador.INSTANCE.getTextoMensaje(mensaje), Controlador.INSTANCE.getNombreContacto(contacto), BubbleText.RECEIVED);
	        }
	    });
	    refrescarPanelContactos();
	}
	
	private void enviarTexto() {
		String texto = textArea.getText().trim();
	    if (texto.isEmpty()) {
	        return;
	    }
	    Controlador.INSTANCE.registrarMensajeContacto(contacto, texto, Mensaje.ICONO_NULL);
	    textArea.setText("");
	    recuperarMensajes();
	    refrescarPanelContactos();
	}
	
	private void crearContenedoresContactos(Contacto contacto) {
	    Mensaje msg = Controlador.INSTANCE.getUltimoMensaje(contacto);
	    if (msg == null) {
	        msg = new Mensaje("", 0, TipoMensaje.ENVIADO);
	    }

	    JButton contenedor = new JButton();
	    contenedor.setLayout(new GridBagLayout());
	    contenedor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
	    contenedor.setBackground(new Color(242, 216, 245));
	    contenedor.setBorder(BorderFactory.createLineBorder(new Color(135, 0, 146)));

	    contenedor.setFocusPainted(false);
	    contenedor.setContentAreaFilled(false);
	    contenedor.setOpaque(true);

	    contenedor.addActionListener(e -> {
	        if (contacto instanceof ContactoIndividual) {
	            this.contacto = (ContactoIndividual) contacto;
	            lblContactoChat.setText(Controlador.INSTANCE.getNombreContacto(contacto));
	            recuperarMensajes();
	        }
	    });

	    GridBagConstraints gbc_contenedor = new GridBagConstraints();
	    gbc_contenedor.insets = new Insets(5, 5, 5, 5);
	    gbc_contenedor.gridy = 0;
	    gbc_contenedor.gridx = 0;
	    gbc_contenedor.anchor = GridBagConstraints.WEST;
	    JLabel lblFotoContacto = new JLabel(""); 
	    // TODO Aqui habria que meter la foto 
	    contenedor.add(lblFotoContacto, gbc_contenedor);

	    gbc_contenedor.gridx = 1;
	    gbc_contenedor.anchor = GridBagConstraints.CENTER;
	    contenedor.add(new JLabel(Controlador.INSTANCE.getNombreContacto(contacto)), gbc_contenedor);

	    gbc_contenedor.gridx = 2;
	    gbc_contenedor.anchor = GridBagConstraints.EAST;
	    JLabel lblFecha = new JLabel(Controlador.INSTANCE.getMomentoEnvioMensaje(msg));
	    contenedor.add(lblFecha, gbc_contenedor);

	    gbc_contenedor.gridx = 0;
	    gbc_contenedor.gridy = 1;
	    gbc_contenedor.gridwidth = 3;
	    gbc_contenedor.gridwidth = GridBagConstraints.REMAINDER;
	    gbc_contenedor.fill = GridBagConstraints.HORIZONTAL;
	    gbc_contenedor.anchor = GridBagConstraints.WEST;
	    gbc_contenedor.weightx = 1.0;
	    JTextArea texto = new JTextArea(Controlador.INSTANCE.getTextoMensaje(msg));
	    texto.setLineWrap(true);
	    texto.setWrapStyleWord(true);
	    texto.setEditable(false);
	    texto.setBackground(new Color(180, 159, 185));
	    texto.setMargin(new Insets(5, 5, 5, 5));
	    
	    texto.setAlignmentX(Component.LEFT_ALIGNMENT);
	    contenedor.add(texto, gbc_contenedor);

	    contactos.add(contenedor);
	    contactos.revalidate();
	    contactos.repaint();
	}
	
	private void refrescarPanelContactos() {
	    contactos.removeAll();
	    Usuario u = Controlador.INSTANCE.getUsuarioActual();
	    for (ContactoIndividual c : Controlador.INSTANCE.getContactosIndividuales(u)) {
	        crearContenedoresContactos(c);
	    }
	    contactos.revalidate();
	    contactos.repaint();
	}

	
	
}
