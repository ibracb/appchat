package umu.tds.appchat.windows.components;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;
import umu.tds.appchat.controllers.ControladorAppChat;
import umu.tds.appchat.models.Contacto;
import umu.tds.appchat.models.ContactoIndividual;
import umu.tds.appchat.models.Grupo;

public class ContactCellRenderer extends JPanel implements ListCellRenderer<Contacto> {

	private static final long serialVersionUID = 1L;
	private static final int DIMENSIONES_POR_DEFECTO = 60;

	private JLabel nameLabel;
	private JLabel phoneLabel;
	private JLabel imageLabel;

// Imagen por defecto para contactos
	private static final ImageIcon DEFAULT_ICON = new ImageIcon(new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB));

	public ContactCellRenderer() {
		setLayout(new BorderLayout(5, 5));

		nameLabel = new JLabel();
		phoneLabel = new JLabel();
		imageLabel = new JLabel(DEFAULT_ICON);

		add(imageLabel, BorderLayout.WEST);
		add(nameLabel, BorderLayout.NORTH);
		add(phoneLabel, BorderLayout.SOUTH);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Contacto> list, Contacto contacto, int index,
			boolean isSelected, boolean cellHasFocus) {

		nameLabel.setText(contacto.getNombre());
		try {
			if (contacto instanceof ContactoIndividual) {
				ContactoIndividual ci = (ContactoIndividual) contacto;  
				ContactoIndividual contactoIndividual = (ContactoIndividual) contacto;
				phoneLabel.setText(ci.getTelefono());
				imageLabel.setIcon(ControladorAppChat.getInstancia().obtenerImagen(contactoIndividual,DIMENSIONES_POR_DEFECTO));
			} else {
				Grupo grupo = (Grupo) contacto;
				phoneLabel.setText(grupo.getNumContactos() + " miembros");
				imageLabel.setIcon(ControladorAppChat.getInstancia().obtenerImagen(grupo,DIMENSIONES_POR_DEFECTO));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	    if (isSelected) {
	        setBackground(new Color(169, 169, 169)); // Color gris cuando el ítem está seleccionado
	        setForeground(list.getSelectionForeground()); // El color del texto cuando se selecciona
	    } else {
	        setBackground(list.getBackground()); // El color de fondo cuando no está seleccionado
	        setForeground(list.getForeground()); // El color del texto cuando no está seleccionado
	    }

		return this;
	}

}