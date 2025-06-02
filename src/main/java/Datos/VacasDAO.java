package Datos;

import Modelo.Vacas;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VacasDAO {
    private static final String SQL_INSERT = 
        "INSERT INTO vacas (codigo_identificacion, nombre, raza, fecha_nacimiento, genero, " +
        "peso_kg, estado_salud, proposito, estado_productivo, id_pasto, id_madre, id_padre, " +
        "fecha_adquisicion, precio_compra, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_UPDATE = 
        "UPDATE vacas SET codigo_identificacion=?, nombre=?, raza=?, fecha_nacimiento=?, " +
        "genero=?, peso_kg=?, estado_salud=?, proposito=?, estado_productivo=?, id_pasto=?, " +
        "id_madre=?, id_padre=?, fecha_adquisicion=?, precio_compra=?, estado=? WHERE id=?";
    
    private static final String SQL_DELETE = "UPDATE vacas SET estado=false WHERE id=?";
    
    private static final String SQL_SELECT = "SELECT * FROM vacas WHERE estado=true";
    
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM vacas WHERE id=? AND estado=true";

    public boolean insertar(Vacas vaca) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            
            stmt.setString(1, vaca.getCodigoIdentificacion());
            stmt.setString(2, vaca.getNombre());
            stmt.setString(3, vaca.getRaza());
            stmt.setDate(4, new java.sql.Date(vaca.getFechaNacimiento().getTime()));
            stmt.setString(5, vaca.getGenero());
            stmt.setDouble(6, vaca.getPesoKg());
            stmt.setString(7, vaca.getEstadoSalud());
            stmt.setString(8, vaca.getProposito());
            stmt.setString(9, vaca.getEstadoProductivo());
            stmt.setInt(10, vaca.getIdPasto());
            
            if (vaca.getIdMadre() != null) {
                stmt.setInt(11, vaca.getIdMadre());
            } else {
                stmt.setNull(11, java.sql.Types.INTEGER);
            }
            
            if (vaca.getIdPadre() != null) {
                stmt.setInt(12, vaca.getIdPadre());
            } else {
                stmt.setNull(12, java.sql.Types.INTEGER);
            }
            
            stmt.setDate(13, new java.sql.Date(vaca.getFechaAdquisicion().getTime()));
            stmt.setDouble(14, vaca.getPrecioCompra());
            stmt.setBoolean(15, vaca.isEstado());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Vacas vaca) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setString(1, vaca.getCodigoIdentificacion());
            stmt.setString(2, vaca.getNombre());
            stmt.setString(3, vaca.getRaza());
            stmt.setDate(4, new java.sql.Date(vaca.getFechaNacimiento().getTime()));
            stmt.setString(5, vaca.getGenero());
            stmt.setDouble(6, vaca.getPesoKg());
            stmt.setString(7, vaca.getEstadoSalud());
            stmt.setString(8, vaca.getProposito());
            stmt.setString(9, vaca.getEstadoProductivo());
            stmt.setInt(10, vaca.getIdPasto());
            
            if (vaca.getIdMadre() != null) {
                stmt.setInt(11, vaca.getIdMadre());
            } else {
                stmt.setNull(11, java.sql.Types.INTEGER);
            }
            
            if (vaca.getIdPadre() != null) {
                stmt.setInt(12, vaca.getIdPadre());
            } else {
                stmt.setNull(12, java.sql.Types.INTEGER);
            }
            
            stmt.setDate(13, new java.sql.Date(vaca.getFechaAdquisicion().getTime()));
            stmt.setDouble(14, vaca.getPrecioCompra());
            stmt.setBoolean(15, vaca.isEstado());
            stmt.setInt(16, vaca.getId());
            
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

    public List<Vacas> listarTodas() {
        List<Vacas> vacas = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT)) {
            
            while (rs.next()) {
                vacas.add(extraerVaca(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return vacas;
    }

    public Vacas obtenerPorId(int id) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extraerVaca(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    private Vacas extraerVaca(ResultSet rs) throws SQLException {
        Vacas vaca = new Vacas();
        vaca.setId(rs.getInt("id"));
        vaca.setCodigoIdentificacion(rs.getString("codigo_identificacion"));
        vaca.setNombre(rs.getString("nombre"));
        vaca.setRaza(rs.getString("raza"));
        vaca.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
        vaca.setGenero(rs.getString("genero"));
        vaca.setPesoKg(rs.getDouble("peso_kg"));
        vaca.setEstadoSalud(rs.getString("estado_salud"));
        vaca.setProposito(rs.getString("proposito"));
        vaca.setEstadoProductivo(rs.getString("estado_productivo"));
        vaca.setIdPasto(rs.getInt("id_pasto"));
        
        int idMadre = rs.getInt("id_madre");
        if (!rs.wasNull()) {
            vaca.setIdMadre(idMadre);
        }
        
        int idPadre = rs.getInt("id_padre");
        if (!rs.wasNull()) {
            vaca.setIdPadre(idPadre);
        }
        
        vaca.setFechaAdquisicion(rs.getDate("fecha_adquisicion"));
        vaca.setPrecioCompra(rs.getDouble("precio_compra"));
        vaca.setEstado(rs.getBoolean("estado"));
        
        return vaca;
    }
}
