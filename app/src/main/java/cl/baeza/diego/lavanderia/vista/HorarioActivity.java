package cl.baeza.diego.lavanderia.vista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cl.baeza.diego.lavanderia.R;
import cl.baeza.diego.lavanderia.controlador.Mail;

public class HorarioActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnConfirmar;
    private RadioGroup rgHorario;
    private RadioButton rbHorario1;
    private RadioButton rbHorario2;
    private RadioButton rbHorario3;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);

        btnConfirmar = findViewById(R.id.btnConfirmar);
        rgHorario = findViewById(R.id.rgHorario);
        rbHorario1 = findViewById(R.id.rbHorario1);
        rbHorario2 = findViewById(R.id.rbHorario2);
        rbHorario3 = findViewById(R.id.rbHorario3);

        btnConfirmar.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if(v == btnConfirmar){

            String horario = "";

            if(rbHorario1.isChecked()){
                horario = rbHorario1.getText().toString();
            }
            else if(rbHorario2.isChecked()){
                horario = rbHorario2.getText().toString();
            }
            else if(rbHorario3.isChecked()){
                horario = rbHorario3.getText().toString();
            }

            Intent i = new Intent(HorarioActivity.this, PagoActivity.class);
            i.putExtra("servicio",getIntent().getExtras().getString("servicio"));
            i.putExtra("mejora",getIntent().getExtras().getString("mejora"));
            i.putExtra("direccion", getIntent().getExtras().getString("ubicacion"));
            i.putExtra("horario", horario);
            startActivity(i);


        }

    }

}
