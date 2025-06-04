package umu.tds.appchat.models;

import java.time.LocalDateTime;

public class Mensaje {

	private final int NULL_EMOJI = -1;
	
	private String texto;
	private int emoji;
	private Usuario emisor;
	private Usuario receptor;
	private LocalDateTime fechaHora;
	private int id;
	
	 public Mensaje(String texto, int emoji, Usuario emisor, Usuario receptor, LocalDateTime fechaHora) {
	        this.texto = texto != null ? texto : "";  // Si no se pasa texto, se asigna un string vacío
	        this.emoji = (emoji >= 0) ? emoji : NULL_EMOJI;  // Si no se pasa emoji, se asigna NULL_EMOJI
	        this.emisor = emisor;
	        this.receptor = receptor;
	        this.fechaHora = fechaHora;
	        this.id = 0;  // Este valor se puede ajustar si es necesario
	    }
	 
	public Mensaje() {
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Usuario getEmisor() {
		return emisor;
	}
	public int getLength() {
		return texto.length();
	}

	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}

	public Usuario getReceptor() {
		return receptor;
	}

	public void setReceptor(Usuario receptor) {
		this.receptor = receptor;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isEmisor(Usuario user) {
		return user.equals(emisor);
	}
	
	public boolean isReceptor(Usuario user) {
		return user.equals(receptor);
	}
	
	public boolean isFromAGroup() {
		return emisor.equals(receptor);
	}
	
	public int getEmoji() {
		return emoji;
	}
	public void setEmoji(int emoji) {
		this.emoji = emoji;
	}
	
	public boolean isAMessage() {
		return texto != null || texto.isEmpty();
	}
	
	public boolean isAnEmoji() {
		return emoji >= 0;
	}
	
	
	@Override
	public String toString() {
		return "Mensaje [texto=" + texto + ", emisor=" + emisor.getNombre() + ", receptor=" + receptor.getNombre() + ", fechaHora=" + fechaHora
				+ ", id=" + id + "]";
	}
	

}
