package cl.baeza.diego.lavanderia.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cl.baeza.diego.lavanderia.R;

public class PagoActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnEfectivo;
    Button btnCredito;
    Button btnTransferencia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);

        btnEfectivo = findViewById(R.id.btnEfectivo);
        btnCredito = findViewById(R.id.btnCredito);
        btnTransferencia = findViewById(R.id.btnTransferencia);

        btnEfectivo.setOnClickListener(this);
        btnCredito.setOnClickListener(this);
        btnTransferencia.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == btnEfectivo){
            Intent i = new Intent(PagoActivity.this, DetalleActivity.class);
            i.putExtra("servicio",getIntent().getExtras().getString("servicio"));
            i.putExtra("mejora",getIntent().getExtras().getString("mejora"));
            i.putExtra("nombre",getIntent().getExtras().getString("nombre"));
            i.putExtra("direccion", getIntent().getExtras().getString("direccion"));
            i.putExtra("horario", getIntent().getExtras().getString("horario"));
            i.putExtra("tipoPago","Efectivo");
            startActivity(i);

        }
        else if(v == btnCredito){
            Intent i = new Intent(PagoActivity.this, DetalleActivity.class);
            i.putExtra("servicio",getIntent().getExtras().getString("servicio"));
            i.putExtra("mejora",getIntent().getExtras().getString("mejora"));
            i.putExtra("nombre",getIntent().getExtras().getString("nombre"));
            i.putExtra("direccion", getIntent().getExtras().getString("direccion"));
            i.putExtra("horario", getIntent().getExtras().getString("horario"));
            i.putExtra("tipoPago","Credito");
            startActivity(i);

        }
        else if(v == btnTransferencia){
            Intent i = new Intent(PagoActivity.this, DetalleActivity.class);
            i.putExtra("servicio",getIntent().getExtras().getString("servicio"));
            i.putExtra("mejora",getIntent().getExtras().getString("mejora"));
            i.putExtra("nombre",getIntent().getExtras().getString("nombre"));
            i.putExtra("direccion", getIntent().getExtras().getString("direccion"));
            i.putExtra("horario", getIntent().getExtras().getString("horario"));
            i.putExtra("tipoPago","Transferencia");
            startActivity(i);

        }
    }
}
