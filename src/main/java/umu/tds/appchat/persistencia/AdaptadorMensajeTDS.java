package umu.tds.appchat.persistencia;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.appchat.models.ContactoIndividual;
import umu.tds.appchat.models.Mensaje;
import umu.tds.appchat.models.Usuario;

public class AdaptadorMensajeTDS implements IAdaptadorMensajeDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorMensajeTDS unicaInstancia = null;

	public static AdaptadorMensajeTDS getUnicaInstancia() { // patrón singleton
		if (unicaInstancia == null)
			return new AdaptadorMensajeTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorMensajeTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public boolean registrarMensaje(Mensaje mensaje) {
	    if (mensaje.getId() > 0) { // Si ya tiene ID, no lo registra nuevamente
	        Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getId());
	        if (eMensaje != null) return false;
	    }

	    Entidad eMensaje = new Entidad();
	    eMensaje.setNombre("mensaje");
	    eMensaje.setPropiedades(new ArrayList<>(List.of(
	        new Propiedad("texto", mensaje.getTexto()),
	        new Propiedad("emisor", obtenerCodigoUsuario(mensaje.getEmisor())),
	        new Propiedad("receptor", obtenerCodigoUsuario(mensaje.getReceptor())),
	        new Propiedad("fechaHora", mensaje.getFechaHora().toString()),
	        new Propiedad("emoji", Integer.toString(mensaje.getEmoji()))
	    )));

	    eMensaje = servPersistencia.registrarEntidad(eMensaje);
	    mensaje.setId(eMensaje.getId());
	    return true;
	}

	public Mensaje recuperarMensaje(int id) {
		Entidad eMensaje = servPersistencia.recuperarEntidad(id);
		Mensaje mensaje = new Mensaje();
		mensaje.setId(eMensaje.getId());
		mensaje.setTexto(servPersistencia.recuperarPropiedadEntidad(eMensaje, "texto"));
		mensaje.setEmoji(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje,"emoji")));
		String idEmisor = servPersistencia.recuperarPropiedadEntidad(eMensaje, "emisor");
		String idReceptor = servPersistencia.recuperarPropiedadEntidad(eMensaje, "receptor");
		mensaje.setFechaHora(LocalDateTime.parse(servPersistencia.recuperarPropiedadEntidad(eMensaje, "fechaHora")));
		mensaje.setReceptor(obtenerUsuarioCodigo(idReceptor));
		mensaje.setEmisor(obtenerUsuarioCodigo(idEmisor));

		return mensaje;

	}

	public List<Mensaje> recuperarTodasMensajes() {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		List<Entidad> eMensajes = servPersistencia.recuperarEntidades("mensaje");
		for (Entidad eMensaje : eMensajes) {
			mensajes.add(recuperarMensaje(eMensaje.getId()));
		}
		return mensajes;

	}

	// -------------------Funciones auxiliares---------------------------
	public String obtenerCodigoUsuario(Usuario usuario) {
		return usuario.getTelefono() + " ";
	}

	public String obtenerCodigoContacto(ContactoIndividual contacto) {
		return contacto.getTelefono() + " ";
	}

	public ContactoIndividual obtenerContactoCodigo(String id) {
		return AdaptadorContactoTDS.getUnicaInstancia().recuperarContactoTelefono(id.trim());
	}

	// TODO: Dejarlo legible: Dado un id retorna el usuario correspondiente
	public Usuario obtenerUsuarioCodigo(String id) {
		return AdaptadorUsuarioTDS.getUnicaInstancia().recuperarUsuarioTelefono(id.trim());
	}

}
