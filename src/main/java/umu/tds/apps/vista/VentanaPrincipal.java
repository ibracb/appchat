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
import javax.swing.filechooser.FileNameExtensionFilter;

import tds.BubbleText;
import umu.tds.apps.controlador.Controlador;
import umu.tds.apps.dominio.Usuario;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel chat = new JPanel();
	private JScrollPane scrollChat;
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
	private JButton btnPdfChat;

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

	public VentanaPrincipal() {
		initialize();
	}

	public void mostrarVentanaPrincipal(Dimension tam, Point ubi) {
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
		
		btnPdfChat = new JButton("PDF Chat");
		btnPdfChat.setFont(new Font("Georgia", Font.BOLD, 12));
		menuBar.add(btnPdfChat);

		contactos.setPreferredSize(new Dimension(200, 700));
		contactos.setLayout(new BoxLayout(contactos, BoxLayout.Y_AXIS));
		getContentPane().add(contactos, BorderLayout.WEST);

		chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
		chat.setBackground(Color.WHITE); // Mejora estética
		scrollChat = new JScrollPane(chat);
		scrollChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollChat.setPreferredSize(new Dimension(400, 700));
		getContentPane().add(scrollChat, BorderLayout.CENTER);

		crearMensaje("Hola", "IBRA", BubbleText.SENT);
		crearMensaje("Hola", "MARIA", BubbleText.RECEIVED);
	}

	private void cerrarSesion() {
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
					JOptionPane.showMessageDialog(this, "Error inesperado", "Vaya fail XD",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Por favor selecciona un fichero .png válido.",
						"Fichero no válido", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(this, "No eres premium. ESPABILA", "No premium",
					JOptionPane.ERROR_MESSAGE);
		}
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
		vContactos.setVisible(true);
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
		scrollChat.getVerticalScrollBar().setValue(scrollChat.getVerticalScrollBar().getMaximum());
	}
}
