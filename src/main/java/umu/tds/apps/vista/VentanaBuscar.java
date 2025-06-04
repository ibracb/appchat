package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaBuscar {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaBuscar window = new VentanaBuscar();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void mostrarVentanaBuscar(Dimension tam, Point ubi) {
		frame.setVisible(true);
		frame.setSize(tam);
		frame.setLocation(ubi);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 798, 529);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\imagenes\\iconoPestanas.PNG"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelBuscar = new JPanel();
		panelBuscar.setBackground(new Color(242, 216, 245));
		frame.getContentPane().add(panelBuscar, BorderLayout.NORTH);
		GridBagLayout gbl_panelBuscar = new GridBagLayout();
		gbl_panelBuscar.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelBuscar.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelBuscar.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelBuscar.rowWeights = new double[]{2.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		panelBuscar.setLayout(gbl_panelBuscar);
		
		JLabel ImagenBuscar = new JLabel("");
		ImagenBuscar.setIcon(new ImageIcon("src\\main\\resources\\imagenes\\buscar.png"));
		GridBagConstraints gbc_ImagenBuscar = new GridBagConstraints();
		gbc_ImagenBuscar.fill = GridBagConstraints.VERTICAL;
		gbc_ImagenBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_ImagenBuscar.gridx = 7;
		gbc_ImagenBuscar.gridy = 2;
		panelBuscar.add(ImagenBuscar, gbc_ImagenBuscar);
		
		JLabel lblCampoBuscTexto = new JLabel("Buscador Texto");
		GridBagConstraints gbc_lblCampoBuscTexto = new GridBagConstraints();
		gbc_lblCampoBuscTexto.anchor = GridBagConstraints.EAST;
		gbc_lblCampoBuscTexto.insets = new Insets(0, 0, 5, 5);
		gbc_lblCampoBuscTexto.gridx = 2;
		gbc_lblCampoBuscTexto.gridy = 4;
		panelBuscar.add(lblCampoBuscTexto, gbc_lblCampoBuscTexto);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 8;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 4;
		panelBuscar.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblCampoBuscTelefono = new JLabel("Buscador Teléfono");
		lblCampoBuscTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblCampoBuscTelefono = new GridBagConstraints();
		gbc_lblCampoBuscTelefono.anchor = GridBagConstraints.EAST;
		gbc_lblCampoBuscTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblCampoBuscTelefono.gridx = 2;
		gbc_lblCampoBuscTelefono.gridy = 5;
		panelBuscar.add(lblCampoBuscTelefono, gbc_lblCampoBuscTelefono);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 2;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 5;
		panelBuscar.add(textField_1, gbc_textField_1);
		
		JLabel lblBuscadorContacto = new JLabel("Buscador Contacto");
		lblBuscadorContacto.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblBuscadorContacto = new GridBagConstraints();
		gbc_lblBuscadorContacto.anchor = GridBagConstraints.EAST;
		gbc_lblBuscadorContacto.insets = new Insets(0, 0, 5, 5);
		gbc_lblBuscadorContacto.gridx = 8;
		gbc_lblBuscadorContacto.gridy = 5;
		panelBuscar.add(lblBuscadorContacto, gbc_lblBuscadorContacto);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.gridwidth = 2;
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 9;
		gbc_textField_2.gridy = 5;
		panelBuscar.add(textField_2, gbc_textField_2);
		
		JButton botonBuscar = new JButton("Buscar");
		botonBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		botonBuscar.setPreferredSize(new Dimension(100, 30));
		botonBuscar.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_botonBuscar = new GridBagConstraints();
		gbc_botonBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_botonBuscar.gridx = 6;
		gbc_botonBuscar.gridy = 6;
		panelBuscar.add(botonBuscar, gbc_botonBuscar);
		
		JPanel panelCentral = new JPanel();
		panelCentral.setBackground(new Color(242, 216, 245));
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		JPanel contenedorMensajes = new JPanel();
		JScrollPane scrollMensajes = new JScrollPane(contenedorMensajes);
		scrollMensajes.setPreferredSize(new Dimension(700, 300));
		scrollMensajes.getViewport().setBackground(new Color(242, 216, 245));
		GridBagConstraints gbc_scroll = new GridBagConstraints();
		gbc_scroll.gridx = 0;
		gbc_scroll.gridy = 6;
		gbc_scroll.gridwidth = 7;
		gbc_scroll.fill = GridBagConstraints.BOTH;
		gbc_scroll.weighty = 1.0;
		panelCentral.add(scrollMensajes, gbc_scroll);
		
	}

}
