package umu.tds.apps.vista;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import umu.tds.apps.controlador.Controlador;
import umu.tds.apps.dominio.Grupo;

/**
 * Clase que representa la ventana de grupos de la aplicación, donde se pueden
 * crear, eliminar y gestionar grupos de contactos.
 */
public class VentanaGrupos extends JFrame {
	/**
	 * Serialización de la clase VentanaGrupos.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Modelo de lista que almacena los grupos disponibles.
	 */
	private DefaultListModel<Grupo> modeloLista;
	/**
	 * Lista que muestra los grupos disponibles.
	 */
	private JList<Grupo> listaGrupos;
	/**
	 * Panel que contiene los componentes de la ventana.
	 */
	private JPanel contentPane;

	/**
	 * Método que muestra la ventana de grupos con un tamaño y ubicación
	 * específicos.
	 * 
	 * @param tam - Tamaño de la ventana.
	 * @param ubi - Ubicación de la ventana en la pantalla.
	 */
	protected void mostrarVentanaGrupos(Dimension tam, Point ubi) {
		setVisible(true);
		setSize(tam);
		setLocation(ubi);
	}

	/**
	 * Crea el frame.
	 */
	protected VentanaGrupos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Grupos");
		setIconImage(new ImageIcon(getClass().getResource("/imagenes/iconoPestanas.png")).getImage());
		setBackground(new Color(242, 216, 245));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(242, 216, 245));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panelBotones = new JPanel();
		panelBotones.setBackground(new Color(242, 216, 245));
		contentPane.add(panelBotones, BorderLayout.SOUTH);
		
		//Boton "Crear Grupo"
		JButton btnCrearGrupo = new JButton("Crear Grupo");
		btnCrearGrupo.setBackground(new Color(209, 188, 214));
		btnCrearGrupo.addActionListener(e -> crearGrupo());
		panelBotones.add(btnCrearGrupo);

		// Boton "Añadir Miembros"
		JButton btnAnadirMiembros = new JButton("Añadir Miembros");
		btnAnadirMiembros.setBackground(new Color(209, 188, 214));
		btnAnadirMiembros.addActionListener(e -> anadirMiembros());
		panelBotones.add(btnAnadirMiembros);
		
		// Botones "Eliminar Grupo"
		JButton btnEliminarGrupo = new JButton("Eliminar Grupo");
		btnEliminarGrupo.setBackground(new Color(209, 188, 214));
		btnEliminarGrupo.addActionListener(e -> eliminarGrupo());
		panelBotones.add(btnEliminarGrupo);
		
		// Botón "Eliminar Miembros"
		JButton btnEliminarMiembros = new JButton("Eliminar Miembros");
		btnEliminarMiembros.setBackground(new Color(209, 188, 214));
		btnEliminarMiembros.addActionListener(e -> eliminarMiembros());
		panelBotones.add(btnEliminarMiembros);
		
		// Botón "Volver"
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(209, 188, 214));
		btnVolver.addActionListener(e -> volver());
		panelBotones.add(btnVolver);

		modeloLista = new DefaultListModel<>();
		cargarGrupos();
		listaGrupos = new JList<>(modeloLista);
		JScrollPane scrollPane = new JScrollPane(listaGrupos);

		listaGrupos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
					mostrarInformacionGrupo();
				}
			}
		});

		JPanel panelGrupos = new JPanel();
		panelGrupos.setBackground(new Color(242, 216, 245));
		contentPane.add(panelGrupos, BorderLayout.CENTER);
		panelGrupos.setLayout(new BorderLayout());

		panelGrupos.add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * Método que carga los grupos del usuario actual en el modelo de la lista. Se
	 * llama al inicializar la ventana para mostrar los grupos existentes.
	 */
	private void cargarGrupos() {
		modeloLista.clear();
		Controlador.INSTANCE.getUsuarioActual().getGrupos().stream().filter(c -> c != null).forEach(modeloLista::addElement);
	}

	/**
	 * Método que se activa al presionar el botón "Crear Grupo" y muestra un diálogo
	 * para introducir el nombre del nuevo grupo y ccrearlo con ese nombre.
	 */
	private void crearGrupo() {
		JTextField campoNombre = new JTextField();
		Object[] campos = { "Nombre:", campoNombre };
		int resultado = JOptionPane.showConfirmDialog(this, campos, "Nuevo Grupo", JOptionPane.OK_CANCEL_OPTION);
		if (resultado == JOptionPane.OK_OPTION) {
			String nombre = campoNombre.getText().trim();

			if (!nombre.isEmpty()) {
				if (Controlador.INSTANCE.registrarGrupo(nombre)) {
					Grupo nuevoGrupo = Controlador.INSTANCE.recuperarGrupo(nombre);
					if (nuevoGrupo != null) {
						modeloLista.addElement(nuevoGrupo);
					} else {
						JOptionPane.showMessageDialog(this, "No se pudo recuperar grupo después de agregarlo.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(this, "No se pudo registrar el grupo", "Error",
							JOptionPane.WARNING_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(this, "Debes introducir nombre.", "Datos incompletos",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Método que se activa al presionar el botón "Eliminar Grupo" y elimina el
	 * grupo seleccionado de la lista y del controlador.
	 */
	private void eliminarGrupo() {
		Grupo seleccionado = listaGrupos.getSelectedValue();
		if (seleccionado != null) {
			int confirm = JOptionPane.showConfirmDialog(this,
					"¿Eliminar grupo \"" + seleccionado.getNombre() + "\"?",
					"Confirmar eliminación", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				Controlador.INSTANCE.borrarGrupo(seleccionado);
				modeloLista.removeElement(seleccionado);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Selecciona un grupo para eliminar.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Método que se activa al hacer clic en un grupo de la lista y muestra un
	 * diálogo con la información del grupo seleccionado y varias opciones como son
	 * "Aceptar", "Chatear" y "Cambiar Imagen".
	 */
	private void mostrarInformacionGrupo() {
		Grupo seleccionado = listaGrupos.getSelectedValue();
		if (seleccionado != null) {
			String mensaje = "Nombre:\t" + seleccionado.getNombre() + "\nMiembros:\t"
					+ seleccionado.getMiembros().size();
			Object[] opciones = { "Aceptar", "Chatear", "Cambiar Imagen", "Cambiar Nombre" };
			int resultado = JOptionPane.showOptionDialog(this, mensaje, "Información del Grupo",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
			if (resultado == 1) {
				chatear(seleccionado);
			} else if (resultado == 2) {
				cambiarImagenGrupo(seleccionado);
			} else if (resultado == 3) {
				cambiarNombreGrupo(seleccionado);
			}
		}
	}
	
	/**
	 * Método que se activa al presionar el botón "Cambiar Nombre" y permite cambiar
	 * el nombre del grupo seleccionado.
	 * 
	 * @param seleccionado - El grupo seleccionado al que se le desea cambiar el
	 *                     nombre.
	 */
	private void cambiarNombreGrupo(Grupo seleccionado) {
		JTextField campoNuevoNombre = new JTextField();
		seleccionado.getNombre();
		Object[] campos = { "Nuevo Nombre:", campoNuevoNombre };

		int resultado = JOptionPane.showConfirmDialog(this, campos, "Cambiar Nombre", JOptionPane.OK_CANCEL_OPTION);
		if (resultado == JOptionPane.OK_OPTION) {
			String nuevoNombre = campoNuevoNombre.getText().trim();
			if (!nuevoNombre.isEmpty()) {
				Controlador.INSTANCE.cambiarNombreGrupo(seleccionado, nuevoNombre);
				modeloLista.setElementAt(seleccionado, modeloLista.indexOf(seleccionado));
			} else {
				JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Nombre inválido",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Método que se activa al presionar el botón "Cambiar Imagen" y permite cambiar
	 * la imagen del grupo seleccionado.
	 * 
	 * @param seleccionado - El grupo seleccionado al que se le desea cambiar la
	 *                     imagen.
	 */
	private void cambiarImagenGrupo(Grupo seleccionado) {
		String url = JOptionPane.showInputDialog(this, "Introduce la URL de la imagen:",
				"Seleccionar imagen desde internet", JOptionPane.PLAIN_MESSAGE);

		if (url != null && !url.trim().isEmpty()) {
			Controlador.INSTANCE.cambiarImagenGrupo(seleccionado, url);
		}
	}

	/**
	 * Método que se activa al presionar el botón "Chatear" y te lleva a la ventana
	 * principal con el grupo seleccionado.
	 * 
	 * @param grupo - El grupo con el que se desea chatear.
	 */
	private void chatear(Grupo grupo) {
		VentanaPrincipal v = new VentanaPrincipal(grupo);
		dispose();
		v.mostrarVentanaPrincipal(getSize(), getLocation());
		SwingUtilities.invokeLater(() -> v.recuperarMensajes());
	}

	/**
	 * Método que se activa al presionar el botón "Volver" y te lleva a la ventana
	 * principal.
	 */
	private void volver() {
		VentanaPrincipal v = new VentanaPrincipal();
		dispose();
		v.mostrarVentanaPrincipal(this.getSize(), this.getLocation());
	}

	/**
	 * Método que se activa al presionar el botón "Añadir Miembros" y te lleva a la
	 * ventana correspondiente que permite añadir miembros al grupo seleccionado.
	 */
	private void anadirMiembros() {
		VentanaGestionarMiembros v = new VentanaGestionarMiembros();
		Grupo seleccionado = listaGrupos.getSelectedValue();
		if (seleccionado == null) {
			JOptionPane.showMessageDialog(this, "Selecciona un grupo al que añadir miembros.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
		} else {
			dispose();
			v.mostrarVentanaGestionarMiembros(this.getSize(), this.getLocation(), seleccionado,
					VentanaGestionarMiembros.MODO_ANADIR_MIEMBROS);
		}
	}

	/**
	 * Método que se activa al presionar el botón "Eliminar Miembros" y te lleva a
	 * la ventana correspondiente que permite eliminar miembros de un grupo.
	 */
	private void eliminarMiembros() {
		VentanaGestionarMiembros v = new VentanaGestionarMiembros();
		Grupo seleccionado = listaGrupos.getSelectedValue();
		if (seleccionado == null) {
			JOptionPane.showMessageDialog(this, "Selecciona un grupo del que eliminar miembros.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
		} else {
			dispose();
			v.mostrarVentanaGestionarMiembros(this.getSize(), this.getLocation(), seleccionado,
					VentanaGestionarMiembros.MODO_ELIMINAR_MIEMBROS);
		}
	}

}