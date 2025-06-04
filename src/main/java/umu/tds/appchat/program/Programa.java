package umu.tds.appchat.program;

import java.time.LocalDate;
import javax.swing.UIManager;
import com.jtattoo.plaf.noire.NoireLookAndFeel;
import umu.tds.appchat.controllers.ControladorAppChat;
import umu.tds.appchat.windows.vista.VentanaLogin;

public class Programa {
    public static void main(String[] args) {
        try {
            // Establecer el look-and-feel de JTattoo
            UIManager.setLookAndFeel(new NoireLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

 	// Iniciar la interfaz gráfica
		VentanaLogin vl = new VentanaLogin();
		vl.setVisible(true);
    }
}

