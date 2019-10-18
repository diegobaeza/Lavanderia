package cl.baeza.diego.lavanderia.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
    LinearLayout llbloqueServicio1;
    LinearLayout llbloqueServicio2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mejora);

        btnMejora1 = findViewById(R.id.btnMejora1);
        btnMejora2 = findViewById(R.id.btnMejora2);
        btnContinuar = findViewById(R.id.btnContinuar);
        tvAgregado1 = findViewById(R.id.tvAgregado1);
        tvAgregado2 = findViewById(R.id.tvAgregado2);

        llbloqueServicio1 = findViewById(R.id.bloqueServicio1);
        llbloqueServicio2 = findViewById(R.id.bloqueServicio2);

        tvAgregado1.setVisibility(View.INVISIBLE);
        tvAgregado2.setVisibility(View.INVISIBLE);

        btnMejora1.setOnClickListener(this);
        btnMejora2.setOnClickListener(this);
        btnContinuar.setOnClickListener(this);





    }



    @Override
    public void onClick(View v) {

        if(v == btnMejora1){

            if(tvAgregado1.getVisibility() == View.INVISIBLE){
                tvAgregado1.setVisibility(View.VISIBLE);

                llbloqueServicio1.setBackground(ContextCompat.getDrawable(this,R.drawable.button_selected));
                if(tvAgregado1.getVisibility() == View.VISIBLE || tvAgregado2.getVisibility() == View.VISIBLE){
                    btnContinuar.setText(R.string.continuar);
                }

            }else{
                tvAgregado1.setVisibility(View.INVISIBLE);
                llbloqueServicio1.setBackground(ContextCompat.getDrawable(this,R.drawable.background_button_primary));

                if(tvAgregado1.getVisibility() == View.INVISIBLE && tvAgregado2.getVisibility() == View.INVISIBLE){
                    btnContinuar.setText(R.string.continuar_sm);
                }
            }


        }
        else if(v == btnMejora2){

            if(tvAgregado2.getVisibility() == View.INVISIBLE){
                tvAgregado2.setVisibility(View.VISIBLE);


                llbloqueServicio2.setBackground(ContextCompat.getDrawable(this,R.drawable.button_selected));

                if(tvAgregado1.getVisibility() == View.VISIBLE || tvAgregado2.getVisibility() == View.VISIBLE){
                    btnContinuar.setText(R.string.continuar);
                }

            }else{
                tvAgregado2.setVisibility(View.INVISIBLE);

                llbloqueServicio2.setBackground(ContextCompat.getDrawable(this,R.drawable.background_button_primary));
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
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

        }
    }
}
