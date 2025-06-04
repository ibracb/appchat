package umu.tds.appchat.windows.vista;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import tds.BubbleText;
import umu.tds.appchat.controllers.ControladorAppChat;
import umu.tds.appchat.models.Mensaje;
import umu.tds.appchat.windows.components.MensajeCellRenderer;

public class VentanaPrincipal extends JFrame implements VentanaCambiaImagenes {

	private static final long serialVersionUID = 1L;
	private static final File IMAGEN_POR_DEFECTO = null;
	private static final boolean IS_A_MESSAGE = true;
	private static final boolean IS_AN_EMOJI = false;
	private static final int DEFAUL_HEIGHT_AND_WIDTH = 50;

	private URL url;
	private File destinationFile = IMAGEN_POR_DEFECTO;
	private JPanel contentPane;
	private JList<Mensaje> list;
	private JLabel nombreUsuario;
	private JComboBox<String> comboBox;
	private VentanaLogin v;
	private JTextField textField_2;
	private JScrollPane scrollPane;
	private DefaultListModel<Mensaje> modeloMensajes;
	private DefaultComboBoxModel<String> modeloContactos;
	private JLabel userLabel;
	private JPanel chatPanel;
	private JPanel panelEscribir;

	public VentanaPrincipal(VentanaLogin v) {
		this.v = v;
		setTitle("App Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// Panel Superior con comboBox y botones
		JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		contentPane.add(panelSuperior, BorderLayout.NORTH);

		modeloContactos = new DefaultComboBoxModel<>(getContactos().toArray(new String[0]));
		comboBox = new JComboBox<>(modeloContactos);
		comboBox.setToolTipText("Contactos");
		comboBox.addActionListener(e -> crearListaContactos());

		// Crear el JPopupMenu principal
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem exportarChat = new JMenuItem("Exportar chat");
		JMenuItem cambiarImagen = new JMenuItem("Cambiar de imagen");

		cambiarImagen.addActionListener(e -> abrirVentanaCambioImagen());
		exportarChat.addActionListener(e -> abrirVentanaExportar());

		// Añadir el submenú y el elemento al menú principal
		popupMenu.add(exportarChat);
		popupMenu.add(cambiarImagen);

		// Agregar un listener para mostrar el menú emergente
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				if (e.isPopupTrigger()) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		JButton btnNewButton = new JButton("Configuracion");
		panelSuperior.add(btnNewButton);
		btnNewButton.addActionListener(e -> popupMenu.show(btnNewButton, 0, btnNewButton.getHeight()));
		panelSuperior.add(comboBox);

		// Botón "Flecha hacia atrás"
		ImageIcon iconoFlecha = new ImageIcon(getClass().getResource("/flecha-hacia-atras.png"));
		Image imagenFlecha = iconoFlecha.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		JButton btnFlecha = new JButton(new ImageIcon(imagenFlecha));
		btnFlecha.addActionListener(e -> logOut());
		panelSuperior.add(btnFlecha);

		// Botón "Buscar"
		ImageIcon iconoBuscar = new ImageIcon(getClass().getResource("/buscar.png"));
		Image imagenBuscar = iconoBuscar.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		JButton btnBuscar = new JButton(new ImageIcon(imagenBuscar));
		btnBuscar.addActionListener(e -> abrirVentanaBusqueda());
		panelSuperior.add(btnBuscar);

		// Botón "Contactos"
		ImageIcon iconoContactos = new ImageIcon(getClass().getResource("/contacto.png"));
		Image imagenContactos = iconoContactos.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		JButton btnContactos = new JButton(new ImageIcon(imagenContactos));
		btnContactos.setText("Contactos");
		btnContactos.addActionListener(e -> abrirVentanaContactos());
		panelSuperior.add(btnContactos);

		// Botón "Premium"
		ImageIcon iconoPremium = new ImageIcon(getClass().getResource("/diamante.png"));
		Image imagenPremium = iconoPremium.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		JButton btnPremium = new JButton(new ImageIcon(imagenPremium));
		btnPremium.setText("Premium");
		btnPremium.addActionListener(e -> abrirVentanaSuscripcion());
		panelSuperior.add(btnPremium);

		userLabel = new JLabel();
		userLabel.setText(ControladorAppChat.getInstancia().getNombreUsuarioActual());
		try {
			userLabel.setIcon(ControladorAppChat.getInstancia().obtenerImagenUsuarioActual(DEFAUL_HEIGHT_AND_WIDTH));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		panelSuperior.add(userLabel);

		// Panel Izquierdo con la lista de mensajes
		JPanel panelIzquierdo = new JPanel();
		panelIzquierdo.setMinimumSize(new Dimension(100, 10));
		panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.X_AXIS));
		contentPane.add(panelIzquierdo, BorderLayout.WEST);
		modeloMensajes = new DefaultListModel<>();
		List<Mensaje> mensajesRecientes = getUltimosMensajesUsuario();

		for (Mensaje m : mensajesRecientes) {
			modeloMensajes.addElement(m);
		}

		list = new JList<>(modeloMensajes);
		list.setCellRenderer(new MensajeCellRenderer());
		list.addListSelectionListener(e -> manejarSeleccionMensaje(list.getSelectedValue()));

		scrollPane = new JScrollPane(list);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Siempre visible
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setMinimumSize(new Dimension(150, 0));
		panelIzquierdo.add(scrollPane);

		// Panel Central para mostrar el chat y el área de entrada de texto
		JPanel panelCentral = new JPanel(new BorderLayout());
		contentPane.add(panelCentral, BorderLayout.CENTER);
		chatPanel = new JPanel();
		chatPanel.setBackground(new Color(0, 0, 0));
		chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
		JScrollPane scrollPaneChat = new JScrollPane(chatPanel);
		scrollPaneChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Siempre visible
		scrollPaneChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panelCentral.add(scrollPaneChat, BorderLayout.CENTER);
		JPanel nombrePanel = new JPanel();
		nombreUsuario = new JLabel();
		nombreUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		nombrePanel.add(nombreUsuario);
		nombrePanel.add(Box.createVerticalStrut(20));
		panelCentral.add(nombrePanel, BorderLayout.NORTH);

		// Panel para enviar mensaje
		panelEscribir = new JPanel(new BorderLayout()); // Cambiado a BorderLayout
		ImageIcon iconoEnviar = new ImageIcon(getClass().getResource("/avion-de-papel.png"));
		Image imagenEnviar = iconoEnviar.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		JButton btnEnviar = new JButton(new ImageIcon(imagenEnviar));
		btnEnviar.addActionListener(e -> enviarMensaje());

		panelEscribir.add(btnEnviar);
		panelEscribir.add(btnEnviar, BorderLayout.EAST); // Botón colocado a la derecha
		
		// Panel para enviar mensaje
		ImageIcon iconoEmoji = new ImageIcon(getClass().getResource("/emoji.png"));
		Image imagenEmoji = iconoEmoji.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		JButton btnEmoji = new JButton(new ImageIcon(imagenEmoji));
		btnEmoji.addActionListener(e -> enviarMensaje());

		panelEscribir.add(btnEmoji);
		panelEscribir.add(btnEmoji, BorderLayout.WEST); // Botón colocado a la derecha
		
		JPanel panelEmojis = new JPanel();
		panelEmojis.setLayout(new BoxLayout(panelEmojis, BoxLayout.X_AXIS));
		for (int i = 0; i <= BubbleText.MAXICONO; i++) {
			JButton botonEmoji = new JButton(BubbleText.getEmoji(i));
			botonEmoji.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			botonEmoji.setBackground(panelEmojis.getBackground());
			botonEmoji.setBorder(BorderFactory.createEmptyBorder());
			int emoji = i;
			botonEmoji.addActionListener(e -> enviarEmoji(emoji));
			panelEmojis.add(botonEmoji);
			panelEmojis.add(Box.createRigidArea(new Dimension(25, 0)));			
		}		
		
		JScrollPane scrollPaneEmojis = new JScrollPane();
		scrollPaneEmojis.setViewportView(panelEmojis);
		scrollPaneEmojis.setPreferredSize(new Dimension(500, 70));

		scrollPaneEmojis.setVisible(false);
		panelEscribir.add(scrollPaneEmojis, BorderLayout.NORTH);

		btnEmoji.addActionListener(e -> {
			scrollPaneEmojis.setVisible(!scrollPaneEmojis.isVisible());
			panelEscribir.revalidate();
			panelEscribir.repaint();
		});
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnEnviar.doClick(); // Simula un clic en el botón
				}
			}
		});
		panelEscribir.add(textField_2, BorderLayout.CENTER);
		panelEscribir.setVisible(false);
		panelCentral.add(panelEscribir, BorderLayout.SOUTH);
	}

	void crearListaContactos() {
		String nombreContacto = (String) comboBox.getSelectedItem();
		if (ControladorAppChat.getInstancia().hasContactoNombre(nombreContacto)) {
			ControladorAppChat.getInstancia().setContactoSeleccionado(nombreContacto);
			mostrarChat(ControladorAppChat.getInstancia().getMensajesContactoSeleccionado());
			panelEscribir.setVisible(true);
		}
		comboBox.revalidate();
		comboBox.repaint();
	}

	void refreshContacts() {
		modeloContactos.removeAllElements();
		modeloContactos = new DefaultComboBoxModel<>(getContactos().toArray(new String[0]));
		comboBox.setModel(modeloContactos);
		comboBox.revalidate();
		comboBox.repaint();
	}

	void refreshMessages() {
		List<Mensaje> mensajesRecientes = getUltimosMensajesUsuario();
		modeloMensajes.clear();
		for (Mensaje m : mensajesRecientes) {
			modeloMensajes.addElement(m);
		}
	    list.setModel(modeloMensajes);  // No recreamos la lista, solo actualizamos el modelo.
		list.setCellRenderer(new MensajeCellRenderer());
		list.addListSelectionListener(e -> manejarSeleccionMensaje(list.getSelectedValue()));
		scrollPane.revalidate();
		scrollPane.repaint();
	}

	private void logOut() {
		ControladorAppChat.getInstancia().logOut();
		v.setVisible(true);
		dispose();
	}

	private List<String> getContactos() {
		return ControladorAppChat.getInstancia().getNombresContactos();
	}

	private List<Mensaje> getUltimosMensajesUsuario() {
		return ControladorAppChat.getInstancia().getUltimosMensajes();
	}

	private void manejarSeleccionMensaje(Mensaje mensaje) {
		if (mensaje != null) {
			ControladorAppChat.getInstancia().setContactoSeleccionado(mensaje);
			mostrarChat(ControladorAppChat.getInstancia().getMensajesContactoSeleccionado());
			panelEscribir.setVisible(true);
		}
	}

	private void mostrarChat(List<Mensaje> todosLosMensajes) {
	    chatPanel.removeAll();
	    
	    for (Mensaje mensaje : todosLosMensajes) {
	        boolean esMensajeDeUsuario = mensaje.getEmisor().equals(ControladorAppChat.getInstancia().getUsuarioActual());
	        Color color = esMensajeDeUsuario ? Color.LIGHT_GRAY : Color.DARK_GRAY;
	        
	        String nombreEmisor = esMensajeDeUsuario ? mensaje.getEmisor().getNombre() 
	                                                  : ControladorAppChat.getInstancia().getNombreOrTelefono(mensaje);
	        int tipoBurbuja = esMensajeDeUsuario ? BubbleText.SENT : BubbleText.RECEIVED;

	        if (mensaje.isAnEmoji()) {
	            chatPanel.add(new BubbleText(chatPanel, mensaje.getEmoji(), color, nombreEmisor, tipoBurbuja, 12));
	        } else { 
	            chatPanel.add(new BubbleText(chatPanel, mensaje.getTexto(), color, nombreEmisor, tipoBurbuja));
	        }
	    }

	    actualizarUsuario();
	    chatPanel.revalidate();
	    chatPanel.repaint();
	}

	private void actualizarUsuario() {
	    nombreUsuario.setText(ControladorAppChat.getInstancia().getNombreOrTelefonoContactoSeleccionado());
	    try {
	        nombreUsuario.setIcon(ControladorAppChat.getInstancia().obtenerImagenContactoSeleccionado(DEFAUL_HEIGHT_AND_WIDTH));
	    } catch (IOException e) {
	        // Considera loguear el error o mostrar un mensaje de usuario
	        e.printStackTrace();
	    }
	}

	/**
	 * Método para enviar un mensaje y agregarlo al chat.
	 */
	private void enviarMensaje() {
		if(ControladorAppChat.getInstancia().hasContactoSeleccionado()) {
			
			String texto = textField_2.getText();
			if (!texto.isEmpty()) {
				ControladorAppChat.getInstancia().enviarMensajeOEmoji(texto, LocalDateTime.now(), IS_A_MESSAGE);
				chatPanel.add(new BubbleText(chatPanel, texto, Color.LIGHT_GRAY,
						ControladorAppChat.getInstancia().getNombreUsuarioActual(), BubbleText.SENT));
				chatPanel.add(Box.createVerticalStrut(10));
				chatPanel.revalidate();
				chatPanel.repaint();
				textField_2.setText("");
			}
			refreshMessages();
		}
	}
	
	private void enviarEmoji(int emoji) {
		if (emoji >= 0 && emoji <= BubbleText.MAXICONO) {
			ControladorAppChat.getInstancia().enviarMensajeOEmoji(emoji, LocalDateTime.now(), IS_AN_EMOJI);
			chatPanel.add(new BubbleText(chatPanel, emoji, Color.LIGHT_GRAY,
					ControladorAppChat.getInstancia().getNombreUsuarioActual(), BubbleText.SENT,12));
			chatPanel.add(Box.createVerticalStrut(10));
			chatPanel.revalidate();
			chatPanel.repaint();
		}
		refreshMessages();
	}

	private void abrirVentanaCambioImagen() {
		VentanaCambioImagen ventanaCambioImage = new VentanaCambioImagen(this);
		ventanaCambioImage.setVisible(true);
	}

	private void abrirVentanaBusqueda() {
		VentanaBusqueda ventanaBusqueda = new VentanaBusqueda();
		ventanaBusqueda.setVisible(true);
	}

	private void abrirVentanaContactos() {
		VentanaContactos ventanaContactos = new VentanaContactos(this);
		ventanaContactos.setVisible(true);
		this.setVisible(false);
	}

	private void abrirVentanaSuscripcion() {
		VentanaSuscripcion ventanaSuscripcion = new VentanaSuscripcion();
		ventanaSuscripcion.setVisible(true);
	}
	
	private void abrirVentanaExportar() {
		VentanaExportarChat ventanaExportarChat = new VentanaExportarChat(getContactos());
		ventanaExportarChat.mostrarVentana();
	}

	public void setIcon(ImageIcon imageIcon, URL url) {
	    if (url != null) {
	        this.url = url;
	        imageIcon = new ImageIcon(url);
	    } else if (destinationFile != null) {
	        String path = destinationFile.getAbsolutePath();
	        imageIcon = new ImageIcon(path);
	    }
	    userLabel.setIcon(ControladorAppChat.getInstancia().getScaledImage(imageIcon, DEFAUL_HEIGHT_AND_WIDTH));
		cambiarImagen();
	}

	@Override
	public void setDestinationFile(File d) {
		destinationFile = d;
		cambiarImagen();
	}

	@Override
	public String getTelefono() {
		return ControladorAppChat.getInstancia().getTelefonoUsuarioActual();
	}

	public void cambiarImagen() {
		if (destinationFile != null) {
			String imagePath = destinationFile.getAbsolutePath();
			ControladorAppChat.getInstancia().setImagen(imagePath);
		} else if (url != null) {
			ControladorAppChat.getInstancia().setImagen(url.toString());

		}
	}

}