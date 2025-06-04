package umu.tds.appchat.windows.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import umu.tds.appchat.controllers.ControladorAppChat;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;

public class VentanaSuscripcion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public VentanaSuscripcion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 64, 245, 0, 45, 0};
		gbl_contentPane.rowHeights = new int[]{0, 33, 0, 0, 19, 0, 14, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel titulo = new JLabel("Suscripcion anual");
		titulo.setForeground(Color.LIGHT_GRAY);
		titulo.setBackground(new Color(255, 255, 255));
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setFont(new Font("Microsoft Tai Le", Font.BOLD, 30));
		GridBagConstraints gbc_titulo = new GridBagConstraints();
		gbc_titulo.insets = new Insets(0, 0, 5, 5);
		gbc_titulo.gridx = 2;
		gbc_titulo.gridy = 1;
		contentPane.add(titulo, gbc_titulo);
		
		double precioFinal = ControladorAppChat.getInstancia().obtenerPrecioFinal();
		JLabel precioSuscripcion = new JLabel(String.format("%.2f", precioFinal) + "€");
		precioSuscripcion.setForeground(Color.LIGHT_GRAY);
		precioSuscripcion.setBackground(new Color(255, 255, 255));
		precioSuscripcion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_precioSuscripcion = new GridBagConstraints();
		gbc_precioSuscripcion.insets = new Insets(0, 0, 5, 5);
		gbc_precioSuscripcion.gridx = 2;
		gbc_precioSuscripcion.gridy = 2;
		contentPane.add(precioSuscripcion, gbc_precioSuscripcion);
		
		JButton bttnSuscribir = new JButton("Hazte premiun");
		bttnSuscribir.addActionListener(e -> abrirVentanaPago());

		GridBagConstraints gbc_bttnSuscribir = new GridBagConstraints();
		gbc_bttnSuscribir.insets = new Insets(0, 0, 5, 5);
		gbc_bttnSuscribir.gridx = 2;
		gbc_bttnSuscribir.gridy = 3;
		contentPane.add(bttnSuscribir, gbc_bttnSuscribir);
		
		JLabel ventaja1 = new JLabel("<html><ul><li>Podras exportar tus chats</li><li>Podras personarlizar tus chats</li></ul></html>");
		ventaja1.setForeground(Color.LIGHT_GRAY);
		ventaja1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ventaja1.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_ventaja1 = new GridBagConstraints();
		gbc_ventaja1.gridwidth = 2;
		gbc_ventaja1.insets = new Insets(0, 0, 5, 5);
		gbc_ventaja1.gridx = 1;
		gbc_ventaja1.gridy = 5;
		contentPane.add(ventaja1, gbc_ventaja1);
		
		JButton bttnVolver = new JButton("Quizas mas tarde");
		bttnVolver.addActionListener( e -> dispose());
		GridBagConstraints gbc_bttnVolver = new GridBagConstraints();
		gbc_bttnVolver.insets = new Insets(0, 0, 0, 5);
		gbc_bttnVolver.gridx = 2;
		gbc_bttnVolver.gridy = 6;
		contentPane.add(bttnVolver, gbc_bttnVolver);
	}


	private void abrirVentanaPago() {
		VentanaPago ventanaPago = new VentanaPago();
		ventanaPago.setVisible(true);
		this.setVisible(false);
	}

}
