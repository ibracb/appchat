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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import umu.tds.apps.controlador.Controlador;
import umu.tds.apps.dominio.ContactoIndividual;

/**
 * Ventana para mostrar y gestionar contactos individuales. Permite agregar,
 * eliminar y ver detalles de los contactos.
 */
public class VentanaContactos extends JFrame {
	/**
	 * Serial version UID para la clase VentanaContactos.
	 */
    private static final long serialVersionUID = 1L;
	/**
	 * Modelo de lista para mostrar los contactos individuales.
	 */
    private DefaultListModel<ContactoIndividual> modeloLista;
    /**
     * Lista que muestra los contactos individuales.
     */
    private JList<ContactoIndividual> listaContactos;
    
	/**
	 * Método para mostrar la ventana de contactos con un tamaño y ubicación
	 * específicos.
	 * 
	 * @param tam Tamaño de la ventana.
	 * @param ubi Ubicación de la ventana.
	 */
    protected void mostrarVentanaContactos(Dimension tam, Point ubi) {
		setVisible(true);
		setSize(tam);
		setLocation(ubi);
	}

	/**
	 * Constructor de la clase VentanaContactos. Configura la ventana, carga los
	 * contactos y crea los componentes necesarios.
	 */
    protected VentanaContactos() {
        setTitle("Contactos Individuales");
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/iconoPestanas.PNG")).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(242, 216, 245));
        setSize(400, 300);
        setLocationRelativeTo(null);

        modeloLista = new DefaultListModel<>();
        cargarContactos();

        listaContactos = new JList<>(modeloLista);
        listaContactos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaContactos.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            String texto = (value != null) ? Controlador.INSTANCE.getNombreContacto(value) : "Contacto inválido";
            JLabel label = new JLabel(texto);
            if (isSelected) {
                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
                label.setOpaque(true);
            }
            return label;
        });

        JScrollPane scrollPane = new JScrollPane(listaContactos);

        JButton btnAgregar = new JButton("Agregar Contacto");
        btnAgregar.addActionListener(e -> agregarContacto());

        JButton btnEliminar = new JButton("Eliminar Contacto");
        btnEliminar.addActionListener(e -> eliminarContacto());

        listaContactos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                    mostrarInformacionContacto();
                }
            }
        });

        JPanel botonesPanel = new JPanel();
        botonesPanel.setBackground(new Color(242, 216, 245));
        botonesPanel.add(btnAgregar);
        botonesPanel.add(btnEliminar);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(botonesPanel, BorderLayout.SOUTH);
        
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> volver());
        botonesPanel.add(btnVolver);
    }
    
	/**
	 * Método para cargar los contactos individuales del usuario actual en el modelo
	 * de lista.
	 */
    private void cargarContactos() {
        modeloLista.clear();
        Controlador.INSTANCE.getContactosIndividualesAñadidosUsuarioActual().stream()
            .filter(c -> c != null)
            .forEach(modeloLista::addElement);
    }

	/**
	 * Método para agregar un nuevo contacto individual. Muestra un diálogo para
	 * introducir el nombre y el móvil del contacto.
	 */
    private void agregarContacto() {
        JTextField campoNombre = new JTextField();
        JTextField campoMovil = new JTextField();
        Object[] campos = {
                "Nombre:", campoNombre,
                "Móvil:", campoMovil
        };

        int resultado = JOptionPane.showConfirmDialog(this, campos, "Nuevo Contacto", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            String nombre = campoNombre.getText().trim();
            String movil = campoMovil.getText().trim();

            if (!nombre.isEmpty() && !movil.isEmpty()) {
                if (Controlador.INSTANCE.registrarContactoIndividual(nombre, movil)) {
                    ContactoIndividual nuevoContacto = Controlador.INSTANCE.recuperarContacto(movil);
                    if (nuevoContacto != null) {
                        modeloLista.addElement(nuevoContacto);
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "No se pudo recuperar el contacto después de agregarlo.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                        "No se pudo registrar el contacto",
                        "Error", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Debes introducir nombre y móvil.",
                        "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
	/**
	 * Método para eliminar un contacto individual seleccionado de la lista. Muestra
	 * un diálogo de confirmación antes de proceder con la eliminación.
	 */
    private void eliminarContacto() {
        ContactoIndividual seleccionado = listaContactos.getSelectedValue();
        if (seleccionado != null) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Eliminar contacto \"" + Controlador.INSTANCE.getNombreContacto(seleccionado) + "\"?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Controlador.INSTANCE.borrarContactoIndividual(seleccionado);
                modeloLista.removeElement(seleccionado);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un contacto para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
	/**
	 * Método para mostrar la información del contacto seleccionado en un diálogo.
	 * Permite al usuario ver detalles y chatear con el contacto.
	 */
    private void mostrarInformacionContacto() {
    	ContactoIndividual seleccionado = listaContactos.getSelectedValue();
    	if (seleccionado != null) {
    		String mensaje = "Nombre:\t" + Controlador.INSTANCE.getNombreContacto(seleccionado) + "\nMóvil:\t" + Controlador.INSTANCE.getMovilContactoIndividual(seleccionado);

    		Object[] opciones = {"Aceptar", "Chatear", "Cambiar Nombre"};
    		int resultado = JOptionPane.showOptionDialog(
    			this,
    			mensaje,
    			"Información del Contacto",
    			JOptionPane.DEFAULT_OPTION,
    			JOptionPane.INFORMATION_MESSAGE,
    			null,
    			opciones,
    			opciones[0]
    		);
    		if (resultado == 1) {
    			chatear(seleccionado);
    		} else if (resultado ==2){   
    			cambiarNombre(seleccionado);
    		}
    	}
    }
    
	/**
	 * Método para cambiar el nombre de un contacto individual. Muestra un diálogo
	 * para introducir el nuevo nombre y actualiza el contacto en el modelo.
	 * 
	 * @param contacto El contacto individual cuyo nombre se desea cambiar.
	 */
	private void cambiarNombre(ContactoIndividual contacto) {
		JTextField campoNuevoNombre = new JTextField();
		campoNuevoNombre.setText(Controlador.INSTANCE.getNombreContacto(contacto));
		Object[] campos = { "Nuevo Nombre:", campoNuevoNombre };

		int resultado = JOptionPane.showConfirmDialog(this, campos, "Cambiar Nombre", JOptionPane.OK_CANCEL_OPTION);
		if (resultado == JOptionPane.OK_OPTION) {
			String nuevoNombre = campoNuevoNombre.getText().trim();
			if (!nuevoNombre.isEmpty()) {
				Controlador.INSTANCE.cambiarNombreContactoIndividual(contacto, nuevoNombre);
				modeloLista.setElementAt(contacto, modeloLista.indexOf(contacto));
			} else {
				JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Nombre inválido",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}
    
	/**
	 * Método para iniciar una conversación de chat con el contacto
	 * individual seleccionado. Crea una nueva ventana de chat
	 * y la muestra al usuario.
	 */
    private void chatear(ContactoIndividual contacto) {
    	VentanaPrincipal v = new VentanaPrincipal(contacto);
    	dispose();
    	v.mostrarVentanaPrincipal(getSize(), getLocation());
    }
    
    /**
     * Método para volver a la ventana principal.
     */
    private void volver() {
    	VentanaPrincipal v = new VentanaPrincipal();
    	dispose();
    	v.mostrarVentanaPrincipal(this.getSize(), this.getLocation());
    }
    
}
