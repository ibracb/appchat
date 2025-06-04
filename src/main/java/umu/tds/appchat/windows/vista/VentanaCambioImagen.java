package umu.tds.appchat.windows.vista;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

public class VentanaCambioImagen extends JFrame {

	private static final long serialVersionUID = 1L;
	private VentanaCambiaImagenes v;
	private JTextField textFieldURL;

	public VentanaCambioImagen(VentanaCambiaImagenes v) {

		this.setBounds(100, 100, 700, 300);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// cierra la ventana cuando cancelas

		this.v = v;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 20, 20, 40, 10, 0, 10, 40, 20, 20, 0 };
		gridBagLayout.rowHeights = new int[] { 30, 0, 0, 0, 30, 0, 0, 30, 0, 20, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblURL = new JLabel(
				"Si quieres cambiar tu imagen por URL, escribe el URL en el campo de abajo y pulsa el boton");
		GridBagConstraints gbc_lblURL = new GridBagConstraints();
		gbc_lblURL.gridwidth = 5;
		gbc_lblURL.insets = new Insets(0, 0, 5, 5);
		gbc_lblURL.gridx = 2;
		gbc_lblURL.gridy = 2;
		getContentPane().add(lblURL, gbc_lblURL);

		JButton btnURL = new JButton("URL");
		GridBagConstraints gbc_btnURL = new GridBagConstraints();
		gbc_btnURL.insets = new Insets(0, 0, 5, 5);
		gbc_btnURL.gridx = 2;
		gbc_btnURL.gridy = 3;
		btnURL.addActionListener(e -> cambiarImagenConURL());
		getContentPane().add(btnURL, gbc_btnURL);

		textFieldURL = new JTextField();
		GridBagConstraints gbc_textFieldURL = new GridBagConstraints();
		gbc_textFieldURL.gridwidth = 3;
		gbc_textFieldURL.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldURL.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldURL.gridx = 4;
		gbc_textFieldURL.gridy = 3;
		getContentPane().add(textFieldURL, gbc_textFieldURL);
		textFieldURL.setColumns(10);

		JLabel lblArchivo = new JLabel("Si quieres cambiar la imagen por una en tus archivos pulsa el boton de abajo");
		GridBagConstraints gbc_lblArchivo = new GridBagConstraints();
		gbc_lblArchivo.gridwidth = 5;
		gbc_lblArchivo.insets = new Insets(0, 0, 5, 5);
		gbc_lblArchivo.gridx = 2;
		gbc_lblArchivo.gridy = 5;
		getContentPane().add(lblArchivo, gbc_lblArchivo);

		JButton btnArchivos = new JButton("Archivos");
		GridBagConstraints gbc_btnArchivos = new GridBagConstraints();
		gbc_btnArchivos.insets = new Insets(0, 0, 5, 5);
		gbc_btnArchivos.gridx = 4;
		gbc_btnArchivos.gridy = 6;
		btnArchivos.addActionListener(e -> cambiarImagen());
		getContentPane().add(btnArchivos, gbc_btnArchivos);

		JButton btnVolver = new JButton("Volver");
		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.insets = new Insets(0, 0, 5, 5);
		gbc_btnVolver.gridx = 4;
		gbc_btnVolver.gridy = 8;
		btnVolver.addActionListener(e -> dispose());
		getContentPane().add(btnVolver, gbc_btnVolver);
	}

	private void cambiarImagen() {
		// Abrir diálogo para seleccionar archivo
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Seleccionar Imagen");
		fileChooser.setFileFilter(
				new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "png", "jpg", "jpeg", "gif"));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			// Validar si el archivo seleccionado es una imagen
			if (!selectedFile.getName().toLowerCase().matches(".*\\.(png|jpg|jpeg|gif)$")) {
				JOptionPane.showMessageDialog(this, "Seleccione un archivo de imagen válido.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				BufferedImage originalImage = ImageIO.read(selectedFile);
				String telefono = v.getTelefono();
				File destinationFile = new File("src/main/resources/imagenPerfil" + telefono + ".png");
				v.setDestinationFile(destinationFile);
				ImageIO.write(originalImage, "png", destinationFile);
				v.setIcon(null, null);
				JOptionPane.showMessageDialog(this, "Imagen cambiada y guardada correctamente");
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Error al procesar la imagen: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			dispose();
		}
	}

	private void cambiarImagenConURL() {
		try {
			URL imageURL = new URL(textFieldURL.getText());
			BufferedImage image = ImageIO.read(imageURL);
			ImageIcon imageIcon = new ImageIcon(image); // Tamaño ajustado
			v.setIcon(imageIcon, imageURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		dispose();
	}

}