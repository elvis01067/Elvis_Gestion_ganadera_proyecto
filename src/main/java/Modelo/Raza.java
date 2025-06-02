package Modelo;

public class Raza {
    private int id;
    private String nombre;
    private String imagenUrl;
    private String descripcion;

    public Raza() {
    }

    public Raza(int id, String nombre, String imagenUrl, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
