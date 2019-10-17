package cl.baeza.diego.lavanderia.modelo;


import java.io.Serializable;

@SuppressWarnings("serial")
public class Direccion implements Serializable {

    private int id;
    private String direccion;
    private String comuna;
    private int nro_casa;
    private int nro_depto;

    public Direccion() {
        direccion = "";
        comuna = "";
        nro_casa = 0;
        nro_depto = 0;
    }

    public Direccion(String direccion) {
        this.direccion = direccion;
    }

    public Direccion(String direccion, String comuna, int nro_casa, int nro_depto) {
        this.direccion = direccion;
        this.comuna = comuna;
        this.nro_casa = nro_casa;
        this.nro_depto = nro_depto;
    }

    public Direccion(String direccion, String comuna, int nro_casa) {
        this.direccion = direccion;
        this.comuna = comuna;
        this.nro_casa = nro_casa;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public int getNro_casa() {
        return nro_casa;
    }

    public void setNro_casa(int nro_casa) {
        this.nro_casa = nro_casa;
    }

    public int getNro_depto() {
        return nro_depto;
    }

    public void setNro_depto(int nro_depto) {
        this.nro_depto = nro_depto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "direccion='" + direccion + '\'' +
                ", comuna='" + comuna + '\'' +
                ", nro_casa=" + nro_casa +
                '}';
    }
}
