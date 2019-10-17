package cl.baeza.diego.lavanderia.vista;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.UniversalTimeScale;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import cl.baeza.diego.lavanderia.R;
import cl.baeza.diego.lavanderia.controlador.Utilidades;
import cl.baeza.diego.lavanderia.db.ConexionSQLiteHelper;
import cl.baeza.diego.lavanderia.modelo.Direccion;

public class AgregarDireccionActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etComuna;
    EditText etDireccion;
    EditText etNumero;
    EditText etNroDepto;

    RadioButton rbCasa;
    RadioButton rbDepto;

    Button btnListo;
    Direccion direccionEditar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_direccion);

        btnListo = findViewById(R.id.btnListo);

        etComuna = findViewById(R.id.etComuna);
        etDireccion = findViewById(R.id.etDireccion);
        etNumero = findViewById(R.id.etNumero);
        etNroDepto = findViewById(R.id.etNroDepto);

        rbCasa = findViewById(R.id.rbCasa);
        rbDepto = findViewById(R.id.rbDepartamento);

        rbCasa.setChecked(true);

        etNroDepto.setVisibility(View.GONE);

        rbCasa.setOnClickListener(this);
        rbDepto.setOnClickListener(this);
        btnListo.setOnClickListener(this);

        if(getIntent().getExtras().getInt("editando") == 1){
            direccionEditar = (Direccion)getIntent().getExtras().getSerializable("direccion");

            if(direccionEditar.getNro_depto() > 0){
                rbDepto.setChecked(true);
                etNroDepto.setVisibility(View.VISIBLE);

                etNroDepto.setText(Integer.toString(direccionEditar.getNro_depto()));

            }

            etComuna.setText(direccionEditar.getComuna());
            etDireccion.setText(direccionEditar.getDireccion());
            etNumero.setText(Integer.toString(direccionEditar.getNro_casa()));
        }

        if(getIntent().getExtras().get("direccion") != null){
            Direccion direccion = (Direccion) getIntent().getExtras().get("direccion");
            etComuna.setText(direccion.getComuna());
            etDireccion.setText(direccion.getDireccion());
            etNumero.setText(Integer.toString(direccion.getNro_casa()));
        }
    }

    @Override
    public void onClick(View v) {
        if(v == rbCasa){
            etNroDepto.setVisibility(View.GONE);
        }
        else if(v == rbDepto){
            etNroDepto.setVisibility(View.VISIBLE);
        }
        else if(v == btnListo){

            if(etComuna.length() < 1){
                etComuna.setError("Ingrese una Comuna");
                return;
            }
            else if(etDireccion.length() < 1){
                etDireccion.setError("Ingrese una Direccion");
                return;
            }
            else if(etNumero.length() < 1 && etNroDepto.getVisibility() == View.INVISIBLE){
                etNumero.setError("Ingrese un Numero Casa");
                return;
            }
            else if(etNumero.length() < 1 && etNroDepto.getVisibility() == View.VISIBLE){
                etNumero.setError("Ingrese un Numero de Block o Condominio");
                return;
            }
            else if(etNroDepto.getVisibility() == View.VISIBLE && etNroDepto.length() < 1){
                etNroDepto.setError("Ingrese un Numero de Departamento");
                return;
            }


            Direccion direccion;
            ContentValues values;

            if(etNroDepto.getVisibility() == View.VISIBLE){
                direccion = new Direccion(etDireccion.getText().toString(),
                        etComuna.getText().toString(),
                        Integer.parseInt(etNumero.getText().toString()),
                        Integer.parseInt(etNroDepto.getText().toString()));

                values = new ContentValues();
                values.put(Utilidades.CAMPO_DIRECCION, direccion.getDireccion());
                values.put(Utilidades.CAMPO_COMUNA, direccion.getComuna());
                values.put(Utilidades.CAMPO_NRO, direccion.getNro_casa());
                values.put(Utilidades.CAMPO_DEPTO, direccion.getNro_depto());

            }else{
                direccion = new Direccion(etDireccion.getText().toString(),
                        etComuna.getText().toString(),
                        Integer.parseInt(etNumero.getText().toString()));

                values = new ContentValues();
                values.put(Utilidades.CAMPO_DIRECCION, direccion.getDireccion());
                values.put(Utilidades.CAMPO_COMUNA, direccion.getComuna());
                values.put(Utilidades.CAMPO_NRO, direccion.getNro_casa());
            }

            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(AgregarDireccionActivity.this,Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
            SQLiteDatabase db = conn.getWritableDatabase();

            if(getIntent().getExtras().getInt("editando") == 1){
                String where = Utilidades.CAMPO_ID + "= ?";
                db.update(Utilidades.TABLA_DIRECCION,values,where,new String[]{String.valueOf(direccionEditar.getId())});

            }else{
                db.insert(Utilidades.TABLA_DIRECCION, null, values);
            }



            db.close();

            if(getIntent().getExtras().get("direccion") != null){

                String direccionCompleta;

                if(etNroDepto.getText().length() > 0){
                    direccionCompleta = etDireccion.getText()
                            + " #" + etNumero.getText()
                            + " Depto. " + etNroDepto.getText()
                            + ", " + etComuna.getText();
                }
                else{
                    direccionCompleta = etDireccion.getText()
                            + " #" + etNumero.getText()
                            + ", " + etComuna.getText();
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra("resultado", direccionCompleta);
                setResult(RESULT_OK, resultIntent);
                finish();

            }else{

            }

            finish();


        }
    }
}
