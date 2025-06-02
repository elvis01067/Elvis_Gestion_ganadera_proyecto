package Datos;

import Modelo.Raza;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RazaDAO {
    private static final String SQL_SELECT = "SELECT * FROM razas";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM razas WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO razas (nombre, imagen_url, descripcion) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE razas SET nombre = ?, imagen_url = ?, descripcion = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM razas WHERE id = ?";

    public List<Raza> listarTodos() {
        List<Raza> razas = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT)) {
            
            while (rs.next()) {
                razas.add(extraerRaza(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return razas;
    }

    public Raza obtenerPorId(int id) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extraerRaza(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertar(Raza raza) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, raza.getNombre());
            stmt.setString(2, raza.getImagenUrl());
            stmt.setString(3, raza.getDescripcion());
            
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        raza.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean actualizar(Raza raza) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setString(1, raza.getNombre());
            stmt.setString(2, raza.getImagenUrl());
            stmt.setString(3, raza.getDescripcion());
            stmt.setInt(4, raza.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminar(int id) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Raza extraerRaza(ResultSet rs) throws SQLException {
        Raza raza = new Raza();
        raza.setId(rs.getInt("id"));
        raza.setNombre(rs.getString("nombre"));
        raza.setImagenUrl(rs.getString("imagen_url"));
        raza.setDescripcion(rs.getString("descripcion"));
        return raza;
    }
}
