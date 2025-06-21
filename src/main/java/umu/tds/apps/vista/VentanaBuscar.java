package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import tds.BubbleText;
import umu.tds.apps.controlador.Controlador;
import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.TipoMensaje;
import umu.tds.apps.dominio.Usuario;

public class VentanaBuscar extends JFrame {

	/**
	 * Serial version UID para la clase VentanaBuscar.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Campos de texto para introducir los criterios de búsqueda. 
	 */
	private JTextField textField, textField_1, textField_2;
	/**
	 * Panel que contiene los componentes de búsqueda y los mensajes encontrados.
	 */
	private JPanel panelBuscar, contenedorMensajes;
	/**
	 * Layout y restricciones para organizar los componentes en el panel de
	 * búsqueda.
	 */
	private GridBagLayout gbl_panelBuscar;
	private GridBagConstraints gbc_ImagenBuscar, gbc_lblCampoBuscTexto, gbc_textField, gbc_lblBuscadorContacto,
			gbc_textField_2, gbc_lblCampoBuscTelefono, gbc_textField_1, gbc_lblBuscaFecha, gbc_dateChooser,
			gbc_btnVolver, gbc_botonBuscar;
	/**
	 * Componentes de la ventana de búsqueda.
	 */
	private JLabel ImagenBuscar, lblCampoBuscTexto, lblBuscadorContacto, lblCampoBuscTelefono, lblBuscaFecha;
	/**
	 * Botones para buscar y volver a la ventana principal.
	 */
	private JButton botonBuscar, btnVolver;
	/**
	 * Componente para seleccionar una fecha en el buscador.
	 */
	private JDateChooser dateChooser;
	/**
	 * Panel de desplazamiento que contiene los mensajes encontrados.
	 */
	private JScrollPane scrollMensajes;
	

	/**
	 * Método para mostrar la ventana de búsqueda con un tamaño y ubicación
	 * específicos.
	 * 
	 * @param tam Tamaño de la ventana.
	 * @param ubi Ubicación de la ventana.
	 */
	protected void mostrarVentanaBuscar(Dimension tam, Point ubi) {
		contenedorMensajes.removeAll();
		setVisible(true);
		setSize(tam);
		setLocation(ubi);
	}

	/**
	 * Create the application.
	 */
	protected VentanaBuscar() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 798, 529);
		setIconImage(new ImageIcon(getClass().getResource("/imagenes/iconoPestanas.PNG")).getImage());
		setTitle("Buscar en AppChat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		panelBuscar = new JPanel();
		panelBuscar.setBackground(new Color(242, 216, 245));
		getContentPane().add(panelBuscar, BorderLayout.NORTH);
		gbl_panelBuscar = new GridBagLayout();
		gbl_panelBuscar.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panelBuscar.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panelBuscar.columnWeights = new double[] { 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0,
				1.0, Double.MIN_VALUE };
		gbl_panelBuscar.rowWeights = new double[] { 2.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0,
				Double.MIN_VALUE };
		panelBuscar.setLayout(gbl_panelBuscar);

		ImagenBuscar = new JLabel("");
		ImagenBuscar.setIcon(new ImageIcon("/imagenes/buscar.png"));
		gbc_ImagenBuscar = new GridBagConstraints();
		gbc_ImagenBuscar.fill = GridBagConstraints.VERTICAL;
		gbc_ImagenBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_ImagenBuscar.gridx = 7;
		gbc_ImagenBuscar.gridy = 2;
		panelBuscar.add(ImagenBuscar, gbc_ImagenBuscar);

		lblCampoBuscTexto = new JLabel("Buscador Texto");
		gbc_lblCampoBuscTexto = new GridBagConstraints();
		gbc_lblCampoBuscTexto.anchor = GridBagConstraints.EAST;
		gbc_lblCampoBuscTexto.insets = new Insets(0, 0, 5, 5);
		gbc_lblCampoBuscTexto.gridx = 2;
		gbc_lblCampoBuscTexto.gridy = 4;
		panelBuscar.add(lblCampoBuscTexto, gbc_lblCampoBuscTexto);

		textField = new JTextField();
		gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 3;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 4;
		panelBuscar.add(textField, gbc_textField);
		textField.setColumns(10);

		lblBuscadorContacto = new JLabel("Buscador Contacto");
		lblBuscadorContacto.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc_lblBuscadorContacto = new GridBagConstraints();
		gbc_lblBuscadorContacto.anchor = GridBagConstraints.EAST;
		gbc_lblBuscadorContacto.insets = new Insets(0, 0, 5, 5);
		gbc_lblBuscadorContacto.gridx = 8;
		gbc_lblBuscadorContacto.gridy = 4;
		panelBuscar.add(lblBuscadorContacto, gbc_lblBuscadorContacto);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.gridwidth = 3;
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 9;
		gbc_textField_2.gridy = 4;
		panelBuscar.add(textField_2, gbc_textField_2);

		lblCampoBuscTelefono = new JLabel("Buscador Teléfono");
		lblCampoBuscTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc_lblCampoBuscTelefono = new GridBagConstraints();
		gbc_lblCampoBuscTelefono.anchor = GridBagConstraints.EAST;
		gbc_lblCampoBuscTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblCampoBuscTelefono.gridx = 2;
		gbc_lblCampoBuscTelefono.gridy = 6;
		panelBuscar.add(lblCampoBuscTelefono, gbc_lblCampoBuscTelefono);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 3;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 6;
		panelBuscar.add(textField_1, gbc_textField_1);

		lblBuscaFecha = new JLabel("Buscador Fecha");
		gbc_lblBuscaFecha = new GridBagConstraints();
		gbc_lblBuscaFecha.insets = new Insets(0, 0, 5, 5);
		gbc_lblBuscaFecha.gridx = 8;
		gbc_lblBuscaFecha.gridy = 6;
		panelBuscar.add(lblBuscaFecha, gbc_lblBuscaFecha);

		dateChooser = new JDateChooser();
		gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.gridwidth = 3;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 9;
		gbc_dateChooser.gridy = 6;
		panelBuscar.add(dateChooser, gbc_dateChooser);

		btnVolver = new JButton("Volver");
		btnVolver.setPreferredSize(new Dimension(100, 30));
		btnVolver.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.insets = new Insets(0, 0, 5, 5);
		gbc_btnVolver.gridx = 4;
		gbc_btnVolver.gridy = 7;
		panelBuscar.add(btnVolver, gbc_btnVolver);

		btnVolver.addActionListener(e -> gestionarBtnVolver());

		botonBuscar = new JButton("Buscar");
		botonBuscar.setPreferredSize(new Dimension(100, 30));
		botonBuscar.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_botonBuscar = new GridBagConstraints();
		gbc_botonBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_botonBuscar.gridx = 8;
		gbc_botonBuscar.gridy = 7;
		panelBuscar.add(botonBuscar, gbc_botonBuscar);
		botonBuscar.addActionListener(e -> gestionarBtnBuscar());

		contenedorMensajes = new JPanel();
		contenedorMensajes.setLayout(new BoxLayout(contenedorMensajes, BoxLayout.Y_AXIS));
		contenedorMensajes.setBackground(new Color(242, 216, 245));
		contenedorMensajes.setBorder(new EmptyBorder(0, 30, 0, 30));

		scrollMensajes = new JScrollPane(contenedorMensajes);
		scrollMensajes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollMensajes.getViewport().setBackground(new Color(242, 216, 245));

		// Esto asegura que el scroll panel ocupe todo el espacio disponible bajo el
		// panelBuscar
		getContentPane().add(scrollMensajes, BorderLayout.CENTER);
	}

	/**
	 * Método para gestionar el evento del botón "Volver". Cierra la ventana actual
	 * y muestra la ventana principal.
	 */
	private void gestionarBtnVolver() {
		VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
		ventanaPrincipal.mostrarVentanaPrincipal(this.getSize(), this.getLocation());
		dispose();
	}

	/**
	 * Método para crear un mensaje en la ventana de búsqueda.
	 * 
	 * @param msg    -   El mensaje a mostrar.
	 * @param contacto - El contacto asociado al mensaje.
	 */
	private void crearMensaje(Mensaje msg, Contacto contacto) {
		JButton botonMensaje = new JButton();
		botonMensaje.setLayout(new GridBagLayout());
		botonMensaje.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
		botonMensaje.setBorder(BorderFactory.createLineBorder(new Color(135, 0, 146)));
		botonMensaje.setBackground(new Color(242, 216, 245));
		botonMensaje.setFocusPainted(false);
		botonMensaje.setContentAreaFilled(false); // para mantener el fondo plano
		botonMensaje.setOpaque(true); // se asegura que el color de fondo se aplica

		Usuario emisor = null;
		Usuario receptor = null;

		if (msg.getTipo() == TipoMensaje.ENVIADO) {
			emisor = Controlador.INSTANCE.getUsuarioActual();
		} else {
			receptor = Controlador.INSTANCE.getUsuarioActual();
		}

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridy = 0;

		// Emisor
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.WEST;
		JLabel lblEmisor = new JLabel("");
		lblEmisor.setFont(new Font("Arial", Font.BOLD, 12));
		if (emisor == null) {
			lblEmisor.setText(Controlador.INSTANCE.getNombreContacto(contacto));
		} else {
			lblEmisor.setText(Controlador.INSTANCE.getNombreUsuario(emisor));
		}
		botonMensaje.add(lblEmisor, gbc);

		// Receptor
		gbc.gridx = 2;
		gbc.anchor = GridBagConstraints.EAST;
		JLabel lblReceptor = new JLabel("");
		lblReceptor.setFont(new Font("Arial", Font.BOLD, 12));
		if (receptor == null) {
			lblReceptor.setText(Controlador.INSTANCE.getNombreContacto(contacto));
		} else {
			lblReceptor.setText(Controlador.INSTANCE.getNombreUsuario(receptor));
		}
		botonMensaje.add(lblReceptor, gbc);

		// Texto del mensaje
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;

		
		if (msg.getTexto() == null || msg.getTexto().isEmpty()) {
			JLabel texto = new JLabel("");
			texto.setIcon(BubbleText.getEmoji(Controlador.INSTANCE.getEmojiMensaje(msg)));
			texto.setBackground(Color.WHITE);
			botonMensaje.add(texto, gbc);
		} else {
			JTextArea texto = new JTextArea("");
			texto.setText(Controlador.INSTANCE.getTextoMensaje(msg));
			texto.setLineWrap(true);
			texto.setWrapStyleWord(true);
			texto.setEditable(false);
			texto.setBackground(Color.WHITE);
			texto.setFont(new Font("Arial", Font.PLAIN, 12));
			botonMensaje.add(texto, gbc);
		}

		// Acción al hacer clic en el botón
		botonMensaje.addActionListener(e -> {
			VentanaPrincipal v = new VentanaPrincipal(contacto);
			dispose();
			v.mostrarVentanaPrincipal(getSize(), getLocation());
		});

		contenedorMensajes.add(botonMensaje);
		contenedorMensajes.revalidate();
		contenedorMensajes.repaint();
	}

	/**
	 * Método para gestionar la acción del botón "Buscar". Limpia el contenedor de
	 * mensajes y busca los mensajes según los filtros introducidos.
	 */
	private void gestionarBtnBuscar() {
		contenedorMensajes.removeAll(); // Limpiar el contenedor de mensajes antes de buscar
		String texto = textField.getText();
		String telefono = textField_1.getText();
		String contacto = textField_2.getText();
		LocalDate fecha = null;
		if (dateChooser.getDate() != null) {
			fecha = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}

		if (texto.isEmpty() && telefono.isEmpty() && contacto.isEmpty() && fecha == null) {
			JOptionPane.showMessageDialog(this,
					"No hay datos que buscar. Por favor, complete al menos uno de los campos.", "Mensaje busqueda",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			// Se llama a la función para filtrar los mensajes del controlador
			Map<Mensaje, Contacto> mensajes = Controlador.INSTANCE
					.filtrarMensajes(Controlador.INSTANCE.getUsuarioActual(), texto, telefono, contacto, fecha);
			// Bucle for para cada mensaje que se encuentre con los filtros
			for (Mensaje mensaje : mensajes.keySet()) {
				Contacto contactoEncontrado = Controlador.INSTANCE.encontrarContacto(mensajes, mensaje);
				crearMensaje(mensaje, contactoEncontrado);
			}
		}

	}

}