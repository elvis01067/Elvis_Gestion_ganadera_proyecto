package Modelo;

import java.util.Date;

public class Cliente {
    private int id;
    private String nombre;
    private String apellido;
    private String cedulaRuc;
    private String direccion;
    private String telefono;
    private String email;
    private String tipo; // "persona" o "empresa"
    private Date fechaRegistro;
    private boolean estado;

    public Cliente() {}

    public Cliente(int id, String nombre, String apellido, String cedulaRuc, String direccion, 
                  String telefono, String email, String tipo, Date fechaRegistro, boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedulaRuc = cedulaRuc;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.tipo = tipo;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCedulaRuc() { return cedulaRuc; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public String getTipo() { return tipo; }
    public Date getFechaRegistro() { return fechaRegistro; }
    public boolean isEstado() { return estado; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setCedulaRuc(String cedulaRuc) { this.cedulaRuc = cedulaRuc; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
