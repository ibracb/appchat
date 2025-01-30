package dominio;

import java.time.LocalDateTime;

import utils.Utils;

/**
 * Representación de un Mensaje.
 */
public class Mensaje implements Comparable<Mensaje> {
	
	/**
	 * Cadena de texto que representa la cadena vacía y, por lo tanto, no tiene texto el mensaje.
	 */
	private static final String TEXTO_NULL = "";
	
	/**
	 * Valor entero que especifica que un mensaje no tiene emoticono
	 */
	private static final int ICONO_NULL = -1;
	
	/**
	 * Identificador único asociado al mensaje.
	 */
	private int id;
	
	/**
	 * Indica el número de teléfono de la persona que envió el mensaje.
	 */
	private final String emisor;
	
	/**
	 * Indica el número de teléfono de la persona que recibió el mensaje.
	 */
	private final String receptor;
	
	/**
	 * Representación del instante de tiempo en el que el mensaje fue enviado.
	 */
	private final LocalDateTime momentoEnvio;
	
	/**
	 * Cadena de texto que incluye el contenido textual enviado.
	 */
	private final String texto;
	
	/**
	 * Número entero que representa el emoji del mensaje.
	 */
	private final int emoticono;
	
	/**
	 * Indica si el mensaje ha sido enviado o recibido por el Usuario.
	 */
	private final TipoMensaje tipo;
	
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

	@Override
	public int compareTo(Mensaje o) {
		return o.getMomentoEnvio().compareTo(this.getMomentoEnvio());
	}
	
}
