package Modelo;

import java.util.Date;

public class Pasto {
    private int id;
    private String nombre;
    private String descripcion;
    private double areaHectareas;
    private String estadoPasto; // "disponible", "ocupado", "mantenimiento"
    private int capacidadAnimales;
    private Date fechaUltimaRotacion;
    private boolean estado;

    public Pasto() {}

    public Pasto(int id, String nombre, String descripcion, double areaHectareas, 
                String estadoPasto, int capacidadAnimales, Date fechaUltimaRotacion, boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.areaHectareas = areaHectareas;
        this.estadoPasto = estadoPasto;
        this.capacidadAnimales = capacidadAnimales;
        this.fechaUltimaRotacion = fechaUltimaRotacion;
        this.estado = estado;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getAreaHectareas() { return areaHectareas; }
    public String getEstadoPasto() { return estadoPasto; }
    public int getCapacidadAnimales() { return capacidadAnimales; }
    public Date getFechaUltimaRotacion() { return fechaUltimaRotacion; }
    public boolean isEstado() { return estado; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setAreaHectareas(double areaHectareas) { this.areaHectareas = areaHectareas; }
    public void setEstadoPasto(String estadoPasto) { this.estadoPasto = estadoPasto; }
    public void setCapacidadAnimales(int capacidadAnimales) { this.capacidadAnimales = capacidadAnimales; }
    public void setFechaUltimaRotacion(Date fechaUltimaRotacion) { this.fechaUltimaRotacion = fechaUltimaRotacion; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
