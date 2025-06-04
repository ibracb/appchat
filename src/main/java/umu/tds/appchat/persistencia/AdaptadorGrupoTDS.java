package umu.tds.appchat.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.appchat.models.ContactoIndividual;
import umu.tds.appchat.models.Grupo;

public class AdaptadorGrupoTDS implements IAdaptadorGrupoDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorGrupoTDS unicaInstancia = null;

	public static AdaptadorGrupoTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorGrupoTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorGrupoTDS() {
		AdaptadorGrupoTDS.servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	
	@Override
	public boolean registrarGrupo(Grupo grupo) {
		Entidad eGrupo = null;
		try {
			eGrupo = servPersistencia.recuperarEntidad(grupo.getId());
		} catch (NullPointerException e) {
			System.err.println("No existe el grupo");
		}
		if (eGrupo != null)
			return false;
		// 2. Se registran sus objetos referenciados si no lo están ya.

		agregarContactosIndividuales(grupo.getContactos());

		// 3. Se crea la entidad (con getID() se puede obtener el id para asignarlo al
		// objeto)
		eGrupo = new Entidad();
		eGrupo.setNombre("grupo"); // setTipo() hubiese sido más apropiado

		// 4. Se crean y añaden las propiedades a la entidad creada.
		eGrupo.setPropiedades(new ArrayList<>(Arrays.asList(new Propiedad("nombre", grupo.getNombre()),
				new Propiedad("usuario",  String.valueOf(grupo.getUsuario())),
				new Propiedad("imagen", grupo.getImagen()),
				new Propiedad("contactos", obtenerIDsContactos(grupo.getContactos())
				))));

		// 5. Se registra la entidad y se asocia id al objeto almacenado.
		eGrupo = servPersistencia.registrarEntidad(eGrupo);
		grupo.setId(eGrupo.getId());
		return true;
	
	}
	
	public void agregarContactosIndividuales(List<ContactoIndividual> contactos) {
		for (ContactoIndividual c : contactos) {
			try {
				if (!FactoriaDAO.getInstancia().getContactoDAO().contactoYaRegistrado(c)) {
					FactoriaDAO.getInstancia().getContactoDAO().registrarContacto(c);
				}
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
	}

	public String obtenerIDsContactos(List<ContactoIndividual> contactos) {
		// líneas de venta ya tienen el código dado por el servicio de persistencia
		String codes = "";
		for (ContactoIndividual contacto : contactos) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(contacto.getId());
			stringBuilder.append(" ");
			codes += stringBuilder.toString();
		}
		return codes.trim();
	}
	
	@Override
	public Grupo recuperarGrupo(int id) {
		
		Entidad eGrupo = servPersistencia.recuperarEntidad(id);
		String nombre, usuario, imagen;
		nombre = servPersistencia.recuperarPropiedadEntidad(eGrupo, "nombre");
		usuario= servPersistencia.recuperarPropiedadEntidad(eGrupo, "usuario");
		imagen = servPersistencia.recuperarPropiedadEntidad(eGrupo, "imagen");
		List<ContactoIndividual> contactos = AdaptadorUsuarioTDS.getUnicaInstancia().obtenerContactosDesdeIds(
				servPersistencia.recuperarPropiedadEntidad(eGrupo, "contactos"));
		// 3. Se crea el objeto, se inicializa con propiedades anteriores y se añade al
		// pool si
		// es necesario
		Grupo grupo = new Grupo(nombre, Integer.parseInt(usuario), contactos, imagen);
		grupo.setId(id);
		PoolDAO.INSTANCE.addObject(id, grupo);

		// 4. Se recuperan los objetos referenciados y se actualiza el objeto
		// Aqui habria que recuperar los contactos
		// Primero se recuperan las propiedades de la entidad que no son objetos
		return grupo;
	}

	@Override
	public Map<String, Grupo> recuperarTodosGrupos() {
		List<Entidad> eGrupos = servPersistencia.recuperarEntidades("grupo");
		HashMap<String, Grupo> grupos = new HashMap<>();
		for (Entidad eGrupo : eGrupos) {
			Grupo grupo = recuperarGrupo(eGrupo.getId());
			grupos.put(grupo.getNombre(), grupo);
		}
		return grupos;
	}

	@Override
	public boolean existeGrupo(Grupo g) {
		return false;
	}

	public boolean agregarOEliminarContacto(Grupo grupo) {
		Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getId());
		List<ContactoIndividual> contactos = grupo.getContactos();
		for (Propiedad prop : eGrupo.getPropiedades()) {
			if (prop.getNombre().equals("contactos")) {
				prop.setValor(obtenerIDsContactos(contactos));
				servPersistencia.modificarPropiedad(prop);
				return true;
			}
		}

		return false;
	}

}
