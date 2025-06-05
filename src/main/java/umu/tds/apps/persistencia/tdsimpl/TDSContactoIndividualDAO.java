package umu.tds.apps.persistencia.tdsimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.apps.dominio.ContactoIndividual;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.Usuario;
import umu.tds.apps.persistencia.ContactoIndividualDAO;
import umu.tds.apps.persistencia.PoolDAO;

/**
 * Clase para manejo de persistencia de contactos individuales en TDS.
 */
public class TDSContactoIndividualDAO implements ContactoIndividualDAO {
	
	/**
	 * Nombre de la entidad ContactoIndividual en la base de datos.
	 */
	private static final String ENTIDAD_CONTACTO = "Contacto";
	
	/**
	 * Nombre de la propiedad movil en la base de datos.
	 */
	private static final String PROPIEDAD_MOVIL = "movil";
	
	
	/**
	 * Servicio de Persistencia global propuesto desde la asignatura de TDS.
	 */
	private static ServicioPersistencia servPersistencia;
	
	/**
	 * Instancia global de TDSContactoIndividualDAO.
	 */
	private static TDSContactoIndividualDAO INSTANCE;
	
	/**
	 * Devuelve la instancia global de TDSContactoIndividualDAO. Si es null, la inicializa.
	 * @return el único TDSContactoIndividualDAO.
	 */
	public static TDSContactoIndividualDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TDSContactoIndividualDAO();
		}
		return INSTANCE;
	}
	
	/**
	 * Inicialización privada de TDSContactoIndividualDAO.
	 */
	private TDSContactoIndividualDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	@Override
	public void create(ContactoIndividual contacto) {
	    // Verificar si el contacto ya tiene un ID válido (ya fue persistido)
	    if (contacto.getId() > 0) {
	        try {
	            Entidad eContacto = servPersistencia.recuperarEntidad(contacto.getId());
	            if (eContacto != null) {
	                return; // Ya existe, no necesita ser creado de nuevo
	            }
	        } catch (Exception e) {
	            // Si hay excepción, continuamos con la creación
	        }
	    }
	    
	    // Crear los mensajes primero
	    TDSMensajeDAO adaptadorMensaje = TDSMensajeDAO.getInstance();
	    contacto.getMensajes().forEach(mensaje -> {
	        if (mensaje.getId() <= 0) { // Solo crear mensajes nuevos
	            adaptadorMensaje.create(mensaje);
	        }
	    });
	    
	    // Crear la entidad del contacto
	    Entidad eContacto = new Entidad();
	    eContacto.setNombre(ENTIDAD_CONTACTO);
	    eContacto.setPropiedades(
	        new ArrayList<Propiedad>(Arrays.asList(
	                new Propiedad(TDSContactosUtilsDAO.PROPIEDAD_ID, String.valueOf(contacto.getId())),
	                new Propiedad(TDSContactosUtilsDAO.PROPIEDAD_NOMBRE, contacto.getNombre()),
	                new Propiedad(TDSContactosUtilsDAO.PROPIEDAD_USUARIO, String.valueOf(contacto.getUsuario().getId())),
	                new Propiedad(TDSContactosUtilsDAO.PROPIEDAD_MENSAJES, getIdsMensajes(contacto.getMensajes())),
	                new Propiedad(PROPIEDAD_MOVIL, contacto.getMovil()))));
	    
	    // Registrar en la base de datos
	    eContacto = servPersistencia.registrarEntidad(eContacto);
	    contacto.setId(eContacto.getId());
	}

	@Override
	public void delete(ContactoIndividual contacto) {
		Entidad eContacto = servPersistencia.recuperarEntidad(contacto.getId());
		contacto.getMensajes().stream()
			.forEach(mensaje -> TDSMensajeDAO.getInstance().delete(mensaje));
		servPersistencia.borrarEntidad(eContacto);
	}

	@Override
	public void update(ContactoIndividual contacto) {
		Entidad eContacto = servPersistencia.recuperarEntidad(contacto.getId());
		eContacto.getPropiedades().forEach(propiedad -> {
			if(propiedad.getNombre().equals(TDSContactosUtilsDAO.PROPIEDAD_NOMBRE)) {
				propiedad.setValor(contacto.getNombre());
			}
			else if(propiedad.getNombre().equals(TDSContactosUtilsDAO.PROPIEDAD_USUARIO)) {
				propiedad.setValor(String.valueOf(contacto.getUsuario().getId()));
			}
			else if(propiedad.getNombre().equals(TDSContactosUtilsDAO.PROPIEDAD_MENSAJES)) {
				propiedad.setValor(getIdsMensajes(contacto.getMensajes()));
			}
			else if(propiedad.getNombre().equals(PROPIEDAD_MOVIL)) {
				propiedad.setValor(contacto.getMovil());
			}
			servPersistencia.modificarPropiedad(propiedad);
		});
	}

	@Override
	public ContactoIndividual get(int id) {
		if (PoolDAO.INSTANCE.contains(id)) {
	        return (ContactoIndividual) PoolDAO.INSTANCE.getObject(id);
	    }

	    Entidad eContacto = servPersistencia.recuperarEntidad(id);
	    if (eContacto == null) return null;

	    String nombre = servPersistencia.recuperarPropiedadEntidad(eContacto, TDSContactosUtilsDAO.PROPIEDAD_NOMBRE);
	    String idUsuarioStr = servPersistencia.recuperarPropiedadEntidad(eContacto, TDSContactosUtilsDAO.PROPIEDAD_USUARIO);

	    if (idUsuarioStr == null || idUsuarioStr.trim().isEmpty()) {
	        // No se puede continuar si no hay usuario
	        return null;
	    }

	    int usuarioId = 0;
	    try {
	        usuarioId = Integer.parseInt(idUsuarioStr);
	    } catch (NumberFormatException e) {
	        // Si no es un número válido, simplemente no cargamos este contacto
	        return null;
	    }

	    Usuario usuario = TDSUsuarioDAO.getInstance().get(usuarioId);
	    if (usuario == null) return null;

	    ContactoIndividual contacto = new ContactoIndividual(nombre, usuario);
	    contacto.setId(id);
	    PoolDAO.INSTANCE.addObject(contacto.getId(), contacto);

	    String mensajesStr = servPersistencia.recuperarPropiedadEntidad(eContacto, TDSContactosUtilsDAO.PROPIEDAD_MENSAJES);
	    Set<Mensaje> mensajes = getMensajesFromIds(mensajesStr);
	    contacto.setMensajes(mensajes);

	    return contacto;
	}

	@Override
	public Set<ContactoIndividual> getAll() {
		Set<ContactoIndividual> contactos = new HashSet<ContactoIndividual>();
		List<Entidad> eContactos = servPersistencia.recuperarEntidades(ENTIDAD_CONTACTO);
		eContactos.stream()
			.forEach(eContacto -> contactos.add(get(eContacto.getId())));
		return contactos;
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
	

}
