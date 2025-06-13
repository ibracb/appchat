package umu.tds.apps.vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.util.*;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;


import umu.tds.apps.controlador.Controlador;
import umu.tds.apps.dominio.*;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;


public class VentanaAnadirMiembros extends JFrame {
	private JPanel contenedorContactos;
	private JScrollPane scrollContactos;
	private JButton btnCancelar;
	private Grupo grupo;
	private Map<JRadioButton, ContactoIndividual> botonesContacto = new HashMap<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAnadirMiembros window = new VentanaAnadirMiembros();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	protected void mostrarVentanaAnadirMiembros(Dimension tam, Point ubi, Grupo grupo) {
		contenedorContactos.removeAll();
		this.grupo = grupo;
		completarVentana(grupo);
		setVisible(true);
		setSize(tam);
		setLocation(ubi);
	}
	/**
	 * Create the application.
	 */
	public VentanaAnadirMiembros() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 798, 529);
		setIconImage(new ImageIcon(getClass().getResource("/imagenes/iconoPestanas.PNG")).getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(242, 216, 245));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		// Panel que contendrá los botones
		contenedorContactos = new JPanel();
		contenedorContactos.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		contenedorContactos.setBackground(new Color(242, 216, 245));
		contenedorContactos.setBorder(new EmptyBorder(0, 30, 0, 30));
		

		// Scroll que contiene el panel de contactos
		scrollContactos = new JScrollPane(contenedorContactos);
		scrollContactos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollContactos.getViewport().setBackground(new Color(242, 216, 245));

		GridBagConstraints gbc_scroll = new GridBagConstraints();
		gbc_scroll.gridx = 1;               // Comienza en columna 1
		gbc_scroll.gridy = 1;               // Comienza en fila 1
		gbc_scroll.gridwidth = 4;           // Ocupa 4 columnas
		gbc_scroll.gridheight = 3;          // Ocupa 3 filas
		gbc_scroll.insets = new Insets(10, 10, 10, 10);
		gbc_scroll.fill = GridBagConstraints.BOTH;
		gbc_scroll.weightx = 1.0;
		gbc_scroll.weighty = 1.0;
		getContentPane().add(scrollContactos, gbc_scroll);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setPreferredSize(new Dimension(100, 30));
		btnCancelar.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 1;
		gbc_btnCancelar.gridy = 4;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
		btnCancelar.addActionListener(e -> accionCancelar());
		
		JButton btnAnadir = new JButton("Añadir");
		btnAnadir.setPreferredSize(new Dimension(100, 30));
		btnAnadir.setFont(new Font("Georgia", Font.BOLD, 12));
		GridBagConstraints gbc_btnAnadir = new GridBagConstraints();
		gbc_btnAnadir.insets = new Insets(0, 0, 5, 5);
		gbc_btnAnadir.gridx = 4;
		gbc_btnAnadir.gridy = 4;
		getContentPane().add(btnAnadir, gbc_btnAnadir);
		btnAnadir.addActionListener(e -> anadirContacto());
		
	}
	
	/**
	 * Método que devuelve el panel de contactos.
	 * @return JPanel contenedorContactos
	 */
	public void crearBotonContacto(ContactoIndividual contacto) {
        JRadioButton botonContacto = new JRadioButton();
        botonContacto.setPreferredSize(new Dimension(100, 30));
        botonContacto.setFont(new Font("Georgia", Font.BOLD, 12));
        botonContacto.setBackground(new Color(209, 188, 214));
        botonContacto.setText(contacto.getNombre());
        botonesContacto.put(botonContacto, contacto);
        contenedorContactos.add(botonContacto);
        contenedorContactos.revalidate();
        contenedorContactos.repaint();
    }
	
	public void anadirContacto() {
	    List<JRadioButton> seleccionados = new ArrayList<>();
	    for (JRadioButton b : botonesContacto.keySet()) {
	        if (b.isSelected()) {
	            seleccionados.add(b);
	        }
	    }
		for (JRadioButton boton : seleccionados) {
			ContactoIndividual contacto = botonesContacto.get(boton);
			if (grupo.addMiembro(contacto)) {
				Controlador.INSTANCE.getUsuarioActual().addContacto(contacto, grupo);
			}
		}
		VentanaGrupos ventanaGrupos = new VentanaGrupos();
		dispose();
		ventanaGrupos.mostrarVentanaGrupos(getSize(), getLocation());
	}
	
	/**
	 * Método que completa la ventana con los contactos que no pertenecen al grupo.
	 * @param grupo - Grupo al que se le quieren añadir miembros.
	 */
	public void completarVentana(Grupo grupo) {
		Set<ContactoIndividual> contactos = Controlador.INSTANCE.getUsuariosNoPertenecientesAlGrupo(grupo);
		for (ContactoIndividual contacto : contactos) {
			crearBotonContacto(contacto);
		}
		
	}
	
	public void accionCancelar() {
		VentanaGrupos ventanaGrupos = new VentanaGrupos();
		dispose(); 
		ventanaGrupos.mostrarVentanaGrupos(getSize(), getLocation());
	}
	
	

}
