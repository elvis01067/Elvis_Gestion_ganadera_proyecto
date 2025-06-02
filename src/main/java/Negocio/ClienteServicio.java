package Negocio;

import Datos.ClienteDAO;
import Modelo.Cliente;
import java.util.List;
import java.util.Date;

public class ClienteServicio {
    private final ClienteDAO clienteDAO;
    
    public ClienteServicio() {
        this.clienteDAO = new ClienteDAO();
    }
    
    public boolean registrarCliente(Cliente cliente) {
        // Validaciones básicas
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if (cliente.getCedulaRuc() == null || cliente.getCedulaRuc().trim().isEmpty()) {
            throw new IllegalArgumentException("La cédula/RUC es requerida");
        }
        
        // Asegurar que la fecha de registro esté establecida
        if (cliente.getFechaRegistro() == null) {
            cliente.setFechaRegistro(new Date());
        }
        
        // Asegurar que el estado esté establecido
        cliente.setEstado(true);
        
        return clienteDAO.insertar(cliente);
    }
    
    public boolean actualizarCliente(Cliente cliente) {
        // Validaciones básicas
        if (cliente.getId() <= 0) {
            throw new IllegalArgumentException("ID de cliente inválido");
        }
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if (cliente.getCedulaRuc() == null || cliente.getCedulaRuc().trim().isEmpty()) {
            throw new IllegalArgumentException("La cédula/RUC es requerida");
        }
        
        return clienteDAO.actualizar(cliente);
    }
    
    public boolean eliminarCliente(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de cliente inválido");
        }
        return clienteDAO.eliminar(id);
    }
    
    public List<Cliente> listarTodosClientes() {
        return clienteDAO.listarTodos();
    }
    
    public Cliente obtenerClientePorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de cliente inválido");
        }
        return clienteDAO.obtenerPorId(id);
    }
}
