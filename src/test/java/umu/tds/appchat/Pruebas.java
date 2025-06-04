package umu.tds.appchat;

import java.time.LocalDate;
import umu.tds.appchat.controllers.ControladorAppChat;
import umu.tds.appchat.windows.vista.VentanaLogin;

public class Pruebas {

	public static void main(String[] args) {
		ControladorAppChat controlador = ControladorAppChat.getInstancia();

		// Crear usuarios
		controlador.registrarUsuario("admin", "Admin", "100", "passwd", LocalDate.now(), "");
		controlador.registrarUsuario("user1", "User", "101", "passwd", LocalDate.now(), "");
		controlador.registrarUsuario("user2", "User", "102", "passwd", LocalDate.now(), "");
		controlador.registrarUsuario("user3", "User", "103", "passwd", LocalDate.now(), "");
		controlador.registrarUsuario("user4", "User", "104", "passwd", LocalDate.now(), "");
		controlador.registrarUsuario("user5", "User", "105", "passwd", LocalDate.now(), "");
		controlador.registrarUsuario("user6", "User", "106", "passwd", LocalDate.now(), "");
		// Iniciar sesión como admin
		controlador.comprobarUsuario("100", "passwd");
		controlador.confirmarPago();
		controlador.logOut();
		// Iniciar la interfaz gráfica
		VentanaLogin vl = new VentanaLogin();
		vl.setVisible(true);
	}
}
