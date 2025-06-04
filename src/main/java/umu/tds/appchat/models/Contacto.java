package umu.tds.appchat.models;

public abstract class Contacto {
	private static String IMAGEN_POR_DEFECTO = "src.main.resources.persona";
	
	private String nombre;
	private final int usuario;
	private String imagen;
	private int id;

    public Contacto(String nombre, int usuario, String imagen) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.id = 0;
        this.imagen = imagen;
    }

    public Contacto(String nombre, int usuario) {
        this(nombre, usuario, null);
    }
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getUsuario() {
		return usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	public String getImagen() {
		return imagen != null ? imagen : IMAGEN_POR_DEFECTO;
	}
	
	public boolean hasImage() {
		return imagen!=null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true; // Son el mismo objeto

		if (obj == null || getClass() != obj.getClass())
			return false; // Clases diferentes
		Contacto other = (Contacto) obj;

		return this.usuario == other.usuario && this.nombre.equals(other.nombre);
	}



}
