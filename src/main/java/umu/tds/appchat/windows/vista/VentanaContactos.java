package umu.tds.appchat.windows.vista;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import umu.tds.appchat.controllers.ControladorAppChat;
import umu.tds.appchat.models.Contacto;
import umu.tds.appchat.models.ContactoIndividual;
import umu.tds.appchat.models.Grupo;
import umu.tds.appchat.windows.components.ContactCellRenderer;

enum Estado {
	GRUPO, CONTACTOS
}

public class VentanaContactos extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Estado ESTADO_DEFAULT = Estado.CONTACTOS;

	private JPanel leftPanel; // Aseguramos que leftPanel esté disponible en la clase
	private JPanel rightPanel;
	private VentanaPrincipal v;
	private DefaultListModel<Contacto> modeloContactos;
	private DefaultListModel<ContactoIndividual> modeloGrupos;
	private JList<Contacto> list;
	private JList<ContactoIndividual> listGrupo;
	private Estado state;
	private Contacto contactoSeleccionado;
	private ContactoIndividual contactoGrupo;
	private JLabel labelGrupo;
	private JButton btnNewButton_3;
	private JButton btnNewButton_2;
	private JPanel moveButtonsPanel;

	public VentanaContactos(VentanaPrincipal v) {
		state = ESTADO_DEFAULT;
		this.v = v;
		modeloContactos = new DefaultListModel<>();
		list = new JList<>(modeloContactos);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new ContactCellRenderer()); // Asegúrate de hacer esto al inicializar
		list.addListSelectionListener(e -> manejarSeleccionContacto());
		modeloGrupos = new DefaultListModel<>();
		listGrupo = new JList<>(modeloGrupos);
		listGrupo.setCellRenderer(new ContactCellRenderer()); // Asegúrate de hacer esto al inicializar

		setTitle("Gestión de Contactos y Grupos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);

		// Panel principal con BorderLayout
		JPanel mainPanel = new JPanel(new BorderLayout());
		getContentPane().add(mainPanel);

		// Panel central con GridLayout (1 fila, 3 columnas)
		JPanel centerPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		mainPanel.add(centerPanel, BorderLayout.CENTER);

		leftPanel = new JPanel(new BorderLayout());
		centerPanel.add(leftPanel);

		JLabel labelContactos = new JLabel("Lista Contactos", JLabel.CENTER);
		leftPanel.add(labelContactos, BorderLayout.NORTH);

		// Aseguramos que 'list' esté dentro de un JScrollPane
		JScrollPane scrollPane = new JScrollPane(list);
		leftPanel.add(scrollPane, BorderLayout.CENTER);

		ImageIcon iconoEnviar = new ImageIcon(getClass().getResource("/agregar-usuario (1).png"));
		Image imagenEnviar = iconoEnviar.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		JButton btnAddContacto = new JButton(new ImageIcon(imagenEnviar));
		btnAddContacto.setText("Añadir Contacto");
		btnAddContacto.addActionListener(e -> abrirVentanaAgregarContacto());
		leftPanel.add(btnAddContacto, BorderLayout.SOUTH);

		// Panel central para los botones de mover (>> y <<)
		moveButtonsPanel = new JPanel();
		moveButtonsPanel.setLayout(new BoxLayout(moveButtonsPanel, BoxLayout.Y_AXIS));
		centerPanel.add(moveButtonsPanel);

		Component verticalGlue_1 = Box.createVerticalGlue();
		moveButtonsPanel.add(verticalGlue_1);

		JButton btnNewButton = new JButton(">>");
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton.addActionListener(e -> añadirContactoAGrupo());
		moveButtonsPanel.add(btnNewButton);

		Component verticalStrut = Box.createVerticalStrut(20);
		moveButtonsPanel.add(verticalStrut);

		JButton btnNewButton_1 = new JButton("<<");
		btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton_1.addActionListener(e -> eliminarContactoDeGrupo());
		moveButtonsPanel.add(btnNewButton_1);

		Component verticalGlue = Box.createVerticalGlue();
		moveButtonsPanel.add(verticalGlue);

		btnNewButton_2 = new JButton("Volver");
		btnNewButton_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton_2.addActionListener(e -> cerrarVentanaContactos());
		moveButtonsPanel.add(btnNewButton_2);

		// Panel derecha (lista de contactos en el grupo)
		rightPanel = new JPanel(new BorderLayout());
		centerPanel.add(rightPanel);

		labelGrupo = new JLabel("", JLabel.CENTER);
		rightPanel.add(labelGrupo, BorderLayout.NORTH);

		rightPanel.add(new JScrollPane(listGrupo), BorderLayout.CENTER);

		JButton btnAddGrupo = new JButton("Añadir Grupo");
		btnAddGrupo.addActionListener(e -> abrirVentanaCrearGrupo());

		rightPanel.add(btnAddGrupo, BorderLayout.SOUTH);

		refreshContacts();
	}

	private void añadirContactoAGrupo() {
		if (state.equals(Estado.GRUPO)) {
			contactoGrupo = (ContactoIndividual) list.getSelectedValue();
			if (contactoGrupo != null) {
				if (ControladorAppChat.getInstancia().agregarContactoAGrupo(contactoSeleccionado.getNombre(),
						contactoGrupo)) {
					mostrarContactosGrupo(contactoSeleccionado.getNombre());
				}
				else
					JOptionPane.showMessageDialog(this, "Error al añadir el contacto, prueba otra vez", "Error",
							JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "Selecciona un contacto para usar este boton", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		} else {
			JOptionPane.showMessageDialog(this, "Necesitas seleccionar un grupo primero para usar este boton", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void eliminarContactoDeGrupo() {
		if (state.equals(Estado.GRUPO)) {
			contactoGrupo = listGrupo.getSelectedValue();
			if (contactoGrupo != null) {
				ControladorAppChat.getInstancia().elminarContactoDeGrupo(contactoSeleccionado.getNombre(),
						contactoGrupo);
				mostrarContactosGrupo(contactoSeleccionado.getNombre());
			} else {
				JOptionPane.showMessageDialog(this, "Selecciona un contacto para usar este boton", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Necesitas seleccionar un grupo primero para usar este boton", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void manejarSeleccionContacto() {
		if (state.equals(Estado.CONTACTOS)) {
			contactoSeleccionado = list.getSelectedValue();
			if (contactoSeleccionado instanceof Grupo) {
				state = Estado.GRUPO;
				String nombre = contactoSeleccionado.getNombre();
				btnNewButton_2.setText("Salir del grupo");
				labelGrupo.setText(nombre);
				mostrarContactosGrupo(nombre);
			}
		}
	}

	private void mostrarContactosGrupo(String nombre) {
		List<ContactoIndividual> contactosGrupo = ControladorAppChat.getInstancia()
				.getContactosIndividualesDeUnGrupo(nombre);
		modeloGrupos.clear();
		for (ContactoIndividual contacto : contactosGrupo) {
			modeloGrupos.addElement(contacto);
		}
		refreshContacts();
		rightPanel.revalidate();
		rightPanel.repaint();
		listGrupo.repaint();
	}

	/**
	 * Refresca la lista de contactos en el panel izquierdo.
	 */
	void refreshContacts() {
		List<Contacto> contactos;
		if (state.equals(Estado.CONTACTOS)) {
			contactos = ControladorAppChat.getInstancia().getContactos();
		} else {
			contactos = ControladorAppChat.getInstancia()
					.getContactosIndividualesQueNoEstenEnUnGrupo(contactoSeleccionado.getNombre());
		}
		modeloContactos.clear();
		for (Contacto c : contactos) {
			modeloContactos.addElement(c);
		}

		// Asegurar que se actualiza correctamente
		leftPanel.revalidate();
		leftPanel.repaint();
		list.repaint();
		v.refreshContacts();
	}

	/**
	 * Método para abrir la ventana de agregar un contacto.
	 */
	private void abrirVentanaAgregarContacto() {
		if (state.equals(Estado.GRUPO)) {
			JOptionPane.showMessageDialog(this,
					"Tienes un grupo Seleccionado, dale al boton de cancelar para poder crear un grupo", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			VentanaAgregarContacto ventanaAgregarContacto = new VentanaAgregarContacto(this);
			ventanaAgregarContacto.setVisible(true);
			this.setVisible(false);
		}

	}

	private void abrirVentanaCrearGrupo() {
		if (state.equals(Estado.GRUPO)) {
			JOptionPane.showMessageDialog(this,
					"Tienes un grupo Seleccionado, dale al boton de cancelar para poder crear un grupo", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			VentanaCrearGrupo ventanaCrearGrupo = new VentanaCrearGrupo(this);
			ventanaCrearGrupo.setVisible(true);
			this.setVisible(false);
		}

	}

	/**
	 * Cierra la ventana de contactos.
	 */
	private void cerrarVentanaContactos() {
		if (state.equals(Estado.CONTACTOS)) {
			v.refreshContacts();
			v.setVisible(true);
			this.dispose();
		} else {
			modeloGrupos.clear();
			labelGrupo.setText("");
			btnNewButton_2.setText("Volver");
			state = Estado.CONTACTOS;
			refreshContacts();
		}

	}
}
