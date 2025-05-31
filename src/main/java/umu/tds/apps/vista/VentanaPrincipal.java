package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import tds.BubbleText;
import umu.tds.apps.controlador.Controlador;
import umu.tds.apps.dominio.Usuario;

public class VentanaPrincipal extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel chat = new JPanel();
	private JPanel contactos = new JPanel();
	private Component horizontalGlue;
	private Component horizontalGlue_1;
	private JMenuBar menuBar;
	private JMenu MTuContacto;
	private JMenuItem MCerrarSesion;
	private JMenuItem MCambiarImagenPerfil;
	private JMenuItem MContactos;
	private JMenuItem MBuscar;
	private ImageIcon imagenPerfil;
	private ImageIcon imagenBuscar;
	private ImageIcon imagenPremium;
	private JPanel panel;
	private JButton btnPremium;
	private JButton btnPdf;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
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
	public VentanaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
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
		
		
		
		MTuContacto = new JMenu("Nombre");
		// Hacer que el nombre sea el nombre del usuario logeado + apellidos
		MTuContacto.setFont(new Font("Georgia", Font.BOLD, 12));
		MTuContacto.setPreferredSize(new Dimension(200, 25));
		MTuContacto.setAlignmentX(Component.LEFT_ALIGNMENT);
		//MTuContacto.setIcon(new ImageIcon("FOTO QUE NOS HAN PASADO COMO FOTO DE PERFIL"));
		menuBar.add(MTuContacto);
		
		MCambiarImagenPerfil = new JMenuItem("Cambiar imagen de perfil");
		MCambiarImagenPerfil.setFont(new Font("Georgia", Font.PLAIN, 12));
		MTuContacto.add(MCambiarImagenPerfil);
		
		MCambiarImagenPerfil.addActionListener(this);
		
		MCerrarSesion = new JMenuItem("Cerrar sesión");
		MCerrarSesion.setFont(new Font("Georgia", Font.PLAIN, 12));
		MTuContacto.add(MCerrarSesion);
		
		MCerrarSesion.addActionListener(this);
		
		horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);
		
		horizontalGlue_1 = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue_1);
		
		MContactos = new JMenuItem("Contactos");
		MContactos.setFont(new Font("Georgia", Font.BOLD, 12));
		MContactos.setMaximumSize(new Dimension(128, 128));
		imagenPerfil = new ImageIcon(getClass().getResource("/imagenes/login.png"));
		MContactos.setIcon(imagenPerfil);
		menuBar.add(MContactos);
		
		MContactos.addActionListener(this);
		
		MBuscar = new JMenuItem("Buscar");
		MBuscar.setFont(new Font("Georgia", Font.BOLD, 12));
		imagenBuscar = new ImageIcon(getClass().getResource("/imagenes/buscar.png"));
		MBuscar.setIcon(imagenBuscar);
		MBuscar.setMaximumSize(new Dimension(128, 128));
		menuBar.add(MBuscar);
		
		imagenPremium = new ImageIcon(getClass().getResource("/imagenes/premiumTick.png"));
		btnPremium = new JButton("Premium", imagenPremium);
		btnPremium.setFont(new Font("Georgia", Font.BOLD, 12));
		btnPremium.addActionListener(e -> abrirPremium());
		menuBar.add(btnPremium);
		
		btnPdf = new JButton("Generar PDF");
		btnPdf.setFont(new Font("Georgia", Font.BOLD, 12));
		btnPdf.addActionListener(e -> gestionarPdf());
		menuBar.add(btnPdf);
		
		MBuscar.addActionListener(this);
		
		
		
		contactos.setPreferredSize(new Dimension(200, 700));
		contactos.setLayout(new BoxLayout(contactos,BoxLayout.Y_AXIS));
		getContentPane().add(contactos, BorderLayout.WEST);
		// Añadir contactos en el lado de los chats 
		
		
		
		chat.setLayout(new BoxLayout(chat,BoxLayout.Y_AXIS));
		chat.setSize(400,700);
		chat.setMinimumSize(new Dimension(400,700));
		chat.setMaximumSize(new Dimension(400,700));
		chat.setPreferredSize(new Dimension(400,700));
		getContentPane().add(chat, BorderLayout.CENTER);
		
		crearMensaje("Hola", "IBRA", BubbleText.SENT);
		crearMensaje("Hola", "MARIA", BubbleText.RECEIVED);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == MCerrarSesion) {
			//Llamar al controlador para cerrar sesion
			VentanaLogin ventanaLogin = new VentanaLogin();
			dispose();
			ventanaLogin.mostrarLogin();
		}
		else if (e.getSource() == MCambiarImagenPerfil) {
			// Llamar al controlador para cambiar la imagen de perfil
			// TODO Habria que hacer una ventana para cambiar la imagen de perfil
		} else if (e.getSource() == MContactos) {
			// Llamar al controlador para mostrar contactos
			// TODO: no se que ventana mostrar para esto
		} else if (e.getSource() == MBuscar) {
			// Llamar al controlador para buscar contactos
			VentanaBuscar ventanaBuscar = new VentanaBuscar();
			dispose();
			ventanaBuscar.mostrarVentanaBuscar();
		}
	}
	
	private void gestionarPdf() {
		Controlador.INSTANCE.generarPdf();
	}
	
	private void abrirPremium() {
		if(!Controlador.INSTANCE.getUsuarioActual().isPremium()) {
			int respuestaActivar = JOptionPane.showConfirmDialog(null, "¿Desea activar premium?", "Gestión premium", JOptionPane.YES_NO_OPTION);
			if(respuestaActivar == JOptionPane.YES_OPTION) {
				Controlador.INSTANCE.getUsuarioActual().setPremium(true);
				JOptionPane.showMessageDialog(null, "Premium activado. Gracias por esos " +
				(Usuario.PRECIO_INICIAL - Controlador.INSTANCE.getUsuarioActual().getDescuento().getDescuento(Usuario.PRECIO_INICIAL, Controlador.INSTANCE.getUsuarioActual())) + 
				" euros", "Gestión premium", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else {
			int respuestaDesactivar = JOptionPane.showConfirmDialog(null, "¿Desea desactivar premium?", "Gestión premium", JOptionPane.YES_NO_OPTION);
			if(respuestaDesactivar == JOptionPane.YES_OPTION) {
				Controlador.INSTANCE.getUsuarioActual().setPremium(false);
				JOptionPane.showMessageDialog(null, "Premium desactivado", "Gestión premium", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public void crearMensaje(String mensaje, String usuario, int tipo) {
		Color color;
		if (tipo == BubbleText.RECEIVED) {color = Color.PINK;} 
		else { color = Color.CYAN;}	
		BubbleText burbuja = new BubbleText(chat, mensaje, color, usuario, tipo);
		chat.add(burbuja);
	}

}
