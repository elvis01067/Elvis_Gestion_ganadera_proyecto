package Util;

import Datos.ConexionDB;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseInitializer {
    public static void initializeDatabase() {
        try {
            System.out.println("Iniciando inicialización de la base de datos...");
            
            String sql;
            // Leer el archivo SQL
            try (InputStream is = DatabaseInitializer.class.getResourceAsStream("/db/init_admin.sql");
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                
                if (is == null) {
                    System.err.println("No se pudo encontrar el archivo init_admin.sql");
                    return;
                }
                
                sql = reader.lines().collect(Collectors.joining("\n"));
            }
            
            System.out.println("SQL a ejecutar:");
            System.out.println(sql);
            
            // Ejecutar el SQL
            try (Connection conn = ConexionDB.getConnection();
                 Statement stmt = conn.createStatement()) {
                
                for (String statement : sql.split(";")) {
                    if (!statement.trim().isEmpty()) {
                        System.out.println("Ejecutando: " + statement);
                        stmt.execute(statement);
                    }
                }
                System.out.println("Base de datos inicializada correctamente");
            }
            
        } catch (Exception e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
