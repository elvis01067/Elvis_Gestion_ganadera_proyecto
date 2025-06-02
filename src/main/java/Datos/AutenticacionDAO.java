/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;


import Modelo.Usuario;
import java.sql.*;

public class AutenticacionDAO {
    private static final String SQL_LOGIN = 
        "SELECT id, nombre_usuario, rol, estado FROM usuarios " +
        "WHERE nombre_usuario = ? AND contrasena = MD5(?)";

    public Usuario autenticarUsuario(String nombreUsuario, String contrasena) {
        System.out.println("Intentando autenticar usuario: " + nombreUsuario);
        
        try (Connection conn = ConexionDB.getConnection()) {
            System.out.println("Conexión a la base de datos establecida");
            
            try (PreparedStatement stmt = conn.prepareStatement(SQL_LOGIN)) {
                stmt.setString(1, nombreUsuario);
                stmt.setString(2, contrasena);
                
                System.out.println("Ejecutando consulta SQL para usuario: " + nombreUsuario);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Usuario encontrado en la base de datos");
                        int id = rs.getInt("id");
                        String rol = rs.getString("rol");
                        boolean estado = rs.getBoolean("estado");
                        
                        System.out.println("ID: " + id);
                        System.out.println("Rol: " + rol);
                        System.out.println("Estado: " + estado);
                        
                        Usuario usuario = new Usuario(id, nombreUsuario, rol, estado);
                        return usuario;
                    } else {
                        System.out.println("No se encontró el usuario en la base de datos");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());
            e.printStackTrace();
            
            // Verificar si es un error de conexión
            if (e.getMessage().contains("Communications link failure")) {
                System.err.println("Error de conexión a la base de datos. Verifique que el servidor MySQL esté en ejecución.");
            }
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}