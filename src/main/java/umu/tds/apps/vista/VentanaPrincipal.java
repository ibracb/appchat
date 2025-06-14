package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import tds.BubbleText;
import umu.tds.apps.controlador.Controlador;
import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.Usuario;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

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
	private JLabel lblNewLabel;
	private JButton btnPdfChat;
	private JPanel panelEnviarMensaje;
	private JTextField textField;
	private JButton btnEnviar;
	private JTextArea textArea;

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
		
		lblNewLabel = new JLabel("NOMBRE DEL CONTACTO");
		panelInfo.add(lblNewLabel);
		
		btnPdfChat = new JButton("PDF Chat");
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
		GridBagConstraints gbc_btnEnviar = new GridBagConstraints();
		gbc_btnEnviar.insets = new Insets(5, 5, 5, 5);
		gbc_btnEnviar.gridx = 1;
		gbc_btnEnviar.gridy = 0;
		panelEnviarMensaje.add(btnEnviar, gbc_btnEnviar);
		
		
		

		/*chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
		chat.setBackground(Color.WHITE); // Mejora estética
		scrollChat = new JScrollPane(chat);
		scrollChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollChat.setPreferredSize(new Dimension(400, 700));
		getContentPane().add(scrollChat, BorderLayout.CENTER);*/
		

		crearMensaje("Hola", "IBRA", BubbleText.SENT);
		crearMensaje("Hola", "MARIA", BubbleText.RECEIVED);
		crearMensaje("Hola", "IBRA", BubbleText.SENT);
		crearMensaje("Hola", "MARIA", BubbleText.RECEIVED);
		crearMensaje("Hola", "IBRA", BubbleText.SENT);
		crearMensaje("Hola", "MARIA", BubbleText.RECEIVED);
		crearMensaje("Hola", "IBRA", BubbleText.SENT);
		crearMensaje("Hola", "MARIA", BubbleText.RECEIVED);
		crearMensaje("Hola", "IBRA", BubbleText.SENT);
		crearMensaje("Hola", "MARIA", BubbleText.RECEIVED);
		crearMensaje("Hola", "IBRA", BubbleText.SENT);
		crearMensaje("Hola", "MARIA", BubbleText.RECEIVED);
		
		
		
		
	}

	private void cerrarSesion() {
		Controlador.INSTANCE.cerrarSesion();
		VentanaLogin ventanaLogin = new VentanaLogin();
		dispose();
		ventanaLogin.mostrarLogin(this.getSize(), this.getLocation());
	}

	private void cambiarImagen() {
		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle("Selecciona un fichero PNG");
		selector.setFileFilter(new FileNameExtensionFilter("Imágenes PNG", "png"));
		int resultado = selector.showOpenDialog(null);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			File archivo = selector.getSelectedFile();
			String nombreArchivo = archivo.getName().toLowerCase();
			if (nombreArchivo.endsWith(".png")) {
				try {
					Controlador.INSTANCE.cambiarImagenUsuarioActual(archivo.getCanonicalPath());
					refrescarImagen();
					JOptionPane.showMessageDialog(this,
							"Imagen de " + Controlador.INSTANCE.getNombreUsuarioActual() + " modificada",
							"Cambio de imagen OK", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, "Error inesperado", "Vaya fail XD", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Por favor selecciona un fichero .png válido.", "Fichero no válido",
						JOptionPane.ERROR_MESSAGE);
				cambiarImagen();
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
		// TODO se debe implementar
	}

	private void abrirPremium() {
		if (!Controlador.INSTANCE.isPremiumUsuarioActual()) {
			int respuestaActivar = JOptionPane.showConfirmDialog(this, "¿Desea activar premium?", "Gestión premium",
					JOptionPane.YES_NO_OPTION);
			if (respuestaActivar == JOptionPane.YES_OPTION) {
				Controlador.INSTANCE.activarPremiumUsuarioActual();
				Controlador.INSTANCE.modificarUsuario();
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

	private void crearMensaje(String mensaje, String usuario, int tipo) {
		Color color = (tipo == BubbleText.RECEIVED) ? Color.PINK : Color.CYAN;
		
		BubbleText burbuja = new BubbleText(chat, mensaje, color, usuario, tipo);
		burbuja.setMaximumSize(new Dimension(380, Integer.MAX_VALUE));
		chat.add(burbuja);
		chat.revalidate();
		chat.repaint();
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
	}
	
	private void crearContenedoresContactos (Contacto contacto) {
		Mensaje msg = Controlador.INSTANCE.getUltimoMensaje(contacto);
		JPanel contendor = new JPanel(new GridBagLayout());
		contendor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
		contendor.setBorder(BorderFactory.createLineBorder(new Color(135, 0, 146)));
		contendor.setBackground(new Color(242, 216, 245));
		
		GridBagConstraints gbc_contenedor = new GridBagConstraints();
		gbc_contenedor.insets = new Insets(5, 5, 5, 5);
		gbc_contenedor.gridy = 0;
		gbc_contenedor.gridx = 0;
		gbc_contenedor.anchor = GridBagConstraints.WEST;
	    JLabel lblFotoContacto = new JLabel(""); 
	    // TODO Aqui habria que meter la foto
	    contendor.add(lblFotoContacto, gbc_contenedor);
		
		
		gbc_contenedor.insets = new Insets(5, 5, 5, 5);
		gbc_contenedor.gridy = 0;
		gbc_contenedor.gridx = 0;
		gbc_contenedor.anchor = GridBagConstraints.CENTER;
	    JLabel lblNombreContacto = new JLabel(contacto.getNombre()); 
	    contendor.add(lblNombreContacto, gbc_contenedor);
	    
	    gbc_contenedor.insets = new Insets(5, 5, 5, 5);
		gbc_contenedor.gridy = 0;
		gbc_contenedor.gridx = 0;
		gbc_contenedor.anchor = GridBagConstraints.EAST;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String formatted = msg.getMomentoEnvio().format(formatter);
	    JLabel lblFecha = new JLabel(formatted);
	    contendor.add(lblFecha, gbc_contenedor);
	    
	    gbc_contenedor.gridx = 0;
	    gbc_contenedor.gridy = 1;
	    gbc_contenedor.gridwidth = 3;
	    gbc_contenedor.fill = GridBagConstraints.HORIZONTAL;
	    gbc_contenedor.anchor = GridBagConstraints.CENTER;
	    JTextArea texto = new JTextArea(msg.getTexto());
	    texto.setText(msg.getTexto());
	    texto.setLineWrap(true);
	    texto.setWrapStyleWord(true);
	    texto.setEditable(false);
	    texto.setBackground(new Color(255, 255, 255));
	    contendor.add(texto, gbc_contenedor);
	}
	
	
}
