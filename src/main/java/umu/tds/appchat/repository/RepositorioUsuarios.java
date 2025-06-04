package umu.tds.appchat.repository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import umu.tds.appchat.models.Usuario;
import umu.tds.appchat.persistencia.DAOException;
import umu.tds.appchat.persistencia.FactoriaDAO;

import umu.tds.appchat.persistencia.IAdaptadorUsuarioDAO;

public class RepositorioUsuarios {

	private Map<String, Usuario> usuarios;
	private static RepositorioUsuarios unicaInstancia;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private FactoriaDAO factoriaDAO;

	private RepositorioUsuarios() {
		try {
			factoriaDAO = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoriaDAO.getUsuarioDAO();
		this.usuarios = adaptadorUsuario.recuperarTodosUsuarios();
	}

	public static RepositorioUsuarios getUnicaIntacia() {
		if (unicaInstancia == null) {
			unicaInstancia = new RepositorioUsuarios();
		}
		return unicaInstancia;
	}

	public Optional<Usuario> registrarUsuario(String nombre, String apellidos, String telefono, String contraseña,
			LocalDate fechaNaci, String saludo) {
		Usuario user = new Usuario(nombre, apellidos, telefono, contraseña, fechaNaci, saludo);
		if (usuarios.get(telefono) != null) {
			return Optional.empty(); // Devuelve un Optional vacío si ya existe el usuario o si el registro falla
		}
		usuarios.put(telefono, user);
		return Optional.of(user); // Devuelve el usuario envuelto en Optional
	}

	public Usuario comprobarUsuario(String tlf, String passwd) {
		Usuario user = usuarios.get(tlf);
		return user != null && user.isPassword(passwd) ? user : null;
	}

	public Usuario getUser(String telefono) {
		return usuarios.get(telefono);
	}

	public void updateUser(String telefono, Usuario user) {
		usuarios.put(telefono, user);
	}

}
