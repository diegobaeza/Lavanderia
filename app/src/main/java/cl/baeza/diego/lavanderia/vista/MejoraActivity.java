package cl.baeza.diego.lavanderia.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import cl.baeza.diego.lavanderia.R;
import cl.baeza.diego.lavanderia.modelo.Nombre;

public class MejoraActivity extends AppCompatActivity implements View.OnClickListener {


    ImageButton btnMejora1;
    ImageButton btnMejora2;
    Button btnContinuar;
    TextView tvAgregado1;
    TextView tvAgregado2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mejora);

        btnMejora1 = findViewById(R.id.btnMejora1);
        btnMejora2 = findViewById(R.id.btnMejora2);
        btnContinuar = findViewById(R.id.btnContinuar);
        tvAgregado1 = findViewById(R.id.tvAgregado1);
        tvAgregado2 = findViewById(R.id.tvAgregado2);

        tvAgregado1.setVisibility(View.INVISIBLE);
        tvAgregado2.setVisibility(View.INVISIBLE);

        btnMejora1.setOnClickListener(this);
        btnMejora2.setOnClickListener(this);
        btnContinuar.setOnClickListener(this);




        Toast.makeText(this, getIntent().getExtras().getString("direccion"), Toast.LENGTH_LONG).show();

    }



    @Override
    public void onClick(View v) {

        if(v == btnMejora1){

            if(tvAgregado1.getVisibility() == View.INVISIBLE){
                tvAgregado1.setVisibility(View.VISIBLE);

                if(tvAgregado1.getVisibility() == View.VISIBLE || tvAgregado2.getVisibility() == View.VISIBLE){
                    btnContinuar.setText(R.string.continuar);
                }

            }else{
                tvAgregado1.setVisibility(View.INVISIBLE);

                if(tvAgregado1.getVisibility() == View.INVISIBLE && tvAgregado2.getVisibility() == View.INVISIBLE){
                    btnContinuar.setText(R.string.continuar_sm);
                }
            }


        }
        else if(v == btnMejora2){

            if(tvAgregado2.getVisibility() == View.INVISIBLE){
                tvAgregado2.setVisibility(View.VISIBLE);

                if(tvAgregado1.getVisibility() == View.VISIBLE || tvAgregado2.getVisibility() == View.VISIBLE){
                    btnContinuar.setText(R.string.continuar);
                }

            }else{
                tvAgregado2.setVisibility(View.INVISIBLE);

                if(tvAgregado1.getVisibility() == View.INVISIBLE && tvAgregado2.getVisibility() == View.INVISIBLE){
                    btnContinuar.setText(R.string.continuar_sm);
                }
            }


        }
        else if(v == btnContinuar){

            //Arreglar paso de datos
            Intent i = new Intent(MejoraActivity.this, PagoActivity.class);
            i.putExtra("servicio",getIntent().getExtras().getString("servicio"));
            //i.putExtra("mejora",getIntent().getExtras().getString("mejora"));
            i.putExtra("nombre", getIntent().getExtras().getString("nombre"));
            i.putExtra("direccion", getIntent().getExtras().getString("direccion"));
            i.putExtra("horario", getIntent().getExtras().getString("horario"));

            if(tvAgregado1.getVisibility() == View.VISIBLE && tvAgregado2.getVisibility() == View.INVISIBLE){
                i.putExtra("mejora","Mejora 1");
            }
            else if(tvAgregado1.getVisibility() == View.INVISIBLE && tvAgregado2.getVisibility() == View.VISIBLE){
                i.putExtra("mejora","Mejora 2");
            }
            else if(tvAgregado1.getVisibility() == View.VISIBLE && tvAgregado2.getVisibility() == View.VISIBLE){
                i.putExtra("mejora","Mejora 1 y Mejora 2");
            }
            else if(tvAgregado1.getVisibility() == View.INVISIBLE && tvAgregado2.getVisibility() == View.INVISIBLE){
                i.putExtra("mejora", "Sin Mejora");
            }




            startActivity(i);

        }
    }
}
