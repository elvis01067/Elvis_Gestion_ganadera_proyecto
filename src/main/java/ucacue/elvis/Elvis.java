/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ucacue.elvis;

import Presentacion.SistemaLogin;
import Util.DatabaseInitializer;

public class Elvis {
    public static void main(String[] args) {
        try {
            System.out.println("Inicializando base de datos...");
            DatabaseInitializer.initializeDatabase();
            System.out.println("Base de datos inicializada, iniciando sistema...");
            
            SistemaLogin.main(args);
        } catch (Exception e) {
            System.err.println("Error al iniciar el sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}