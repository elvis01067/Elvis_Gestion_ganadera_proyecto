package Util;

import java.util.regex.Pattern;

public class Validaciones {
    private static final String PATRON_EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PATRON_CEDULA = "^[0-9]{10}$";
    private static final String PATRON_RUC = "^[0-9]{13}$";
    private static final String PATRON_TELEFONO = "^[0-9]{10}$";
    
    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return Pattern.compile(PATRON_EMAIL).matcher(email).matches();
    }
    
    public static boolean validarCedula(String cedula) {
        if (cedula == null || !cedula.matches(PATRON_CEDULA)) {
            return false;
        }
        
        try {
            int provincia = Integer.parseInt(cedula.substring(0, 2));
            if (provincia < 1 || provincia > 24) {
                return false;
            }
            
            int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
            if (tercerDigito > 6) {
                return false;
            }
            
            int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
            int verificador = Integer.parseInt(cedula.substring(9, 10));
            int suma = 0;
            int resultado;
            
            for (int i = 0; i < coeficientes.length; i++) {
                resultado = Integer.parseInt(cedula.substring(i, i + 1)) * coeficientes[i];
                suma += resultado >= 10 ? resultado - 9 : resultado;
            }
            
            suma = suma % 10 != 0 ? 10 - (suma % 10) : 0;
            
            return suma == verificador;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean validarRUC(String ruc) {
        if (ruc == null || !ruc.matches(PATRON_RUC)) {
            return false;
        }
        
        // Validar los primeros dígitos como cédula
        return validarCedula(ruc.substring(0, 10));
    }
    
    public static boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        return telefono.matches(PATRON_TELEFONO);
    }
    
    public static boolean validarNombre(String nombre) {
        return nombre != null && !nombre.trim().isEmpty() && nombre.length() <= 100;
    }
    
    public static boolean validarMonto(double monto) {
        return monto >= 0 && monto <= 999999.99;
    }
    
    public static boolean validarPeso(double peso) {
        return peso > 0 && peso <= 9999.99;
    }
    
    public static boolean validarArea(double area) {
        return area > 0 && area <= 9999.99;
    }
    
    public static boolean validarCapacidad(int capacidad) {
        return capacidad > 0 && capacidad <= 999;
    }
}
