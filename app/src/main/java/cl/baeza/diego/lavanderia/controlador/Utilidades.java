package cl.baeza.diego.lavanderia.controlador;

public class Utilidades {


    //Constantes base de datos General
    public static final String NOMBRE_BD = "usuarios";
    public static final byte VERSION_BD = 1;
    public static final String CAMPO_ID = "id";

    //Constates Tabla Direccion
    public static final String TABLA_DIRECCION = "direccion";

    public static final String CAMPO_DIRECCION= "direccion";
    public static final String CAMPO_COMUNA = "comuna";
    public static final String CAMPO_NRO = "numero";
    public static final String CAMPO_DEPTO = "nro_depto";


    public static final String CREAR_TABLA_DIRECCION = "CREATE TABLE " + TABLA_DIRECCION
                                                        + "("+ CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                        + CAMPO_DIRECCION +" TEXT,"
                                                        + CAMPO_COMUNA + " TEXT,"
                                                        + CAMPO_NRO + " INTEGER,"
                                                        + CAMPO_DEPTO + " INTEGER)";

    //Constantes Tabla Nombre
    public static final String TABLA_NOMBRE = "nombre";

    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_TELEFONO_ALTERNATIVO = "telefono_alternativo";

    public static final String CREAR_TABLA_NOMBRE = "CREATE TABLE " + TABLA_NOMBRE + "("+ CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_NOMBRE +" TEXT)";


    //Constantes Tabla Usuario

    public static final String TABLA_USUARIO = "usuario";

    public static final String CAMPO_TELEFONO = "telefono";

    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + "("+ CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_TELEFONO +" TEXT)";

}
