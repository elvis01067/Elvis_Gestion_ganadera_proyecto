package Util;

import java.util.HashMap;
import java.util.Map;

public class RazaImagenes {
    private static final Map<String, String> IMAGENES_RAZA = new HashMap<>();
    
    static {
        IMAGENES_RAZA.put("Holstein", "/imagenes/razas/holstein.jpg");
        IMAGENES_RAZA.put("Brahman", "/imagenes/razas/brahman.jpg");
        IMAGENES_RAZA.put("Angus", "/imagenes/razas/angus.jpg");
        IMAGENES_RAZA.put("Hereford", "/imagenes/razas/hereford.jpg");
        IMAGENES_RAZA.put("Simmental", "/imagenes/razas/simmental.jpg");
        IMAGENES_RAZA.put("Jersey", "/imagenes/razas/jersey.jpg");
        IMAGENES_RAZA.put("Charolais", "/imagenes/razas/charolais.jpg");
        IMAGENES_RAZA.put("Limousin", "/imagenes/razas/limousin.jpg");
        IMAGENES_RAZA.put("Gyr", "/imagenes/razas/gyr.jpg");
        IMAGENES_RAZA.put("Normando", "/imagenes/razas/normando.jpg");
    }
    
    public static String getImagenRuta(String raza) {
        return IMAGENES_RAZA.getOrDefault(raza, null);
    }
    
    public static String[] getRazas() {
        return IMAGENES_RAZA.keySet().toArray(new String[0]);
    }
}
