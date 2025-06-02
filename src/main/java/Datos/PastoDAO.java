package Datos;

import Modelo.Pasto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PastoDAO {
    private static final String SQL_SELECT = "SELECT * FROM pastos WHERE estado = 1";
    private static final String SQL_INSERT = 
        "INSERT INTO pastos (nombre, descripcion, area_hectareas, estado_pasto, " +
        "capacidad_animales, fecha_ultima_rotacion, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = 
        "UPDATE pastos SET nombre = ?, descripcion = ?, area_hectareas = ?, " +
        "estado_pasto = ?, capacidad_animales = ?, fecha_ultima_rotacion = ?, " +
        "estado = ? WHERE id = ?";
    private static final String SQL_DELETE = "UPDATE pastos SET estado = 0 WHERE id = ?";

    public List<Pasto> listarPastos() {
        List<Pasto> pastos = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT)) {
            
            while (rs.next()) {
                Pasto pasto = new Pasto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("area_hectareas"),
                    rs.getString("estado_pasto"),
                    rs.getInt("capacidad_animales"),
                    rs.getDate("fecha_ultima_rotacion"),
                    rs.getBoolean("estado")
                );
                pastos.add(pasto);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar pastos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return pastos;
    }

    public void insertarPasto(Pasto pasto) throws SQLException {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, pasto.getNombre());
            stmt.setString(2, pasto.getDescripcion());
            stmt.setDouble(3, pasto.getAreaHectareas());
            stmt.setString(4, pasto.getEstadoPasto());
            stmt.setInt(5, pasto.getCapacidadAnimales());
            stmt.setDate(6, pasto.getFechaUltimaRotacion() != null ? 
                new java.sql.Date(pasto.getFechaUltimaRotacion().getTime()) : null);
            stmt.setBoolean(7, pasto.isEstado());
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pasto.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void actualizarPasto(Pasto pasto) throws SQLException {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setString(1, pasto.getNombre());
            stmt.setString(2, pasto.getDescripcion());
            stmt.setDouble(3, pasto.getAreaHectareas());
            stmt.setString(4, pasto.getEstadoPasto());
            stmt.setInt(5, pasto.getCapacidadAnimales());
            stmt.setDate(6, pasto.getFechaUltimaRotacion() != null ? 
                new java.sql.Date(pasto.getFechaUltimaRotacion().getTime()) : null);
            stmt.setBoolean(7, pasto.isEstado());
            stmt.setInt(8, pasto.getId());
            
            stmt.executeUpdate();
        }
    }

    public void eliminarPasto(int id) throws SQLException {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
