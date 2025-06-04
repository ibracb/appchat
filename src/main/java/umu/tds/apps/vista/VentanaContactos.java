package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import umu.tds.apps.controlador.Controlador;
import umu.tds.apps.dominio.ContactoIndividual;

public class VentanaContactos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<ContactoIndividual> modelContactos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaContactos frame = new VentanaContactos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Mostrar la ventana de la lista contactos
	 */
	public void mostrarVentanaContactos(Dimension tam, Point ubi) {
		setVisible(true);
		setSize(tam);
		setLocation(ubi);
	}
	/**
	 * Create the frame.
	 */
	public VentanaContactos() {
		setTitle("Lista de contactos");
		setBackground(getForeground());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(242, 216, 245));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelBotones = new JPanel();
		panelBotones.setBackground(new Color(242, 216, 245));
		contentPane.add(panelBotones, BorderLayout.SOUTH);
		
		JButton btnAñadir = new JButton("Añadir contacto");
		btnAñadir.setFont(new Font("Georgia", Font.BOLD, 12));
		btnAñadir.addActionListener(e -> añadirContacto());
		panelBotones.add(btnAñadir);
		
		JButton btnEliminar = new JButton("Eliminar contacto");
		btnEliminar.setFont(new Font("Georgia", Font.BOLD, 12));
		btnEliminar.addActionListener(e -> eliminarContacto());
		panelBotones.add(btnEliminar);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		modelContactos = new DefaultListModel<ContactoIndividual>();
		
		JList<ContactoIndividual> listContactos = new JList<>(modelContactos);
		listContactos.setCellRenderer(new ContactoListCellRenderer());
		scrollPane.setViewportView(listContactos);
		
		updateListaContactos();
		
	}
	
	private void añadirContacto() {
		JTextField textFieldNombre = new JTextField();
		JTextField textFieldMovil = new JTextField();
		Object[] mensaje = {"Nombre:", textFieldNombre, "Móvil:", textFieldMovil};
		int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Añadir contacto", JOptionPane.OK_CANCEL_OPTION);
		if (opcion == JOptionPane.OK_OPTION) {
			String nombre = textFieldNombre.getText();
			String movil = textFieldMovil.getText();
			boolean registro = Controlador.INSTANCE.registrarContactoIndividual(nombre, movil);
			if(registro) {
				updateListaContactos();
				JOptionPane.showMessageDialog(this, "Nombre: " + nombre + "\nMóvil: " + movil);
			}
			else {
				JOptionPane.showMessageDialog(this, "Error en la adición del contacto");
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "Cancelado");
		}
	}
	
	private void updateListaContactos() {
		modelContactos.clear();
		Controlador.INSTANCE.getContactosIndividualesUsuarioActual().forEach(contacto -> {
			modelContactos.addElement((ContactoIndividual) contacto);
		});
	}
	
	private void eliminarContacto() {
		
	}
	
}
