package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
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

import umu.tds.apps.controlador.Controlador;
import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.ContactoIndividual;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.TipoMensaje;
import umu.tds.apps.dominio.Usuario;

public class VentanaBuscar extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPanel panelBuscar;
	private GridBagLayout gbl_panelBuscar;
	private GridBagConstraints gbc_ImagenBuscar;
	private JLabel ImagenBuscar;
	private GridBagConstraints gbc_lblCampoBuscTexto;
	private JLabel lblCampoBuscTexto;
	private GridBagConstraints gbc_textField;
	private GridBagConstraints gbc_lblCampoBuscTelefono;
	private JLabel lblCampoBuscTelefono;
	private GridBagConstraints gbc_textField_1;
	private GridBagConstraints gbc_lblBuscadorContacto;
	private JLabel lblBuscadorContacto;
	private GridBagConstraints gbc_textField_2;
	private JButton botonBuscar;
	private GridBagConstraints gbc_botonBuscar;
	private JButton btnVolver;
	private GridBagConstraints gbc_btnVolver;
	private JLabel lblBuscaFecha;
	private GridBagConstraints gbc_lblBuscaFecha;
	private JDateChooser dateChooser;
	private GridBagConstraints gbc_dateChooser;
	private JPanel panelCentral;
	private JScrollPane scrollMensajes;
	private GridBagConstraints gbc_scroll;
	private JPanel contenedorMensajes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaBuscar window = new VentanaBuscar();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void mostrarVentanaBuscar(Dimension tam, Point ubi) {
		contenedorMensajes.removeAll();
		setVisible(true);
		setSize(tam);
		setLocation(ubi);
	}

	/**
	 * Create the application.
	 */
	public VentanaBuscar() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 798, 529);
		setIconImage(new ImageIcon(getClass().getResource("/imagenes/iconoPestanas.PNG")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		panelBuscar = new JPanel();
		panelBuscar.setBackground(new Color(242, 216, 245));
		getContentPane().add(panelBuscar, BorderLayout.NORTH);
		gbl_panelBuscar = new GridBagLayout();
		gbl_panelBuscar.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelBuscar.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelBuscar.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelBuscar.rowWeights = new double[]{2.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
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
		
		/*panelCentral = new JPanel();
		panelCentral.setBackground(new Color(242, 216, 245));
		getContentPane().add(panelCentral, BorderLayout.CENTER);
		contenedorMensajes = new JPanel();
		contenedorMensajes.setLayout(new BoxLayout(contenedorMensajes, BoxLayout.Y_AXIS));
		contenedorMensajes.setBackground(new Color(242, 216, 245));
		scrollMensajes = new JScrollPane(contenedorMensajes);
		scrollMensajes.setPreferredSize(new Dimension(700, 300));
		scrollMensajes.getViewport().setBackground(new Color(242, 216, 245));
		gbc_scroll = new GridBagConstraints();
		gbc_scroll.gridx = 0;
		gbc_scroll.gridy = 6;
		gbc_scroll.gridwidth = 7;
		gbc_scroll.fill = GridBagConstraints.BOTH;
		gbc_scroll.weighty = 1.0;
		panelCentral.add(scrollMensajes, gbc_scroll);*/
		contenedorMensajes = new JPanel();
		contenedorMensajes.setLayout(new BoxLayout(contenedorMensajes, BoxLayout.Y_AXIS));
		contenedorMensajes.setBackground(new Color(242, 216, 245));
		contenedorMensajes.setBorder(new EmptyBorder(0, 30, 0, 30));

		scrollMensajes = new JScrollPane(contenedorMensajes);
		scrollMensajes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollMensajes.getViewport().setBackground(new Color(242, 216, 245));

		// Esto asegura que el scroll panel ocupe todo el espacio disponible bajo el panelBuscar
		getContentPane().add(scrollMensajes, BorderLayout.CENTER);
	}
	
	public void gestionarBtnVolver() {
		VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
		ventanaPrincipal.mostrarVentanaPrincipal(this.getSize(), this.getLocation());
		dispose();
	}
	
	public void crearMensaje(Mensaje msg, Contacto contacto) {
	    JPanel mensaje = new JPanel(new GridBagLayout());
	    Usuario emisor = null;
	    Usuario receptor = null;
	    
	    mensaje.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
	    mensaje.setBorder(BorderFactory.createLineBorder(new Color(135, 0, 146)));
	    mensaje.setBackground(new Color(242, 216, 245));
	    
	    if (msg.getTipo() == TipoMensaje.ENVIADO) {
        	emisor = Controlador.INSTANCE.getUsuarioActual();
        	// de alguna forma se podra encontrar el receptor del mensaje;
        } else {
        	// de alguna forma se podra encontrar el emisor del mensaje
        	receptor = Controlador.INSTANCE.getUsuarioActual();
        }
	    
	    GridBagConstraints gbc_mensaje = new GridBagConstraints();
	    gbc_mensaje.insets = new Insets(5, 5, 5, 5);
	    gbc_mensaje.gridy = 0;

	    // Emisor
	    gbc_mensaje.gridx = 0;
	    gbc_mensaje.anchor = GridBagConstraints.WEST;
	    JLabel lblEmisor = new JLabel(""); 
		if (emisor == null) {
			 lblEmisor.setText(contacto.getNombre());
		} else {
			lblEmisor.setText(emisor.getNombre());
		}
	    mensaje.add(lblEmisor, gbc_mensaje);

	    // Receptor
	    gbc_mensaje.gridx = 2;
	    gbc_mensaje.anchor = GridBagConstraints.EAST;
	    JLabel lblReceptor = new JLabel("");
	    if (receptor == null) {
        	lblReceptor.setText(contacto.getNombre());
        } else {
        	lblReceptor.setText(receptor.getNombre());
        }
	    mensaje.add(lblReceptor, gbc_mensaje);

	    // Texto del mensaje (segunda fila, centrado)
	    gbc_mensaje.gridx = 0;
	    gbc_mensaje.gridy = 1;
	    gbc_mensaje.gridwidth = 3;
	    gbc_mensaje.fill = GridBagConstraints.HORIZONTAL;
	    gbc_mensaje.anchor = GridBagConstraints.CENTER;
	    JTextArea texto = new JTextArea("");
	    texto.setText(msg.getTexto());
	    texto.setLineWrap(true);
	    texto.setWrapStyleWord(true);
	    texto.setEditable(false);
	    texto.setBackground(new Color(255, 255, 255));
	    mensaje.add(texto, gbc_mensaje);

	    contenedorMensajes.add(mensaje);
	    contenedorMensajes.revalidate();
	    contenedorMensajes.repaint();
	}
	
	public void gestionarBtnBuscar() {
		contenedorMensajes.removeAll(); // Limpiar el contenedor de mensajes antes de buscar
		// Aquí se implementaría la lógica de búsqueda
		String texto = textField.getText();
		String telefono = textField_1.getText();
		String contacto = textField_2.getText();
		LocalDate fecha = null;
		if (dateChooser.getDate() != null) {
		    fecha = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		
		
		/*ESTO SE USA PARA PROBAR LA VISTA, NO SE DEBE USAR EN EL PROYECTO FINAL Y HAY QUE BORRARLO
		Mensaje msg = new Mensaje("Mensaje ejemplo, aquí iría el texto del mensaje", 0, TipoMensaje.ENVIADO); // Crear un mensaje de ejemplo
		Usuario usuarioEjemplo = new Usuario("Usuario Ejemplo", LocalDate.of(2004, 7, 15), "ue@um.es", null, "123", "ue", "Hola"); // Crear un usuario de ejemplo
		Contacto contactoEjemplo = new ContactoIndividual("Contacto Ejemplo", usuarioEjemplo); // Crear un contacto de ejemplo
		crearMensaje(msg, contactoEjemplo); // Ejemplo de cómo crear un mensaje
		Mensaje msg2 = new Mensaje("Mensaje ejemplo 2, aquí iría el texto del mensaje", 0, TipoMensaje.ENVIADO); // Crear un mensaje de ejemplo
		crearMensaje(msg2, contactoEjemplo);
		Mensaje msg3 = new Mensaje("Mensaje ejemplo 3, aquí iría el texto del mensaje", 0, TipoMensaje.ENVIADO); // Crear un mensaje de ejemplo
		crearMensaje(msg3, contactoEjemplo);
		crearMensaje(msg2, contactoEjemplo);
		crearMensaje(msg, contactoEjemplo);
		crearMensaje(msg3, contactoEjemplo);
		crearMensaje(msg2, contactoEjemplo);
		crearMensaje(msg, contactoEjemplo);
		crearMensaje(msg3, contactoEjemplo);
		crearMensaje(msg2, contactoEjemplo);
		crearMensaje(msg, contactoEjemplo);
		crearMensaje(msg3, contactoEjemplo);
		crearMensaje(msg2, contactoEjemplo);
		crearMensaje(msg, contactoEjemplo);
		crearMensaje(msg3, contactoEjemplo);
		crearMensaje(msg2, contactoEjemplo);
		crearMensaje(msg, contactoEjemplo);*/
		
		if (texto.isEmpty() && telefono.isEmpty() && contacto.isEmpty() && fecha == null) {
			JOptionPane.showMessageDialog(this,
					"No hay datos que buscar. Por favor, complete al menos uno de los campos.",
					"Mensaje busquedad", JOptionPane.INFORMATION_MESSAGE);
		} else {
			//Se llama a la función para filtrar los mensajes del controlador
			Map<Mensaje, Contacto> mensajes = Controlador.INSTANCE.filtrarMensajes(Controlador.INSTANCE.getUsuarioActual(), texto, telefono, contacto, fecha);
			//Bucle for para cada mensaje que se encuentre con los filtros
			for (Mensaje mensaje : mensajes.keySet()) {
				Contacto contactoEncontrado = mensajes.get(mensaje);
				crearMensaje(mensaje, contactoEncontrado);
			}
		}

	}

}