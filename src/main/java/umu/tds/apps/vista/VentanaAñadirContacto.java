package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.Font;

public class VentanaAñadirContacto extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNombre;
	private JTextField textFieldTelefono;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaAñadirContacto dialog = new VentanaAñadirContacto();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaAñadirContacto() {
		setTitle("Añadir contacto");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(new Color(242, 216, 245));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel labelFoto = new JLabel("");
			GridBagConstraints gbc_labelFoto = new GridBagConstraints();
			gbc_labelFoto.insets = new Insets(0, 0, 5, 5);
			gbc_labelFoto.gridx = 2;
			gbc_labelFoto.gridy = 3;
			contentPanel.add(labelFoto, gbc_labelFoto);
		}
		{
			JLabel labelMensaje = new JLabel("Introduzca nombre y teléfono al contacto:");
			labelMensaje.setFont(new Font("Georgia", Font.BOLD, 12));
			GridBagConstraints gbc_labelMensaje = new GridBagConstraints();
			gbc_labelMensaje.insets = new Insets(0, 0, 5, 5);
			gbc_labelMensaje.gridx = 3;
			gbc_labelMensaje.gridy = 3;
			contentPanel.add(labelMensaje, gbc_labelMensaje);
		}
		{
			JLabel labelNombre = new JLabel("nombre");
			labelNombre.setFont(new Font("Georgia", Font.BOLD, 12));
			GridBagConstraints gbc_labelNombre = new GridBagConstraints();
			gbc_labelNombre.anchor = GridBagConstraints.EAST;
			gbc_labelNombre.insets = new Insets(0, 0, 5, 5);
			gbc_labelNombre.gridx = 2;
			gbc_labelNombre.gridy = 5;
			contentPanel.add(labelNombre, gbc_labelNombre);
		}
		{
			textFieldNombre = new JTextField();
			GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
			gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldNombre.gridx = 3;
			gbc_textFieldNombre.gridy = 5;
			contentPanel.add(textFieldNombre, gbc_textFieldNombre);
			textFieldNombre.setColumns(32);
		}
		{
			JLabel labelTelefono = new JLabel("teléfono");
			labelTelefono.setFont(new Font("Georgia", Font.BOLD, 12));
			GridBagConstraints gbc_labelTelefono = new GridBagConstraints();
			gbc_labelTelefono.anchor = GridBagConstraints.EAST;
			gbc_labelTelefono.insets = new Insets(0, 0, 5, 5);
			gbc_labelTelefono.gridx = 2;
			gbc_labelTelefono.gridy = 7;
			contentPanel.add(labelTelefono, gbc_labelTelefono);
		}
		{
			textFieldTelefono = new JTextField();
			GridBagConstraints gbc_textFieldTelefono = new GridBagConstraints();
			gbc_textFieldTelefono.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldTelefono.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldTelefono.gridx = 3;
			gbc_textFieldTelefono.gridy = 7;
			contentPanel.add(textFieldTelefono, gbc_textFieldTelefono);
			textFieldTelefono.setColumns(32);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			buttonPane.setBackground(new Color(242, 216, 245));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Aceptar");
				okButton.setFont(new Font("Georgia", Font.BOLD, 12));
				okButton.setActionCommand("Aceptar");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setFont(new Font("Georgia", Font.BOLD, 12));
				cancelButton.setActionCommand("Cancelar");
				buttonPane.add(cancelButton);
			}
		}
	}

}
