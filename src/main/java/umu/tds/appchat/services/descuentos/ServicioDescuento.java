package umu.tds.appchat.services.descuentos;

import java.util.HashMap;
import java.util.Map;
import umu.tds.appchat.models.Usuario;

public class ServicioDescuento {
	private final int MENSAJES_NECESARIOS_PARA_DESCUENTO = 1000;
	private final double PRECIO_BASE_PREMIUN = 14.99;
    private final Map<String, Descuento> estrategiasDescuento;
    

    public ServicioDescuento() {
    	estrategiasDescuento = new HashMap<>();

        // Registrar las estrategias disponibles
        estrategiasDescuento.put("bienvenida", new DescuentoFecha());
        estrategiasDescuento.put("fidelidad", new DescuentoMensaje());
        estrategiasDescuento.put("porDefecto", new DescuentoPorDefecto());
    }

    public double calcularPrecioFinal(Usuario user) {
    	
        String tipoDescuento = determinarDescuentoAplicable(user);
        
        // Obtenemos la estrategia correspondiente
        Descuento estrategia = estrategiasDescuento.get(tipoDescuento);
        if (estrategia == null) {
            throw new RuntimeException("Tipo de descuento no válido");
        }
        return  estrategia.calcularDescuento(PRECIO_BASE_PREMIUN);
    }
    
    public String determinarDescuentoAplicable(Usuario user) {
    	if (user.getNumMensajesEnviadosEsteMes() > MENSAJES_NECESARIOS_PARA_DESCUENTO )
    		return "fidelidad";
    	if ( user.esNuevo())
    		return "bienvenida";

    	return "porDefecto";
    }
}
