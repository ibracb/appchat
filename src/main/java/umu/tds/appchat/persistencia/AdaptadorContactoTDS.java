package umu.tds.appchat.persistencia;

import umu.tds.appchat.models.ContactoIndividual;
import tds.driver.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import beans.Entidad;
import beans.Propiedad;

public class AdaptadorContactoTDS implements IAdaptadorContactoDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorContactoTDS unicaInstancia = null;

	public static AdaptadorContactoTDS getUnicaInstancia() { // patrón singleton
		if (unicaInstancia == null)
			return new AdaptadorContactoTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorContactoTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* Cuando se registra un contacto se le asigna un identificador único */
	public boolean registrarContacto(ContactoIndividual contacto) {
        if (contacto.getNombre() == null || contacto.getTelefono() == null) {
            throw new IllegalArgumentException("El contacto debe tener un nombre y un teléfono válidos.");
        }

		Entidad eContacto = null;
		if (contactoYaRegistrado(contacto)) return false;
		Boolean existeContacto = AdaptadorContactoTDS.getUnicaInstancia()
			    .recuperarTodosContactos()
			    .values()
			    .stream()
			    .anyMatch(c -> c.getTelefono().equals(contacto.getTelefono()) && c.getId() == contacto.getId()); 
		
		if (Boolean.FALSE.equals(existeContacto)) {
			// Se crea una nueva entidad para el contacto
			eContacto = new Entidad();
			eContacto.setNombre("contacto");
			eContacto.setPropiedades(new ArrayList<>(List.of(
					new Propiedad("nombre", contacto.getNombre()),
					new Propiedad("telefono", contacto.getTelefono()),
					new Propiedad("usuario", String.valueOf(contacto.getUsuario()))
			// Se guarda el String del contacto
			)));

			// Se registra la entidad y se asigna el ID al contacto
			eContacto = servPersistencia.registrarEntidad(eContacto);
			contacto.setId(eContacto.getId());
			return true;
		}

		return false;
	}

	public ContactoIndividual recuperarContacto(int id) {
		// Se recupera la entidad del contacto a partir del ID
		if (PoolDAO.INSTANCE.contains(id))
			return (ContactoIndividual) PoolDAO.INSTANCE.getObject(id);
		
		Entidad eContacto = servPersistencia.recuperarEntidad(id);
		String nombre = servPersistencia.recuperarPropiedadEntidad(eContacto, "nombre");
		String telefono = servPersistencia.recuperarPropiedadEntidad(eContacto, "telefono");
		int usuario = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eContacto, "usuario"));

		// Se crea el objeto Contacto con la propiedad recuperada
		ContactoIndividual contacto = new ContactoIndividual(nombre, telefono, usuario);
		contacto.setId(id);
		PoolDAO.INSTANCE.addObject(id, contacto);
		// Aquí puedes agregar el contacto al pool si es necesario

		return contacto;
	}
	
	@Override
	public Map<String, ContactoIndividual> recuperarTodosContactos() {
		List<Entidad> eContactos = servPersistencia.recuperarEntidades("contacto");
		HashMap<String, ContactoIndividual> contactos = new HashMap<>();
		for (Entidad eContacto : eContactos) {
			ContactoIndividual contacto = recuperarContacto(eContacto.getId());
			contactos.put(contacto.getTelefono(), contacto);
		}
		return contactos;
	}

	public boolean eliminarContacto(ContactoIndividual contacto) {
		// 1. Se recupera la entidad del contacto desde el servicio de persistencia
		// usando su ID
		Entidad eContacto = servPersistencia.recuperarEntidad(contacto.getId());

		// 2. Se elimina la entidad del contacto de la base de datos
		return servPersistencia.borrarEntidad(eContacto);
	}

	@Override
	public boolean contactoYaRegistrado(ContactoIndividual c) {
		Entidad eContacto = null;
		try {
			eContacto = servPersistencia.recuperarEntidad(c.getId());
		} catch (NullPointerException e) {
		}

		return eContacto != null;
	}
	
	@Override
	public ContactoIndividual recuperarContactoTelefono(String tlf) {
		HashMap<String, ContactoIndividual> c = (HashMap<String, ContactoIndividual>) recuperarTodosContactos();
		return c.get(tlf);
	}

}
