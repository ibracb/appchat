package umu.tds.appchat.persistencia;

public class TDSFactoriaDAO extends FactoriaDAO {

	@Override
	public IAdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuarioTDS.getUnicaInstancia();
	}

	public IAdaptadorContactoDAO getContactoDAO() {
		return AdaptadorContactoTDS.getUnicaInstancia();
	}
	public IAdaptadorMensajeDAO getMensajeDAO() {
		return AdaptadorMensajeTDS.getUnicaInstancia();
	}
	public IAdaptadorGrupoDAO getGrupoDAO() {
		return AdaptadorGrupoTDS.getUnicaInstancia();
	}

}
