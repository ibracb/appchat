package umu.tds.apps.servicios.filtros;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import umu.tds.apps.dominio.Contacto;
import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.Usuario;

public class FiltroPorFecha implements Filtro {
	
	private LocalDate fecha;
	
	public FiltroPorFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	@Override
	public Map<Mensaje, Contacto> filtrar(Usuario usuario) {
		return usuario.getContactosIndividuales().stream()
				.flatMap(contacto -> contacto.getMensajes().stream()
				.filter(mensaje -> mensaje.getMomentoEnvio().getDayOfMonth() == fecha.getDayOfMonth()
					&& mensaje.getMomentoEnvio().getMonth().equals(fecha.getMonth())
					&& mensaje.getMomentoEnvio().getYear() == fecha.getYear())
				.map(mensaje -> Map.entry(mensaje, contacto)))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (m1, m2) -> m1, TreeMap::new));
	}

	@Override
	public boolean seFiltra() {
		return fecha != null;
	}

}
