package Datos;

import Modelo.Ganado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GanadoDAO {
    private static final String SQL_INSERT = 
        "INSERT INTO ganado (arete, raza, fecha_nacimiento, genero, peso, estado_salud, proposito, estado_productivo) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_UPDATE = 
        "UPDATE ganado SET arete = ?, raza = ?, fecha_nacimiento = ?, genero = ?, peso = ?, " +
        "estado_salud = ?, proposito = ?, estado_productivo = ? WHERE id = ?";
    
    private static final String SQL_DELETE = "UPDATE ganado SET estado = false WHERE id = ?";
    
    private static final String SQL_SELECT = "SELECT * FROM ganado WHERE estado = true";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM ganado WHERE id = ? AND estado = true";
    private static final String SQL_SELECT_BY_ARETE = "SELECT * FROM ganado WHERE arete = ? AND estado = true";

    public boolean insertar(Ganado ganado) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            
            stmt.setString(1, ganado.getArete());
            stmt.setString(2, ganado.getRaza());
            stmt.setDate(3, new java.sql.Date(ganado.getFechaNacimiento().getTime()));
            stmt.setString(4, ganado.getGenero());
            stmt.setDouble(5, ganado.getPeso());
            stmt.setString(6, ganado.getEstadoSalud());
            stmt.setString(7, ganado.getProposito());
            stmt.setString(8, ganado.getEstadoProductivo());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Ganado ganado) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setString(1, ganado.getArete());
            stmt.setString(2, ganado.getRaza());
            stmt.setDate(3, new java.sql.Date(ganado.getFechaNacimiento().getTime()));
            stmt.setString(4, ganado.getGenero());
            stmt.setDouble(5, ganado.getPeso());
            stmt.setString(6, ganado.getEstadoSalud());
            stmt.setString(7, ganado.getProposito());
            stmt.setString(8, ganado.getEstadoProductivo());
            stmt.setInt(9, ganado.getId());
            
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

    public List<Ganado> listarTodos() {
        List<Ganado> ganados = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT)) {
            
            while (rs.next()) {
                ganados.add(extraerGanado(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ganados;
    }

    public Ganado buscarPorArete(String arete) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ARETE)) {
            
            stmt.setString(1, arete);
            System.out.println("Buscando ganado con arete: " + arete); // Debug
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Ganado ganado = extraerGanado(rs);
                    System.out.println("Ganado encontrado: " + ganado.getArete()); // Debug
                    return ganado;
                } else {
                    System.out.println("No se encontró ganado con arete: " + arete); // Debug
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar ganado: " + e.getMessage()); // Debug
            e.printStackTrace();
        }
        
        return null;
    }

    public Ganado obtenerPorId(int id) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extraerGanado(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    private Ganado extraerGanado(ResultSet rs) throws SQLException {
        Ganado ganado = new Ganado();
        ganado.setId(rs.getInt("id"));
        String arete = rs.getString("arete");
        System.out.println("Leyendo arete de la BD: " + arete); // Debug
        ganado.setArete(arete);
        ganado.setRaza(rs.getString("raza"));
        ganado.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
        ganado.setGenero(rs.getString("genero"));
        ganado.setPeso(rs.getDouble("peso"));
        ganado.setEstadoSalud(rs.getString("estado_salud"));
        ganado.setProposito(rs.getString("proposito"));
        ganado.setEstadoProductivo(rs.getString("estado_productivo"));
        return ganado;
    }
}
