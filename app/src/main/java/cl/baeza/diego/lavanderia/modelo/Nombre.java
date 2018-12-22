package cl.baeza.diego.lavanderia.modelo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Nombre implements Serializable {

    private int id;
    private String nombre;

    public Nombre() {
    }

    public Nombre(String nombre) {
        this.nombre = nombre;
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


}
