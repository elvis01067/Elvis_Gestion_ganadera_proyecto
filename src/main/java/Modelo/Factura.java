package Modelo;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Factura {
    private int id;
    private String numeroFactura;
    private Date fecha;
    private String tipoDocumento; // FACTURA_VENTA o FACTURA_COMPRA
    private Integer clienteId;
    private Integer proveedorId;
    private double subtotal;
    private double ivaPorcentaje;
    private double ivaValor;
    private double descuentoPorcentaje;
    private double descuentoValor;
    private double total;
    private boolean estado;
    private List<DetalleFactura> detalles;
    
    // Referencias a objetos completos (no solo IDs)
    private Cliente cliente;
    private Proveedor proveedor;

    public Factura() {
        this.detalles = new ArrayList<>();
        this.fecha = new Date();
        this.estado = true;
    }

    // Getters
    public int getId() { return id; }
    public String getNumeroFactura() { return numeroFactura; }
    public Date getFecha() { return fecha; }
    public String getTipoDocumento() { return tipoDocumento; }
    public Integer getClienteId() { return clienteId; }
    public Integer getProveedorId() { return proveedorId; }
    public double getSubtotal() { return subtotal; }
    public double getIvaPorcentaje() { return ivaPorcentaje; }
    public double getIvaValor() { return ivaValor; }
    public double getDescuentoPorcentaje() { return descuentoPorcentaje; }
    public double getDescuentoValor() { return descuentoValor; }
    public double getTotal() { return total; }
    public boolean isEstado() { return estado; }
    public List<DetalleFactura> getDetalles() { return detalles; }
    public Cliente getCliente() { return cliente; }
    public Proveedor getProveedor() { return proveedor; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }
    public void setProveedorId(Integer proveedorId) { this.proveedorId = proveedorId; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    public void setIvaPorcentaje(double ivaPorcentaje) { this.ivaPorcentaje = ivaPorcentaje; }
    public void setIvaValor(double ivaValor) { this.ivaValor = ivaValor; }
    public void setDescuentoPorcentaje(double descuentoPorcentaje) { this.descuentoPorcentaje = descuentoPorcentaje; }
    public void setDescuentoValor(double descuentoValor) { this.descuentoValor = descuentoValor; }
    public void setTotal(double total) { this.total = total; }
    public void setEstado(boolean estado) { this.estado = estado; }
    public void setDetalles(List<DetalleFactura> detalles) { this.detalles = detalles; }
    public void setCliente(Cliente cliente) { 
        this.cliente = cliente;
        this.clienteId = cliente != null ? cliente.getId() : null;
    }
    public void setProveedor(Proveedor proveedor) { 
        this.proveedor = proveedor;
        this.proveedorId = proveedor != null ? proveedor.getId() : null;
    }

    // Métodos de negocio
    public void addDetalle(DetalleFactura detalle) {
        detalles.add(detalle);
        calcularTotales();
    }

    public void removeDetalle(DetalleFactura detalle) {
        detalles.remove(detalle);
        calcularTotales();
    }

    public void calcularTotales() {
        // Calcular subtotal
        subtotal = detalles.stream()
            .mapToDouble(DetalleFactura::getSubtotal)
            .sum();

        // Calcular IVA
        ivaValor = subtotal * (ivaPorcentaje / 100);

        // Calcular descuento
        descuentoValor = (subtotal + ivaValor) * (descuentoPorcentaje / 100);

        // Calcular total
        total = subtotal + ivaValor - descuentoValor;
    }
}
