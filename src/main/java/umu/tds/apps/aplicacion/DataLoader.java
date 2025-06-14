package umu.tds.apps.aplicacion;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import umu.tds.apps.dominio.Usuario;
import umu.tds.apps.persistencia.DAOException;
import umu.tds.apps.persistencia.FactoriaDAO;
import umu.tds.apps.persistencia.UsuarioDAO;

public class DataLoader {

    public static void cargarDatosIniciales() {
        UsuarioDAO usuarioDAO = null;
		try {
			usuarioDAO = FactoriaDAO.getInstance().getUsuarioDAO();
		} catch (DAOException e) {
		}
        Set<Usuario> usuarios = usuarioDAO.getAll();

        if (usuarios.isEmpty()) {
        	
        	/*
             * CREDENCIALES PARA INICIAR SESIÓN EN LA CUENTA
             * TELEFONO: 21
             * CONTRASEÑA: m
            */
            Usuario maria = new Usuario("maria bm", LocalDate.of(2004,Month.JULY,15), "m@um.es", "", "21", "m", "");
            usuarioDAO.create(maria);
            
            /*
             * CREDENCIALES PARA INICIAR SESIÓN EN LA CUENTA
             * TELEFONO: 31
             * CONTRASEÑA: i
            */
            Usuario ibra = new Usuario("ibra cb", LocalDate.of(2003,Month.MAY,20), "i@um.es", "", "31", "i", "");
            usuarioDAO.create(ibra);
            
            /*
             * CREDENCIALES PARA INICIAR SESIÓN EN LA CUENTA
             * TELEFONO: 12
             * CONTRASEÑA: m
            */
            Usuario maria2 = new Usuario("maria bm2", LocalDate.of(2005,Month.JULY,15), "m@um.es", "", "12", "m", "");
            usuarioDAO.create(maria2);
            
            /*
             * CREDENCIALES PARA INICIAR SESIÓN EN LA CUENTA
             * TELEFONO: 13
             * CONTRASEÑA: i
            */
            Usuario ibra2 = new Usuario("ibra cb2", LocalDate.of(2003,Month.MAY,10), "i@um.es", "", "13", "i", "");
            usuarioDAO.create(ibra2);
        }
    }
}
