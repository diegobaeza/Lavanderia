package cl.baeza.diego.lavanderia.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import cl.baeza.diego.lavanderia.R;



public class UbicacionActivity extends AppCompatActivity implements View.OnClickListener {

    int PLACE_PICKER_REQUEST = 1;

    private Button btnSelecUbi;
    private Button btnContinuar;
    private EditText etDireccion;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        btnSelecUbi = findViewById(R.id.btnSelecUbicacion);
        btnContinuar = findViewById(R.id.btnContinuar);
        etDireccion = findViewById(R.id.etDireccion);

        btnSelecUbi.setOnClickListener(this);
        btnContinuar.setOnClickListener(this);


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
        else if(v == btnContinuar){
            if(etDireccion.getText().length() > 0){
                Intent i = new Intent(UbicacionActivity.this, HorarioActivity.class);
                i.putExtra("servicio" , getIntent().getExtras().getString("servicio"));
                i.putExtra("mejora" , getIntent().getExtras().getString("mejora"));
                i.putExtra("ubicacion" , etDireccion.getText().toString());
                startActivity(i);
            }else{
                String toast = "Ingrese una direccion o seleccione desde el mapa";
                Toast.makeText(this,toast,Toast.LENGTH_SHORT).show();
            }

        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                etDireccion.setText(place.getAddress());
            }
        }
    }

}
