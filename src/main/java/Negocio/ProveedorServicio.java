package Negocio;

import Datos.ProveedorDAO;
import Modelo.Proveedor;
import java.util.List;

public class ProveedorServicio {
    private final ProveedorDAO proveedorDAO;
    
    public ProveedorServicio() {
        this.proveedorDAO = new ProveedorDAO();
    }
    
    public boolean registrarProveedor(Proveedor proveedor) {
        // Validaciones básicas
        if (proveedor.getNombre() == null || proveedor.getNombre().trim().isEmpty()) {
            return false;
        }
        if (proveedor.getApellido() == null || proveedor.getApellido().trim().isEmpty()) {
            return false;
        }
        if (proveedor.getCedulaRuc() == null || proveedor.getCedulaRuc().trim().isEmpty()) {
            return false;
        }
        if (proveedor.getTipoProducto() == null || proveedor.getTipoProducto().trim().isEmpty()) {
            return false;
        }
        
        return proveedorDAO.insertar(proveedor);
    }
    
    public boolean actualizarProveedor(Proveedor proveedor) {
        if (proveedor.getId() <= 0) {
            return false;
        }
        return proveedorDAO.actualizar(proveedor);
    }
    
    public boolean eliminarProveedor(int id) {
        if (id <= 0) {
            return false;
        }
        return proveedorDAO.eliminar(id);
    }
    
    public List<Proveedor> listarProveedores() {
        return proveedorDAO.listarTodos();
    }
    
    public Proveedor obtenerProveedor(int id) {
        if (id <= 0) {
            return null;
        }
        return proveedorDAO.obtenerPorId(id);
    }
}
