package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
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

public class VentanaContactos extends JFrame {

    private static final long serialVersionUID = 1L;
    private DefaultListModel<ContactoIndividual> modeloLista;
    private JList<ContactoIndividual> listaContactos;
    
    public void mostrarVentanaContactos(Dimension tam, Point ubi) {
		setVisible(true);
		setSize(tam);
		setLocation(ubi);
	}

    public VentanaContactos() {
        setTitle("Contactos Individuales");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        modeloLista = new DefaultListModel<>();
        cargarContactos();

        listaContactos = new JList<>(modeloLista);
        listaContactos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaContactos.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            String texto = (value != null) ? value.getNombre() : "Contacto inválido";
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
        botonesPanel.add(btnAgregar);
        botonesPanel.add(btnEliminar);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(botonesPanel, BorderLayout.SOUTH);
        
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> volver());
        botonesPanel.add(btnVolver);
    }

    private void cargarContactos() {
        modeloLista.clear();
        Controlador.INSTANCE.getContactosIndividualesUsuarioActual().stream()
            .filter(c -> c != null)
            .forEach(modeloLista::addElement);
    }

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

    private void eliminarContacto() {
        ContactoIndividual seleccionado = listaContactos.getSelectedValue();
        if (seleccionado != null) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Eliminar contacto \"" + seleccionado.getNombre() + "\"?",
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

    private void mostrarInformacionContacto() {
        ContactoIndividual seleccionado = listaContactos.getSelectedValue();
        if (seleccionado != null) {
            JOptionPane.showMessageDialog(this,
                    "Nombre: " + seleccionado.getNombre() + "\nMóvil: " + seleccionado.getMovil(),
                    "Información del Contacto", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void volver() {
    	VentanaPrincipal v = new VentanaPrincipal();
    	dispose();
    	v.mostrarVentanaPrincipal(this.getSize(), this.getLocation());
    }
    
}
