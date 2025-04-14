package umu.tds.apps.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaTelefonoNoExiste extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel panelCentral = new JPanel();
	private JLabel labelFoto;
	private JLabel labelMensaje;
	private ImageIcon imagen;
	private GridBagLayout gbl_panelCentral;
	private GridBagConstraints gbc_labelFoto;
	private GridBagConstraints gbc_labelMensaje;
	private GridBagConstraints gbc_okButton;
	private JButton okButton;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			VentanaTelefonoNoExiste dialog = new VentanaTelefonoNoExiste();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public VentanaTelefonoNoExiste() {
		setBounds(100, 100, 451, 245);
		setTitle("Teléfono inexstente!!");
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\main\\resources\\imagenes\\iconoPestanas.PNG"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		panelCentral.setBackground(new Color(242, 216, 245));
		getContentPane().add(panelCentral, BorderLayout.CENTER);
		gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelCentral.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panelCentral.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelCentral.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelCentral.setLayout(gbl_panelCentral);
		
		labelFoto = new JLabel("");
		imagen = new ImageIcon(new ImageIcon("src\\main\\resources\\imagenes\\senalAdvertencia.png").getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
		labelFoto.setIcon(imagen);
		gbc_labelFoto = new GridBagConstraints();
		gbc_labelFoto.gridheight = 2;
		gbc_labelFoto.insets = new Insets(0, 0, 5, 5);
		gbc_labelFoto.gridx = 3;
		gbc_labelFoto.gridy = 1;
		panelCentral.add(labelFoto, gbc_labelFoto);
		
		labelMensaje = new JLabel("¡El teléfono indicado no existe!");
		labelMensaje.setFont(new Font("Georgia", Font.BOLD, 12));
		gbc_labelMensaje = new GridBagConstraints();
		gbc_labelMensaje.gridwidth = 2;
		gbc_labelMensaje.insets = new Insets(0, 0, 5, 5);
		gbc_labelMensaje.gridx = 4;
		gbc_labelMensaje.gridy = 1;
		panelCentral.add(labelMensaje, gbc_labelMensaje);
		

		okButton = new JButton("Aceptar");
		okButton.setFont(new Font("Georgia", Font.BOLD, 12));
		okButton.setActionCommand("Aceptar");
		gbc_okButton = new GridBagConstraints();
		gbc_okButton.anchor = GridBagConstraints.EAST;
		gbc_okButton.insets = new Insets(0, 0, 5, 5);
		gbc_okButton.gridx = 5;
		gbc_okButton.gridy = 2;
		panelCentral.add(okButton, gbc_okButton);
		okButton.addActionListener(this);
	}
	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		if (e.getSource() == okButton) {
			this.dispose();
		}
	}

}
