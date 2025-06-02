package Negocio;

import Datos.PastoDAO;
import Modelo.Pasto;
import java.sql.SQLException;
import java.util.List;

public class PastoServicio {
    private final PastoDAO pastoDAO;
    
    public PastoServicio() {
        this.pastoDAO = new PastoDAO();
    }
    
    public List<Pasto> obtenerPastos() throws Exception {
        try {
            List<Pasto> pastos = pastoDAO.listarPastos();
            if (pastos.isEmpty()) {
                System.out.println("No se encontraron pastos registrados");
            }
            return pastos;
        } catch (Exception e) {
            System.err.println("Error en el servicio al obtener pastos: " + e.getMessage());
            throw new Exception("Error al obtener la lista de pastos");
        }
    }
    
    public void guardarPasto(Pasto pasto) throws Exception {
        try {
            validarPasto(pasto);
            pastoDAO.insertarPasto(pasto);
        } catch (SQLException e) {
            System.err.println("Error en la base de datos al guardar pasto: " + e.getMessage());
            throw new Exception("Error al guardar el pasto en la base de datos");
        } catch (Exception e) {
            System.err.println("Error en el servicio al guardar pasto: " + e.getMessage());
            throw e;
        }
    }
    
    public void actualizarPasto(Pasto pasto) throws Exception {
        try {
            validarPasto(pasto);
            pastoDAO.actualizarPasto(pasto);
        } catch (SQLException e) {
            System.err.println("Error en la base de datos al actualizar pasto: " + e.getMessage());
            throw new Exception("Error al actualizar el pasto en la base de datos");
        } catch (Exception e) {
            System.err.println("Error en el servicio al actualizar pasto: " + e.getMessage());
            throw e;
        }
    }
    
    public void eliminarPasto(int id) throws Exception {
        try {
            pastoDAO.eliminarPasto(id);
        } catch (SQLException e) {
            System.err.println("Error en la base de datos al eliminar pasto: " + e.getMessage());
            throw new Exception("Error al eliminar el pasto de la base de datos");
        } catch (Exception e) {
            System.err.println("Error en el servicio al eliminar pasto: " + e.getMessage());
            throw e;
        }
    }
    
    private void validarPasto(Pasto pasto) throws Exception {
        if (pasto.getNombre() == null || pasto.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del pasto es requerido");
        }
        
        if (pasto.getAreaHectareas() <= 0) {
            throw new Exception("El área debe ser mayor a 0 hectáreas");
        }
        
        if (pasto.getCapacidadAnimales() <= 0) {
            throw new Exception("La capacidad de animales debe ser mayor a 0");
        }
        
        if (pasto.getEstadoPasto() == null || pasto.getEstadoPasto().trim().isEmpty()) {
            throw new Exception("El estado del pasto es requerido");
        }
    }
}
