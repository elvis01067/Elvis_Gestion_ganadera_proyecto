/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author XRLab
 */
public class PersistenceUtil {
    
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("GanaderiaElvis");
    
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
    
    public static void Cerrar(){
        if(emf != null && emf.isOpen()){
            emf.close();
        }
    }
}