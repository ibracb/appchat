package umu.tds.appchat.controllers;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import umu.tds.appchat.models.*;
import umu.tds.appchat.persistencia.DAOException;
import umu.tds.appchat.persistencia.FactoriaDAO;
import umu.tds.appchat.persistencia.IAdaptadorContactoDAO;
import umu.tds.appchat.persistencia.IAdaptadorGrupoDAO;
import umu.tds.appchat.persistencia.IAdaptadorMensajeDAO;
import umu.tds.appchat.persistencia.IAdaptadorUsuarioDAO;
import umu.tds.appchat.repository.RepositorioUsuarios;
import umu.tds.appchat.services.pdf.AdaptadorItext;
import umu.tds.appchat.services.filtros.*;
import umu.tds.appchat.services.descuentos.ServicioDescuento;

public class ControladorAppChat {

	private static ControladorAppChat unicaInstancia;
	private RepositorioUsuarios repoUsers;
	private IAdaptadorContactoDAO adapatadorContacto;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorMensajeDAO adaptadorMensaje;
	private IAdaptadorGrupoDAO adaptadorGrupo;
	private FactoriaDAO factoriaDAO;
	private Usuario user;
	private Contacto contactoSeleccionado;

	// Constructor privado para Singleton
	private ControladorAppChat() {
		try {
			factoriaDAO = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoriaDAO.getUsuarioDAO();
		adapatadorContacto = factoriaDAO.getContactoDAO();
		adaptadorMensaje = factoriaDAO.getMensajeDAO();
		adaptadorGrupo = factoriaDAO.getGrupoDAO();
		repoUsers = RepositorioUsuarios.getUnicaIntacia();
	}

	// Singleton: obtener instancia única
	public static ControladorAppChat getInstancia() {
		if (unicaInstancia == null) {
			unicaInstancia = new ControladorAppChat();
		}
		return unicaInstancia;
	}

	// ----------------------------------------------
	// Gestión de usuarios
	// ----------------------------------------------

	public boolean registrarUsuario(String nombre, String apellidos, String telefono, String contrasena,
			LocalDate fechaNaci, String saludo) {
		Optional<Usuario> usuario = repoUsers.registrarUsuario(nombre, apellidos, telefono, contrasena, fechaNaci,
				saludo);
		if (usuario.isPresent()) {
			Usuario u = usuario.get();
			this.user = u;
			adaptadorUsuario.registrarUsuario(user);
		}
		return usuario.isPresent();
	}

	public void setSaludo(String saludo) {
		user.setSaludo(saludo);
		repoUsers.updateUser(user.getTelefono(), user);
	}

	public boolean comprobarUsuario(String telefono, String passwd) {
		Usuario usuario = repoUsers.comprobarUsuario(telefono, passwd);
		boolean result = usuario != null;
		if (result) {
			this.user = usuario;
		}
		return result;
	}

	// ----------------------------------------------
	// Funciones usuarioActual
	// ----------------------------------------------

	public Usuario getUsuarioActual() {
		return this.user;
	}

	public Usuario getUsuarioPorTelefono(String tlf) {
		return repoUsers.getUser(tlf);
	}

	public String getNombreUsuarioActual() {
		return user.getNombre();
	}

	public String getTelefonoUsuarioActual() {
		return user.getTelefono();
	}

	public void logOut() {
		user = null;
		contactoSeleccionado = null;
	}

	public void setImagen(String image) {
		if (user != null) {
			user.setImagen(image);
			repoUsers.updateUser(user.getTelefono(), user);
			adaptadorUsuario.modificarImagen(user);
		}
	}

	// ----------------------------------------------
	// Funciones contactos
	// ----------------------------------------------

	public List<Contacto> getContactos() {
		return user.getContactos();
	}

	public Optional<Contacto> getContactoPorNombre(String nombre) {
		return user.getContactoPorNombre(nombre);
	}

	public Optional<ContactoIndividual> getContactoPorTelefono(String tlf) {
		return user.getContactoPorTelefono(tlf);
	}

	public List<String> getNombresContactos() {
		return user.getContactos().stream().map(Contacto::getNombre).collect(Collectors.toList());
	}

	public List<ContactoIndividual> getContactosIndividualesDeUnGrupo(String grupo) {
		return user.getContactosNombreGrupo(grupo);
	}

	public List<Contacto> getContactosIndividualesQueNoEstenEnUnGrupo(String grupo) {
		return user.getContactosNombreFueraDeGrupo(grupo);
	}

	public boolean hasContactoPorTelefono(String tlf) {
		return user.hasContactoPorTelefono(tlf);
	}

	public boolean hasContactoNombre(String nombre) {
		return user.hasContactoPorNombre(nombre);
	}

	public String getNombreOrTelefonoContactoSeleccionado() {
		if (contactoSeleccionado instanceof Grupo) {
			return contactoSeleccionado.getNombre();
		}
		if (hasContactoNombre(contactoSeleccionado.getNombre())) {
			return contactoSeleccionado.getNombre();
		}
		ContactoIndividual ci = (ContactoIndividual) contactoSeleccionado;
		return ci.getTelefono();
	}

	public String getNombreOrTelefono(Mensaje mensaje) {
		if (mensaje.isFromAGroup()) {
			return obtenerNombreGrupo(mensaje);
		}

		Usuario interlocutor = mensaje.isEmisor(user) ? mensaje.getReceptor() : mensaje.getEmisor();
		return obtenerNombreSiExiste(interlocutor.getTelefono());
	}

	private String obtenerNombreSiExiste(String telefono) {
		return ControladorAppChat.getInstancia().getContactoPorTelefono(telefono).map(Contacto::getNombre)
				.orElse(telefono); // Si no existe, devuelve el teléfono
	}

	private String obtenerNombreGrupo(Mensaje mensaje) {
		return user.whichGroupHasThis(mensaje);
	}

	public boolean agregarContacto(String nombre, String telefono) {
		if (user.IsMyNumber(telefono)) { // comprobar si se quiere agregar a si mismo
			return false;
		}
		Usuario receptor = repoUsers.getUser(telefono);
		if (receptor == null) {
			return false;
		}
		Optional<ContactoIndividual> contacto = user.addContacto(nombre, telefono);
		if (contacto.isPresent()) {
			boolean result = adapatadorContacto.registrarContacto(contacto.get());
			if (result) {
				result = adaptadorUsuario.addContactoIndividual(user);
				repoUsers.updateUser(user.getTelefono(), user);
			}
			return result;
		}
		return false;
	}

	public boolean agregarGrupo(String nombre, String imagen) {
		Grupo grupo = user.addGrupo(nombre, imagen);
		if (grupo != null) {
			adaptadorGrupo.registrarGrupo(grupo);
			adaptadorUsuario.actualizarGrupos(user);
			repoUsers.updateUser(user.getTelefono(), user);
			return true;
		}
		return false;
	}

	public boolean agregarContactoAGrupo(String nombreGrupo, ContactoIndividual contacto) {
		boolean result = user.añdirContactoAGrupo(nombreGrupo, contacto);
		if (result) {
			Optional<Grupo> grupoOptional = user.getGrupoPorNombre(nombreGrupo);
			if (grupoOptional.isPresent()) {
				adaptadorGrupo.agregarOEliminarContacto(grupoOptional.get());
				adaptadorUsuario.actualizarGrupos(user);
				repoUsers.updateUser(user.getTelefono(), user);
				return true;
			}

		}
		return false;
	}

	public boolean elminarContactoDeGrupo(String nombreGrupo, ContactoIndividual contacto) {
		boolean result = user.eliminarContactoDeGrupo(nombreGrupo, contacto);
		if (result) {
			Optional<Grupo> grupoOptional = user.getGrupoPorNombre(nombreGrupo);
			if (grupoOptional.isPresent()) {
				adaptadorGrupo.agregarOEliminarContacto(grupoOptional.get());
				adaptadorUsuario.actualizarGrupos(user);
				repoUsers.updateUser(user.getTelefono(), user);
				return true;
			}

		}
		return false;
	}

	public boolean isAnyOneAContact(Mensaje mensaje) {
		if (mensaje.isFromAGroup())
			return true;
		else if (mensaje.isEmisor(user))
			return user.hasContactoPorTelefono(mensaje.getReceptor().getTelefono());
		return user.hasContactoPorTelefono(mensaje.getEmisor().getTelefono());
	}

	// ----------------------------------------------
	// Gestión de ContactoSeleccionado
	// ----------------------------------------------

	public void setContactoSeleccionado(Mensaje mensaje) {
		contactoSeleccionado = null;
		if (mensaje.isFromAGroup()) {
			String nombreGrupo = user.whichGroupHasThis(mensaje);
			contactoSeleccionado = user.getGrupoPorNombre(nombreGrupo).get();
		} else {
			Usuario usuario;
			if (mensaje.isEmisor(user)) {
				usuario = mensaje.getReceptor();
			} else
				usuario = mensaje.getEmisor(); // user

			Optional<ContactoIndividual> contacto = getContactoPorTelefono(usuario.getTelefono());
			if (contacto.isPresent())
				this.contactoSeleccionado = contacto.get();
			else
				this.contactoSeleccionado = new ContactoIndividual(usuario.getNombre(), usuario.getTelefono(),
						usuario.getId());
		}

	}

	public Contacto setContactoSeleccionado(String nombreContacto) {
		contactoSeleccionado = null;
		Optional<Contacto> contacto = getContactoPorNombre(nombreContacto);
		Contacto c = contacto.get();
		this.contactoSeleccionado = c;
		return c;
	}

	public Contacto getContactoSeleccionado() {
		return this.contactoSeleccionado;
	}

	public boolean hasContactoSeleccionado() {
		return contactoSeleccionado != null;
	}

	// ----------------------------------------------
	// Gestión de mensajes
	// ----------------------------------------------

	public void enviarMensajeOEmoji(Object textoOEmoji, LocalDateTime now, boolean flag) {
	    if (contactoSeleccionado instanceof Grupo)
	        enviarMensajeOEmojiGrupo(textoOEmoji, now, flag);
	    else {
	        ContactoIndividual ci = (ContactoIndividual) contactoSeleccionado;
	        enviarMensajeOEmojiAContactoIndividual(ci.getTelefono(), textoOEmoji, now, flag);
	        repoUsers.updateUser(user.getTelefono(), user);
	        adaptadorUsuario.añadirMensajeEnviado(user); // Actualizar al emisor
	    }
	}

	public void enviarMensajeOEmojiAContactoIndividual(String telefonoContacto, Object textoOEmoji, LocalDateTime now, boolean flag) {
	    Usuario usuarioReceptor = repoUsers.getUser(telefonoContacto);
	    Mensaje mensaje;
	    
	    if (flag) {
	        String texto = (String) textoOEmoji;
	        mensaje = user.addMensajeEnviado(texto, now, usuarioReceptor);
	    } else {
	        int emoji = (int) textoOEmoji; // Tratamos directamente el emoji como un int
	        mensaje = user.addEmojiEnviado(emoji, now, usuarioReceptor);
	    }
	    
	    usuarioReceptor.addMensajeRecibido(mensaje);
	    adaptadorMensaje.registrarMensaje(mensaje);
	    adaptadorUsuario.añadirMensajeRecibido(usuarioReceptor);
	    repoUsers.updateUser(usuarioReceptor.getTelefono(), usuarioReceptor);
	}

	public void enviarMensajeOEmojiGrupo(Object textoOEmoji, LocalDateTime now, boolean flag) {
	    Grupo grupo = (Grupo) contactoSeleccionado;
	    List<ContactoIndividual> contactosGrupo = grupo.getContactos();
	    List<Usuario> receptores = recuperarReceptores(contactosGrupo);
	    Mensaje mensaje;
	    
	    if (flag) {
	        String texto = (String) textoOEmoji;
	        mensaje = user.enviarMensajeGrupo(texto, now, grupo, receptores);
	    } else {
	        int emoji = (int) textoOEmoji; // Tratamos directamente el emoji como un int
	        mensaje = user.enviarEmojiGrupo(emoji, now, grupo, receptores);
	    }
	    
	    adaptadorMensaje.registrarMensaje(mensaje);
	    contactosGrupo.forEach(c -> enviarMensajeOEmojiAContactoIndividual(c.getTelefono(), textoOEmoji, now, flag));
	    adaptadorUsuario.añadirMensajeEnviado(user); // Actualizar al emisor
	    repoUsers.updateUser(user.getTelefono(), user);
	}


	public List<Usuario> recuperarReceptores(List<ContactoIndividual> contactos) {
		return contactos.stream().map(c -> repoUsers.getUser(c.getTelefono())).toList();
	}

	public Map<String, List<Mensaje>> getMensajes() {
		return user.getChats();
	}

	public List<Mensaje> getUltimosMensajes() {
		return user.getUltimosMensajes();
	}

	public List<Mensaje> getMensajesContactoSeleccionado() {
		if (contactoSeleccionado instanceof ContactoIndividual) {
			ContactoIndividual ci = (ContactoIndividual) contactoSeleccionado;
			return user.getMensajesTelefono(ci.getTelefono());
		} else if (contactoSeleccionado instanceof Grupo) {
			return user.getMensajesEnviadosGrupo(contactoSeleccionado.getNombre());
		}
		return Collections.emptyList();
	}

	// ----------------------------------------------
	// Exportación y premium
	// ----------------------------------------------
	public boolean exportarConversacionPDF(String contacto, String ruta) {
		AdaptadorItext exportador = new AdaptadorItext();
		List<Mensaje> mensajes = user.convertirConversacionPDF(contacto);
		return exportador.exportarConversacion(mensajes, ruta, contacto);
	}

	public double obtenerPrecioFinal() {
		ServicioDescuento servicioDescuento = new ServicioDescuento();
		return servicioDescuento.calcularPrecioFinal(user);
	}

	public void confirmarPago() {
		user.setPremium(true);
	}

	public List<Mensaje> buscarMensaje(String texto, String telefono, String nombre) {
		Filtro filtro = new BasicFiltro();
		if (texto != null) {
			filtro = new FiltroTexto(filtro, texto);
		} else if (telefono != null) {
			filtro = new FiltroTelefono(filtro, telefono);
		} else if (nombre != null) {
			filtro = new FiltroNombre(filtro, nombre);
		}

		return user.filtrarMensajes(filtro);
	}

	// ----------------------------------------------
	// Gestion de Imagenes
	// ----------------------------------------------

	public ImageIcon obtenerImagen(Object objeto, int dimensiones) throws IOException {
		if (objeto instanceof Mensaje) { // Si el objeto es un mensaje
			Mensaje mensaje = (Mensaje) objeto;
			if (mensaje.isFromAGroup()) { // Se mira si es de un grupo
				String nombreGrupo = obtenerNombreGrupo(mensaje);
				Optional<Contacto> futuroGrupo = user.getContactoPorNombre(nombreGrupo);
				if (futuroGrupo.isPresent()) {
					return whichImage(futuroGrupo.get(), dimensiones); // Se busca la imagen del grupo
				}
			} else if (mensaje.isEmisor(user)) { // Si no es de un grupo y soy el emisor
				return whichImage(mensaje.getReceptor(), dimensiones); // Busco la imagen del receptor
			} else
				return whichImage(mensaje.getEmisor(), dimensiones); // Lo contrario de antes

		} else if (objeto instanceof ContactoIndividual) { // Si el objeto es un ContactoIndividual
			return whichImage(getUsuarioPorTelefono(((ContactoIndividual) objeto).getTelefono()), dimensiones); 
		} else if (objeto instanceof Grupo) { // Si es un grupo se busca su foto
			return whichImage(objeto, dimensiones);
		} else if (objeto instanceof Usuario) { // Si es un usuario se coge la del usuario
			return whichImage(objeto, dimensiones);
		}
		return getScaledDefaultImage(dimensiones); // Fallback por defecto
	}

	public ImageIcon obtenerImagenUsuarioActual(int dimensiones) throws IOException { // Excepcion para el usuarioActual
		return whichImage(user, dimensiones);
	}

	public ImageIcon obtenerImagenContactoSeleccionado(int dimensiones) throws IOException { // Excepcion para el
																								// contactoSeleccionado
		return obtenerImagen(contactoSeleccionado, dimensiones);
	}

	@SuppressWarnings("deprecation")
	private ImageIcon whichImage(Object obj, int dimensiones) throws IOException {
		BufferedImage image = null;
		String imagen = null;

		if (obj instanceof Grupo) {
			Grupo grupo = (Grupo) obj;
			if (grupo.hasImage())
				imagen = grupo.getImagen();
		} else if (obj instanceof Usuario) {
			Usuario usuario = (Usuario) obj;
			if (usuario.hasImage())
				imagen = usuario.getImagen();
		}
		if (imagen != null) {
			if (isURL(imagen)) {
				image = ImageIO.read(new URL(imagen));
			} else if (Files.exists(Paths.get(imagen))) {
				image = ImageIO.read(Paths.get(imagen).toFile());
			}
		}

		if (image != null) {
			return getScaledImage(image, dimensiones);
		} else {
			return getScaledDefaultImage(dimensiones);
		}
	}

	public ImageIcon getScaledDefaultImage(int dimensiones) {
		return getScaledImage(new ImageIcon(getClass().getResource("/persona.png")), dimensiones);
	}

	public ImageIcon getScaledImage(BufferedImage bufferedImage, int dimensiones) {
		BufferedImage scaledImage = scaleAndMakeCircular(bufferedImage, dimensiones);
		return new ImageIcon(scaledImage);
	}

	public ImageIcon getScaledImage(ImageIcon image, int dimensiones) {
		BufferedImage bufferedImage = iconToBufferedImage(image);
		BufferedImage scaledImage = scaleAndMakeCircular(bufferedImage, dimensiones);
		return new ImageIcon(scaledImage);
	}

	private BufferedImage scaleAndMakeCircular(BufferedImage originalImage, int targetSize) {
		BufferedImage scaledImage = scaleImage(originalImage, targetSize, targetSize);
		BufferedImage circularImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = circularImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setClip(new java.awt.geom.Ellipse2D.Double(0, 0, targetSize, targetSize));
		g2d.drawImage(scaledImage, 0, 0, targetSize, targetSize, null);
		g2d.dispose();
		return circularImage;
	}

	private BufferedImage scaleImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
		BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = scaledImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		g2d.dispose();
		return scaledImage;
	}

	private BufferedImage iconToBufferedImage(ImageIcon icon) {
		Image image = icon.getImage();
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		return bufferedImage;
	}

	@SuppressWarnings("deprecation")
	private boolean isURL(String input) {
		try {
			new URL(input).toURI();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}