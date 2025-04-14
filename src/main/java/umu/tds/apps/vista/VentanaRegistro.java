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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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

public class VentanaRegistro extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel etiquetaImagen;
	private JLabel etiquetaNombre;
	private JTextField textFieldNombre;
	private JLabel etiquetaApellidos;
	private JTextField textFieldApellidos;
	
	private JPasswordField passwordField;
	private JLabel etiquetaContrasena2;
	private JPasswordField passwordField_1;
	private JLabel etiquetaContrasena1;
	private JTextField textFieldTelefono;
	private JLabel etiquetaTelefono;
	private JButton btnAceptar;
	private JLabel etiquetaFechaNacimiento;
	private JDateChooser dateChooser;
	private JLabel etiquetaSaludo;
	private JButton btnCancelar;
	private Component horizontalStrut;
	private JPanel panel;
	private JPanel panelBotones;
	private JTextArea textArea;
	private JButton btnPerfil;
	private ImageIcon imagen;
	private Image imagenEscalada;
	private JScrollPane scrollPane;
	private GridBagLayout gridBagLayout;
	private GridBagConstraints gbc_etiquetaNombre;
	private GridBagConstraints gbc_textFieldNombre;
	private GridBagConstraints gbc_etiquetaApellidos;
	private GridBagConstraints gbc_textFieldApellidos;
	private GridBagConstraints gbc_etiquetaTelefono;
	private GridBagConstraints gbc_textFieldTelefono;
	private GridBagConstraints gbc_etiquetaContrasena1;
	private GridBagConstraints gbc_passwordField;
	private GridBagConstraints gbc_etiquetaContrasena2;
	private GridBagConstraints gbc_passwordField_1;
	private GridBagConstraints gbc_etiquetaFechaNacimiento;
	private GridBagConstraints gbc_dateChooser;
	private GridBagConstraints gbc_etiquetaImagen;
	private GridBagConstraints gbc_btnPerfil;
	private GridBagConstraints gbc_etiquetaSaludo;
	private GridBagConstraints gbc_scrollPane;
	private GridBagConstraints gbc_lblPerfil;
	private GridBagConstraints gbc_panel_1;
	
	


	private JLabel lblPerfil;

	/**
	 * Create the application.
	 * @param ventanaLogin 
	 */
	public VentanaRegistro() {
		initialize();
	}
	public void mostrarRegistro() {
		setVisible(true);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		getContentPane().setBackground(new Color(242, 216, 245));
		setBounds(100, 100, 613, 464);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\imagenes\\iconoPestanas.PNG"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setBackground(new Color(242, 216, 245));
		getContentPane().add(panel, BorderLayout.CENTER);
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 110, 0, 0, 110, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 34, 40, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0};
		panel.setLayout(gridBagLayout);
		
		etiquetaNombre = new JLabel("Nombre");
		etiquetaNombre.setFont(new Font("Georgia", Font.BOLD, 12));
		etiquetaNombre.setHorizontalAlignment(SwingConstants.TRAILING);
		gbc_etiquetaNombre = new GridBagConstraints();
		gbc_etiquetaNombre.anchor = GridBagConstraints.EAST;
		gbc_etiquetaNombre.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaNombre.gridx = 1;
		gbc_etiquetaNombre.gridy = 1;
		panel.add(etiquetaNombre, gbc_etiquetaNombre);
		
		textFieldNombre = new JTextField();
		gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.gridwidth = 4;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.gridx = 2;
		gbc_textFieldNombre.gridy = 1;
		panel.add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);
		
		etiquetaApellidos = new JLabel("Apellidos");
		etiquetaApellidos.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_etiquetaApellidos = new GridBagConstraints();
		gbc_etiquetaApellidos.anchor = GridBagConstraints.EAST;
		gbc_etiquetaApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaApellidos.gridx = 1;
		gbc_etiquetaApellidos.gridy = 2;
		panel.add(etiquetaApellidos, gbc_etiquetaApellidos);
		
		textFieldApellidos = new JTextField();
		gbc_textFieldApellidos = new GridBagConstraints();
		gbc_textFieldApellidos.gridwidth = 4;
		gbc_textFieldApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldApellidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldApellidos.gridx = 2;
		gbc_textFieldApellidos.gridy = 2;
		panel.add(textFieldApellidos, gbc_textFieldApellidos);
		textFieldApellidos.setColumns(10);
		
		etiquetaTelefono = new JLabel("Teléfono");
		etiquetaTelefono.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_etiquetaTelefono = new GridBagConstraints();
		gbc_etiquetaTelefono.anchor = GridBagConstraints.EAST;
		gbc_etiquetaTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaTelefono.gridx = 1;
		gbc_etiquetaTelefono.gridy = 3;
		panel.add(etiquetaTelefono, gbc_etiquetaTelefono);
		
		textFieldTelefono = new JTextField();
		gbc_textFieldTelefono = new GridBagConstraints();
		gbc_textFieldTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTelefono.gridx = 2;
		gbc_textFieldTelefono.gridy = 3;
		panel.add(textFieldTelefono, gbc_textFieldTelefono);
		textFieldTelefono.setColumns(10);
		
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
		
		passwordField_1 = new JPasswordField();
		gbc_passwordField_1 = new GridBagConstraints();
		gbc_passwordField_1.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_1.gridx = 5;
		gbc_passwordField_1.gridy = 4;
		panel.add(passwordField_1, gbc_passwordField_1);
		
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
		panel.add(btnPerfil, gbc_btnPerfil);
		
		etiquetaSaludo = new JLabel("Saludo");
		etiquetaSaludo.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_etiquetaSaludo = new GridBagConstraints();
		gbc_etiquetaSaludo.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaSaludo.gridx = 1;
		gbc_etiquetaSaludo.gridy = 6;
		panel.add(etiquetaSaludo, gbc_etiquetaSaludo);
		
		
		//TODO Mirar el saludo a ver como hacer un scroll panel
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
		panelBotones.add(btnAceptar);
		
		horizontalStrut = Box.createHorizontalStrut(20);
		panelBotones.add(horizontalStrut);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Georgia", Font.BOLD, 12));
		btnCancelar.setPreferredSize(new Dimension(100, 30)); // Establece el tamaño preferido
		panelBotones.add(btnCancelar);
		
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAceptar) {
			//Aqui se llamar al controlador y que guarde y compruebe todos los valores
			
			//Si esta correcto vuelve a la ventana de login y si no muestra un mensaje de error
			dispose(); // Cierra la ventana actual
			
		} else if (e.getSource() == btnCancelar){
			VentanaLogin ventanaLogin = new VentanaLogin();
			dispose(); // Cierra la ventana actual
			ventanaLogin.mostrarLogin();
		} else { // btnPerfil
			seleccionarImagen();
		}
	}
	
	private void seleccionarImagen() {
	    while (true) {
	        JFileChooser selector = new JFileChooser();
	        selector.setDialogTitle("Selecciona un archivo PNG");
	        selector.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes PNG", "png"));
	        int resultado = selector.showOpenDialog(null);
	        if (resultado == JFileChooser.APPROVE_OPTION) {
	            File archivo = selector.getSelectedFile();
	            if (archivo.getName().toLowerCase().endsWith(".png")) {
	                ImageIcon icono = new ImageIcon(archivo.getAbsolutePath());
	                // Verifica que las dimensiones del JLabel sean válidas
	                int width = lblPerfil.getWidth();
	                int height = lblPerfil.getHeight();
	                if (width > 0 && height > 0) {
	                    Image imagenEscalada = icono.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
	                    lblPerfil.setIcon(new ImageIcon(imagenEscalada));
	                } else {
	                    // Si las dimensiones son inválidas, puedes establecer un tamaño por defecto
	                    Image imagenEscalada = icono.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
	                    lblPerfil.setIcon(new ImageIcon(imagenEscalada));
	                }
	                break;
	            } else {
	                JOptionPane.showMessageDialog(null, "Por favor selecciona un archivo .png válido.", "Archivo no válido", JOptionPane.ERROR_MESSAGE);
	            }
	        } else {
	            break;
	        }
	    }
	}

	
}
