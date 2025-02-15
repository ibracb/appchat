package umu.tds.apps.dominio;

import java.time.LocalDateTime;
import java.util.Objects;

import umu.tds.apps.utils.Utils;

/**
 * Representación de un Mensaje.
 */
public class Mensaje implements Comparable<Mensaje> {

	/**
	 * Cadena de texto que representa la cadena vacía y, por lo tanto, no tiene texto el mensaje.
	 */
	public static final String TEXTO_NULL = "";
	
	/**
	 * Valor entero que especifica que un mensaje no tiene emoticono
	 */
	public static final int ICONO_NULL = -1;
	
	/**
	 * Identificador único asociado al mensaje.
	 */
	private int id;

	/**
	 * Indica el número de teléfono de la persona que envió el mensaje.
	 */
	private String emisor;
	
	/**
	 * Indica el número de teléfono de la persona que recibió el mensaje.
	 */
	private String receptor;
	
	/**
	 * Representación del instante de tiempo en el que el mensaje fue enviado.
	 */
	private LocalDateTime momentoEnvio;
	
	/**
	 * Cadena de texto que incluye el contenido textual enviado.
	 */
	private String texto;
	
	/**
	 * Número entero que representa el emoji del mensaje.
	 */
	private int emoticono;
	
	/**
	 * Indica si el mensaje ha sido enviado o recibido por el Usuario.
	 */
	private TipoMensaje tipo;
	
	/**
	 * Constructor de Mensaje, con solo texto.
	 * @param emisor - El teléfono móvil de quien haya enviado el mensaje.
	 * @param receptor - El teléfono móvil de quien haya recibido el mensaje.
	 * @param texto - El texto escrito en el mensaje.
	 * @param tipo - El tipo de mensaje.
	 */
	public Mensaje(String emisor, String receptor, String texto, TipoMensaje tipo) {
		this.id = Utils.ID_DEFAULT;
		this.emisor = emisor;
		this.receptor = receptor;
		this.momentoEnvio = Utils.FECHA_ACTUAL;
		this.texto = texto;
		this.emoticono = ICONO_NULL;
		this.tipo = tipo;
	}
	
	/**
	 * Constructor de Mensaje, con solo un emoticono.
	 * @param emisor - El teléfono móvil de quien haya enviado el mensaje.
	 * @param receptor - El teléfono móvil de quien haya recibido el mensaje.
	 * @param emoticono - El emoticono empleado en el mensaje.
	 * @param tipo - El tipo de mensaje.
	 */
	public Mensaje(String emisor, String receptor, int emoticono, TipoMensaje tipo) {
		this.id = Utils.ID_DEFAULT;
		this.emisor = emisor;
		this.receptor = receptor;
		this.momentoEnvio = Utils.FECHA_ACTUAL;
		this.texto = TEXTO_NULL;
		this.emoticono = emoticono;
		this.tipo = tipo;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(emisor, emoticono, id, momentoEnvio, receptor, texto, tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mensaje other = (Mensaje) obj;
		return Objects.equals(emisor, other.emisor) && emoticono == other.emoticono && id == other.id
				&& Objects.equals(momentoEnvio, other.momentoEnvio) && Objects.equals(receptor, other.receptor)
				&& Objects.equals(texto, other.texto) && tipo == other.tipo;
	}

	/**
	 * Devuelve el identificador asociado al mensaje.
	 * @return el id del mensaje
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Asigna un identificador al mensaje
	 * @param id - El identificador a asignar al mensaje
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Devuelve el teléfono móvil de quien envió el mensaje.
	 * @return el emisor correspondiente.
	 */
	public String getEmisor() {
		return emisor;
	}
	
	/**
	 * Devuelve el teléfono móvil de quien recibió el mensaje.
	 * @return el receptor correspondiente.
	 */
	public String getReceptor() {
		return receptor;
	}
	
	/**
	 * Devuelve la fecha y hora en la que se envió el mensaje.
	 * @return el momento de envío
	 */
	public LocalDateTime getMomentoEnvio() {
		return momentoEnvio;
	}
	
	/**
	 * Devuelve el texto escrito en el mensaje.
	 * @return el texto correspondiente.
	 */
	public String getTexto() {
		return texto;
	}
	
	/**
	 * Devuelve el emoticono introducido en el mensaje.
	 * @return el emoticono correspondiente
	 */
	public int getEmoticono() {
		return emoticono;
	}
	
	/**
	 * Devuelve el tipo de menssaje, si ha sido enviado o recibido por el usuario.
	 * @return el tipo correspondiente.
	 */
	public TipoMensaje getTipo() {
		return tipo;
	}
	
	/**
	 * Establece un emisor al mensaje.
	 * @param emisor - Teléfono del emisor a establecer.
	 */
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
	
	/**
	 * Establece un receptor al mensaje.
	 * @param receptor - Teléfono del receptor a establecer.
	 */
	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}
	
	/**
	 * Establece un instante de envío al mensaje.
	 * @param momentoEnvio - Instante de envío a establecer.
	 */
	public void setMomentoEnvio(LocalDateTime momentoEnvio) {
		this.momentoEnvio = momentoEnvio;
	}
	
	/**
	 * Establece un texto al mensaje.
	 * @param texto - Texto a establecer.
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	/**
	 * Establece un emoticono al mensaje.
	 * @param emoticono - Emoticono a establecer.
	 */
	public void setEmoticono(int emoticono) {
		this.emoticono = emoticono;
	}
	
	
	/**
	 * Establece el tipo de mensaje al mensaje.
	 * @param tipo - tipo de mensaje a establecer.
	 */
	public void setTipo(TipoMensaje tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public int compareTo(Mensaje o) {
		return o.getMomentoEnvio().compareTo(this.getMomentoEnvio());
	}
	
}