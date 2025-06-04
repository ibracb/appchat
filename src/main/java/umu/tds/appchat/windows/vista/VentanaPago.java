package umu.tds.appchat.windows.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import umu.tds.appchat.controllers.ControladorAppChat;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;

public class VentanaPago extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField numeroTarjeta;
	private JTextField nombreTitular;
	private JTextField fechaVencimiento;
	private JTextField codigoSeguridad;
	private JLabel tituloVentana;
	private JButton btnNewButton_1;
	private JButton Cancelar;

	public VentanaPago() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 20, 0, 0, 20, 0 };
		gbl_contentPane.rowHeights = new int[] { 38, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		SwingUtilities.updateComponentTreeUI(this);
		this.revalidate();
		this.repaint();

		contentPane.setLayout(gbl_contentPane);

		tituloVentana = new JLabel("Introduzca su tarjeta de Credito");
		tituloVentana.setFont(new Font("Verdana", Font.PLAIN, 14));
		GridBagConstraints gbc_tituloVentana = new GridBagConstraints();
		gbc_tituloVentana.gridwidth = 2;
		gbc_tituloVentana.insets = new Insets(0, 0, 5, 5);
		gbc_tituloVentana.gridx = 1;
		gbc_tituloVentana.gridy = 0;
		contentPane.add(tituloVentana, gbc_tituloVentana);

		numeroTarjeta = new JTextField();
		numeroTarjeta.setText("Número tarjeta");
		GridBagConstraints gbc_numeroTarjeta = new GridBagConstraints();
		gbc_numeroTarjeta.gridwidth = 2;
		gbc_numeroTarjeta.insets = new Insets(0, 0, 5, 5);
		gbc_numeroTarjeta.fill = GridBagConstraints.HORIZONTAL;
		gbc_numeroTarjeta.gridx = 1;
		gbc_numeroTarjeta.gridy = 1;
		contentPane.add(numeroTarjeta, gbc_numeroTarjeta);
		numeroTarjeta.setColumns(10);

		nombreTitular = new JTextField();
		nombreTitular.setText("Nombre Titular");
		GridBagConstraints gbc_nombreTitular = new GridBagConstraints();
		gbc_nombreTitular.gridwidth = 2;
		gbc_nombreTitular.insets = new Insets(0, 0, 5, 5);
		gbc_nombreTitular.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreTitular.gridx = 1;
		gbc_nombreTitular.gridy = 2;
		contentPane.add(nombreTitular, gbc_nombreTitular);
		nombreTitular.setColumns(10);

		fechaVencimiento = new JTextField();
		fechaVencimiento.setText("Fecha de vencimiento");
		GridBagConstraints gbc_fechaVencimiento = new GridBagConstraints();
		gbc_fechaVencimiento.insets = new Insets(0, 0, 5, 5);
		gbc_fechaVencimiento.fill = GridBagConstraints.HORIZONTAL;
		gbc_fechaVencimiento.gridx = 1;
		gbc_fechaVencimiento.gridy = 3;
		contentPane.add(fechaVencimiento, gbc_fechaVencimiento);
		fechaVencimiento.setColumns(10);

		codigoSeguridad = new JTextField();
		codigoSeguridad.setText("codigo de seguridad");
		GridBagConstraints gbc_codigoSeguridad = new GridBagConstraints();
		gbc_codigoSeguridad.insets = new Insets(0, 0, 5, 5);
		gbc_codigoSeguridad.fill = GridBagConstraints.HORIZONTAL;
		gbc_codigoSeguridad.gridx = 2;
		gbc_codigoSeguridad.gridy = 3;
		contentPane.add(codigoSeguridad, gbc_codigoSeguridad);
		codigoSeguridad.setColumns(10);

		btnNewButton_1 = new JButton("Confirmar");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 7;
		btnNewButton_1.addActionListener(e -> confirmarPago());
		
		Cancelar = new JButton("Cancelar");
		Cancelar.addActionListener(e -> dispose());
		Cancelar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_Cancelar = new GridBagConstraints();
		gbc_Cancelar.insets = new Insets(0, 0, 5, 5);
		gbc_Cancelar.gridx = 1;
		gbc_Cancelar.gridy = 7;
		contentPane.add(Cancelar, gbc_Cancelar);
		contentPane.add(btnNewButton_1, gbc_btnNewButton_1);
	}

	private void confirmarPago() {
		String tarjeta, cvv, nombre, fecha;
		tarjeta = numeroTarjeta.getText();
		cvv = codigoSeguridad.getText();
		nombre = nombreTitular.getText();
		fecha = fechaVencimiento.getText();

		if (tarjeta == null || cvv == null || nombre == null || fecha == null) {
			JOptionPane.showMessageDialog(null, "Faltan campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (!nombre.equals(ControladorAppChat.getInstancia().getUsuarioActual().getNombre())) {
			JOptionPane.showMessageDialog(null,
					"No eres el titular de la tarjeta", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!tarjeta.matches("(\\d{4}-){3}\\d{4}")) {
			JOptionPane.showMessageDialog(null,
					"Número de tarjeta inválido. Debe tener el formato XXXX-XXXX-XXXX-XXXX.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			LocalDate fechaActual = LocalDate.now();
			LocalDate fechaIngresada = LocalDate.parse("01/" + fecha, DateTimeFormatter.ofPattern("dd/MM/yy"));

			if (!fechaIngresada.isAfter(fechaActual)) {
				JOptionPane.showMessageDialog(null,
						"Fecha de expiración inválida. Debe ser posterior a la fecha actual.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		} catch (DateTimeParseException e) {
			System.out.println("Formato de fecha inválido. Debe estar en el formato MM/yy.");
			return;
		}

		if (!cvv.matches("\\d{3}")) {
			JOptionPane.showMessageDialog(null, "CVV inválido. Debe tener exactamente 3 dígitos.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(null, "Pago realizado correctamente", "Conseguido", JOptionPane.PLAIN_MESSAGE);
		ControladorAppChat.getInstancia().confirmarPago();
		dispose();
	}

}
