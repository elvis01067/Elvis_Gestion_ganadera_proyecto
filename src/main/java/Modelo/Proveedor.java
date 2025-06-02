package Modelo;

import java.util.Date;

public class Proveedor {
    private int id;
    private String nombre;
    private String apellido;
    private String cedulaRuc;
    private String direccion;
    private String telefono;
    private String email;
    private String tipoProducto; // "alimentos", "medicina", "equipos"
    private Date fechaRegistro;
    private boolean estado;

    public Proveedor() {}

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCedulaRuc() { return cedulaRuc; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public String getTipoProducto() { return tipoProducto; }
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
    public void setTipoProducto(String tipoProducto) { this.tipoProducto = tipoProducto; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
