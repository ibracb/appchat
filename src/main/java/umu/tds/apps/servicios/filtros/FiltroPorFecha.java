package umu.tds.apps.servicios.filtros;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import umu.tds.apps.dominio.Mensaje;
import umu.tds.apps.dominio.Usuario;

public class FiltroPorFecha implements Filtro {
	
	private LocalDate fecha;
	
	public FiltroPorFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	@Override
	public Set<Mensaje> filtrar(Usuario usuario) {
		return usuario.getContactosIndividuales().stream()
				.flatMap(contacto -> contacto.getMensajes().stream())
				.filter(mensaje -> mensaje.getMomentoEnvio().getDayOfMonth() == fecha.getDayOfMonth()
					&& mensaje.getMomentoEnvio().getMonth().equals(fecha.getMonth())
					&& mensaje.getMomentoEnvio().getYear() == fecha.getYear())
				.collect(Collectors.toCollection(TreeSet::new));
	}

	@Override
	public boolean seFiltra() {
		return fecha != null;
	}

}
