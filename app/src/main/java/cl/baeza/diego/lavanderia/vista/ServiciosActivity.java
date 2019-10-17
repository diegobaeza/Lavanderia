package cl.baeza.diego.lavanderia.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import cl.baeza.diego.lavanderia.R;


public class ServiciosActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnServicio1;
    private ImageButton btnServicio2;
    private ImageButton btnServicio3;
    private ImageButton btnServicio4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        btnServicio1 = findViewById(R.id.btnServicio1);
        btnServicio2 = findViewById(R.id.btnServicio2);
        btnServicio3 = findViewById(R.id.btnServicio3);
        btnServicio4 = findViewById(R.id.btnServicio4);



        btnServicio1.setOnClickListener(this);
        btnServicio2.setOnClickListener(this);
        btnServicio3.setOnClickListener(this);
        btnServicio4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == btnServicio1){
            Intent i = new Intent(this, NombreActivity.class);
            i.putExtra("servicio" , "Servicio 1");
            startActivity(i);
        }
        else if(v == btnServicio2){
            Intent i = new Intent(this, NombreActivity.class);
            i.putExtra("servicio" , "Servicio 2");
            startActivity(i);
        }
        else if(v == btnServicio3){
            Intent i = new Intent(this, NombreActivity.class);
            i.putExtra("servicio" , "Servicio 3");
            startActivity(i);
        }
        else if(v == btnServicio4){
            Intent i = new Intent(this, NombreActivity.class);
            i.putExtra("servicio" , "Servicio 4");
            startActivity(i);
        }
    }
}
