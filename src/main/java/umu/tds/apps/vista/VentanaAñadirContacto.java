package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaAñadirContacto extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNombre;
	private JTextField textFieldTelefono;
	private JLabel labelFoto;
	private JLabel labelMensaje;
	private JLabel labelNombre;
	private JLabel labelTelefono;
	private GridBagLayout gbl_panelCentral;
	private GridBagConstraints gbc_labelFoto;
	private GridBagConstraints gbc_labelMensaje;
	private GridBagConstraints gbc_labelNombre;
	private GridBagConstraints gbc_textFieldNombre;
	private GridBagConstraints gbc_labelTelefono;
	private GridBagConstraints gbc_textFieldTelefono;
	private GridBagConstraints gbc_lblContactoCorrecto;
	private GridBagConstraints gbc_panel_1;
	private JPanel panelBotones;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private Component horizontalStrut;
	private Component horizontalStrut2;
	private JButton btnAceptar2;
	private JLabel lblContactoCorrecto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaAñadirContacto dialog = new VentanaAñadirContacto();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void mostrarVentanaAñadirContacto() {
		this.setVisible(true);
	}

	/**
	 * Create the dialog.
	 */
	public VentanaAñadirContacto() {
		setBounds(100, 100, 550, 245);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\imagenes\\iconoPestanas.PNG"));
		setTitle("Añadir contacto");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true); // Esto hace que hasta que no se cierre esta ventana no se pueda interactuar con la ventana que la llamo
		
		contentPanel.setBackground(new Color(242, 216, 245));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[]{0, 0, 0, 0, 62, 0};
		gbl_panelCentral.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelCentral.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.5, Double.MIN_VALUE};
		gbl_panelCentral.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_panelCentral);
		
		labelFoto = new JLabel("");
		gbc_labelFoto = new GridBagConstraints();
		gbc_labelFoto.insets = new Insets(0, 0, 5, 5);
		gbc_labelFoto.gridx = 2;
		gbc_labelFoto.gridy = 3;
		contentPanel.add(labelFoto, gbc_labelFoto);
		
		labelMensaje = new JLabel("Introduzca nombre y teléfono al contacto:");
		labelMensaje.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_labelMensaje = new GridBagConstraints();
		gbc_labelMensaje.insets = new Insets(0, 0, 5, 5);
		gbc_labelMensaje.gridx = 3;
		gbc_labelMensaje.gridy = 3;
		contentPanel.add(labelMensaje, gbc_labelMensaje);
		
		labelNombre = new JLabel("nombre");
		labelNombre.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_labelNombre = new GridBagConstraints();
		gbc_labelNombre.anchor = GridBagConstraints.EAST;
		gbc_labelNombre.insets = new Insets(0, 0, 5, 5);
		gbc_labelNombre.gridx = 2;
		gbc_labelNombre.gridy = 5;
		contentPanel.add(labelNombre, gbc_labelNombre);
		
		textFieldNombre = new JTextField();
		gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.gridx = 3;
		gbc_textFieldNombre.gridy = 5;
		contentPanel.add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(32);
		
		labelTelefono = new JLabel("teléfono");
		labelTelefono.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_labelTelefono = new GridBagConstraints();
		gbc_labelTelefono.anchor = GridBagConstraints.EAST;
		gbc_labelTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_labelTelefono.gridx = 2;
		gbc_labelTelefono.gridy = 7;
		contentPanel.add(labelTelefono, gbc_labelTelefono);
		
		textFieldTelefono = new JTextField();
		gbc_textFieldTelefono = new GridBagConstraints();
		gbc_textFieldTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTelefono.gridx = 3;
		gbc_textFieldTelefono.gridy = 7;
		contentPanel.add(textFieldTelefono, gbc_textFieldTelefono);
		textFieldTelefono.setColumns(32);
		
		lblContactoCorrecto = new JLabel("El contanto ha sido agregado correctamente");
		lblContactoCorrecto.setFont(new Font("Georgia", Font.BOLD, 12));
		lblContactoCorrecto.setForeground(new Color(135, 0, 146));
		lblContactoCorrecto.setVisible(false); // Inicialmente oculto
		gbc_lblContactoCorrecto = new GridBagConstraints();
		gbc_lblContactoCorrecto.gridwidth = 6;
		gbc_lblContactoCorrecto.insets = new Insets(0, 0, 5, 5);
		gbc_lblContactoCorrecto.gridx = 0;
		gbc_lblContactoCorrecto.gridy = 8;
		contentPanel.add(lblContactoCorrecto, gbc_lblContactoCorrecto);
		
		panelBotones = new JPanel();
		panelBotones.setBackground(new Color(242, 216, 245));
		gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 6;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 9;
		contentPanel.add(panelBotones, gbc_panel_1);
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
		
		horizontalStrut2 = Box.createHorizontalStrut(20);
		panelBotones.add(horizontalStrut2);
		
		btnAceptar2 = new JButton("Aceptar");
		btnAceptar2.setFont(new Font("Georgia", Font.BOLD, 12));
		btnAceptar2.setPreferredSize(new Dimension(100, 30)); // Establece el tamaño preferido
		panelBotones.add(btnAceptar2);
		btnAceptar2.setVisible(false); // Inicialmente oculto
		
		
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);
		btnAceptar2.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAceptar) {
			// Aquí puedes agregar la lógica para manejar el evento de aceptar
			boolean escorrecto = true; // escorrecto se cambiara por la contestación del controlador 
			if (escorrecto) {
				lblContactoCorrecto.setVisible(true); // Mostrar el mensaje de éxito
				btnAceptar.setVisible(false); // Ocultar el botón de aceptar
				btnCancelar.setVisible(false); // Ocultar el botón de cancelar
				btnAceptar2.setVisible(true); // Mostrar el botón de aceptar 2
				textFieldNombre.setEnabled(false); // Deshabilitar el campo de texto
				textFieldTelefono.setEnabled(false); // Deshabilitar el campo de texto	
			}
		} else if (e.getSource() == btnCancelar) {
			this.dispose();
		} else if (e.getSource() == btnAceptar2){ // btnAceptar2
			this.dispose();
		}
	}

}
