package umu.tds.appchat.windows.vista;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import umu.tds.appchat.controllers.ControladorAppChat;
import umu.tds.appchat.models.Mensaje;
import umu.tds.appchat.windows.components.MensajeCellRenderer;

public class VentanaBusqueda extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField textField;
	private JTextField telefonoField;
	private JTextField nameFile;
	private JList<Mensaje> list;
	private JScrollPane scrollPane;

	public VentanaBusqueda() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		ImageIcon iconoFlecha = new ImageIcon(getClass().getResource("/flecha-hacia-atras.png"));
		Image imagenFlecha = iconoFlecha.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		JButton btnFlecha = new JButton(new ImageIcon(imagenFlecha));
		btnFlecha.addActionListener(e -> dispose());
		btnFlecha.setBorder(null);
		GridBagConstraints gbc_btnFlecha = new GridBagConstraints();
		gbc_btnFlecha.insets = new Insets(0, 0, 5, 5);
		gbc_btnFlecha.gridx = 0;
		gbc_btnFlecha.gridy = 0;
		panel.add(btnFlecha, gbc_btnFlecha);

		// Etiquetas y campos de texto
		JLabel lblBusqueda = new JLabel("Texto");
		GridBagConstraints gbc_lblBusqueda = new GridBagConstraints();
		gbc_lblBusqueda.insets = new Insets(0, 0, 5, 5);
		gbc_lblBusqueda.anchor = GridBagConstraints.EAST;
		gbc_lblBusqueda.gridx = 0;
		gbc_lblBusqueda.gridy = 1;
		panel.add(lblBusqueda, gbc_lblBusqueda);
		
		JButton btnNewButton = new JButton("Buscar");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 3;
		btnNewButton.addActionListener(e -> buscarMensajes());
		panel.add(btnNewButton, gbc_btnNewButton);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 3;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		panel.add(textField, gbc_textField);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnNewButton.doClick(); // Simula un clic en el botón
				}
			}
		});
		textField.setColumns(10);

		JLabel lblFiltro1 = new JLabel("Telefono");
		GridBagConstraints gbc_lblFiltro1 = new GridBagConstraints();
		gbc_lblFiltro1.insets = new Insets(0, 0, 5, 5);
		gbc_lblFiltro1.anchor = GridBagConstraints.EAST;
		gbc_lblFiltro1.gridx = 0;
		gbc_lblFiltro1.gridy = 2;
		panel.add(lblFiltro1, gbc_lblFiltro1);

		telefonoField = new JTextField();
		GridBagConstraints gbc_telefonoField = new GridBagConstraints();
		gbc_telefonoField.insets = new Insets(0, 0, 5, 5);
		gbc_telefonoField.fill = GridBagConstraints.HORIZONTAL;
		gbc_telefonoField.gridx = 1;
		gbc_telefonoField.gridy = 2;
		panel.add(telefonoField, gbc_telefonoField);
		telefonoField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnNewButton.doClick(); // Simula un clic en el botón
				}
			}
		});
		telefonoField.setColumns(10);

		JLabel lblFiltro2 = new JLabel("Nombre");
		lblFiltro2.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblFiltro2 = new GridBagConstraints();
		gbc_lblFiltro2.insets = new Insets(0, 0, 5, 5);
		gbc_lblFiltro2.anchor = GridBagConstraints.EAST;
		gbc_lblFiltro2.gridx = 2;
		gbc_lblFiltro2.gridy = 2;
		panel.add(lblFiltro2, gbc_lblFiltro2);

		nameFile = new JTextField();
		GridBagConstraints gbc_nameFile = new GridBagConstraints();
		gbc_nameFile.insets = new Insets(0, 0, 5, 5);
		gbc_nameFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameFile.gridx = 3;
		gbc_nameFile.gridy = 2;
		panel.add(nameFile, gbc_nameFile);
		nameFile.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnNewButton.doClick(); // Simula un clic en el botón
				}
			}
		});
		nameFile.setColumns(10);
		list = new JList<>();
		list.setCellRenderer(new MensajeCellRenderer()); // Opcional, para personalizar
		scrollPane = new JScrollPane(list);
		contentPane.add(scrollPane, BorderLayout.CENTER);

	}

	private void buscarMensajes() {
		String text = textField.getText();
		String telefono = telefonoField.getText();
		String nombre = nameFile.getText();
		List<Mensaje> lista = ControladorAppChat.getInstancia().buscarMensaje(text, telefono, nombre);

		list.setModel(new AbstractListModel<Mensaje>() {
			private static final long serialVersionUID = 1L;

			@Override
			public int getSize() {
				return lista.size();
			}

			@Override
			public Mensaje getElementAt(int index) {
				return lista.get(index);
			}
		});

		scrollPane.revalidate();
		scrollPane.repaint();
	}
}