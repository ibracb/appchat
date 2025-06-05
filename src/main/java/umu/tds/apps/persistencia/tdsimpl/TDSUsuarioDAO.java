package umu.tds.apps.persistencia.tdsimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.ContactoIndividual;
import umu.tds.apps.dominio.Grupo;
import umu.tds.apps.dominio.Usuario;
import umu.tds.apps.persistencia.PoolDAO;
import umu.tds.apps.persistencia.UsuarioDAO;

/**
 * Clase para manejo de persistencia de usuarios en TDS.
 */
public class TDSUsuarioDAO implements UsuarioDAO {
	
	
	/**
	 * Identificador de la entidad de usuario.
	 */
	private static final String ENTIDAD_USUARIO = "Usuario";
	
	/**
	 * Identificador de la propiedad de id.
	 */
	private static final String PROPIEDAD_ID = "id";
	
	/**
	 * Identificador de la propiedad de nombre.
	 */
	private static final String PROPIEDAD_NOMBRE = "nombre";
	
	/**
	 * Identificador de la propiedad de fecha de nacimiento.
	 */
	private static final String PROPIEDAD_FECHA_NACIMIENTO = "fechaNacimiento";
	
	/**
	 * Identificador de la propiedad de fecha de registro.
	 */
	private static final String PROPIEDAD_FECHA_REGISTRO = "fechaRegistro";
	
	/**
	 * Identificador de la propiedad de email.
	 */
	private static final String PROPIEDAD_EMAIL = "email";
	
	/**
	 * Identificador de la propiedad de imagen.
	 */
	private static final String PROPIEDAD_IMAGEN = "imagen";
	
	/**
	 * Identificador de la propiedad de movil.
	 */
	private static final String PROPIEDAD_MOVIL = "movil";
	
	/**
	 * Identificador de la propiedad de contraseña.
	 */
	private static final String PROPIEDAD_CONTRASEÑA = "contraseña";
	
	/**
	 * Identificador de la propiedad de saludo.
	 */
	private static final String PROPIEDAD_SALUDO = "saludo";
	
	/**
	 * Identificador de la propiedad de premium.
	 */
	private static final String PROPIEDAD_PREMIUM = "premium";
	
	/**
	 * Identificador de la propiedad de contactos individuales.
	 */
	private static final String PROPIEDAD_CONTACTOS_INDIVIDUALES = "contactosIndividuales";
	
	/**
	 * Identificador de la propiedad de grupos.
	 */
	private static final String PROPIEDAD_GRUPOS = "grupos";
	
	
	/**
	 * Servicio de Persistencia global propuesto desde la asignatura de TDS.
	 */
	private static ServicioPersistencia servPersistencia;
	
	/**
	 * Formateador de fechas para la base de datos.
	 */
	private SimpleDateFormat dateFormat;
	
	/**
	 * Instancia global de TDSUsuarioaDAO.
	 */
	private static TDSUsuarioDAO INSTANCE;
	
	/**
	 * Devuelve la instancia global de TDSUsuarioaDAO. Si es null, la inicializa.
	 * @return el único TDSUsuarioDAO.
	 */
	public static TDSUsuarioDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TDSUsuarioDAO();
		}
		return INSTANCE;
	}
	
	/**
	 * Inicialización privada de TDSUsuarioDAO.
	 */
	private TDSUsuarioDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	@Override
	public void create(Usuario usuario) {
	    Entidad eUsuario = null;
	    boolean noRegistrar = true;
	    try {
	        eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
	    } catch (NullPointerException e) {
	        noRegistrar = false;
	    }
	    if (usuario.getId() <= 0) {
	        noRegistrar = false;
	    }
	    if (noRegistrar) {
	        return; // Ya existe el usuario, no registrar
	    }

	    TDSContactoIndividualDAO adaptadorContactoIndividual = TDSContactoIndividualDAO.getInstance();
	    TDSGrupoDAO adaptadorGrupo = TDSGrupoDAO.getInstance();

	    // SOLO crea contactos que NO tengan ID válido
	    usuario.getContactos().forEach(contacto -> {
	        if (contacto instanceof ContactoIndividual) {
	            if (contacto.getId() <= 0) {
	                adaptadorContactoIndividual.create((ContactoIndividual) contacto);
	            }
	        } else if (contacto instanceof Grupo) {
	            if (contacto.getId() <= 0) {
	                adaptadorGrupo.create((Grupo) contacto);
	            }
	        }
	    });

	    eUsuario = new Entidad();
	    eUsuario.setNombre(ENTIDAD_USUARIO);
	    eUsuario.setPropiedades(Arrays.asList(
	        new Propiedad(PROPIEDAD_ID, String.valueOf(usuario.getId())),
	        new Propiedad(PROPIEDAD_NOMBRE, usuario.getNombre()),
	        new Propiedad(PROPIEDAD_FECHA_NACIMIENTO,
	            dateFormat.format(Date.from(usuario.getFechaNacimiento().atStartOfDay(ZoneId.systemDefault()).toInstant()))),
	        new Propiedad(PROPIEDAD_FECHA_REGISTRO,
	            dateFormat.format(Date.from(usuario.getFechaRegistro().atStartOfDay(ZoneId.systemDefault()).toInstant()))),
	        new Propiedad(PROPIEDAD_EMAIL, usuario.getEmail()),
	        new Propiedad(PROPIEDAD_IMAGEN, usuario.getImagen()),
	        new Propiedad(PROPIEDAD_MOVIL, usuario.getMovil()),
	        new Propiedad(PROPIEDAD_CONTRASEÑA, usuario.getContraseña()),
	        new Propiedad(PROPIEDAD_SALUDO, usuario.getSaludo()),
	        new Propiedad(PROPIEDAD_PREMIUM, String.valueOf(usuario.isPremium())),
	        new Propiedad(PROPIEDAD_CONTACTOS_INDIVIDUALES, getIdsContactosIndividuales(usuario.getContactos())),
	        new Propiedad(PROPIEDAD_GRUPOS, getIdsGrupos(usuario.getContactos()))
	    ));

	    eUsuario = servPersistencia.registrarEntidad(eUsuario);
	    usuario.setId(eUsuario.getId());
	}



	
	@Override
	public void delete(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		usuario.getContactos().forEach(contacto -> {
			if (contacto instanceof ContactoIndividual) {
				TDSContactoIndividualDAO.getInstance().delete((ContactoIndividual) contacto);
			} else if (contacto instanceof Grupo) {
				TDSGrupoDAO.getInstance().delete((Grupo) contacto);
			}
		});
		servPersistencia.borrarEntidad(eUsuario);
	}

	@Override
	public void update(Usuario usuario) {
	    Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
	    if (eUsuario == null) {
	        return;
	    }

	    TDSContactoIndividualDAO adaptadorContactoIndividual = TDSContactoIndividualDAO.getInstance();
	    TDSGrupoDAO adaptadorGrupo = TDSGrupoDAO.getInstance();

	    // Crear contactos sin ID antes de actualizar propiedades
	    usuario.getContactos().forEach(contacto -> {
	        if (contacto instanceof ContactoIndividual) {
	            if (contacto.getId() <= 0) {
	                adaptadorContactoIndividual.create((ContactoIndividual) contacto);
	            }
	        } else if (contacto instanceof Grupo) {
	            if (contacto.getId() <= 0) {
	                adaptadorGrupo.create((Grupo) contacto);
	            }
	        }
	    });

	    eUsuario.getPropiedades().forEach(propiedad -> {
	        if(propiedad.getNombre().equals(PROPIEDAD_NOMBRE)) {
	            propiedad.setValor(usuario.getNombre());
	        }
	        else if(propiedad.getNombre().equals(PROPIEDAD_FECHA_NACIMIENTO)) {
	            Date dateNacimiento = Date.from(usuario.getFechaNacimiento().atStartOfDay(ZoneId.systemDefault()).toInstant());
	            propiedad.setValor(dateFormat.format(dateNacimiento));
	        }
	        else if(propiedad.getNombre().equals(PROPIEDAD_FECHA_REGISTRO)) {
	            Date dateRegistro = Date.from(usuario.getFechaRegistro().atStartOfDay(ZoneId.systemDefault()).toInstant());
	            propiedad.setValor(dateFormat.format(dateRegistro));
	        }
	        else if(propiedad.getNombre().equals(PROPIEDAD_EMAIL)) {
	            propiedad.setValor(usuario.getEmail());
	        }
	        else if(propiedad.getNombre().equals(PROPIEDAD_IMAGEN)) {
	            propiedad.setValor(usuario.getImagen());
	        }
	        else if(propiedad.getNombre().equals(PROPIEDAD_MOVIL)) {
	            propiedad.setValor(usuario.getMovil());
	        }
	        else if(propiedad.getNombre().equals(PROPIEDAD_CONTRASEÑA)) {
	            propiedad.setValor(usuario.getContraseña());
	        }
	        else if(propiedad.getNombre().equals(PROPIEDAD_SALUDO)) {
	            propiedad.setValor(usuario.getSaludo());
	        }
	        else if(propiedad.getNombre().equals(PROPIEDAD_PREMIUM)) {
	            propiedad.setValor(String.valueOf(usuario.isPremium()));
	        }
	        else if(propiedad.getNombre().equals(PROPIEDAD_CONTACTOS_INDIVIDUALES)) {
	            propiedad.setValor(getIdsContactosIndividuales(usuario.getContactos()));
	        }
	        else if(propiedad.getNombre().equals(PROPIEDAD_GRUPOS)) {
	            propiedad.setValor(getIdsGrupos(usuario.getContactos()));
	        }
	        servPersistencia.modificarPropiedad(propiedad);
	    });
	}


	@Override
	public Usuario get(int id) {
		if(PoolDAO.INSTANCE.contains(id)) {
			return (Usuario) PoolDAO.INSTANCE.getObject(id);
		}
		String nombre;
		LocalDate fechaNacimiento = null;
		LocalDate fechaRegistro = null;
		String email;
		String imagen;
		String movil;
		String contraseña;
		Optional<String> saludo;
		boolean premium;
		Set<ContactoIndividual> contactos;
		Set<Grupo> grupos;
		Entidad eUsuario = servPersistencia.recuperarEntidad(id);
		nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_NOMBRE);
		try {
			fechaNacimiento = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_FECHA_NACIMIENTO)).toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDate();
			fechaRegistro = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_FECHA_REGISTRO)).toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDate();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		email = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_EMAIL);
		imagen = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_IMAGEN);
		movil = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_MOVIL);
		contraseña = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_CONTRASEÑA);
		saludo = Optional.ofNullable(servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_SALUDO));
		premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_PREMIUM));
		Usuario usuario = new Usuario(nombre, fechaNacimiento, email, imagen, movil, contraseña, saludo.toString());
		usuario.setId(id);
		usuario.setFechaRegistro(fechaRegistro);
		usuario.setPremium(premium);
		PoolDAO.INSTANCE.addObject(usuario.getId(), usuario);
		contactos = getContactosIndividualesFromIds(servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_CONTACTOS_INDIVIDUALES));
		contactos.stream()
			.filter(contacto -> contacto != null)
			.forEach(contacto -> usuario.addContacto(contacto));
		grupos = getGruposFromIds(servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPIEDAD_GRUPOS));
		grupos.stream()
			.filter(grupo -> grupo != null)
			.forEach(grupo -> usuario.createGrupo(grupo.getNombre(), grupo.getImagen(), grupo.getMiembros().toArray(new ContactoIndividual[0])));
		return usuario;
	}

	@Override
	public Set<Usuario> getAll() {
		Set<Usuario> usuarios = new HashSet<Usuario>();
		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades(ENTIDAD_USUARIO);
		eUsuarios.stream()
			.forEach(eUsuario -> usuarios.add(get(eUsuario.getId())));
		return usuarios;
	}
	
	/**
	 * Devuelve los ids de los contactos individuales a partir de un conjunto de contactos.
	 * @param contactos - Conjunto de contactos.
	 * @return ids de los contactos individuales.
	 */
	private String getIdsContactosIndividuales(Set<Contacto> contactos) {
		return contactos.stream()
				.filter(contacto -> contacto instanceof ContactoIndividual)
				.map(contacto -> String.valueOf(contacto.getId()))
				.collect(Collectors.joining(TDSContactosUtilsDAO.ESPACIO_EN_BLANCO));
	}
	
	/**
	 * Devuelve los contactos individuales a partir de un conjunto de ids.
	 * @param lineas - Conjunto de ids.
	 * @return contactos individuales.
	 */
	private Set<ContactoIndividual> getContactosIndividualesFromIds(String lineas) {
		TDSContactoIndividualDAO adaptadorContactoIndividual = TDSContactoIndividualDAO.getInstance();
		return Arrays.stream(lineas.split(TDSContactosUtilsDAO.ESPACIO_EN_BLANCO))
				.filter(s -> !s.isBlank())
				.map(Integer::valueOf)
				.map(adaptadorContactoIndividual::get)
				.filter(contacto -> contacto instanceof ContactoIndividual)
				.collect(Collectors.toSet());
	}
	
	/**
	 * Devuelve los ids de los grupos a partir de un conjunto de contactos.
	 * @param contactos - Conjunto de contactos.
	 * @return ids de los grupos.
	 */
	private String getIdsGrupos(Set<Contacto> contactos) {
		return contactos.stream()
				.filter(contacto -> contacto instanceof Grupo)
				.map(contacto -> String.valueOf(contacto.getId()))
				.collect(Collectors.joining(TDSContactosUtilsDAO.ESPACIO_EN_BLANCO));
	}
	
	/**
	 * Devuelve los grupos a partir de un conjunto de ids.
	 * @param lineas - Conjunto de ids.
	 * @return grupos.
	 */
	private Set<Grupo> getGruposFromIds(String lineas) {
		TDSGrupoDAO adaptadorGrupo = TDSGrupoDAO.getInstance();
		return Arrays.stream(lineas.split(TDSContactosUtilsDAO.ESPACIO_EN_BLANCO))
			.filter(s -> !s.isBlank())	
			.map(Integer::valueOf)
			.map(adaptadorGrupo::get)
			.filter(contacto -> contacto instanceof Grupo)
			.collect(Collectors.toSet());
	}

}