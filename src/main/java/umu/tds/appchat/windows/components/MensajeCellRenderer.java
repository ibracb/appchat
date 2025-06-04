package umu.tds.appchat.windows.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import umu.tds.appchat.controllers.ControladorAppChat;
import umu.tds.appchat.models.Mensaje;
import java.time.format.DateTimeFormatter;
import javax.swing.SwingConstants;

import com.itextpdf.text.Image;

import tds.BubbleText;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MensajeCellRenderer extends JPanel implements ListCellRenderer<Mensaje> {
	private static final long serialVersionUID = 1L;
	private static final int DEFAUL_HEIGHT_AND_WIDTH_IMAGE = 60;
	private static final int DEFAUL_HEIGHT_AND_WIDTH_EMOJI = 20;

	private JLabel imageLabel;
	private JLabel messageLabel;
	private JLabel timeLabel;
	private DateTimeFormatter formato;
	private JPanel panel;
	private JLabel nameLabel;

	public MensajeCellRenderer() {
		setLayout(new BorderLayout(5, 5));
		messageLabel = new JLabel();
		imageLabel = new JLabel();
		timeLabel = new JLabel();
		add(imageLabel, BorderLayout.CENTER);
		add(messageLabel, BorderLayout.SOUTH);
		add(timeLabel, BorderLayout.EAST);

		panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 153, 1, 10, 0 };
		gbl_panel.rowHeights = new int[] { 23, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		nameLabel = new JLabel();
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.WEST;
		gbc_nameLabel.insets = new Insets(0, 0, 0, 5);
		gbc_nameLabel.gridx = 0;
		gbc_nameLabel.gridy = 0;
		panel.add(nameLabel, gbc_nameLabel);

		// Crear el botón solo una vez
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 0;

		formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Mensaje> list, Mensaje mensaje, int index,
			boolean isSelected, boolean cellHasFocus) {
		messageLabel.setIcon(null);
		messageLabel.setText("");
		nameLabel.setText(ControladorAppChat.getInstancia().getNombreOrTelefono(mensaje));
		if (mensaje.isAnEmoji()) {
			ImageIcon emojiIcon = BubbleText.getEmoji(mensaje.getEmoji());
			emojiIcon = ControladorAppChat.getInstancia().getScaledImage(emojiIcon, DEFAUL_HEIGHT_AND_WIDTH_EMOJI);
			messageLabel.setIcon(emojiIcon);
		} else
			messageLabel.setText(mensaje.getTexto());
		timeLabel.setText(mensaje.getFechaHora().format(formato));
		try {
			imageLabel.setIcon(ControladorAppChat.getInstancia().obtenerImagen(mensaje, DEFAUL_HEIGHT_AND_WIDTH_IMAGE));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (isSelected) {
			setBackground(new Color(169, 169, 169)); // Color gris cuando el ítem está seleccionado
			setForeground(list.getSelectionForeground()); // El color del texto cuando se selecciona
			panel.setBackground(new Color(169, 169, 169)); // Color gris cuando el ítem está seleccionado
			panel.setForeground(list.getSelectionForeground()); // Color gris cuando el ítem está seleccionado

		} else {
			setBackground(list.getBackground()); // El color de fondo cuando no está seleccionado
			setForeground(list.getForeground()); // El color del texto cuando no está seleccionado
			panel.setBackground(list.getBackground()); // Color gris cuando el ítem está seleccionado
			panel.setForeground(list.getForeground()); // Color gris cuando el ítem está seleccionado

		}

		return this;
	}

}
