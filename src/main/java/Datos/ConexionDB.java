/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/SistemaGestionGanadera";
    private static final String USER = "root";
    private static final String PASS = "0106707359";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            System.out.println("Intentando conectar a la base de datos...");
            System.out.println("URL: " + URL);
            System.out.println("Usuario: " + USER);
            
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexión establecida con éxito");
            return conn;
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            if (e.getMessage().contains("Communications link failure")) {
                System.err.println("Verifique que el servidor MySQL esté en ejecución y que el puerto sea correcto");
            } else if (e.getMessage().contains("Access denied")) {
                System.err.println("Verifique el nombre de usuario y contraseña");
            } else if (e.getMessage().contains("Unknown database")) {
                System.err.println("La base de datos no existe");
            }
            throw e;
        }
    }
}