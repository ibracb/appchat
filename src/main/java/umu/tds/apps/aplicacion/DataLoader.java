package umu.tds.apps.aplicacion;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;
import java.util.stream.Collectors;

import umu.tds.apps.controlador.Controlador;
import umu.tds.apps.dominio.ContactoIndividual;
import umu.tds.apps.dominio.Grupo;
import umu.tds.apps.dominio.Usuario;
import umu.tds.apps.persistencia.ContactoIndividualDAO;
import umu.tds.apps.persistencia.DAOException;
import umu.tds.apps.persistencia.FactoriaDAO;
import umu.tds.apps.persistencia.GrupoDAO;
import umu.tds.apps.persistencia.UsuarioDAO;

public class DataLoader {

	public static void cargarDatosIniciales() {
		// Inicializar los adaptadores de acceso a datos
		UsuarioDAO usuarioDAO = null;
		ContactoIndividualDAO adaptadorContactoIndividual = null;
		UsuarioDAO adaptadorUsuario = null;
		GrupoDAO grupoDAO = null;

		try {
			usuarioDAO = FactoriaDAO.getInstance().getUsuarioDAO();
			adaptadorContactoIndividual = FactoriaDAO.getInstance().getContactoIndividualDAO();
			adaptadorUsuario = FactoriaDAO.getInstance().getUsuarioDAO();
			grupoDAO = FactoriaDAO.getInstance().getGrupoDAO();
		} catch (DAOException e) {
			e.printStackTrace();
			return;
		}

		Set<Usuario> usuarios = usuarioDAO.getAll();

		if (usuarios.isEmpty()) {
			// Crear usuarios
			/*
			 * CREDENCIALES PARA INICIAR SESIÓN EN LA CUENTA TELEFONO: 212121212 CONTRASEÑA:
			 * m
			 */
			Usuario maria = new Usuario("maria bm", LocalDate.of(2004, Month.JULY, 15), "m@um.es", "212121212", "m",
					"");
			/*
			 * CREDENCIALES PARA INICIAR SESIÓN EN LA CUENTA TELEFONO: 313131313 CONTRASEÑA:
			 * i
			 */
			Usuario ibra = new Usuario("ibra cb", LocalDate.of(2003, Month.MAY, 20), "i@um.es", "313131313", "i", "");
			/*
			 * CREDENCIALES PARA INICIAR SESIÓN EN LA CUENTA TELEFONO: 121212121 CONTRASEÑA:
			 * m
			 */
			Usuario maria2 = new Usuario("maria bm2", LocalDate.of(2005, Month.JULY, 15), "m@um.es", "121212121", "m",
					"");
			/*
			 * CREDENCIALES PARA INICIAR SESIÓN EN LA CUENTA TELEFONO: 131313131 CONTRASEÑA:
			 * i
			 */
			Usuario ibra2 = new Usuario("ibra cb2", LocalDate.of(2003, Month.MAY, 10), "i@um.es", "131313131", "i", "");
			/*
			 * CREDENCIALES PARA INICIAR SESIÓN EN LA CUENTA TELEFONO: 171717171 CONTRASEÑA:
			 * l
			 */
			Usuario laura = new Usuario("laura cc", LocalDate.of(2004, Month.JANUARY, 28), "l@um.es", "171717171", "l",
					"");
			/*
			 * CREDENCIALES PARA INICIAR SESIÓN EN LA CUENTA TELEFONO: 999999999 CONTRASEÑA:
			 * mj
			 */
			Usuario mariajose = new Usuario("maria jose tr", LocalDate.of(2004, Month.JULY, 15), "mj@um.es",
					"999999999", "mj", "");
			/*
			 * CREDENCIALES PARA INICIAR SESIÓN EN LA CUENTA TELEFONO: 777777777 CONTRASEÑA:
			 * a
			 */
			Usuario alex = new Usuario("alex ll", LocalDate.of(2004, Month.JULY, 6), "a@um.es", "777777777", "a", "");
			/*
			 * CREDENCIALES PARA INICIAR SESIÓN EN LA CUENTA TELEFONO: 666666666 CONTRASEÑA:
			 * j
			 */
			Usuario jorge = new Usuario("jorge sr", LocalDate.of(2004, Month.JUNE, 20), "j@um.es", "666666666", "j",
					"");

			// Insertar usuarios en la base de datos
			Usuario[] lista = { maria, ibra, maria2, ibra2, laura, mariajose, alex, jorge };
			for (Usuario u : lista) {
				usuarioDAO.create(u);
			}

			// Crear contactos individuales y asociarlos a los usuarios
			for (int i = 0; i < lista.length; i++) {
				Usuario u = lista[i];
				for (int j = 1; j < 3; j++) {
					int k = i + j;
					if (k < lista.length) {
						ContactoIndividual contacto = new ContactoIndividual(lista[k].getNombre().split(" ")[0],
								lista[k]);
						adaptadorContactoIndividual.create(contacto);
						u.addContacto(contacto);
						adaptadorUsuario.update(u);

					}
				}
				usuarioDAO.update(u);
			}
			usuarios = usuarioDAO.getAll(); // Actualizar la lista de usuarios después de insertar
		}

		// Enviar mensajes entre todos los contactos
		for (Usuario u : usuarios) {
			Controlador.INSTANCE.setUsuarioActual(u);
			for (ContactoIndividual c : u.getContactosIndividuales()) {
				String nombreDest = c.getNombre().split(" ")[0];
				Controlador.INSTANCE.registrarMensajeContacto(c, "Hola " + nombreDest + ", ¿cómo estás?", 0);
				Controlador.INSTANCE.registrarMensajeContacto(c, "¡Espero que estés bien!", 0);
				Controlador.INSTANCE.registrarMensajeContacto(c, "¡Saludos desde la app de chat!", 0);
				Controlador.INSTANCE.registrarMensajeContacto(c, "¡Nos vemos pronto!", 0);
				Controlador.INSTANCE.registrarMensajeContacto(c, "¡Saludos!", 0);
				Controlador.INSTANCE.registrarMensajeContacto(c, "¡Hasta luego!", 0);
				Controlador.INSTANCE.registrarMensajeContacto(c, "¡Qué tal todo!", 0);
				Controlador.INSTANCE.registrarMensajeContacto(c, "¡Todo bien, gracias!", 0);
				Controlador.INSTANCE.registrarMensajeContacto(c, "", 8);
				Controlador.INSTANCE.registrarMensajeContacto(c, "", 9);
			}
			usuarioDAO.update(u); // Actualizar el usuario después de añadir mensajes
			Controlador.INSTANCE.cerrarSesion(); // Cerrar sesión para evitar problemas de concurrencia;
		}

		// Crear grupos para cada usuario con algunos de sus contactos
		for (Usuario u : usuarios) {
			Controlador.INSTANCE.setUsuarioActual(u);
			Set<ContactoIndividual> algunosContactos = u.getContactosIndividuales().stream().limit(3)
					.collect(Collectors.toSet());
			if (!algunosContactos.isEmpty()) {
				Grupo g = u.crearGrupo("Grupo de " + u.getNombre().split(" ")[0]);
				grupoDAO.create(g);
				algunosContactos.forEach(g::addMiembro);
				grupoDAO.update(g);

			}
			usuarioDAO.update(u); // Actualizar el usuario después de crear grupos
			Controlador.INSTANCE.cerrarSesion(); // Cerrar sesión para evitar problemas de concurrencia
		}

		// Enviar mensajes en grupo
		for (Usuario u : usuarios) {
			Controlador.INSTANCE.setUsuarioActual(u);
			for (var grupo : u.getGrupos()) {
				
				Controlador.INSTANCE.registrarMensajeGrupo(grupo, "¡Hola grupo " + grupo.getNombre() + "!", 0);
				Controlador.INSTANCE.registrarMensajeGrupo(grupo, "¿Nos vemos este finde?", 0);
				Controlador.INSTANCE.registrarMensajeGrupo(grupo, "¡Saludos a todos!", 0);
				Controlador.INSTANCE.registrarMensajeGrupo(grupo, "", 6);

				grupoDAO.update(grupo);
			}
			usuarioDAO.update(u); // Actualizar el usuario después de enviar mensajes en grupo
			Controlador.INSTANCE.cerrarSesion(); // Cerrar sesión para evitar problemas de concurrencia
		}

	}
}
