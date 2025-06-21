package umu.tds.apps.controlador;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.ContactoIndividual;
import umu.tds.apps.dominio.Grupo;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.TipoMensaje;
import umu.tds.apps.dominio.Usuario;
import umu.tds.apps.persistencia.ContactoIndividualDAO;
import umu.tds.apps.persistencia.DAOException;
import umu.tds.apps.persistencia.FactoriaDAO;
import umu.tds.apps.persistencia.GrupoDAO;
import umu.tds.apps.persistencia.MensajeDAO;
import umu.tds.apps.persistencia.UsuarioDAO;
import umu.tds.apps.repositorios.RepositorioUsuarios;
import umu.tds.apps.servicios.ExportPDF;
import umu.tds.apps.servicios.filtros.Filtro;
import umu.tds.apps.servicios.filtros.FiltroCompuesto;
import umu.tds.apps.servicios.filtros.FiltroPorFecha;
import umu.tds.apps.servicios.filtros.FiltroPorMovil;
import umu.tds.apps.servicios.filtros.FiltroPorNombreContacto;
import umu.tds.apps.servicios.filtros.FiltroPorTexto;

/**
 * Coordina la lógica de la aplicación, y maneja los eventos capturados por la
 * interfaz de usuario.
 */
public enum Controlador {

	/**
	 * Punto de acceso global al Controlador AppChat.
	 */
	INSTANCE;

	/**
	 * Campo para acceder al adaptador de usuarios para TDS.
	 */
	private UsuarioDAO adaptadorUsuario;

	/**
	 * Campo para acceder al adaptador de contactos individuales para TDS.
	 */
	private ContactoIndividualDAO adaptadorContactoIndividual;

	/**
	 * Campo para acceder al adaptador de grupos para TDS.
	 */
	private GrupoDAO adaptadorGrupo;

	/**
	 * Campo para acceder al adaptador de mensajes para TDS.
	 */
	private MensajeDAO adaptadorMensaje;

	/**
	 * Campo para acceder a la instancia global del repositorio de usuarios .
	 */
	private RepositorioUsuarios repositorioUsuarios;

	/**
	 * Usuario actualmente manejado por el controlador.
	 */
	private Usuario usuarioActual;

	/**
	 * Constructor privado del controlador AppChat.
	 */
	private Controlador() {
		initializeAdaptadores();
		initializeRepositorioUsuarios();
	}

	/**
	 * Comprueba si un usuario está registrado en el sistema de AppChat.
	 * 
	 * @param movil - El número de teléfono móvil del usuario a comprobar.
	 * @return true si el usuario está registrado, false en caso contrario.
	 */
	public boolean isUsuarioRegistrado(String movil) {
		return repositorioUsuarios.findUsuario(movil) != null;
	}

	/**
	 * Registra un nuevo usuario en el sistema de AppChat.
	 * 
	 * @param nombre          - El nombre del usuario a registrar.
	 * @param fechaNacimiento - La fecha de nacimiento del usuario a registrar.
	 * @param email           - El email del usuario a registrar.
	 * @param imagen          - La foto de perfil del usuario a registrar.
	 * @param movil           - El número de teléfono móvil del usuario a registrar.
	 * @param contraseña      - La contraseña del usuario a registrar.
	 * @param saludo          - El saludo del usuario a registrar.
	 */
	public boolean registrarUsuario(String nombre, LocalDate fechaNacimiento, String email, String imagen, String movil,
			String contraseña, String saludo) {
		if (isUsuarioRegistrado(movil)) {
			return false;
		}
		Usuario usuario = new Usuario(nombre, fechaNacimiento, email, imagen, movil, contraseña, saludo);
		adaptadorUsuario.create(usuario);
		repositorioUsuarios.addUsuario(usuario);
		usuarioActual = usuario;
		return true;
	}

	/**
	 * Registra un nuevo usuario en el sistema de AppChat.
	 * 
	 * @param nombre          - El nombre del usuario a registrar.
	 * @param fechaNacimiento - La fecha de nacimiento del usuario a registrar.
	 * @param email           - El email del usuario a registrar.
	 * @param imagen          - La foto de perfil del usuario a registrar.
	 * @param movil           - El número de teléfono móvil del usuario a registrar.
	 * @param contraseña      - La contraseña del usuario a registrar.
	 * @param saludo          - El saludo del usuario a registrar.
	 */
	public boolean registrarUsuario(String nombre, LocalDate fechaNacimiento, String email, String movil,
			String contraseña, String saludo) {
		if (isUsuarioRegistrado(movil)) {
			return false;
		}
		Usuario usuario = new Usuario(nombre, fechaNacimiento, email, movil, contraseña, saludo);
		adaptadorUsuario.create(usuario);
		repositorioUsuarios.addUsuario(usuario);
		usuarioActual = usuario;
		return true;
	}

	/**
	 * Inicia sesión a un usuario.
	 * 
	 * @param movil      - El teléfono móvil del usuario a iniciar sesión.
	 * @param contraseña - La contraseña del usuario a iniciar sesión.
	 */
	public int loginUsuario(String movil, String contraseña) {
		Usuario usuario = repositorioUsuarios.findUsuario(movil);
		if (usuario != null) {
			if (usuario.getContraseña().equals(contraseña)) {
				usuarioActual = usuario;
				return 0; // Inicio de sesión exitoso
			} else {
				return -1; // Contraseña incorrecta
			}
		}
		return -2; // Usuario no encontrado;
	}
	
	
	/**
	 * Establece el usuario actual que maneja el controlador.
	 * 
	 * @param usuarioActual - Usuario a establecer como actual.
	 */
	public void setUsuarioActual(Usuario usuarioActual) {
		this.usuarioActual = usuarioActual;
	}

	/**
	 * Borra un usuario de la base de datos y del repositorio.
	 * 
	 * @param usuario - Usuario a borrar.
	 */
	public void borrarUsuario(Usuario usuario) {
		adaptadorUsuario.delete(usuario);
		repositorioUsuarios.removeUsuario(usuario);
	}

	/**
	 * Llamada al adaptador de contactos individuales para registrar un contacto
	 * individual en la base de datos.
	 * 
	 * @param nombre        - Nombre del contacto a registrar.
	 * @param movilContacto - Móvil de contacto a registrar.
	 */
	public boolean registrarContactoIndividual(String nombre, String movilContacto) {
		// Evitar que un usuario se registre a sí mismo
		if (usuarioActual.getMovil().equals(movilContacto)) {
			// No puede añadirse a sí mismo
			return false;
		}

		if (usuarioActual.getContactoIndividual(movilContacto) != null) {
			return false;
		}

		Usuario usuarioContacto = repositorioUsuarios.findUsuario(movilContacto);
		if (usuarioContacto == null) {
			return false;
		}

		ContactoIndividual contacto = new ContactoIndividual(nombre, usuarioContacto);
		adaptadorContactoIndividual.create(contacto);
		usuarioActual.addContacto(contacto);
		adaptadorUsuario.update(usuarioActual);
		return true;
	}

	/**
	 * Llamada al adaptador de contactos individuales para borrar un contacto
	 * individual de la base de datos.
	 * 
	 * @param contacto - Contacto individual a borrar.
	 */
	public void borrarContactoIndividual(ContactoIndividual contacto) {
		adaptadorContactoIndividual.delete(contacto);
		// Eliminar de la lista en memoria del usuario actual
		usuarioActual.removeContacto(contacto);
		// Actualizar usuario en la base de datos
		adaptadorUsuario.update(usuarioActual);
	}

	/**
	 * Llamada al adaptador de grupos para registrar un grupo en la base de datos.
	 * 
	 * @param movilUsuario - Móvil del usuario que crea el grupo.
	 * @param nombre       - Nombre del grupo.
	 * @param imagen       - Foto de perfil del grupo.
	 * @param miembros     - Miembros añadidos al crear el grupo.
	 */
	public boolean registrarGrupo(String nombre) {
		if (recuperarGrupo(nombre) != null) {
			return false;
		}
		Grupo grupo = usuarioActual.crearGrupo(nombre);
		adaptadorGrupo.create(grupo);
		adaptadorUsuario.update(usuarioActual);
		return true;
	}

	/**
	 * Llamada al adaptador de grupos para que borre un grupo de la base de datos.
	 * 
	 * @param grupo - Grupo a borrar.
	 */
	public void borrarGrupo(Grupo grupo) {
		adaptadorGrupo.delete(grupo);
		grupo.getMiembros().forEach(miembro -> {
			adaptadorUsuario.update(miembro.getUsuario());
		});
		usuarioActual.removeContacto(grupo);
		adaptadorUsuario.update(usuarioActual);
	}

	/**
	 * Método para registrar en la base de datos un mensaje enviado a un contacto
	 * individual.
	 * 
	 * @param emisor    - Móvil del emisor del mensaje.
	 * @param receptor  - Móvil del receptor de mensaje.
	 * @param texto     - Texto empleado en el mensaje.
	 * @param emoticono - Emoticono empleado en el mensaje.
	 */
	public void registrarMensajeContacto(ContactoIndividual contacto, String texto, int emoticono) {
		Mensaje mensaje = contacto.nuevoMensaje(texto, emoticono, TipoMensaje.ENVIADO);
		adaptadorMensaje.create(mensaje);
		adaptadorContactoIndividual.update(contacto);
		adaptadorUsuario.update(usuarioActual);
		Usuario usuarioReceptor = contacto.getUsuario();
		ContactoIndividual contactoInverso = usuarioReceptor.getContactoIndividual(usuarioActual.getMovil());
		if (contactoInverso == null) {
			contactoInverso = usuarioReceptor.crearContactoIndividualDesconocido(usuarioActual);
	        adaptadorContactoIndividual.create(contactoInverso);
	        adaptadorUsuario.update(usuarioReceptor);
		}
		Mensaje mensajeRecibido = contactoInverso.nuevoMensaje(texto, emoticono, TipoMensaje.RECIBIDO);
		adaptadorMensaje.create(mensajeRecibido);
		adaptadorContactoIndividual.update(contactoInverso);
		adaptadorUsuario.update(usuarioReceptor);
	}

	/**
	 * Método para registrar en la base de datos un mensaje enviado a un grupo.
	 * 
	 * @param grupo     - Grupo al que se envía el mensaje.
	 * @param texto     - Texto empleado en el mensaje.
	 * @param emoticono - Emoticono empleado en el mensaje.
	 */
	public void registrarMensajeGrupo(Grupo grupo, String texto, int emoticono) {
		Mensaje mensaje = grupo.nuevoMensaje(texto, emoticono, TipoMensaje.ENVIADO);
		adaptadorMensaje.create(mensaje);
		adaptadorGrupo.update(grupo);
		grupo.getMiembros().forEach(miembro -> registrarMensajeContacto(miembro, texto, emoticono));
	}

	/**
	 * Llamada al adaptador de mensajes para borrar un mensaje de la base de datos.
	 * 
	 * @param mensaje - Mensaje a borrar.
	 */
	public void borrarMensaje(Mensaje mensaje) {
		ContactoIndividual contacto = repositorioUsuarios.findContacto(usuarioActual, mensaje);
		adaptadorMensaje.delete(mensaje);
		adaptadorContactoIndividual.update(contacto);
	}

	/**
	 * Inicializa los adaptadores, en particular para TDS.
	 */
	private void initializeAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstance();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorContactoIndividual = factoria.getContactoIndividualDAO();
		adaptadorGrupo = factoria.getGrupoDAO();
		adaptadorMensaje = factoria.getMensajeDAO();
	}

	/**
	 * Inicializa el repositorio de usuarios.
	 */
	private void initializeRepositorioUsuarios() {
		repositorioUsuarios = RepositorioUsuarios.INSTANCE;
	}

	/**
	 * Devuelve todos los usuarios almacenados en el repositorio.
	 * 
	 * @return los usuarios que hay en el repositorio
	 */
	public Set<Usuario> getUsuarios() {
		return repositorioUsuarios.getUsuarios();
	}

	/**
	 * Devuelve el usuario que actualmente maneja el controlador.
	 * 
	 * @return el usuario actual.
	 */
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	/**
	 * Devuelve los contactos individuales de un usuario.
	 * @param usuario - El usuario cuyos contactos individuales se desean obtener.
	 * @return el conjunto de contactos individuales
	 */
	public Set<ContactoIndividual> getContactosIndividuales(Usuario usuario) {
		return usuario.getContactosIndividuales();
	}
	
	/**
	 * Devuelve los contactos individuales del usuario actual.
	 * @return el conjunto de contactos individuales
	 */
	public Set<ContactoIndividual> getContactosIndividualesUsuarioActual() {
		return usuarioActual.getContactosIndividuales();
	}

	/**
	 * Modifica el usuario actual en la base de datos.
	 */
	public void modificarUsuario() {
		adaptadorUsuario.update(usuarioActual);
	}

	/**
	 * Filtra los mensajes de un usuario según varios criterios.
	 * 
	 * @param usuario - El usuario cuyos mensajes se desean filtrar.
	 * @param texto   - Texto a buscar en los mensajes.
	 * @param movil   - Móvil del contacto a buscar.
	 * @param nombre  - Nombre del contacto a buscar.
	 * @param fecha   - Fecha de envío del mensaje a buscar.
	 * @return un mapa que asocia cada mensaje filtrado con su contacto
	 *         correspondiente
	 */
	public Map<Mensaje, Contacto> filtrarMensajes(Usuario usuario, String texto, String movil, String nombre,
			LocalDate fecha) {
		Set<Filtro> filtros = Set.of(new FiltroPorTexto(texto), new FiltroPorMovil(movil),
				new FiltroPorNombreContacto(nombre), new FiltroPorFecha(fecha));
		Filtro filtroCompuesto = new FiltroCompuesto(filtros);
		return filtroCompuesto.filtrar(usuario);
	}

	/**
	 * Devuelve el nombre del usuario actual.
	 * @return el nombre
	 */
	public String getNombreUsuarioActual() {
		return usuarioActual.getNombre();
	}
	
	/**
	 * Devuelve la imagen del usuario actual.
	 * @return la url de la imamgen de perfil del usuario actual
	 */
	public String getImagenUsuarioActual() {
		return usuarioActual.getImagen();
	}

	/**
	 * Cambia la imagen del usuario actual y lo actualiza en la base de datos.
	 * @param imagen - La url de la nueva imagen del usuario actual.
	 */
	public void cambiarImagenUsuarioActual(String imagen) {
		usuarioActual.setImagen(imagen);
		modificarUsuario();
	}
	
	/**
	 * Comprueba si el usuario actual es premium.
	 * 
	 * @return true si el usuario actual es premium, false en caso contrario.
	 */
	public boolean isPremiumUsuarioActual() {
		return usuarioActual.isPremium();
	}

	/**
	 * Activa el estado premium del usuario actual y actualiza su descuento.
	 */
	public void activarPremiumUsuarioActual() {
		actualizarDescuentoUsuarioActual();
		usuarioActual.setPremium(true);
		modificarUsuario();
	}

	/**
	 * Desactiva el estado premium del usuario actual.
	 */
	public void desactivarPremiumUsuarioActual() {
		usuarioActual.setPremium(false);
		modificarUsuario();
	}

	public void añadirContacto(ContactoIndividual contacto, Grupo grupo) {
		usuarioActual.addContacto(contacto, grupo);
	}

	/**
	 * Elimina un contacto individual del usuario actual en un grupo.
	 * 
	 * @param contacto - El contacto individual a eliminar.
	 * @param grupo    - El grupo del que se eliminará el contacto.
	 */
	public void eliminarContacto(ContactoIndividual contacto, Grupo grupo) {
		usuarioActual.removeContacto(contacto, grupo);
	}

	/**
	 * Devuelve el descuento calculado del usuario actual.
	 * 
	 * @return el descuento.
	 */
	public double getDescuentoCalculadoUsuarioActual() {
		return usuarioActual.getDescuentoCalculado();
	}

	/**
	 * Recupera un contacto individual del usuario actual por su número de móvil.
	 * 
	 * @param movil - El número de móvil del contacto a recuperar.
	 * @return el contacto individual encontrado, o null si no existe.
	 */
	public ContactoIndividual recuperarContacto(String movil) {
		return repositorioUsuarios.findContactoIndividual(usuarioActual.getMovil(), movil);
	}

	/**
	 * Genera un PDF con el listado de contactos del usuario actual.
	 * 
	 * @return true si se ha generado correctamente, false en caso contrario.
	 */
	public boolean generarPdfListado() {
		return ExportPDF.INSTANCE.createPdfListado(usuarioActual);
	}

	/**
	 * Genera un PDF con el chat de un contacto individual.
	 * 
	 * @param contacto - El contacto individual cuyo chat se desea exportar.
	 * @return true si se ha generado correctamente, false en caso contrario.
	 */
	public boolean generarPdfChat(ContactoIndividual contacto) {
		return ExportPDF.INSTANCE.createPdfChat(contacto);
	}

	/**
	 * Obtiene los grupos que ha creado el usuario actual.
	 * @return conjunto de grupos
	 */
	public Set<Grupo> getGruposUsuarioActual() {
		return usuarioActual.getGrupos();
	}

	/**
	 * Obtiene el número de móvil del usuario actual.
	 * 
	 * @return el número de móvil.
	 */
	public String getMovilUsuarioActual() {
		return usuarioActual.getMovil();
	}

	/**
	 * Obtiene el usuario asociado a un contacto individual.
	 * 
	 * @param contacto - El contacto individual del que se desea obtener el usuario.
	 * @return el usuario asociado al contacto individual.
	 */
	public Usuario getUsuarioContactoIndividual(ContactoIndividual contacto) {
		return contacto.getUsuario();
	}

	/**
	 * Obtiene el número de móvil del contacto individual.
	 * 
	 * @param contacto - El contacto individual del que se desea obtener el número
	 *                 de móvil.
	 * @return el número de móvil del contacto individual.
	 */
	public String getMovilContactoIndividual(ContactoIndividual contacto) {
		return getUsuarioContactoIndividual(contacto).getMovil();
	}

	/**
	 * Recupera un grupo del usuario actual por su nombre.
	 * 
	 * @param nombre - El nombre del grupo a recuperar.
	 * @return el grupo encontrado, o null si no existe.
	 */
	public Grupo recuperarGrupo(String nombre) {
		return usuarioActual.getGrupos().stream().filter(grupo -> nombre.equals(grupo.getNombre())).findFirst()
				.orElse(null);
	}

	/**
	 * Cierra la sesión del usuario actual.
	 */
	public void cerrarSesion() {
		usuarioActual = null;
	}

	/**
	 * Obtiene los contactos individuales que no pertenecen a un grupo.
	 * @param grupo - El grupo del que se desea obtener los contactos no pertenecientes.
	 * @return conjunto de contactos individuales no pertenecientes al grupo
	 */
	public Set<ContactoIndividual> getUsuariosNoPertenecientesAlGrupo(Grupo grupo) {
		return usuarioActual.getUsuariosNoPertenecientesAlGrupo(grupo);
	}

	/**
	 * Actualiza un grupo en la base de datos.
	 * 
	 * @param grupo - El grupo a actualizar.
	 */
	public void actualizarGrupo(Grupo grupo) {
		adaptadorGrupo.update(grupo);
	}

	/**
	 * Obtiene los mensajes de un contacto ordenados por fecha de envío.
	 * 
	 * @param contacto - El contacto cuyos mensajes se desean obtener.
	 * @return un conjunto de mensajes ordenados por fecha de envío.
	 */
	public Set<Mensaje> getMensajesInvertidos(Contacto contacto) {
		Set<Mensaje> invertidos = new TreeSet<>(
				Comparator.comparing(Mensaje::getMomentoEnvio).thenComparing(Mensaje::getId));
		invertidos.addAll(contacto.getMensajes());
		return invertidos;
	}

	/**
	 * Obtiene el momento de envío de un mensaje formateado.
	 * 
	 * @param mensaje - El mensaje cuyo momento de envío se desea obtener.
	 * @return el momento de envío del mensaje formateado como cadena.
	 */
	public String getMomentoEnvioMensaje(Mensaje mensaje) {
		return mensaje.getMomentoEnvio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
	}

	/**
	 * Obtiene el nombre del contacto.
	 * 
	 * @param contacto - El contacto cuyo nombre se desea obtener.
	 * @return el nombre del contacto.
	 */
	public String getNombreContacto(Contacto contacto) {
		return contacto.getNombre();
	}

	/**
	 * Obtiene el texto del mensaje.
	 * 
	 * @param mensaje - El mensaje cuyo texto se desea obtener.
	 * @return el texto del mensaje.
	 */
	public String getTextoMensaje(Mensaje mensaje) {
		return mensaje.getTexto();
	}

	/**
	 * Obtiene el tipo de mensaje.
	 * 
	 * @param mensaje - El mensaje cuyo tipo se desea obtener.
	 * @return el tipo de mensaje.
	 */
	public TipoMensaje getTipoMensaje(Mensaje mensaje) {
		return mensaje.getTipo();
	}

	/**
	 * Obtiene el último mensaje de un contacto.
	 * 
	 * @param contacto - El contacto del que se desea obtener el último mensaje.
	 * @return el último mensaje del contacto.
	 */
	public Mensaje getUltimoMensaje(Contacto contacto) {
		return contacto.getUltimoMensaje();
	}

	/**
	 * Obtiene los miembros de un grupo.
	 * 
	 * @param grupo - El grupo cuyos miembros se desean obtener.
	 * @return un conjunto de contactos individuales que son miembros del grupo.
	 */
	public Set<ContactoIndividual> getMiembros(Grupo grupo) {
		return grupo.getMiembros();
	}

	/**
	 * Actualiza el descuento del usuario actual.
	 */
	public void actualizarDescuentoUsuarioActual() {
		usuarioActual.updateDescuento();
	}
	
	/**
	 * Obtiene el número de miembros de un grupo.
	 * 
	 * @param grupo - El grupo del que se desea obtener el número de miembros.
	 * @return el número de miembros del grupo.
	 */
	public int getNumMiembros(Grupo grupo) {
		return getMiembros(grupo).size();
	}

	/**
	 * Obtiene los contactos individuales añadidos por el usuario actual.
	 * 
	 * @return un conjunto de contactos individuales añadidos por el usuario actual.
	 */
	public Set<ContactoIndividual> getContactosIndividualesAñadidosUsuarioActual() {
		return usuarioActual.getContactosIndividualesAñadidos();
	}
	
	/**
	 * Comprueba si un contacto individual ha sido añadido por el usuario actual.
	 * 
	 * @param contacto - El contacto individual a comprobar.
	 * @return true si el contacto ha sido añadido, false en caso contrario.
	 */
	public boolean isContactoIndividualAñadido(ContactoIndividual contacto) {
		return contacto.isAñadido();
	}
	
	/**
	 * Obtiene la imagen de un contacto.
	 * 
	 * @param contacto - El contacto del que se desea obtener la imagen.
	 * @return la URL de la imagen del contacto, o null si no tiene imagen.
	 */
	public String getImagenContacto(Contacto contacto) {
		String imagen = null;
		if (contacto instanceof ContactoIndividual) {
			imagen = ((ContactoIndividual) contacto).getUsuario().getImagen();
		} else if (contacto instanceof Grupo) {
			imagen = ((Grupo) contacto).getImagen();
		}
        return imagen;
	}
	
	/**
	 * Comprueba si un contacto es un contacto individual.
	 * 
	 * @param contacto - El contacto a comprobar.
	 * @return true si es un contacto individual, false en caso contrario.
	 */
	public boolean isContactoIndividual(Contacto contacto) {
		return contacto instanceof ContactoIndividual;
	}
	
	/**
	 * Comprueba si un contacto es un grupo.
	 * 
	 * @param contacto - El contacto a comprobar.
	 * @return true si es un grupo, false en caso contrario.
	 */
	public boolean isGrupo(Contacto contacto) {
		return contacto instanceof Grupo;
	}
	
	/**
	 * Obtiene el emoticono de un mensaje.
	 * 
	 * @param mensaje - El mensaje del que se desea obtener el emoticono.
	 * @return el emoticono del mensaje.
	 */
	public int getEmojiMensaje(Mensaje mensaje) {
		return mensaje.getEmoticono();
	}
	
	/**
	 * Obtiene el nombre de un usuario.
	 * 
	 * @param usuario - El usuario del que se desea obtener el nombre.
	 * @return el nombre del usuario.
	 */
	public String getNombreUsuario(Usuario usuario) {
		return usuario.getNombre();
	}
	
	/**
	 * Encuentra el contacto asociado a un mensaje en un mapa de mensajes.
	 * 
	 * @param mensajes - El mapa que asocia mensajes a contactos.
	 * @param mensaje  - El mensaje del que se desea encontrar el contacto.
	 * @return el contacto asociado al mensaje, o null si no se encuentra.
	 */
	public Contacto encontrarContacto(Map<Mensaje, Contacto> mensajes, Mensaje mensaje) {
		return mensajes.get(mensaje);
	}
	
	/**
	 * Cambia la imagen de un grupo y lo actualiza en la base de datos.
	 * 
	 * @param seleccionado - El grupo cuyo imagen se desea cambiar.
	 * @param url          - La nueva URL de la imagen del grupo.
	 */
	public void cambiarImagenGrupo(Grupo seleccionado, String url) {
		seleccionado.setImagen(url);
		adaptadorGrupo.update(seleccionado);
	}
	
	/**
	 * Actualiza el nombre de un contacto individual.
	 * 
	 * @param contacto    - El contacto individual cuyo nombre se desea actualizar.
	 * @param nuevoNombre - El nuevo nombre del contacto.
	 */
	public void cambiarNombreContactoIndividual(ContactoIndividual contacto, String nuevoNombre) {
		contacto.setNombre(nuevoNombre);
        adaptadorContactoIndividual.update(contacto);
        adaptadorUsuario.update(usuarioActual);
    }
	
	/**
	 * Cambia el nombre de un grupo y lo actualiza en la base de datos.
	 * @param grupo - El grupo cuyo nombre se desea cambiar.
	 * @param nuevoNombre - El nuevo nombre del grupo.
	 */
	public void cambiarNombreGrupo(Grupo grupo, String nuevoNombre) {
		grupo.setNombre(nuevoNombre);
        adaptadorGrupo.update(grupo);
        adaptadorUsuario.update(usuarioActual);
	}
	
}