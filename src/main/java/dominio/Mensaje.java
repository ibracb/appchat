package dominio;

import java.time.LocalDateTime;

public class Mensaje implements Comparable<Mensaje> {
	
	private static final int ID_MENSAJE_DEFAULT = 0;
	private static final String TEXTO_NULL = "";
	private static final int ICONO_NULL = -1;
	
	private int id;
	private final String emisor;
	private final String receptor;
	private final LocalDateTime momentoEnvio;
	private final String texto;
	private final int emoticono;
	private final TipoMensaje tipo;
	
	public Mensaje(String emisor, String receptor, LocalDateTime momentoEnvio, String texto, TipoMensaje tipo) {
		this.id = ID_MENSAJE_DEFAULT;
		this.emisor = emisor;
		this.receptor = receptor;
		this.momentoEnvio = momentoEnvio;
		this.texto = texto;
		this.emoticono = ICONO_NULL;
		this.tipo = tipo;
	}
	
	public Mensaje(String emisor, String receptor, LocalDateTime momentoEnvio, int emoticono, TipoMensaje tipo) {
		this.id = ID_MENSAJE_DEFAULT;
		this.emisor = emisor;
		this.receptor = receptor;
		this.momentoEnvio = momentoEnvio;
		this.texto = TEXTO_NULL;
		this.emoticono = emoticono;
		this.tipo = tipo;
	}

	public int getId() {
		return id;
	}

	public String getEmisor() {
		return emisor;
	}

	public String getReceptor() {
		return receptor;
	}

	public LocalDateTime getMomentoEnvio() {
		return momentoEnvio;
	}

	public String getTexto() {
		return texto;
	}

	public int getEmoticono() {
		return emoticono;
	}

	public TipoMensaje getTipo() {
		return tipo;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public int compareTo(Mensaje o) {
		return o.getMomentoEnvio().compareTo(this.getMomentoEnvio());
	}
	
}
