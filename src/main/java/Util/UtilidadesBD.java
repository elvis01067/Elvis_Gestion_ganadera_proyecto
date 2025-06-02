package Util;

import java.sql.*;

public class UtilidadesBD {
    
    public static void cerrarResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void cerrarStatement(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void cerrarConexion(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void rollback(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static String escaparCaracteresEspeciales(String str) {
        if (str == null) {
            return null;
        }
        return str.replace("'", "''")
                 .replace("\\", "\\\\")
                 .replace("%", "\\%")
                 .replace("_", "\\_");
    }
    
    public static boolean isNumero(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isFechaValida(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            return false;
        }
        try {
            java.sql.Date.valueOf(fecha);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    public static java.sql.Date parseFecha(String fecha) {
        try {
            return java.sql.Date.valueOf(fecha);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
