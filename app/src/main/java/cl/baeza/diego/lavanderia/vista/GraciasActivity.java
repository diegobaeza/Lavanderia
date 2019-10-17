package cl.baeza.diego.lavanderia.vista;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;

import cl.baeza.diego.lavanderia.R;

public class GraciasActivity extends AppCompatActivity {

    LinearLayoutCompat llTransferencia;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gracias);

        llTransferencia = findViewById(R.id.llTransferencia);

        if(getIntent().getExtras().getString("tipoPago").equals("Transferencia")){
            llTransferencia.setVisibility(View.VISIBLE);
        }

    }
}
