package Modelo;

import java.util.Date;

public class Vacas {
    private int id;
    private String codigoIdentificacion;
    private String nombre;
    private String raza;
    private Date fechaNacimiento;
    private String genero; // "macho" o "hembra"
    private double pesoKg;
    private String estadoSalud; // "sano", "enfermo", "tratamiento", "cuarentena"
    private String proposito; // "carne", "leche", "reproduccion", "exhibicion"
    private String estadoProductivo; // "produccion", "seca", "preñada", "novilla", "descarte"
    private int idPasto;
    private Integer idMadre; // Puede ser null
    private Integer idPadre; // Puede ser null
    private Date fechaAdquisicion;
    private double precioCompra;
    private boolean estado;

    public Vacas() {}

    public Vacas(int id, String codigoIdentificacion, String nombre, String raza, Date fechaNacimiento,
                String genero, double pesoKg, String estadoSalud, String proposito, String estadoProductivo,
                int idPasto, Integer idMadre, Integer idPadre, Date fechaAdquisicion, double precioCompra,
                boolean estado) {
        this.id = id;
        this.codigoIdentificacion = codigoIdentificacion;
        this.nombre = nombre;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.pesoKg = pesoKg;
        this.estadoSalud = estadoSalud;
        this.proposito = proposito;
        this.estadoProductivo = estadoProductivo;
        this.idPasto = idPasto;
        this.idMadre = idMadre;
        this.idPadre = idPadre;
        this.fechaAdquisicion = fechaAdquisicion;
        this.precioCompra = precioCompra;
        this.estado = estado;
    }

    // Getters
    public int getId() { return id; }
    public String getCodigoIdentificacion() { return codigoIdentificacion; }
    public String getNombre() { return nombre; }
    public String getRaza() { return raza; }
    public Date getFechaNacimiento() { return fechaNacimiento; }
    public String getGenero() { return genero; }
    public double getPesoKg() { return pesoKg; }
    public String getEstadoSalud() { return estadoSalud; }
    public String getProposito() { return proposito; }
    public String getEstadoProductivo() { return estadoProductivo; }
    public int getIdPasto() { return idPasto; }
    public Integer getIdMadre() { return idMadre; }
    public Integer getIdPadre() { return idPadre; }
    public Date getFechaAdquisicion() { return fechaAdquisicion; }
    public double getPrecioCompra() { return precioCompra; }
    public boolean isEstado() { return estado; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setCodigoIdentificacion(String codigoIdentificacion) { this.codigoIdentificacion = codigoIdentificacion; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setRaza(String raza) { this.raza = raza; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setPesoKg(double pesoKg) { this.pesoKg = pesoKg; }
    public void setEstadoSalud(String estadoSalud) { this.estadoSalud = estadoSalud; }
    public void setProposito(String proposito) { this.proposito = proposito; }
    public void setEstadoProductivo(String estadoProductivo) { this.estadoProductivo = estadoProductivo; }
    public void setIdPasto(int idPasto) { this.idPasto = idPasto; }
    public void setIdMadre(Integer idMadre) { this.idMadre = idMadre; }
    public void setIdPadre(Integer idPadre) { this.idPadre = idPadre; }
    public void setFechaAdquisicion(Date fechaAdquisicion) { this.fechaAdquisicion = fechaAdquisicion; }
    public void setPrecioCompra(double precioCompra) { this.precioCompra = precioCompra; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
