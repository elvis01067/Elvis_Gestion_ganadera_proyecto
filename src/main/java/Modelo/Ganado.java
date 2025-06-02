package Modelo;

import java.util.Date;

public class Ganado {
    private int id;
    private String arete;
    private String raza;
    private Date fechaNacimiento;
    private String genero;
    private double peso;
    private String estadoSalud;
    private String proposito;
    private String estadoProductivo;

    public Ganado() {}

    // Getters
    public int getId() { return id; }
    public String getArete() { return arete; }
    public String getRaza() { return raza; }
    public Date getFechaNacimiento() { return fechaNacimiento; }
    public String getGenero() { return genero; }
    public double getPeso() { return peso; }
    public String getEstadoSalud() { return estadoSalud; }
    public String getProposito() { return proposito; }
    public String getEstadoProductivo() { return estadoProductivo; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setArete(String arete) { this.arete = arete; }
    public void setRaza(String raza) { this.raza = raza; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setPeso(double peso) { this.peso = peso; }
    public void setEstadoSalud(String estadoSalud) { this.estadoSalud = estadoSalud; }
    public void setProposito(String proposito) { this.proposito = proposito; }
    public void setEstadoProductivo(String estadoProductivo) { this.estadoProductivo = estadoProductivo; }
}
