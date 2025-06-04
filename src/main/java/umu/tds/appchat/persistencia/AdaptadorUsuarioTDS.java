package umu.tds.appchat.persistencia;

import umu.tds.appchat.models.*;
import umu.tds.appchat.models.Mensaje;
import tds.driver.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import beans.*;

public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuarioTDS unicaInstancia = null;

	public static AdaptadorUsuarioTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorUsuarioTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorUsuarioTDS() {
		AdaptadorUsuarioTDS.servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	@Override
	public boolean registrarUsuario(Usuario usuario) {

		// 1. Se comprueba que no está registrada la entidad que corresponde al código
		// del objeto (al crear un objeto, el campo id se inicializa a 0 o 1)
		Entidad eUsuario = null;
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		} catch (NullPointerException e) {
			System.err.println("No existe el usuario");
		}
		if (eUsuario != null)
			return false;
		// 2. Se registran sus objetos referenciados si no lo están ya.

		Boolean existeUsuario = AdaptadorUsuarioTDS.getUnicaInstancia().recuperarTodosUsuarios().values().stream()
				.anyMatch(u -> u.getTelefono().equals(usuario.getTelefono()));
		if (Boolean.FALSE.equals(existeUsuario)) {
			agregarContactosIndividual(usuario.getContactosIndividuales());
			agregarGrupos(usuario.getGrupos());

			agregarMensajes(usuario.getMensajesEnviados());
			agregarMensajes(usuario.getMensajesRecibidos());

			// 3. Se crea la entidad (con getID() se puede obtener el id para asignarlo al
			// objeto)
			eUsuario = new Entidad();
			eUsuario.setNombre("usuario"); // setTipo() hubiese sido más apropiado

			// 4. Se crean y añaden las propiedades a la entidad creada.
			eUsuario.setPropiedades(new ArrayList<>(Arrays.asList(new Propiedad("nombre", usuario.getNombre()),
					new Propiedad("apellidos", usuario.getApellidos()),
					new Propiedad("telefono", usuario.getTelefono()), new Propiedad("password", usuario.getPassword()),
					new Propiedad("fechaNaci", usuario.getFechaNacimiento().toString()),
					new Propiedad("fechaRegistro", usuario.getFechaRegistro().toString()),
					new Propiedad("imagen", usuario.getImagen()), new Propiedad("saludo", usuario.getSaludo()),
					new Propiedad("premium", String.valueOf(usuario.isPremium())),
					new Propiedad("contactosIndividuales", obtenerIDsContactos(usuario.getContactosIndividuales())),
					new Propiedad("grupos", obtenerIDsGrupos(usuario.getGrupos())),
					new Propiedad("mensajesEnviados", serializarMensajes(usuario.getMensajesEnviados())),
					new Propiedad("mensajesRecibidos", serializarMensajes(usuario.getMensajesRecibidos())))));

			// 5. Se registra la entidad y se asocia id al objeto almacenado.
			eUsuario = servPersistencia.registrarEntidad(eUsuario);
			usuario.setId(eUsuario.getId());
			return true;
		}
		return false;
	}

	@Override
	public Usuario recuperarUsuario(int id) {
		// 1. Si el objeto está en el pool se retorna,
		if (PoolDAO.INSTANCE.contains(id))
			return (Usuario) PoolDAO.INSTANCE.getObject(id);
		Usuario user = null;

		// 2. Si no lo está se recupera entidad y las propiedades de campos de tipo
		// primitivo
		Entidad eUser = servPersistencia.recuperarEntidad(id);
		String nombre, apellidos, telefono, passwd, fechaNacimiento, fechaRegistro, imagen, saludo, premium;
		nombre = servPersistencia.recuperarPropiedadEntidad(eUser, "nombre");
		apellidos = servPersistencia.recuperarPropiedadEntidad(eUser, "apellidos");
		telefono = servPersistencia.recuperarPropiedadEntidad(eUser, "telefono");
		passwd = servPersistencia.recuperarPropiedadEntidad(eUser, "password");
		fechaNacimiento = servPersistencia.recuperarPropiedadEntidad(eUser, "fechaNaci");
		fechaRegistro = servPersistencia.recuperarPropiedadEntidad(eUser, "fechaRegistro");
		imagen = servPersistencia.recuperarPropiedadEntidad(eUser, "imagen");
		saludo = servPersistencia.recuperarPropiedadEntidad(eUser, "saludo");
		premium = servPersistencia.recuperarPropiedadEntidad(eUser, "premium");

		// 3. Se crea el objeto, se inicializa con propiedades anteriores y se añade al
		// pool si
		// es necesario
		user = new Usuario(nombre, apellidos, telefono, passwd, LocalDate.parse(fechaNacimiento));
		user.setImagen(imagen);
		user.setSaludo(saludo);
		user.setFechaRegistro(LocalDate.parse(fechaRegistro));
		user.setId(id);
		PoolDAO.INSTANCE.addObject(id, user);

		// 4. Se recuperan los objetos referenciados y se actualiza el objeto
		// Aqui habria que recuperar los contactos
		// Primero se recuperan las propiedades de la entidad que no son objetos

		// Como es muy complicado almacenar los contactos individuales y los grupos en
		// una sola lista primero se recuperan por separado y luego se unifican
		List<Contacto> contactos = new LinkedList<>();
		List<ContactoIndividual> contactosIndividuales = obtenerContactosDesdeIds(
				servPersistencia.recuperarPropiedadEntidad(eUser, "contactosIndividuales"));
		List<Grupo> grupos = obtenerGruposDesdeIds(servPersistencia.recuperarPropiedadEntidad(eUser, "grupos"));

		contactosIndividuales.stream().forEach(c -> contactos.add(c));
		grupos.stream().forEach(g -> contactos.add(g));

		user.setContactos(contactos);
		user.setMensajesEnviados(
				deserializarMensajes(servPersistencia.recuperarPropiedadEntidad(eUser, "mensajesEnviados")));

		user.setMensajesRecibidos(
				deserializarMensajes(servPersistencia.recuperarPropiedadEntidad(eUser, "mensajesRecibidos")));

		return user;
	}

	public String obtenerIDsContactos(List<ContactoIndividual> contactos) {

		String codes = "";
		for (ContactoIndividual contacto : contactos) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(contacto.getId());
			stringBuilder.append(" ");
			codes += stringBuilder.toString();
		}
		return codes.trim();
	}

	private String obtenerIDsGrupos(List<Grupo> grupos) {
		String codes = "";
		for (Grupo grupo : grupos) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(grupo.getId());
			stringBuilder.append(" ");
			codes += stringBuilder.toString();
		}
		return codes.trim();
	}

	public List<ContactoIndividual> obtenerContactosDesdeIds(String idsContactos) {
		List<ContactoIndividual> contactos = new LinkedList<>();
		StringTokenizer strTok = new StringTokenizer(idsContactos, " ");
		while (strTok.hasMoreTokens()) {
			try {
				int id = Integer.parseInt(strTok.nextToken());
				ContactoIndividual contacto = FactoriaDAO.getInstancia().getContactoDAO().recuperarContacto(id);
				if (contacto != null) {
					contactos.add(contacto);
				} else {
					System.err.println("Contacto con ID " + id + " no encontrado.");
				}
			} catch (NumberFormatException | DAOException e) {
				e.printStackTrace();
			}
		}
		return contactos;
	}

	// Corrección en serialización de mensajes
	private String serializarMensajes(Map<String, List<Mensaje>> mensajes) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, List<Mensaje>> entry : mensajes.entrySet()) {
			sb.append(entry.getKey()).append(":");
			for (Mensaje mensaje : entry.getValue()) {
				sb.append(mensaje.getId()).append(",");
			}
			if (!entry.getValue().isEmpty())
				sb.deleteCharAt(sb.length() - 1); // Solo elimina si hay mensajes
			sb.append(";");
		}
		return sb.toString();
	}

	private Map<String, List<Mensaje>> deserializarMensajes(String datos) {
		Map<String, List<Mensaje>> mensajes = new HashMap<>();
		if (datos == null || datos.isEmpty())
			return mensajes;
		String[] pares = datos.split(";");
		for (String par : pares) {
			List<Mensaje> listaMensajes = new ArrayList<>();
			String[] contactoMensajes = par.split(":");
			String telCotacto = contactoMensajes[0];
			if (contactoMensajes.length > 1) {
				try {
					recuperarLista(contactoMensajes[1], listaMensajes);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mensajes.put(telCotacto, listaMensajes);
		}
		return mensajes;
	}

	private void recuperarLista(String lista, List<Mensaje> listaMensajes) throws NumberFormatException, DAOException {

		String[] idsMensajes = lista.split(",");
		for (String id : idsMensajes) {
			Mensaje mensaje = FactoriaDAO.getInstancia().getMensajeDAO().recuperarMensaje(Integer.parseInt(id.trim()));
			if (mensaje != null)
				listaMensajes.add(mensaje);
		}
	}

	private List<Grupo> obtenerGruposDesdeIds(String idsGrupos) {
		List<Grupo> grupos = new LinkedList<>();
		StringTokenizer strTok = new StringTokenizer(idsGrupos, " ");
		while (strTok.hasMoreTokens()) {
			try {
				int id = Integer.parseInt(strTok.nextToken());
				Grupo grupo = FactoriaDAO.getInstancia().getGrupoDAO().recuperarGrupo(id);
				if (grupo != null) {
					grupos.add(grupo);
				} else {
					System.err.println("Grupo con ID " + id + " no encontrado.");
				}
			} catch (NumberFormatException | DAOException e) {
				e.printStackTrace();
			}
		}
		return grupos;
	}

	@Override
	public Map<String, Usuario> recuperarTodosUsuarios() {
		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");
		HashMap<String, Usuario> usuarios = new HashMap<>();
		for (Entidad eUser : eUsuarios) {
			Usuario user = recuperarUsuario(eUser.getId());
			usuarios.put(user.getTelefono(), user);
		}
		return usuarios;
	}

	@Override
	public boolean modificarImagen(Usuario user) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(user.getId());
		// 2. Se recorren sus propiedades y se actualiza su valor
		// como nosotros solo deberiamos de poder cambiar la imagen solo pongo eso
		for (Propiedad prop : eUsuario.getPropiedades()) {
			if (prop.getNombre().equals("imagen")) {
				prop.setValor(user.getImagen());
				servPersistencia.modificarPropiedad(prop);
				return true;
			}
		}
		return false;
	}

	public void agregarContactosIndividual(List<ContactoIndividual> contactos) {
		for (ContactoIndividual c : contactos) {
			try {
				if (!FactoriaDAO.getInstancia().getContactoDAO().contactoYaRegistrado(c))
					FactoriaDAO.getInstancia().getContactoDAO().registrarContacto(c);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean addContactoIndividual(Usuario user) {
		// 1. Se recupera entidad
		Entidad eUsuario = servPersistencia.recuperarEntidad(user.getId());
		List<ContactoIndividual> contactos = user.getContactosIndividuales();
		// 2. Se recorren sus propiedades y se actualiza su valor
		for (Propiedad prop : eUsuario.getPropiedades()) {
			if (prop.getNombre().equals("contactosIndividuales")) {
				prop.setValor(obtenerIDsContactos(contactos));
				servPersistencia.modificarPropiedad(prop);
				return true;
			}
		}

		return false;
	}

	private void agregarGrupos(List<Grupo> grupos) {
		for (Grupo g : grupos) {
			try {
				if (!FactoriaDAO.getInstancia().getGrupoDAO().existeGrupo(g))
					FactoriaDAO.getInstancia().getGrupoDAO().registrarGrupo(g);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean actualizarGrupos(Usuario user) {
		// 1. Se recupera entidad
		Entidad eUsuario = servPersistencia.recuperarEntidad(user.getId());
		List<Grupo> grupos = user.getGrupos();
		// 2. Se recorren sus propiedades y se actualiza su valor
		for (Propiedad prop : eUsuario.getPropiedades()) {
			if (prop.getNombre().equals("grupos")) {
				prop.setValor(obtenerIDsGrupos(grupos));
				servPersistencia.modificarPropiedad(prop);
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean añadirMensajeEnviado(Usuario user) {
		// 1. Se recupera entidad
		Entidad eUsuario = servPersistencia.recuperarEntidad(user.getId());
		Map<String, List<Mensaje>> mensajes = user.getMensajesEnviados();
		// 2. Se recorren sus propiedades y se actualiza su valor
		// como nosotros solo deberiamos de poder cambiar la imagen solo pongo eso
		for (Propiedad prop : eUsuario.getPropiedades()) {
			if (prop.getNombre().equals("mensajesEnviados")) {
				prop.setValor(serializarMensajes(mensajes));
				servPersistencia.modificarPropiedad(prop);
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean añadirMensajeRecibido(Usuario user) {
		// 1. Se recupera entidad
		Entidad eUsuario = servPersistencia.recuperarEntidad(user.getId());
		Map<String, List<Mensaje>> mensajes = user.getMensajesRecibidos();
		// 2. Se recorren sus propiedades y se actualiza su valor
		for (Propiedad prop : eUsuario.getPropiedades()) {
			if (prop.getNombre().equals("mensajesRecibidos")) {
				prop.setValor(serializarMensajes(mensajes));
				servPersistencia.modificarPropiedad(prop);
				return true;
			}
		}

		return false;
	}

	private void agregarMensajes(Map<String, List<Mensaje>> mensajesUser) {
		for (String c : mensajesUser.keySet()) { // La clave del mapa es el contacto
			List<Mensaje> mensajes = mensajesUser.get(c);
			for (Mensaje m : mensajes) {
				try {
					FactoriaDAO.getInstancia().getMensajeDAO().registrarMensaje(m);
				} catch (DAOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Usuario recuperarUsuarioTelefono(String tlf) {
		HashMap<String, Usuario> users = (HashMap<String, Usuario>) recuperarTodosUsuarios();
		return users.get(tlf);
	}

}
