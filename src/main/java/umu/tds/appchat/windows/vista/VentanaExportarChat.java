package umu.tds.appchat.windows.vista;

import javax.swing.*;

import umu.tds.appchat.controllers.ControladorAppChat;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

public class VentanaExportarChat {

	private JComboBox<String> comboBoxContactos;

	public VentanaExportarChat(List<String> contactos) {
		// Crear el JComboBox con los contactos
		comboBoxContactos = new JComboBox<>(contactos.toArray(new String[0]));
	}

	public void mostrarVentana() {
		// Crear una ventana emergente para seleccionar el contacto
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("Selecciona un contacto para exportar la conversación:"), BorderLayout.NORTH);
		panel.add(comboBoxContactos, BorderLayout.CENTER);

		// Botón de exportación
		JButton exportButton = new JButton("Exportar Conversación");
		exportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String contactoSeleccionado = (String) comboBoxContactos.getSelectedItem();
				if (contactoSeleccionado != null) {
					abrirSeleccionArchivo(contactoSeleccionado);
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, selecciona un contacto.");
				}
			}
		});
		panel.add(exportButton, BorderLayout.SOUTH);

		// Mostrar el cuadro de diálogo
		JOptionPane.showOptionDialog(null, panel, "Seleccionar Contacto", JOptionPane.DEFAULT_OPTION, -1, null,
				new Object[] {}, null);
	}

	private void abrirSeleccionArchivo(String contactoSeleccionado) {
		// Crear un JFileChooser para que el usuario seleccione la ruta para guardar el
		// archivo
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Selecciona la ubicación para guardar el PDF");
		fileChooser.setSelectedFile(new File(contactoSeleccionado + "_conversacion.pdf"));

		// Filtrar para que solo muestre archivos .pdf
		fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos PDF", "pdf"));

		int userSelection = fileChooser.showSaveDialog(null);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			// Obtener la ruta seleccionada
			File archivoSeleccionado = fileChooser.getSelectedFile();
			// Llamar al método de exportación pasando la ruta seleccionada
			boolean result = ControladorAppChat.getInstancia().exportarConversacionPDF(contactoSeleccionado,
					archivoSeleccionado.getAbsolutePath());

			if (result) {
				System.out.println("PDF exportado exitosamente.");
			} else {
				System.out.println("Hubo un error al exportar el PDF.");
			}
		} else {
			// Si el usuario cancela la selección
			System.out.println("Selección de archivo cancelada.");
		}
	}
}
