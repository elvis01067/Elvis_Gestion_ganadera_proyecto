package Datos;

import Modelo.Proveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {
    private static final String SQL_INSERT = 
        "INSERT INTO proveedores (nombre, apellido, cedula_ruc, direccion, telefono, email, tipo_producto, fecha_registro, estado) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_UPDATE = 
        "UPDATE proveedores SET nombre=?, apellido=?, cedula_ruc=?, direccion=?, telefono=?, " +
        "email=?, tipo_producto=?, fecha_registro=?, estado=? WHERE id=?";
    
    private static final String SQL_DELETE = "UPDATE proveedores SET estado=false WHERE id=?";
    
    private static final String SQL_SELECT = "SELECT * FROM proveedores WHERE estado=true";
    
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM proveedores WHERE id=? AND estado=true";

    public boolean insertar(Proveedor proveedor) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getApellido());
            stmt.setString(3, proveedor.getCedulaRuc());
            stmt.setString(4, proveedor.getDireccion());
            stmt.setString(5, proveedor.getTelefono());
            stmt.setString(6, proveedor.getEmail());
            stmt.setString(7, proveedor.getTipoProducto());
            stmt.setDate(8, new java.sql.Date(proveedor.getFechaRegistro().getTime()));
            stmt.setBoolean(9, proveedor.isEstado());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Proveedor proveedor) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getApellido());
            stmt.setString(3, proveedor.getCedulaRuc());
            stmt.setString(4, proveedor.getDireccion());
            stmt.setString(5, proveedor.getTelefono());
            stmt.setString(6, proveedor.getEmail());
            stmt.setString(7, proveedor.getTipoProducto());
            stmt.setDate(8, new java.sql.Date(proveedor.getFechaRegistro().getTime()));
            stmt.setBoolean(9, proveedor.isEstado());
            stmt.setInt(10, proveedor.getId());
            
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

    public List<Proveedor> listarTodos() {
        List<Proveedor> proveedores = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT)) {
            
            while (rs.next()) {
                proveedores.add(extraerProveedor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return proveedores;
    }

    public Proveedor obtenerPorId(int id) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extraerProveedor(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    private Proveedor extraerProveedor(ResultSet rs) throws SQLException {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(rs.getInt("id"));
        proveedor.setNombre(rs.getString("nombre"));
        proveedor.setApellido(rs.getString("apellido"));
        proveedor.setCedulaRuc(rs.getString("cedula_ruc"));
        proveedor.setDireccion(rs.getString("direccion"));
        proveedor.setTelefono(rs.getString("telefono"));
        proveedor.setEmail(rs.getString("email"));
        proveedor.setTipoProducto(rs.getString("tipo_producto"));
        proveedor.setFechaRegistro(rs.getDate("fecha_registro"));
        proveedor.setEstado(rs.getBoolean("estado"));
        return proveedor;
    }
}
