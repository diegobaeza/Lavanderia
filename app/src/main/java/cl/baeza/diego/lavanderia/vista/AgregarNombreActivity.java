package cl.baeza.diego.lavanderia.vista;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import cl.baeza.diego.lavanderia.R;
import cl.baeza.diego.lavanderia.controlador.Utilidades;
import cl.baeza.diego.lavanderia.db.ConexionSQLiteHelper;
import cl.baeza.diego.lavanderia.modelo.Direccion;
import cl.baeza.diego.lavanderia.modelo.Nombre;

public class AgregarNombreActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etNombre;
    Button btnListoN;

    Nombre nombreEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nombre);




        etNombre = findViewById(R.id.etNombre);
        btnListoN = findViewById(R.id.btnListoN);

        btnListoN.setOnClickListener(this);

        if(getIntent().getExtras().getInt("editando") == 1){
            nombreEdit = (Nombre) getIntent().getExtras().getSerializable("nombre");

            etNombre.setText(nombreEdit.getNombre());
        }

    }

    @Override
    public void onClick(View v) {
        if(v == btnListoN){

            if(etNombre.length() < 1){
                etNombre.setError("Ingrese un nombre");
                return;
            }

            Nombre nombre = new Nombre(etNombre.getText().toString());

            ContentValues values = new ContentValues();
            values.put(Utilidades.CAMPO_NOMBRE, nombre.getNombre());

            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(AgregarNombreActivity.this, Utilidades.NOMBRE_BD, null, Utilidades.VERSION_BD);
            SQLiteDatabase db = conn.getWritableDatabase();

            if(getIntent().getExtras().getInt("editando") == 1){
                String where = Utilidades.CAMPO_ID + "= ?";
                db.update(Utilidades.TABLA_NOMBRE, values, where,new String[]{String.valueOf(nombreEdit.getId())});

            }else{
                db.insert(Utilidades.TABLA_NOMBRE, null, values);
            }

            db.close();

            finish();


            overridePendingTransition(R.anim.nothing, R.anim.slide_to_bottom);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.slide_to_bottom);
    }

    @Override
    public void finish() {
        super.finish();

    }
}
