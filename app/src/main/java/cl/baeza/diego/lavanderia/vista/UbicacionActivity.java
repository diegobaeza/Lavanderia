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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.List;

import cl.baeza.diego.lavanderia.R;
import cl.baeza.diego.lavanderia.controlador.DireccionAdapter;
import cl.baeza.diego.lavanderia.controlador.Utilidades;
import cl.baeza.diego.lavanderia.db.ConexionSQLiteHelper;
import cl.baeza.diego.lavanderia.modelo.Direccion;


public class UbicacionActivity extends AppCompatActivity implements View.OnClickListener {

    int PLACE_PICKER_REQUEST = 1;

    Button btnSelecUbi;
    ImageButton btnAgregar;
    TextView tvSinDireccion;

    RecyclerView rvDireccion;

    List<Direccion> direccionList;

    String servicio;
    String mejora;
    String nombre;
    DireccionAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        btnSelecUbi = findViewById(R.id.btnSelecUbicacion);
        btnAgregar = findViewById(R.id.btnAgregarD);
        tvSinDireccion = findViewById(R.id.tvSinDireccion);

        btnAgregar.setOnClickListener(this);
        btnSelecUbi.setOnClickListener(this);

        tvSinDireccion.setVisibility(View.INVISIBLE);

        rvDireccion = findViewById(R.id.rvDireccion);
        rvDireccion.setHasFixedSize(true);
        rvDireccion.setLayoutManager(new LinearLayoutManager(this));


        direccionList = loadDirecciones();

        servicio = getIntent().getExtras().getString("servicio");
        mejora = getIntent().getExtras().getString("mejora");
        nombre = getIntent().getExtras().getString("nombre");

        if(direccionList.isEmpty()){
            tvSinDireccion.setVisibility(View.VISIBLE);
        }else{
            adapter = new DireccionAdapter(this, direccionList, servicio, mejora, nombre);

            rvDireccion.setAdapter(adapter);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        direccionList = loadDirecciones();
        if(direccionList.isEmpty()){
            tvSinDireccion.setVisibility(View.VISIBLE);
        }else{
            adapter = new DireccionAdapter(this, direccionList, servicio, mejora, nombre);

            rvDireccion.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnSelecUbi){
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            try {
                startActivityForResult(builder.build(UbicacionActivity.this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }

        }
        else if(v == btnAgregar){
            Intent i = new Intent(UbicacionActivity.this, AgregarDireccionActivity.class);
            i.putExtra("editando", 0);
            startActivity(i);
            onStop();
        }

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);

                Intent i = new Intent(UbicacionActivity.this, HorarioActivity.class);
                i.putExtra("servicio", servicio);
                i.putExtra("mejora" , mejora);
                i.putExtra("nombre", nombre);
                i.putExtra("ubicacion" , place.getAddress());
                startActivity(i);

            }
        }
    }


    public ArrayList<Direccion> loadDirecciones() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(UbicacionActivity.this,Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db = conn.getReadableDatabase();

        ArrayList<Direccion> list = new ArrayList<>();

        String[] campos = new String[]{Utilidades.CAMPO_ID, Utilidades.CAMPO_DIRECCION, Utilidades.CAMPO_COMUNA, Utilidades.CAMPO_NRO, Utilidades.CAMPO_DEPTO};
        Cursor c = db.query(Utilidades.TABLA_DIRECCION, campos, null, null, null, null, null);

        try {
            while (c.moveToNext()) {
                Direccion direccion = new Direccion();
                direccion.setId(c.getInt(0));
                direccion.setDireccion(c.getString(1));
                direccion.setComuna(c.getString(2));
                direccion.setNro_casa(c.getInt(3));
                direccion.setNro_depto(c.getInt(4));
                list.add(direccion);
            }
        } finally {
            c.close();
        }
        db.close();

        return list;
    }

}
