package cl.baeza.diego.lavanderia.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cl.baeza.diego.lavanderia.R;

public class MejoraActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mejora1;
    private Button mejora2;
    private Button sinMejora;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mejora);

        mejora1 = findViewById(R.id.btnMejora1);
        mejora2 = findViewById(R.id.btnMejora2);
        sinMejora = findViewById(R.id.btnSinMejora);

        mejora1.setOnClickListener(this);
        mejora2.setOnClickListener(this);
        sinMejora.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {

        if(v == mejora1){

            Intent i = new Intent(MejoraActivity.this, UbicacionActivity.class);
            i.putExtra("servicio" , getIntent().getExtras().getString("servicio"));
            i.putExtra("mejora" , "mejora1");
            startActivity(i);

        }
        else if(v == mejora2){

            Intent i = new Intent(MejoraActivity.this, UbicacionActivity.class);
            i.putExtra("servicio" , getIntent().getExtras().getString("servicio"));
            i.putExtra("mejora" , "mejora2");
            startActivity(i);

        }
        else if(v == sinMejora){

            Intent i = new Intent(MejoraActivity.this, UbicacionActivity.class);
            i.putExtra("servicio" , getIntent().getExtras().getString("servicio"));
            i.putExtra("mejora" , "sinmejora");
            startActivity(i);

        }
    }
}
