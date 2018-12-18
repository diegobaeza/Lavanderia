package cl.baeza.diego.lavanderia.modelo;

public class Usuario {

    private int id;
    private String telefono;

    public Usuario(){

    }

    public Usuario(String telefono, int id){
        this.telefono = telefono;
        this.id = id;
    }

    public String getTelefono(){
        return telefono;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

}
