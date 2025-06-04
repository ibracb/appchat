package umu.tds.appchat.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Comparator;
import umu.tds.appchat.services.filtros.Filtro;

public class Usuario {

	static final boolean PREMIUM_POR_DEFECTO = false;
	static final int EMOJI_POR_DEFECTO = -1;
	static final String TEXTO_POR_DEFECTO = null;
	static final String SALUDO_POR_DEFECTO = "Hey there I'm using Appchat";

	private int id;
	private String nombre;
	private String apellidos;
	private String telefono;
	private String password;
	private LocalDate fechaNacimiento;
	private LocalDate fechaRegistro;
	private String imagen;
	private String saludo;
	private boolean premium;

	private List<Contacto> contactos;
	private Map<String, List<Mensaje>> mensajesEnviados; // String referencia al telefono del contacto
	private Map<String, List<Mensaje>> mensajesRecibidos;

	public Usuario(String nombre, String apellidos, String telefono, String password, LocalDate fechaNacimiento) {
		this.nombre = nombre;
		this.telefono = telefono;
		this.apellidos = apellidos;
		this.id = 0;
		this.fechaNacimiento = fechaNacimiento;
		this.fechaRegistro = LocalDate.now();
		this.password = password;
		this.saludo = SALUDO_POR_DEFECTO;
		this.premium = PREMIUM_POR_DEFECTO;
		this.mensajesEnviados = new HashMap<>();
		this.mensajesRecibidos = new HashMap<>();
		this.contactos = new LinkedList<>();
	}

	public Usuario(String nombre, String apellidos, String telefono, String password, LocalDate fechaNacimiento,
			String saludo) {
		this(nombre, apellidos, telefono, password, fechaNacimiento);
		this.saludo = saludo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public boolean hasImage() {
		return imagen != null;
	}

	public String getSaludo() {
		return saludo;
	}

	public void setSaludo(String saludo) {
		this.saludo = saludo;
	}

	public String getPassword() {
		return password;
	}

	public Map<String, List<Mensaje>> getMensajesEnviados() {
		return mensajesEnviados;
	}

	public void setMensajesEnviados(Map<String, List<Mensaje>> mensajesEnviados) {
		this.mensajesEnviados = mensajesEnviados;
	}

	public Map<String, List<Mensaje>> getMensajesRecibidos() {
		return mensajesRecibidos;
	}

	public void setMensajesRecibidos(Map<String, List<Mensaje>> mensajesRecibidos) {
		this.mensajesRecibidos = mensajesRecibidos;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public List<Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}

	public Optional<Contacto> getContactoPorNombre(String nombre) {
		return contactos.stream().filter(c -> c.getNombre().equals(nombre)).findFirst();
	}

	public boolean hasContactoPorNombre(String nombre) {
		return contactos.stream().anyMatch(c -> c.getNombre().equals(nombre));
	}

	public Optional<ContactoIndividual> getContactoPorTelefono(String tlf) {
		return contactos.stream().filter(c -> c instanceof ContactoIndividual).map(c -> (ContactoIndividual) c) // Cast
																												// seguro
				.filter(ci -> ci.getTelefono().equals(tlf)).findFirst();
		// .map(ci -> (Contacto) ci); // Devuelve como Contacto si se necesita
	}

	public boolean hasContactoPorTelefono(String telefono) {
		return contactos.stream().filter(c -> c instanceof ContactoIndividual).map(c -> (ContactoIndividual) c) // Cast
																												// seguro
				.anyMatch(ci -> ci.getTelefono().equals(telefono));

	}

	public List<Mensaje> getMensajesEnviadosGrupo(String name) {
		return this.mensajesEnviados.getOrDefault(name, new ArrayList<>());
	}

	public boolean isPassword(String passwd) {
		return passwd.equals(password);
	}

	private List<Mensaje> getMensajesEnviadosContacto(String tlf) {
		return this.mensajesEnviados.getOrDefault(tlf, new ArrayList<>());
	}

	private List<Mensaje> getMensajesRecContacto(String tlf) {
		return this.mensajesRecibidos.getOrDefault(tlf, new ArrayList<>());
	}

	public boolean IsMyNumber(String telefono) {
		return this.telefono.equals(telefono);
	}

	public List<Grupo> getGrupos() {
		return contactos.stream().filter(c -> c instanceof Grupo).map(c -> (Grupo) c).collect(Collectors.toList());
	}

	public List<ContactoIndividual> getContactosIndividuales() {
		return contactos.stream().filter(c -> c instanceof ContactoIndividual).map(c -> (ContactoIndividual) c)
				.collect(Collectors.toList());
	}

	// ----------------------------------------------
	// Metodos gestion de Mensajes
	// ----------------------------------------------

	public Mensaje addMensajeEnviado(String texto, LocalDateTime now, Usuario receptor) {
		Mensaje mensaje = new Mensaje(texto, EMOJI_POR_DEFECTO, this, receptor, now);
		mensajesEnviados.computeIfAbsent(receptor.getTelefono(), k -> new ArrayList<>()).add(mensaje);
		return mensaje;
	}

	public Mensaje enviarMensajeGrupo(String texto, LocalDateTime now, Grupo grupo, List<Usuario> receptores) {
		Mensaje mensaje = new Mensaje(texto, EMOJI_POR_DEFECTO, this, this, now);
		mensajesEnviados.computeIfAbsent(grupo.getNombre(), k -> new ArrayList<>()).add(mensaje);
		return mensaje;
	}

	public Mensaje addEmojiEnviado(int emoji, LocalDateTime now, Usuario receptor) {
		Mensaje mensaje = new Mensaje(TEXTO_POR_DEFECTO, emoji, this, receptor, now);
		mensajesEnviados.computeIfAbsent(receptor.getTelefono(), k -> new ArrayList<>()).add(mensaje);
		return mensaje;
	}

	public Mensaje enviarEmojiGrupo(int emoji, LocalDateTime now, Grupo grupo, List<Usuario> receptores) {
		Mensaje mensaje = new Mensaje(TEXTO_POR_DEFECTO, emoji, this, this, now);
		mensajesEnviados.computeIfAbsent(grupo.getNombre(), k -> new ArrayList<>()).add(mensaje);
		return mensaje;
	}

	public void addMensajeRecibido(Mensaje mensaje) {
		mensajesRecibidos.computeIfAbsent(mensaje.getEmisor().getTelefono(), k -> new ArrayList<>()).add(mensaje);
	}

	public List<Mensaje> pasarMensajesALista() {
		HashMap<String, List<Mensaje>> chats = (HashMap<String, List<Mensaje>>) this.getChats();
		List<Mensaje> mensajes = new LinkedList<>();
		for (String tlf : chats.keySet()) {
			mensajes.addAll(chats.get(tlf));
		}
		return mensajes;
	}

	public List<Mensaje> filtrarMensajes(Filtro filtro) {
		List<Mensaje> mensajes = pasarMensajesALista();
		return filtro.filtrar(mensajes);
	}

	public List<Mensaje> getUltimosMensajes() {

		Map<String, List<Mensaje>> chats = getChats();

		List<Mensaje> ultimosMensajes = new ArrayList<>();
		for (String t : chats.keySet()) {
			List<Mensaje> mensajes = chats.get(t); // La lista de mensajes del contacto
			if (!mensajes.isEmpty()) {
				Mensaje ultimoMensaje = mensajes.get(mensajes.size() - 1);

				ultimosMensajes.add(ultimoMensaje);
			}
		}
		return ultimosMensajes.stream().sorted((m1, m2) -> m2.getFechaHora().compareTo(m1.getFechaHora()))
				.collect(Collectors.toList());
	}

	public List<Mensaje> getMensajesTelefono(String telefono) {
		List<Mensaje> mensajesEnv = getMensajesEnviadosContacto(telefono); // Mensajes enviados
		List<Mensaje> mensajesRec = getMensajesRecContacto(telefono); // Mensajes recibidos

		// Combina los mensajes enviados y recibidos
		List<Mensaje> todosLosMensajes = new ArrayList<>();
		todosLosMensajes.addAll(mensajesEnv);
		todosLosMensajes.addAll(mensajesRec);

		// Ordena los mensajes por fecha
		return todosLosMensajes.stream().sorted((m1, m2) -> m1.getFechaHora().compareTo(m2.getFechaHora()))
				.collect(Collectors.toList());
	}

	public Map<String, List<Mensaje>> getChats() {
		// Crear un nuevo mapa con copias de las listas para evitar modificar los
		// originales

		Map<String, List<Mensaje>> chat = new HashMap<>();

		for (Map.Entry<String, List<Mensaje>> entry : mensajesEnviados.entrySet()) {
			chat.put(entry.getKey(), new ArrayList<>(entry.getValue())); // Copia la lista
		}

		for (Map.Entry<String, List<Mensaje>> entry : mensajesRecibidos.entrySet()) {
			chat.putIfAbsent(entry.getKey(), new ArrayList<>());
			chat.get(entry.getKey()).addAll(entry.getValue()); // Agregar mensajes sin modificar los originales
		}
		// Ordenar cada lista de mensajes por fecha
		for (List<Mensaje> listaMensajes : chat.values()) {
			listaMensajes.sort(Comparator.comparing(Mensaje::getFechaHora));
		}

		return chat;
	}

	// ----------------------------------------------
	// Metodos de ContactoIndividual
	// ----------------------------------------------

	public Optional<ContactoIndividual> addContacto(String nombre, String telefono) {
		ContactoIndividual nuevoContacto = new ContactoIndividual(nombre, telefono, id);

		if (contactos.contains(nuevoContacto)) {
			return Optional.empty(); // Ya existe, no se agrega
		} else {
			contactos.add(nuevoContacto);
			mensajesEnviados.computeIfAbsent(telefono, k -> new ArrayList<>());
			mensajesRecibidos.computeIfAbsent(telefono, k -> new ArrayList<>());
			return Optional.of(nuevoContacto);
		}
	}

	// ----------------------------------------------
	// Metodos de los grupos
	// ----------------------------------------------

	public Grupo addGrupo(String nombre, String imagen) {
		Grupo grupo = new Grupo(nombre, id, imagen);
		if (contactos.contains(grupo)) {
			grupo = null;
		} else {
			contactos.add(grupo);
			mensajesEnviados.computeIfAbsent(telefono, k -> new ArrayList<>());
			// Recuerda que nunca vas a poder recibir mensajes de un grupo solo enviarlos
		}
		return grupo;
	}

	public boolean añdirContactoAGrupo(String nombreGrupo, ContactoIndividual contacto) {
		Optional<Grupo> grupoOptional = getGrupoPorNombre(nombreGrupo);
		if (grupoOptional.isPresent()) {
			Grupo grupo = grupoOptional.get();
			if (grupo.addContacto(contacto)) {
				contactos.remove(grupo);
				contactos.add(grupo);
				return true;
			}
		}
		return false;
	}

	public boolean eliminarContactoDeGrupo(String nombreGrupo, ContactoIndividual contacto) {
		if (contactos.contains(contacto)) {
			Optional<Grupo> grupoOptional = getGrupoPorNombre(nombreGrupo);
			if (grupoOptional.isPresent()) {
				Grupo grupo = grupoOptional.get();
				if (grupo.remContacto(contacto)) {
					contactos.remove(grupo);
					contactos.add(grupo);
					return true;
				}

			}
		}
		return false;
	}

	public List<ContactoIndividual> getContactosNombreGrupo(String nombreGrupo) {
		return getGrupoPorNombre(nombreGrupo).stream().flatMap(g -> g.getContactos().stream()) // Aplana la lista de
																								// contactos del grupo
				.toList();
	}

	public List<Contacto> getContactosNombreFueraDeGrupo(String nombreGrupo) {
		List<ContactoIndividual> contactosDeGrupo = getContactosNombreGrupo(nombreGrupo);

		return getContactosIndividuales().stream().filter(c -> !contactosDeGrupo.contains(c))
				.collect(Collectors.toList());
	}

	public String whichGroupHasThis(Mensaje mensaje) {

		for (String name : mensajesEnviados.keySet()) {
			for (Mensaje mensajeEnviado : mensajesEnviados.get(name)) {
				if (!mensajeEnviado.isEmisor(this) || !mensajeEnviado.isReceptor(this)) {
					continue;
				}
				if (mensajeEnviado.equals(mensaje))
					return name;
			}
		}
		return null;
	}

	public Optional<Grupo> getGrupoPorNombre(String nombre) {
		return this.getGrupos().stream().filter(g -> g.getNombre().equals(nombre)).findFirst();
	}

	// ----------------------------------------------
	// Metodos de los descuentos
	// ----------------------------------------------

	public boolean esNuevo() {
		LocalDate hace30Dias = LocalDate.now().plusDays(30);
		return fechaRegistro.isBefore(hace30Dias);
	}

	public long getNumMensajesEnviadosEsteMes() {
		LocalDateTime hace30Dias = LocalDateTime.now().minusDays(30);

		return mensajesEnviados.values().stream().flatMap(L -> L.stream())
				.filter(m -> !m.getFechaHora().isBefore(hace30Dias)).count();
	}

	// ----------------------------------------------
	// Exportar pdf
	// ----------------------------------------------

	public List<Mensaje> convertirConversacionPDF(String nombreContacto) {
		Contacto contacto = this.getContactoPorNombre(nombreContacto).orElse(null);
		if (contacto == null) {
			System.err.println("Error: No se encontró el contacto " + nombreContacto);
			return null;
		}

		String key;
		List<Mensaje> mensajes;
		if (contacto instanceof ContactoIndividual) {
			ContactoIndividual contactoIndividual = (ContactoIndividual) contacto;
			key = contactoIndividual.getTelefono();
			mensajes = getMensajesEnviadosContacto(key);
			mensajes.addAll(getMensajesRecContacto(key));
		} else {
			Grupo grupo = (Grupo) contacto;
			key = grupo.getNombre();
			mensajes = getMensajesEnviadosGrupo(key);
		}

		return mensajes;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", telefono=" + telefono + "]";
	}

}
