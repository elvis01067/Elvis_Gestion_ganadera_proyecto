package Datos;

import Modelo.RegistroSanitario;
import Modelo.Ganado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Datos.ConexionDB;

public class RegistroSanitarioDAO {
    private static final String SQL_INSERT = 
        "INSERT INTO registros_sanitarios (ganado_id, tipo_registro, fecha, descripcion, " +
        "producto_usado, dosis, proximo_tratamiento, observaciones, estado) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_SELECT_BY_GANADO = 
        "SELECT r.*, g.nombre as ganado_nombre, g.raza as ganado_raza " +
        "FROM registros_sanitarios r " +
        "INNER JOIN ganado g ON r.ganado_id = g.id " +
        "WHERE r.ganado_id = ? AND r.estado = true " +
        "ORDER BY r.fecha DESC";
    
    private static final String SQL_SELECT_BY_NOMBRE = 
        "SELECT r.*, g.nombre as ganado_nombre, g.raza as ganado_raza " +
        "FROM registros_sanitarios r " +
        "INNER JOIN ganado g ON r.ganado_id = g.id " +
        "WHERE g.nombre = ? AND r.estado = true " +
        "ORDER BY r.fecha DESC";

    private final GanadoDAO ganadoDAO;

    public RegistroSanitarioDAO() {
        this.ganadoDAO = new GanadoDAO();
    }

    public boolean insertar(RegistroSanitario registro) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            
            stmt.setInt(1, registro.getGanadoId());
            stmt.setString(2, registro.getTipoRegistro());
            stmt.setDate(3, new java.sql.Date(registro.getFecha().getTime()));
            stmt.setString(4, registro.getDescripcion());
            stmt.setString(5, registro.getProductoUsado());
            stmt.setString(6, registro.getDosis());
            stmt.setDate(7, registro.getProximoTratamiento() != null ? 
                new java.sql.Date(registro.getProximoTratamiento().getTime()) : null);
            stmt.setString(8, registro.getObservaciones());
            stmt.setBoolean(9, registro.isEstado());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<RegistroSanitario> buscarPorGanadoId(int ganadoId) {
        List<RegistroSanitario> registros = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_GANADO)) {
            
            stmt.setInt(1, ganadoId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    registros.add(extraerRegistro(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return registros;
    }

    public List<RegistroSanitario> buscarPorNombreGanado(String nombre) {
        List<RegistroSanitario> registros = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_NOMBRE)) {
            
            stmt.setString(1, nombre);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    registros.add(extraerRegistro(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return registros;
    }

    private RegistroSanitario extraerRegistro(ResultSet rs) throws SQLException {
        RegistroSanitario registro = new RegistroSanitario();
        registro.setId(rs.getInt("id"));
        registro.setGanadoId(rs.getInt("ganado_id"));
        registro.setTipoRegistro(rs.getString("tipo_registro"));
        registro.setFecha(rs.getDate("fecha"));
        registro.setDescripcion(rs.getString("descripcion"));
        registro.setProductoUsado(rs.getString("producto_usado"));
        registro.setDosis(rs.getString("dosis"));
        registro.setProximoTratamiento(rs.getDate("proximo_tratamiento"));
        registro.setObservaciones(rs.getString("observaciones"));
        registro.setEstado(rs.getBoolean("estado"));
        
        // Cargar el objeto Ganado completo
        registro.setGanado(ganadoDAO.obtenerPorId(registro.getGanadoId()));
        
        return registro;
    }
}
