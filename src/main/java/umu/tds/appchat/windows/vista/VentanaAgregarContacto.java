package umu.tds.appchat.windows.vista;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import umu.tds.appchat.controllers.ControladorAppChat;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.Dimension;

public class VentanaAgregarContacto extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtContacto;
	private JTextField txtTelefono;
	private VentanaContactos ventanaContactos;

	/**
	 * Create the dialog.
	 */
	public VentanaAgregarContacto(VentanaContactos v) {
		ventanaContactos = v;
		setTitle("alerta");
		setBounds(100, 100, 450, 199);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		JButton btnNewButton = new JButton("cancelar");
		// Controlador para el botón "cancelar"
		btnNewButton.addActionListener(e -> dispose()); // Cierra la ventana
		panel.add(btnNewButton);

		Component horizontalGlue = Box.createHorizontalGlue();
		panel.add(horizontalGlue);

		JButton btnNewButton_1 = new JButton("aceptar");
		btnNewButton_1.addActionListener(e -> agregarContacto());
		panel.add(btnNewButton_1);

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalGlue_1.setPreferredSize(new Dimension(20, 0));
		panel.add(horizontalGlue_1);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 20, 0, 0, 20, 0 };
		gbl_panel_1.rowHeights = new int[] { 20, 0, 0, 0, 20, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblNewLabel_1 = new JLabel("");

		ImageIcon iconoAlerta = new ImageIcon("/alerta.png");
		Image alertaEscalado = iconoAlerta.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		iconoAlerta = new ImageIcon(alertaEscalado);
		lblNewLabel_1.setIcon(iconoAlerta);

		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;

		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("introduzca el nombre del contacto y su teléfono");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 1;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);

		txtContacto = new JTextField();
		txtContacto.setText("contacto");
		GridBagConstraints gbc_txtContacto = new GridBagConstraints();
		gbc_txtContacto.insets = new Insets(0, 0, 5, 5);
		gbc_txtContacto.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtContacto.gridx = 2;
		gbc_txtContacto.gridy = 2;
		txtContacto.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		        	btnNewButton_1.doClick(); // Simula un clic en el botón
		        }
		    }
		});
		panel_1.add(txtContacto, gbc_txtContacto);
		txtContacto.setColumns(10);

		txtTelefono = new JTextField();
		txtTelefono.setText("telefono");
		GridBagConstraints gbc_txtTelefono = new GridBagConstraints();
		gbc_txtTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelefono.gridx = 2;
		gbc_txtTelefono.gridy = 3;
		txtTelefono.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		        	btnNewButton_1.doClick(); // Simula un clic en el botón
		        }
		    }
		});
		panel_1.add(txtTelefono, gbc_txtTelefono);
		txtTelefono.setColumns(10);

	}

	private void agregarContacto() {
		String nombre = txtContacto.getText();
		String telefono = txtTelefono.getText();
		boolean result = ControladorAppChat.getInstancia().agregarContacto(nombre, telefono);
		if (!result)
			JOptionPane.showMessageDialog(null, "No ha sido posible añadir el contacto", "Error",
					JOptionPane.ERROR_MESSAGE);
		else {
			JOptionPane.showMessageDialog(null, "Contacto agregado correctamente", "Conseguido",
					JOptionPane.PLAIN_MESSAGE);
		}
		dispose();

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
