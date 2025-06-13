package umu.tds.apps.dominio;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import umu.tds.apps.dominio.descuentos.Descuento;
import umu.tds.apps.dominio.descuentos.FactoriaDescuentos;
import umu.tds.apps.utils.Utils;

/**
 * Clase que representa a un usuario de AppChat.
 */
public class Usuario {
	
	/**
	 * Mensaje de saludo por defecto.
	 */
	public static final String SALUDO_DEFAULT = "¡Hola! Soy un usuario de AppChat.";
	
	public static final double PRECIO_INICIAL = 40.0;
	
	/**
	 * Identificador asociado a un usuario.
	 */
	private int id;
	
	/**
	 * Nombre del usuario.
	 */
	private final String nombre;
	
	/**
	 * Fecha en la que el usuario nació.
	 */
	private LocalDate fechaNacimiento;
	
	/**
	 * Fecha en la que el usuario se registró en AppChat.
	 */
	private LocalDate fechaRegistro;
	
	/**
	 * Correo eléctronico con el que el usuario se registró.
	 */
	private String email;
	
	/**
	 * Ruta que representa la imagen de perfil del usuario.
	 */
	private String imagen;
	
	/**
	 * Número de teléfono móvil del usuario.
	 */
	private String movil;
	
	/**
	 * Contraseña del usuario para acceder a su cuenta de AppChat.
	 */
	private String contraseña;
	
	/**
	 * Mensaje de saludo opcional del usuario.
	 */
	private Optional<String> saludo;
	
	/**
	 * Condición de usuario Premium.
	 */
	private boolean premium;
	
	/**
	 * Descuento que se le aplicará al usuario.
	 */
	private Descuento descuento;
	
	/**
	 * Colección de contactos (ya sean individuales o grupales) del usuario.
	 */
	private Set<Contacto> contactos;
	
	/**
	 * Constructor de la clase Usuario: se le otorga un estado inicial
	 * @param nombre - Nombre del usuario.
	 * @param fechaNacimiento - Fecha en la que nació el usuario.
	 * @param email - Correo electrónico con el que el usuario se registra en AppChat.
	 * @param imagen - Imagen con la que el usuario establece su foto de perfil.
	 * @param movil - Teléfono del usuario.
	 * @param contraseña - Contraseña de acceso creada por el usuario.
	 * @param saludo - Mensaje de saludo opcional del usuario.
	 */
	public Usuario(String nombre, LocalDate fechaNacimiento, String email, String imagen, String movil, String contraseña, String saludo) {
		this.id = Utils.ID_DEFAULT;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.fechaRegistro = Utils.FECHA_ACTUAL.toLocalDate();
		this.email = email;
		setImagen(imagen);
		this.movil = movil;
		this.contraseña = contraseña;
		setSaludo(saludo);
		this.premium = false;
		this.contactos = new TreeSet<Contacto>();
		updateDescuento();
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, movil);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(email, other.email) || Objects.equals(movil, other.movil);
	}
	
	/**
	 * El usuario crea un grupo.
	 * @param nombre - Nombre asignado al grupo al crearlo.
	 * @param imagen - Imagen asignada al grupo al crearlo.
	 * @param miembros - Miembros que formarán parte del grupo.
	 * @return true si se ha creado bien el grupo, false si no es así.
	 */
	public boolean createGrupo(String nombre, String imagen) {
		Grupo grupo = new Grupo(nombre, imagen);
		return contactos.add(grupo);
	}
	
	public void addContacto(ContactoIndividual contacto) {
		 contactos.add(contacto);
	 }
	
	/**
	 * El usuario elimina un contacto de su lista de contactos.
	 * @param contacto - El contacto que sea desea eliminar.
	 * @return true si el contacto fue eliminado correctamente, false si es al contrario.
	 */
	public boolean removeContacto(ContactoIndividual contacto) {
		return contactos.remove(contacto);
	}
	
	/**
	 * El usuario añade un contacto a un grupo.
	 * @param contacto - El contacto a añadir.
	 * @param grupo - El grupo donde añadir.
	 * @return true si se añadió correctamente el contacto al grupo, false si no ha sido así.
	 */
	public boolean addContacto(ContactoIndividual contacto, Grupo grupo) {
		return grupo.addMiembro(contacto);
	}
	
	/**
	 * El usuario elimina un contacto de un grupo.
	 * @param contacto -  El contacto a eliminar.
	 * @param grupo - El grupo donde eliminar.
	 * @return true si se eliminó correctamente el contacto del grupo, false si no ha sido así.
	 */
	public boolean removeContacto(ContactoIndividual contacto, Grupo grupo) {
		return grupo.removeMiembro(contacto);
	}
	
	/**
	 * Devuelve el id asociado al usuario
	 * @return el id.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Se establece al usuario el id deseado.
	 * @param id - El id a establecer al usuario. 
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Devuelve la ruta asociada a la imagen de perfil que tiene el usuario.
	 * @return la ruta correspondiente.
	 */
	public String getImagen() {
		return imagen;
	}

	/**
	 * Se establece una imagen al usuario mediante una ruta
	 * @param imagen - La ruta para para establecer la imagen de perfil deseada por el ususario.
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	/**
	 * Devuelve el mensaje de saludo del usuario.
	 * @return el saludo correspondiente, o el por defecto si no tiene.
	 */
	public String getSaludo() {
		return saludo.orElse(SALUDO_DEFAULT);
	}
	
	/**
	 * Establece un mensaje de saludo del usuario.
	 * @param saludo - El mensaje de saludo a establecer.
	 */
	public void setSaludo(String saludo) {
		this.saludo = Optional.ofNullable(saludo);
	}
	
	/**
	 * Devuelve si un usuario es (o no) Premium.
	 * @return true si es un usuario Premium, false si no lo es.
	 */
	public boolean isPremium() {
		return premium;
	}
	
	/**
	 * Establece la condición de usuario Premium.
	 * @param premium - La condición premium a asignar.
	 */
	public void setPremium(boolean premium) {
		this.premium = premium;
	}
	
	/**
	 * Devuelve el tipo de descuento que se le aplica al usuario.
	 * @return el descuento correspondiente.
	 */
	public Descuento getDescuento() {
		return descuento;
	}
	
	/**
	 * Establece el descuento que se le aplica al usuario.
	 * @param descuento - El descuento a establecer.
	 */
	public void setDescuento(Descuento descuento) {
		this.descuento = descuento;
	}
	
	/**
	 * Actualización de descuento, dependiendo de ciertas condiciones.
	 */
	public void updateDescuento() {
		this.descuento = FactoriaDescuentos.INSTANCE.createDescuento(this);
	}
	
	/**
	 * Devuelve el nombre del usuario
	 * @return el nombre correspondiente.
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Devuelve la fecha de nacimiento del usuario.
	 * @return la fecha de nacimiento correspondiente.
	 */
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	/**
	 * Establece la fecha de nacimiento del usuario.
	 * @param fechaNacimiento - La fecha de nacimiento a establecer.
	 */
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	/**
	 * Devuelve la fecha en la que el usuario se registró.
	 * @return la fecha de registro correspondiente.
	 */
	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}
	
	/**
	 * Establece la fecha de registro del usuario.
	 * @param fechaRegistro - La fecha de registro a establecer.
	 */
	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	public Grupo crearGrupo(String nombre, String imagen) {
		Grupo grupo = new Grupo(nombre, imagen);
		addContacto(grupo);
		return grupo;
	}
	
	/**
	 * Devuelve el email del usuario.
	 * @return el email correspondiente.
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Establece el email del usuario.
	 * @param email - El email a establecer.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Devuelve el número de teléfono móvil del usuario.
	 * @return el teléfono móvil correspondiente.
	 */
	public String getMovil() {
		return movil;
	}
	
	/**
	 * Establece el número de teléfono móvil del usuario.
	 * @param movil - El número de teléfono móvil a establecer.
	 */
	public void setMovil(String movil) {
		this.movil = movil;
	}
	
	/**
	 * Devuelve la contraseña de acceso establecida por el usuario.
	 * @return la contraseña correspondiente.
	 */
	public String getContraseña() {
		return contraseña;
	}
	
	/**
	 * Establece la contraseña de acceso del usuario.
	 * @param contraseña - La contraseña a establecer.
	 */
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
	/**
	 * Devuelve los contactos (ya sean individuales o grupales) del usuario.
	 * @return los contactos correspondientes.
	 */
	public Set<Contacto> getContactos() {
		return Collections.unmodifiableSet(contactos);
	}
	
	/**
	 * Establece los contactos del usuario.
	 * @param contactos - Los contactos a establecer.
	 */
	public void setContactos(Set<Contacto> contactos) {
		this.contactos = contactos;
	}
	
	public int getTotalMensajesEnviadosUltimoMes() {
		return getContactos().stream()
				.map(contacto -> contacto.getSubTotalMensajesEnviadosUltimoMes())
				.reduce(0, Integer::sum);
	}
	
	public ContactoIndividual getContactoIndividual(String movil) {
		return contactos.stream()
				.filter(contacto -> contacto instanceof ContactoIndividual)
				.map(contacto -> (ContactoIndividual) contacto)
				.filter(contactoIndividual -> contactoIndividual.getMovil().equals(movil))
				.findFirst()
				.orElse(null);
	}
	
	public Set<ContactoIndividual> getContactosIndividuales(){
		return getContactos().stream()
				.filter(contacto -> contacto instanceof ContactoIndividual)
				.map(contacto -> (ContactoIndividual) contacto)
				.collect(Collectors.toCollection(TreeSet::new));
	}
	
	/**
	 * Devuelve un conjunto de contactos individuales que no pertenecen al grupo
	 * indicado.
	 * @param grupo - El grupo del que se quieren obtener los contactos individuales no pertenecientes.
	 * @return un conjunto de contactos individuales no pertenecientes al grupo.
	 */
	public Set<ContactoIndividual> getUsuariosNoPertenecientesAlGrupo(Grupo grupo) {
		return getContactosIndividuales().stream()
				.filter(c -> !grupo.getMiembros().contains(c))
				.collect(Collectors.toCollection(TreeSet::new));
	}
	
	
	public Set<Grupo> getGrupos(){
		return getContactos().stream()
				.filter(contacto -> contacto instanceof Grupo)
				.map(contacto -> (Grupo) contacto)
				.collect(Collectors.toCollection(TreeSet::new));
	}
	
	public Set<Mensaje> getAllMensajes() {
		return contactos.stream()
			.flatMap(c -> c.getMensajes().stream())
			.collect(Collectors.toCollection(TreeSet::new));
	}
	
	public void addContacto(Contacto contacto) {
		contactos.add(contacto);
	}
	
	public void removeContacto(Contacto contacto) {
		contactos.remove(contacto);
	}
	
}
