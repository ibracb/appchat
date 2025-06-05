package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import umu.tds.apps.dominio.Mensaje;

public class VentanaDetallesMensaje {

	private JDialog dialog;
	private Mensaje mensaje;

	public VentanaDetallesMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
		initialize();
	}

	private void initialize() {
		dialog = new JDialog();
		dialog.setTitle("Detalles del Mensaje");
		dialog.setModal(true);
		dialog.setSize(new Dimension(400, 250));
		dialog.setLocationRelativeTo(null);
		dialog.setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(new java.awt.GridLayout(0, 1));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setBackground(Color.WHITE);

		String texto = mensaje.getTexto().isEmpty() ? "(sin texto)" : mensaje.getTexto();
		String fechaStr = mensaje.getMomentoEnvio().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String emoticonoStr = mensaje.getEmoticono() == -1 ? "(ninguno)" : String.valueOf(mensaje.getEmoticono());
		String tipoStr = mensaje.getTipo().toString();

		panel.add(new JLabel("ID: " + mensaje.getId()));
		panel.add(new JLabel("Texto: " + texto));
		panel.add(new JLabel("Emoticono: " + emoticonoStr));
		panel.add(new JLabel("Tipo: " + tipoStr));
		panel.add(new JLabel("Fecha de envío: " + fechaStr));
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(e -> dialog.dispose());

		dialog.getContentPane().add(panel, BorderLayout.CENTER);
		dialog.getContentPane().add(btnCerrar, BorderLayout.SOUTH);
	}

	public void mostrar() {
		dialog.setVisible(true);
	}
}
