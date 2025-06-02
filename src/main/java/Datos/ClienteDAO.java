package Datos;

import Modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private static final String SQL_INSERT = 
        "INSERT INTO clientes (nombre, apellido, cedula_ruc, direccion, telefono, email, tipo, fecha_registro, estado) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_UPDATE = 
        "UPDATE clientes SET nombre=?, apellido=?, cedula_ruc=?, direccion=?, telefono=?, " +
        "email=?, tipo=?, fecha_registro=?, estado=? WHERE id=?";
    
    private static final String SQL_DELETE = "UPDATE clientes SET estado=false WHERE id=?";
    
    private static final String SQL_SELECT = "SELECT * FROM clientes WHERE estado=true";
    
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM clientes WHERE id=? AND estado=true";

    public boolean insertar(Cliente cliente) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getCedulaRuc());
            stmt.setString(4, cliente.getDireccion());
            stmt.setString(5, cliente.getTelefono());
            stmt.setString(6, cliente.getEmail());
            stmt.setString(7, cliente.getTipo());
            stmt.setDate(8, new java.sql.Date(cliente.getFechaRegistro().getTime()));
            stmt.setBoolean(9, cliente.isEstado());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Cliente cliente) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getCedulaRuc());
            stmt.setString(4, cliente.getDireccion());
            stmt.setString(5, cliente.getTelefono());
            stmt.setString(6, cliente.getEmail());
            stmt.setString(7, cliente.getTipo());
            stmt.setDate(8, new java.sql.Date(cliente.getFechaRegistro().getTime()));
            stmt.setBoolean(9, cliente.isEstado());
            stmt.setInt(10, cliente.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT)) {
            
            while (rs.next()) {
                clientes.add(extraerCliente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return clientes;
    }

    public Cliente obtenerPorId(int id) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extraerCliente(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    private Cliente extraerCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellido(rs.getString("apellido"));
        cliente.setCedulaRuc(rs.getString("cedula_ruc"));
        cliente.setDireccion(rs.getString("direccion"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setEmail(rs.getString("email"));
        cliente.setTipo(rs.getString("tipo"));
        cliente.setFechaRegistro(rs.getDate("fecha_registro"));
        cliente.setEstado(rs.getBoolean("estado"));

        return cliente;
    }
}
