package umu.tds.appchat.windows.vista;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import umu.tds.appchat.controllers.ControladorAppChat;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.Box;
import java.awt.Dimension;

public class VentanaCrearGrupo extends JFrame implements VentanaCambiaImagenes {

	private static final long serialVersionUID = 1L;
	private static final File IMAGEN_POR_DEFECTO = null;
	private static final int DEFAUL_HEIGHT_AND_WIDTH = 150;

	private JTextField nombreField;
	private URL url;
	private File destinationFile = IMAGEN_POR_DEFECTO;
	private JLabel imagen;
	private VentanaContactos ventanaContactos;

	public VentanaCrearGrupo(VentanaContactos ventanaContactos) {
		this.ventanaContactos = ventanaContactos;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 350);
		JPanel mainPanel = new JPanel();
		getContentPane().add(mainPanel);
		mainPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		mainPanel.add(panel, BorderLayout.NORTH);

		JLabel lblNombre = new JLabel("Nombre:");
		panel.add(lblNombre);

		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setPreferredSize(new Dimension(0, 40));
		panel.add(verticalStrut);

		nombreField = new JTextField();
		panel.add(nombreField);
		nombreField.setColumns(20);

		// Panel derecha (lista de contactos en el grupo)
		JPanel rightPanel = new JPanel();
		mainPanel.add(rightPanel, BorderLayout.SOUTH);

		JButton btnAcetpar = new JButton("Aceptar");
		btnAcetpar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAcetpar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAcetpar.addActionListener(e -> crearGrupo());

		JButton brnCancelar = new JButton("Cancelar");
		brnCancelar.setHorizontalTextPosition(SwingConstants.CENTER);
		brnCancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
		brnCancelar.addActionListener(e -> dispose());
		rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		rightPanel.add(brnCancelar);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		rightPanel.add(horizontalStrut);
		rightPanel.add(btnAcetpar);

		JPanel centerPanel = new JPanel();
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		centerPanel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblImagen = new JLabel("Imagen:");
		panel_1.add(lblImagen);

		JPanel panel_2 = new JPanel();
		centerPanel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		imagen = new JLabel("");
		imagen.setHorizontalTextPosition(SwingConstants.CENTER);
		imagen.setAlignmentX(Component.CENTER_ALIGNMENT);
		imagen.setHorizontalAlignment(SwingConstants.CENTER);
		imagen.setIcon(ControladorAppChat.getInstancia().getScaledDefaultImage(DEFAUL_HEIGHT_AND_WIDTH));
		panel_2.add(imagen);

		JPanel panel_3 = new JPanel();
		centerPanel.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnCambiarImagen = new JButton("Cambiar Imagen");
		btnCambiarImagen.addActionListener(e -> abrirVentanaCambioImagen());

		panel_3.add(btnCambiarImagen);

	}

	private void crearGrupo() {
		String nombre = nombreField.getText();
		String image = getImagen();
		
		if (!nombre.isEmpty()) {
			if (!nombre.matches("[a-zA-Z0-9]+")) {
				JOptionPane.showMessageDialog(null, "El nombre contiene caracteres no permitidos.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			boolean result = ControladorAppChat.getInstancia().agregarGrupo(nombre, image);
			if (!result)
				JOptionPane.showMessageDialog(null, "No ha sido posible crear el grupo", "Error",
						JOptionPane.ERROR_MESSAGE);
			else {
				JOptionPane.showMessageDialog(null, "Grupo creado correctamente", "Conseguido",
						JOptionPane.PLAIN_MESSAGE);
			}
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, "Necesitas escribir al menos el nombre", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void abrirVentanaCambioImagen() {
		String nombre = nombreField.getText();
		if (!nombre.isEmpty()) {
			VentanaCambioImagen ventanaCambioImage = new VentanaCambioImagen(this);
			ventanaCambioImage.setVisible(true);
		} else
			JOptionPane.showMessageDialog(null, "Necesitas escribir al menos el nombre", "Error",
					JOptionPane.ERROR_MESSAGE);
	}

	public void setIcon(ImageIcon imageIcon, URL url) {
		if (url != null) {
			this.url = url;
			imageIcon = new ImageIcon(url);
		} else if (destinationFile != null) {
			String path = destinationFile.getAbsolutePath();
			imageIcon = new ImageIcon(path);
		}
		imagen.setIcon(ControladorAppChat.getInstancia().getScaledImage(imageIcon, DEFAUL_HEIGHT_AND_WIDTH));
	}

	@Override
	public void setDestinationFile(File d) {
		destinationFile = d;
	}

	@Override
	public String getTelefono() {
		String nombre = nombreField.getText();
		return nombre + "-" + ControladorAppChat.getInstancia().getTelefonoUsuarioActual();
	}

	public String getImagen() {
		if (destinationFile != null) {
			return destinationFile.getAbsolutePath();
		} else if (url != null) {
			return url.toString();
		}
		return null;
	}

	@Override
	public void dispose() {
		if (ventanaContactos != null) {
			ventanaContactos.setVisible(true);
			ventanaContactos.refreshContacts();
		}
		super.dispose();
	}
}
