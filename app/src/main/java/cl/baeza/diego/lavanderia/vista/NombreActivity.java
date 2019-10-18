package cl.baeza.diego.lavanderia.vista;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cl.baeza.diego.lavanderia.R;
import cl.baeza.diego.lavanderia.controlador.NombreAdapter;
import cl.baeza.diego.lavanderia.controlador.Utilidades;
import cl.baeza.diego.lavanderia.db.ConexionSQLiteHelper;
import cl.baeza.diego.lavanderia.modelo.Nombre;

public class NombreActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnAgregarN;
    TextView tvSinNombre;
    RecyclerView rvNombre;

    List<Nombre> nombreList;

    String servicio;
    String mejora;

    NombreAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nombre);

        btnAgregarN = findViewById(R.id.btnAgregarN);
        tvSinNombre = findViewById(R.id.tvSinNombre);
        rvNombre = findViewById(R.id.rvNombre);

        btnAgregarN.setOnClickListener(this);
        tvSinNombre.setVisibility(View.INVISIBLE);

        rvNombre.setHasFixedSize(true);
        rvNombre.setLayoutManager(new LinearLayoutManager(this));


        servicio = getIntent().getExtras().getString("servicio");
        //mejora = getIntent().getExtras().getString("mejora");


        nombreList = cargarNombres();



        if(nombreList.isEmpty()){
            tvSinNombre.setVisibility(View.VISIBLE);
        }
        else{
            adapter = new NombreAdapter(this, nombreList, servicio, mejora);

            rvNombre.setAdapter(adapter);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        nombreList = cargarNombres();

        if(nombreList.isEmpty()){
            tvSinNombre.setVisibility(View.VISIBLE);
        }
        else{
            adapter = new NombreAdapter(this, nombreList, servicio, mejora);

            rvNombre.setAdapter(adapter);
        }

    }

    @Override
    public void onClick(View v) {
        if(v == btnAgregarN){
            Intent i = new Intent(NombreActivity.this, AgregarNombreActivity.class);
            i.putExtra("editando", 0);
            startActivity(i);
            onStop();
            overridePendingTransition(R.anim.slide_from_bottom, R.anim.nothing);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.slide_to_right);
    }


    public ArrayList<Nombre> cargarNombres(){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(NombreActivity.this, Utilidades.NOMBRE_BD, null, Utilidades.VERSION_BD);
        SQLiteDatabase db = conn.getReadableDatabase();

        ArrayList<Nombre> nombreList = new ArrayList<>();

        String[] campos = new String[]{Utilidades.CAMPO_ID,Utilidades.CAMPO_NOMBRE};

        Cursor c = db.query(Utilidades.TABLA_NOMBRE, campos, null,null,null,null,null);

        try{
            while (c.moveToNext()){
                Nombre nombre = new Nombre();
                nombre.setId(c.getInt(0));
                nombre.setNombre(c.getString(1));

                nombreList.add(nombre);
            }
        } finally {
            c.close();
        }

        db.close();

        return nombreList;

    }
}
