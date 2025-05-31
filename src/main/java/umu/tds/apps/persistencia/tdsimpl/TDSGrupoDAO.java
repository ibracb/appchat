package umu.tds.apps.persistencia.tdsimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.dominio.ContactoIndividual;
import umu.tds.apps.dominio.Grupo;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.persistencia.GrupoDAO;
import umu.tds.apps.persistencia.PoolDAO;

/**
 * Clase para manejo de persistencia de grupos en TDS.
 */
public class TDSGrupoDAO implements GrupoDAO {
	
	/**
	 * Nombre de la propiedad id en la base de datos.
	 */
	private static final String ENTIDAD_GRUPO = "Grupo";
	
	/**
	 * Nombre de la propiedad imagen en la base de datos.
	 */
	private static final String PROPIEDAD_IMAGEN = "imagen";
	
	/**
	 * Nombre de la propiedad contactos en la base de datos.
	 */
	private static final String PROPIEDAD_MIEMBROS = "miembros";
	
	/**
	 * Servicio de Persistencia global propuesto desde la asignatura de TDS.
	 */
	private static ServicioPersistencia servPersistencia;
	
	/**
	 * Instancia global de TDSGrupoDAO.
	 */
	private static TDSGrupoDAO INSTANCE;
	
	/**
	 * Devuelve la instancia global de TDSGrupoDAO. Si es null, la inicializa.
	 * @return el único TDSGrupoDAO.
	 */
	public static TDSGrupoDAO getInstance() {
		if (INSTANCE == null) {
			return new TDSGrupoDAO();
		}
		else {
			return INSTANCE;
		}
	}
	
	/**
	 * Inicialización privada de TDSGrupoDAO.
	 */
	private TDSGrupoDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	@Override
	public void create(Grupo grupo) {
		Entidad eGrupo = null;
		boolean noRegistrar = true;
		try {
			eGrupo = servPersistencia.recuperarEntidad(grupo.getId());
		} catch (NullPointerException e) {
			noRegistrar = false;
		}
		if(noRegistrar) {
			return;
		}
		TDSMensajeDAO adaptadorMensaje = TDSMensajeDAO.getInstance();
		grupo.getMensajes().forEach(mensaje -> {
			adaptadorMensaje.create(mensaje);
		});
		eGrupo = new Entidad();
		eGrupo.setNombre(ENTIDAD_GRUPO);
		eGrupo.setPropiedades(
			new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(TDSContactosUtilsDAO.PROPIEDAD_ID, String.valueOf(grupo.getId())),
				new Propiedad(TDSContactosUtilsDAO.PROPIEDAD_NOMBRE, grupo.getNombre()),
				new Propiedad(TDSContactosUtilsDAO.PROPIEDAD_MENSAJES, getIdsMensajes(grupo.getMensajes())),
				new Propiedad(PROPIEDAD_IMAGEN, grupo.getImagen()),
				new Propiedad(PROPIEDAD_MIEMBROS, getIdsContactos(grupo.getMiembros())))));
		servPersistencia.registrarEntidad(eGrupo);
		grupo.setId(eGrupo.getId());
	}

	@Override
	public void delete(Grupo grupo) {
		Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getId());
		grupo.getMensajes().stream()
			.forEach(mensaje -> TDSMensajeDAO.getInstance().delete(mensaje));
		servPersistencia.borrarEntidad(eGrupo);
	}

	@Override
	public void update(Grupo grupo) {
		Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getId());
		eGrupo.getPropiedades().forEach(propiedad -> {
			if(propiedad.getNombre().equals(TDSContactosUtilsDAO.PROPIEDAD_NOMBRE)) {
				propiedad.setValor(grupo.getNombre());
			}
			else if(propiedad.getNombre().equals(TDSContactosUtilsDAO.PROPIEDAD_MENSAJES)) {
				propiedad.setValor(getIdsMensajes(grupo.getMensajes()));
			}
			else if(propiedad.getNombre().equals(PROPIEDAD_IMAGEN)) {
				propiedad.setValor(grupo.getImagen());
			}
			else if(propiedad.getNombre().equals(PROPIEDAD_MIEMBROS)) {
				propiedad.setValor(getIdsContactos(grupo.getMiembros()));
			}
			servPersistencia.modificarPropiedad(propiedad);
		});
	}

	@Override
	public Grupo get(int id) {
		if(PoolDAO.INSTANCE.contains(id)) {
			return (Grupo) PoolDAO.INSTANCE.getObject(id);
		}
		String nombre;
		String imagen;
		Set<Mensaje> mensajes;
		Set<ContactoIndividual> miembros;
		Entidad eGrupo = servPersistencia.recuperarEntidad(id);
		nombre = servPersistencia.recuperarPropiedadEntidad(eGrupo, TDSContactosUtilsDAO.PROPIEDAD_NOMBRE);
		imagen = servPersistencia.recuperarPropiedadEntidad(eGrupo, PROPIEDAD_IMAGEN);
		Grupo grupo = new Grupo(nombre, imagen, new ContactoIndividual[0]);
		grupo.setId(id);
		PoolDAO.INSTANCE.addObject(grupo.getId(), grupo);
		miembros = getContactosFromIds(servPersistencia.recuperarPropiedadEntidad(eGrupo, PROPIEDAD_MIEMBROS));
		miembros.stream()
			.forEach(miembro -> grupo.addMiembro(miembro));
		mensajes = getMensajesFromIds(servPersistencia.recuperarPropiedadEntidad(eGrupo, TDSContactosUtilsDAO.PROPIEDAD_MENSAJES));
		grupo.setMensajes(mensajes);
		return grupo;
	}

	@Override
	public Set<Grupo> getAll() {
		Set<Grupo> grupos = new HashSet<Grupo>();
		List<Entidad> eGrupos = servPersistencia.recuperarEntidades(ENTIDAD_GRUPO);
		eGrupos.stream()
			.forEach(eGrupo -> grupos.add(get(eGrupo.getId())));
		return grupos;
	}
	
	/**
	 * Convierte un conjunto de mensajes en una cadena de ids de mensajes.
	 * @param mensajes conjunto de mensajes.
	 * @return cadena con los ids de los mensajes.
	 */
	private String getIdsMensajes(Set<Mensaje> mensajes) {
		return TDSContactosUtilsDAO.getIdsMensajes(mensajes);
	}
	
	/**
	 * Convierte una cadena de ids de mensajes en un conjunto de mensajes.
	 * @param lineas cadena con los ids de los mensajes.
	 * @return conjunto de mensajes.
	 */
	private Set<Mensaje> getMensajesFromIds(String lineas) {
		return TDSContactosUtilsDAO.getMensajesFromIds(lineas);
	}
	
	/**
	 * Convierte un conjunto de contactos en una cadena de ids de contactos.
	 * @param contactos conjunto de contactos.
	 * @return cadena con los ids de los contactos.
	 */
	private String getIdsContactos(Set<ContactoIndividual> contactos) {
		return contactos.stream()
				.map(contacto -> String.valueOf(contacto.getId()))
				.collect(Collectors.joining(TDSContactosUtilsDAO.ESPACIO_EN_BLANCO));
	}
	
	/**
	 * Convierte una cadena de ids de contactos en un conjunto de contactos.
	 * @param lineas cadena con los ids de los contactos.
	 * @return conjunto de contactos.
	 */
	private Set<ContactoIndividual> getContactosFromIds(String lineas) {
		TDSContactoIndividualDAO adaptadorContacto = TDSContactoIndividualDAO.getInstance();
		return Arrays.stream(lineas.split(TDSContactosUtilsDAO.ESPACIO_EN_BLANCO))
			.map(Integer::valueOf)
			.map(adaptadorContacto::get)
			.collect(Collectors.toSet());
	}

}
