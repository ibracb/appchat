package umu.tds.apps.vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import umu.tds.apps.controlador.Controlador;
import umu.tds.apps.dominio.ContactoIndividual;
import umu.tds.apps.dominio.Grupo;



public class VentanaGestionarMiembros extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public static final String MODO_ANADIR_MIEMBROS = "Añadir Miembros";
	public static final String MODO_ELIMINAR_MIEMBROS = "Eliminar Miembros";
	
	private JPanel contenedorContactos;
	private JScrollPane scrollContactos;
	private JButton btnCancelar;
	private Grupo grupo;
	private Map<JRadioButton, ContactoIndividual> botonesContacto = new HashMap<>();
	private JButton btnPrincipal;
	private GridBagLayout gridBagLayout;
	private GridBagConstraints gbc_scroll;
	private GridBagConstraints gbc_btnCancelar;
	private GridBagConstraints gbc_btnAnadir;
	private String modo;
	
	protected void mostrarVentanaGestionarMiembros(Dimension tam, Point ubi, Grupo grupo, String modo) {
		contenedorContactos.removeAll();
		this.grupo = grupo;
		this.modo = modo;
		completarVentana();
		setVisible(true);
		setSize(tam);
		setLocation(ubi);
	}
	/**
	 * Create the application.
	 */
	protected VentanaGestionarMiembros() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 798, 529);
		setIconImage(new ImageIcon(getClass().getResource("/imagenes/iconoPestanas.PNG")).getImage());
		setTitle("Gestionar Miembros del Grupo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(242, 216, 245));
		
		gridBagLayout = new GridBagLayout();
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

		gbc_scroll = new GridBagConstraints();
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
		gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 1;
		gbc_btnCancelar.gridy = 4;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
		btnCancelar.addActionListener(e -> accionCancelar());
		
		btnPrincipal = new JButton("Añadir");
		btnPrincipal.setPreferredSize(new Dimension(100, 30));
		btnPrincipal.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_btnAnadir = new GridBagConstraints();
		gbc_btnAnadir.insets = new Insets(0, 0, 5, 5);
		gbc_btnAnadir.gridx = 4;
		gbc_btnAnadir.gridy = 4;
		getContentPane().add(btnPrincipal, gbc_btnAnadir);
		btnPrincipal.addActionListener(e -> accionBtnPrincipal());
		
	}
	
	/**
	 * Método que devuelve el panel de contactos.
	 * @return JPanel contenedorContactos
	 */
	private void crearBotonContacto(ContactoIndividual contacto) {
        JRadioButton botonContacto = new JRadioButton();
        botonContacto.setPreferredSize(new Dimension(100, 30));
        botonContacto.setFont(new Font("Georgia", Font.BOLD, 12));
        botonContacto.setBackground(new Color(209, 188, 214));
        botonContacto.setText(Controlador.INSTANCE.getNombreContacto(contacto));
        botonesContacto.put(botonContacto, contacto);
        contenedorContactos.add(botonContacto);
        contenedorContactos.revalidate();
        contenedorContactos.repaint();
    }
	
	private void accionBtnPrincipal() {
		List<JRadioButton> seleccionados = new ArrayList<>();
	    for (JRadioButton b : botonesContacto.keySet()) {
	        if (b.isSelected()) {
	            seleccionados.add(b);
	        }
	    }
		if (seleccionados.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Selecciona algún contacto.", "Aviso", JOptionPane.WARNING_MESSAGE);
		} else if (this.modo.equals(MODO_ANADIR_MIEMBROS)) {
			for (JRadioButton boton : seleccionados) {
				ContactoIndividual contacto = botonesContacto.get(boton);
				if (grupo.addMiembro(contacto)) {
					Controlador.INSTANCE.añadirContacto(contacto, grupo);
				}
			}
		} else if (this.modo.equals(MODO_ELIMINAR_MIEMBROS)) {
			for (JRadioButton boton : seleccionados) {
				ContactoIndividual contacto = botonesContacto.get(boton);
				if (grupo.removeMiembro(contacto)) {
					Controlador.INSTANCE.eliminarContacto(contacto, grupo);
				}
			}   
			
		}
		Controlador.INSTANCE.actualizarGrupo(grupo);
		VentanaGrupos ventanaGrupos = new VentanaGrupos();
		dispose();
		ventanaGrupos.mostrarVentanaGrupos(getSize(), getLocation());
	}
	
	/**
	 * Método que completa la ventana con los contactos que no pertenecen al grupo.
	 * @param grupo - Grupo al que se le quieren añadir miembros.
	 */
	private void completarVentana() {
		
		if (modo.equals(MODO_ANADIR_MIEMBROS)) {
			Set<ContactoIndividual> contactos = Controlador.INSTANCE.getUsuariosNoPertenecientesAlGrupo(grupo);
			for (ContactoIndividual contacto : contactos) {
				crearBotonContacto(contacto);
			}
		} else if (modo.equals(MODO_ELIMINAR_MIEMBROS)) {
			btnPrincipal.setText("Eliminar");
			for (ContactoIndividual cI : Controlador.INSTANCE.getMiembros(grupo)) {
                crearBotonContacto(cI);
            }
		}
		
	}
	
	private void accionCancelar() {
		VentanaGrupos ventanaGrupos = new VentanaGrupos();
		dispose(); 
		ventanaGrupos.mostrarVentanaGrupos(getSize(), getLocation());
	}
	
	

}
