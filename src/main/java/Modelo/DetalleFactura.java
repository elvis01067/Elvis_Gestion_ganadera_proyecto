package Modelo;

public class DetalleFactura {
    private int id;
    private int facturaId;
    private String concepto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public DetalleFactura() {}

    // Getters
    public int getId() { return id; }
    public int getFacturaId() { return facturaId; }
    public String getConcepto() { return concepto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getSubtotal() { return subtotal; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setFacturaId(int facturaId) { this.facturaId = facturaId; }
    public void setConcepto(String concepto) { this.concepto = concepto; }
    public void setCantidad(int cantidad) { 
        this.cantidad = cantidad;
        calcularSubtotal();
    }
    public void setPrecioUnitario(double precioUnitario) { 
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    // Métodos de negocio
    private void calcularSubtotal() {
        this.subtotal = this.cantidad * this.precioUnitario;
    }
}
