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
import java.awt.Font;

public class VentanaTelefonoNoExiste extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaTelefonoNoExiste dialog = new VentanaTelefonoNoExiste();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaTelefonoNoExiste() {
		setTitle("Teléfono inexistente!!");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(new Color(242, 216, 245));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel labelFoto = new JLabel("New label");
			GridBagConstraints gbc_labelFoto = new GridBagConstraints();
			gbc_labelFoto.insets = new Insets(0, 0, 0, 5);
			gbc_labelFoto.gridx = 3;
			gbc_labelFoto.gridy = 3;
			contentPanel.add(labelFoto, gbc_labelFoto);
		}
		{
			JLabel labelMensaje = new JLabel("¡El teléfono indicado no existe!");
			labelMensaje.setFont(new Font("Georgia", Font.BOLD, 12));
			GridBagConstraints gbc_labelMensaje = new GridBagConstraints();
			gbc_labelMensaje.insets = new Insets(0, 0, 0, 5);
			gbc_labelMensaje.gridx = 4;
			gbc_labelMensaje.gridy = 3;
			contentPanel.add(labelMensaje, gbc_labelMensaje);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			buttonPane.setBackground(new Color(242, 216, 245));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Georgia", Font.BOLD, 12));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
