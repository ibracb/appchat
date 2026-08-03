package umu.tds.apps.modelo;

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
	public static final int ICONO_NULL = 0;
	
	/**
	 * Identificador único asociado al mensaje.
	 */
	private int id;
	
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
	 * Constructor de Mensaje, con texto/emoticono, y tipo de mensaje.
	 * @param texto - El texto escrito en el mensaje.
	 * @param emoticono - El emoticono empleado en el mensaje.
	 * @param tipo - El tipo de mensaje.
	 */
	public Mensaje(String texto, int emoticono, TipoMensaje tipo) {
		this.id = Utils.ID_DEFAULT;
		this.momentoEnvio = LocalDateTime.now();
		if (emoticono > ICONO_NULL) {
			this.emoticono = emoticono;
			this.texto = TEXTO_NULL;
		} else {
			this.emoticono = ICONO_NULL;
			this.texto = texto;
		}
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
	public int hashCode() {
		return Objects.hash(emoticono, id, momentoEnvio, texto, tipo);
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
		return emoticono == other.emoticono && id == other.id &&
				Objects.equals(momentoEnvio, other.momentoEnvio) &&
				Objects.equals(texto, other.texto) && tipo == other.tipo;
	}
	
	@Override
	public int compareTo(Mensaje o) {
		int resultado = o.getMomentoEnvio().compareTo(this.getMomentoEnvio());
	    if (resultado == 0) {
	        // Si tienen el mismo momento de envío, comparar por ID
	        resultado = Integer.compare(this.id, o.id);
	    }
	    return resultado;
	}
	
}