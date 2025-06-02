/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;


public class Usuario {

    private int id;
    private String nombreUsuario;
    private String rol;
    private boolean estado;

    public Usuario(int id, String nombreUsuario, String rol, boolean estado) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        this.estado = estado;
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getRol() { return rol; }
    public boolean isEstado() { return estado; }
}