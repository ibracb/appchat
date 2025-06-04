package umu.tds.appchat.windows.vista;

import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;

public interface VentanaCambiaImagenes{
	
	public String getTelefono();
	
	public void setIcon(ImageIcon i, URL url);
	
	public void setDestinationFile(File d);

}
