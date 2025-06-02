package Datos;

import Modelo.Factura;
import Modelo.DetalleFactura;
import Modelo.Cliente;
import Modelo.Proveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO {
    private static final String SQL_INSERT_FACTURA = 
        "INSERT INTO facturas (numero_factura, fecha, tipo_documento, cliente_id, proveedor_id, " +
        "subtotal, iva_porcentaje, iva_valor, descuento_porcentaje, descuento_valor, total, estado) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_INSERT_DETALLE = 
        "INSERT INTO detalles_factura (factura_id, concepto, cantidad, precio_unitario, subtotal) " +
        "VALUES (?, ?, ?, ?, ?)";
    
    private static final String SQL_SELECT = 
        "SELECT f.*, c.nombre as cliente_nombre, c.apellido as cliente_apellido, " +
        "p.nombre as proveedor_nombre, p.apellido as proveedor_apellido " +
        "FROM facturas f " +
        "LEFT JOIN clientes c ON f.cliente_id = c.id " +
        "LEFT JOIN proveedores p ON f.proveedor_id = p.id " +
        "WHERE f.estado = true";
    
    private static final String SQL_SELECT_DETALLES = 
        "SELECT * FROM detalles_factura WHERE factura_id = ?";

    private final ClienteDAO clienteDAO;
    private final ProveedorDAO proveedorDAO;

    public FacturaDAO() {
        this.clienteDAO = new ClienteDAO();
        this.proveedorDAO = new ProveedorDAO();
    }

    public boolean insertar(Factura factura) {
        Connection conn = null;
        PreparedStatement stmtFactura = null;
        PreparedStatement stmtDetalle = null;
        ResultSet generatedKeys = null;
        
        try {
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false);
            
            // Insertar factura
            // Validar que los campos requeridos no sean nulos
            if (factura.getNumeroFactura() == null || factura.getNumeroFactura().trim().isEmpty()) {
                throw new SQLException("El número de factura es requerido");
            }
            if (factura.getTipoDocumento() == null || factura.getTipoDocumento().trim().isEmpty()) {
                throw new SQLException("El tipo de documento es requerido");
            }
            if ("FACTURA_VENTA".equals(factura.getTipoDocumento()) && factura.getClienteId() == null) {
                throw new SQLException("El cliente es requerido para facturas de venta");
            }
            if ("FACTURA_COMPRA".equals(factura.getTipoDocumento()) && factura.getProveedorId() == null) {
                throw new SQLException("El proveedor es requerido para facturas de compra");
            }

            stmtFactura = conn.prepareStatement(SQL_INSERT_FACTURA, Statement.RETURN_GENERATED_KEYS);
            stmtFactura.setString(1, factura.getNumeroFactura());
            stmtFactura.setTimestamp(2, new Timestamp(factura.getFecha().getTime()));
            stmtFactura.setString(3, factura.getTipoDocumento());
            
            // Establecer cliente_id o proveedor_id según el tipo de factura
            if ("FACTURA_VENTA".equals(factura.getTipoDocumento())) {
                stmtFactura.setInt(4, factura.getClienteId());
                stmtFactura.setNull(5, Types.INTEGER);
            } else {
                stmtFactura.setNull(4, Types.INTEGER);
                stmtFactura.setInt(5, factura.getProveedorId());
            }

            stmtFactura.setDouble(6, factura.getSubtotal());
            stmtFactura.setDouble(7, factura.getIvaPorcentaje());
            stmtFactura.setDouble(8, factura.getIvaValor());
            stmtFactura.setDouble(9, factura.getDescuentoPorcentaje());
            stmtFactura.setDouble(10, factura.getDescuentoValor());
            stmtFactura.setDouble(11, factura.getTotal());
            stmtFactura.setBoolean(12, factura.isEstado());
            
            if (stmtFactura.executeUpdate() == 0) {
                throw new SQLException("Crear factura falló, no se insertaron registros.");
            }
            
            generatedKeys = stmtFactura.getGeneratedKeys();
            if (generatedKeys.next()) {
                int facturaId = generatedKeys.getInt(1);
                
                // Insertar detalles
                stmtDetalle = conn.prepareStatement(SQL_INSERT_DETALLE);
                for (DetalleFactura detalle : factura.getDetalles()) {
                    stmtDetalle.setInt(1, facturaId);
                    stmtDetalle.setString(2, detalle.getConcepto());
                    stmtDetalle.setInt(3, detalle.getCantidad());
                    stmtDetalle.setDouble(4, detalle.getPrecioUnitario());
                    stmtDetalle.setDouble(5, detalle.getSubtotal());
                    stmtDetalle.executeUpdate();
                }
            }
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
            
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (stmtDetalle != null) stmtDetalle.close();
                if (stmtFactura != null) stmtFactura.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Factura> listarTodas() {
        List<Factura> facturas = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT)) {
            
            while (rs.next()) {
                Factura factura = extraerFactura(rs);
                cargarDetalles(factura);
                facturas.add(factura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return facturas;
    }

    private Factura extraerFactura(ResultSet rs) throws SQLException {
        Factura factura = new Factura();
        factura.setId(rs.getInt("id"));
        factura.setNumeroFactura(rs.getString("numero_factura"));
        factura.setFecha(rs.getTimestamp("fecha"));
        factura.setTipoDocumento(rs.getString("tipo_documento"));
        
        Integer clienteId = (Integer) rs.getObject("cliente_id");
        if (clienteId != null) {
            factura.setClienteId(clienteId);
            factura.setCliente(clienteDAO.obtenerPorId(clienteId));
        }
        
        Integer proveedorId = (Integer) rs.getObject("proveedor_id");
        if (proveedorId != null) {
            factura.setProveedorId(proveedorId);
            factura.setProveedor(proveedorDAO.obtenerPorId(proveedorId));
        }
        
        factura.setSubtotal(rs.getDouble("subtotal"));
        factura.setIvaPorcentaje(rs.getDouble("iva_porcentaje"));
        factura.setIvaValor(rs.getDouble("iva_valor"));
        factura.setDescuentoPorcentaje(rs.getDouble("descuento_porcentaje"));
        factura.setDescuentoValor(rs.getDouble("descuento_valor"));
        factura.setTotal(rs.getDouble("total"));
        factura.setEstado(rs.getBoolean("estado"));
        
        return factura;
    }

    private void cargarDetalles(Factura factura) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_DETALLES)) {
            
            stmt.setInt(1, factura.getId());
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DetalleFactura detalle = new DetalleFactura();
                    detalle.setId(rs.getInt("id"));
                    detalle.setFacturaId(rs.getInt("factura_id"));
                    detalle.setConcepto(rs.getString("concepto"));
                    detalle.setCantidad(rs.getInt("cantidad"));
                    detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));
                    detalle.setSubtotal(rs.getDouble("subtotal"));
                    factura.getDetalles().add(detalle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String generarNumeroFactura() {
        String numeroFactura = null;
        String sql = "SELECT MAX(CAST(SUBSTRING(numero_factura, 5) AS UNSIGNED)) as ultimo FROM facturas";
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                int ultimo = rs.getInt("ultimo");
                numeroFactura = String.format("FAC-%06d", ultimo + 1);
            } else {
                numeroFactura = "FAC-000001";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return numeroFactura;
    }
}
