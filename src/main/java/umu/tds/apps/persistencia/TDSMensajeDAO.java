package umu.tds.apps.persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.TipoMensaje;

/**
 * Clase para manejo de persistencia de manejos en TDS.
 */
public class TDSMensajeDAO implements MensajeDAO {
	
	/**
	 * Identificador de la entidad Mensaje.
	 */
	private static final String ENTIDAD_MENSAJE = "Mensaje";
	
	/**
	 * Identificador de la propiedad id.
	 */
	private static final String PROPIEDAD_ID = "id";
	
	/**
	 * Identificador de la propiedad momentoEnvio.
	 */
	private static final String PROPIEDAD_MOMENTO_ENVIO = "momentoEnvio";
	
	/**
	 * Identificador de la propiedad texto.
	 */
	private static final String PROPIEDAD_TEXTO = "texto";
	
	/**
	 * Identificador de la propiedad emoticono.
	 */
	private static final String PROPIEDAD_EMOTICONO = "emoticono";
	
	/**
	 * Identificador de la propiedad tipo.
	 */
	private static final String PROPIEDAD_TIPO = "tipo";
	
	/**
	 * Servicio de Persistencia global propuesto desde la asignatura de TDS.
	 */
	private static ServicioPersistencia servPersistencia;
	
	/**
	 * Formateador de fechas para la base de datos.
	 */
	private SimpleDateFormat dateFormat;
	
	/**
	 * Instancia global de TDSMensajeDAO.
	 */
	private static TDSMensajeDAO INSTANCE;
	
	/**
	 * Devuelve la instancia global de TDSMensajeDAO. Si es null, la inicializa.
	 * @return el único TDSMensajeDAO.
	 */
	public static TDSMensajeDAO getInstance() {
		if (INSTANCE == null) {
			return new TDSMensajeDAO();
		}
		else {
			return INSTANCE;
		}
	}
	
	/**
	 * Inicialización privada de TDSMensajeDAO.
	 */
	private TDSMensajeDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	@Override
	public void create(Mensaje mensaje) {
		Entidad eMensaje = null;
		boolean noRegistrar = false;
		try {
			eMensaje = servPersistencia.recuperarEntidad(mensaje.getId());
		} catch (NullPointerException e) {
			noRegistrar = true;
		}
		if(noRegistrar) {
			return;
		}
		eMensaje = new Entidad();
		eMensaje.setNombre(ENTIDAD_MENSAJE);
		eMensaje.setPropiedades(Arrays.asList(
				new Propiedad(PROPIEDAD_ID, String.valueOf(mensaje.getId())),
				new Propiedad(PROPIEDAD_MOMENTO_ENVIO, dateFormat.format(mensaje.getMomentoEnvio())),
				new Propiedad(PROPIEDAD_TEXTO, mensaje.getTexto()),
				new Propiedad(PROPIEDAD_EMOTICONO, String.valueOf(mensaje.getEmoticono())),
				new Propiedad(PROPIEDAD_TIPO, mensaje.getTipo().toString().toLowerCase())));
		eMensaje = servPersistencia.registrarEntidad(eMensaje);
		mensaje.setId(eMensaje.getId());
	}
	
	@Override
	public void delete(Mensaje mensaje) {
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getId());
		servPersistencia.borrarEntidad(eMensaje);
	}
	
	@Override
	public void update(Mensaje mensaje) {
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getId());
		eMensaje.getPropiedades().forEach(propiedad -> {
			if(propiedad.getNombre().equals(PROPIEDAD_MOMENTO_ENVIO)) {
				propiedad.setValor(dateFormat.format(mensaje.getMomentoEnvio()));
			}
			else if(propiedad.getNombre().equals(PROPIEDAD_TEXTO)) {
				propiedad.setValor(mensaje.getTexto());
			}
			else if(propiedad.getNombre().equals(PROPIEDAD_EMOTICONO)) {
				propiedad.setValor(String.valueOf(mensaje.getEmoticono()));
			}
			else if(propiedad.getNombre().equals(PROPIEDAD_TIPO)) {
				propiedad.setValor(mensaje.getTipo().toString().toLowerCase());
			}
			servPersistencia.modificarPropiedad(propiedad);
		});
	}
	
	@Override
	public Mensaje get(int id) {
		if(PoolDAO.INSTANCE.contains(id)) {
			return (Mensaje) PoolDAO.INSTANCE.getObject(id);
		}
		LocalDateTime momentoEnvio = null;
		String texto;
		int emoticono;
		TipoMensaje tipo;
		Entidad eMensaje = servPersistencia.recuperarEntidad(id);
		try {
			momentoEnvio = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPIEDAD_MOMENTO_ENVIO)).toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDateTime();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPIEDAD_TEXTO);
		emoticono = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPIEDAD_EMOTICONO));
		tipo = TipoMensaje.valueOf(servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPIEDAD_TIPO));
		Mensaje mensaje = new Mensaje(texto, emoticono, tipo);
		mensaje.setId(id);
		mensaje.setMomentoEnvio(momentoEnvio);
		PoolDAO.INSTANCE.addObject(mensaje.getId(), mensaje);
		return mensaje;
	}

	@Override
	public Set<Mensaje> getAll() {
		Set<Mensaje> mensajes = new HashSet<Mensaje>();
		List<Entidad> eMensajes = servPersistencia.recuperarEntidades(ENTIDAD_MENSAJE);
		eMensajes.stream()
			.forEach(eMensaje -> mensajes.add(get(eMensaje.getId())));
		return mensajes;
	}

}
