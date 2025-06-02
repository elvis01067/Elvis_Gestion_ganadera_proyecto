package Modelo;

import java.util.Date;

public class RegistroSanitario {
    private int id;
    private int ganadoId;
    private String tipoRegistro;
    private Date fecha;
    private String descripcion;
    private String productoUsado;
    private String dosis;
    private Date proximoTratamiento;
    private String observaciones;
    private boolean estado;
    
    // Referencia al objeto Ganado completo
    private Ganado ganado;

    public RegistroSanitario() {
        this.fecha = new Date();
        this.estado = true;
    }

    // Getters
    public int getId() { return id; }
    public int getGanadoId() { return ganadoId; }
    public String getTipoRegistro() { return tipoRegistro; }
    public Date getFecha() { return fecha; }
    public String getDescripcion() { return descripcion; }
    public String getProductoUsado() { return productoUsado; }
    public String getDosis() { return dosis; }
    public Date getProximoTratamiento() { return proximoTratamiento; }
    public String getObservaciones() { return observaciones; }
    public boolean isEstado() { return estado; }
    public Ganado getGanado() { return ganado; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setGanadoId(int ganadoId) { this.ganadoId = ganadoId; }
    public void setTipoRegistro(String tipoRegistro) { this.tipoRegistro = tipoRegistro; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setProductoUsado(String productoUsado) { this.productoUsado = productoUsado; }
    public void setDosis(String dosis) { this.dosis = dosis; }
    public void setProximoTratamiento(Date proximoTratamiento) { this.proximoTratamiento = proximoTratamiento; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public void setEstado(boolean estado) { this.estado = estado; }
    public void setGanado(Ganado ganado) { 
        this.ganado = ganado;
        this.ganadoId = ganado != null ? ganado.getId() : 0;
    }
}
