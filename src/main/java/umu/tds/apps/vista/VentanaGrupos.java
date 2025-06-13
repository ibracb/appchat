package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
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

public class VentanaGrupos extends JFrame {

	private static final long serialVersionUID = 1L;
	private DefaultListModel<Grupo> modeloLista;
	private JList<Grupo> listaGrupos;
	private JPanel contentPane;
	
	protected void mostrarVentanaGrupos(Dimension tam, Point ubi) {
		setVisible(true);
		setSize(tam);
		setLocation(ubi);
	}

	/**
	 * Create the frame.
	 */
	protected VentanaGrupos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panelBotones = new JPanel();
		contentPane.add(panelBotones, BorderLayout.SOUTH);

		JButton btnCrearGrupo = new JButton("Crear Grupo");
		btnCrearGrupo.addActionListener(e -> crearGrupo());
		panelBotones.add(btnCrearGrupo);
		
		JButton btnAnadirMiembros = new JButton("Añadir Miembros");
		btnAnadirMiembros.addActionListener(e -> anadirMiembros());
		panelBotones.add(btnAnadirMiembros);

		JButton btnEliminarGrupo = new JButton("Eliminar Grupo");
		btnEliminarGrupo.addActionListener(e -> eliminarGrupo());
		panelBotones.add(btnEliminarGrupo);
		
		JButton btnVolver = new JButton("Volver");
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
		contentPane.add(panelGrupos, BorderLayout.CENTER);
		panelGrupos.setLayout(new BorderLayout());

		panelGrupos.add(scrollPane, BorderLayout.CENTER);
	}

	private void cargarGrupos() {
		modeloLista.clear();
		Controlador.INSTANCE.getGruposUsuarioActual().stream()
			.filter(c -> c != null)
			.forEach(modeloLista::addElement);
	}

	private void crearGrupo() {
		JTextField campoNombre = new JTextField();
		Object[] campos = {
			"Nombre:", campoNombre
		};
		int resultado = JOptionPane.showConfirmDialog(this, campos, "Nuevo Grupo", JOptionPane.OK_CANCEL_OPTION);
		if (resultado == JOptionPane.OK_OPTION) {
			String nombre = campoNombre.getText().trim();

			if (!nombre.isEmpty()) {
				if (Controlador.INSTANCE.registrarGrupo(nombre, nombre)) {
					Grupo nuevoGrupo = Controlador.INSTANCE.recuperarGrupo(nombre);
					if (nuevoGrupo != null) {
						modeloLista.addElement(nuevoGrupo);
					} else {
						JOptionPane.showMessageDialog(this,
							"No se pudo recuperar grupo después de agregarlo.",
							"Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(this,
						"No se pudo registrar el grupo",
						"Error", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this,
					"Debes introducir nombre.",
					"Datos incompletos", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private void eliminarGrupo() {
		Grupo seleccionado = listaGrupos.getSelectedValue();
		if (seleccionado != null) {
			int confirm = JOptionPane.showConfirmDialog(
				this,
				"¿Eliminar grupo \"" + seleccionado.getNombre() + "\"?",
				"Confirmar eliminación",
				JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				Controlador.INSTANCE.borrarGrupo(seleccionado);
				modeloLista.removeElement(seleccionado);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Selecciona un grupo para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void mostrarInformacionGrupo() {
		Grupo seleccionado = listaGrupos.getSelectedValue();
		if (seleccionado != null) {
			JOptionPane.showMessageDialog(this,
				"Nombre: " + seleccionado.getNombre() + "\nMiembros: " + seleccionado.getMiembros().size(),
				"Información del Grupo", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void volver() {
		VentanaPrincipal v = new VentanaPrincipal();
		dispose();
		v.mostrarVentanaPrincipal(this.getSize(), this.getLocation());
	}
	
	private void anadirMiembros() {
		VentanaAnadirMiembros v = new VentanaAnadirMiembros();
		Grupo seleccionado = listaGrupos.getSelectedValue();
		dispose();
		v.mostrarVentanaAnadirMiembros(this.getSize(), this.getLocation(), seleccionado);
	}
	

}