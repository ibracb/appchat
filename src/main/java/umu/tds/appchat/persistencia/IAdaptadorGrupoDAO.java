package umu.tds.appchat.persistencia;

import java.util.Map;

import umu.tds.appchat.models.Grupo;


public interface IAdaptadorGrupoDAO {
	public boolean registrarGrupo(Grupo grupo);

	public Grupo recuperarGrupo(int id);

	public Map<String, Grupo> recuperarTodosGrupos();

	public boolean existeGrupo(Grupo g);

	public boolean agregarOEliminarContacto(Grupo grupo);
}
