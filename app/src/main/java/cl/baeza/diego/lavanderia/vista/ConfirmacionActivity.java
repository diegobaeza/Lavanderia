package cl.baeza.diego.lavanderia.vista;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import cl.baeza.diego.lavanderia.R;
import cl.baeza.diego.lavanderia.controlador.Mail;
import cl.baeza.diego.lavanderia.controlador.Utilidades;
import cl.baeza.diego.lavanderia.db.ConexionSQLiteHelper;

public class ConfirmacionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnConfirmar;
    private RadioGroup rgHorario;
    private RadioButton rbHorario1;
    private RadioButton rbHorario2;
    private RadioButton rbHorario3;

    TextView tvPrecio;


    ProgressDialog pdialog;
    Mail m;
    boolean enviado= false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion);

        btnConfirmar = findViewById(R.id.btnConfirmar);
        rgHorario = findViewById(R.id.rgHorario);
        rbHorario1 = findViewById(R.id.rbHorario1);
        rbHorario2 = findViewById(R.id.rbHorario2);
        rbHorario3 = findViewById(R.id.rbHorario3);
        tvPrecio = findViewById(R.id.tvTotal);

        btnConfirmar.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if(v == btnConfirmar){
            if(rbHorario1.isChecked() || rbHorario2.isChecked() || rbHorario3.isChecked()){

                pdialog = ProgressDialog.show(ConfirmacionActivity.this,"Espere","Enviando...",true);

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

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


                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(ConfirmacionActivity.this,"usuarios",null,1);
                SQLiteDatabase db = conn.getReadableDatabase();

                String[] parametros = {"1"};
                String[] campos = {Utilidades.CAMPO_TELEFONO};

                Cursor cursor = db.query(Utilidades.TABLA_USUARIO, campos, Utilidades.CAMPO_ID + "=?",parametros,null,null,null);
                cursor.moveToFirst();

                String mensaje = "Telefono: " + cursor.getString(0) +
                        "\n\nServicio: " + getIntent().getExtras().getString("servicio") +
                        "\n\nMejora: " + getIntent().getExtras().getString("mejora") +
                        "\n\nDireccion: " + getIntent().getExtras().getString("ubicacion") +
                        "\n\nHorario: " + horario +
                        "\n\nPrecio: " + tvPrecio.getText();

                cursor.close();

                m = new Mail(getIntent().getExtras().getString("servicio"), mensaje);

                String[] to = {"diexgox@gmail.com"};

                m.setTo(to);


                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        try {

                            enviado = m.send();
                        }catch(Exception e) {
                            Toast.makeText(ConfirmacionActivity.this, "Hubo problemas enviado el Email", Toast.LENGTH_LONG).show();
                            Log.e("ConfirmacionActivity", "No se pudo enviar el Email", e);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {

                                pdialog.dismiss();
                                if(enviado) {
                                    Toast.makeText(ConfirmacionActivity.this, "Servicio Solicitado", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ConfirmacionActivity.this, "Email no fue enviado.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }).start();
            } else{
                Toast.makeText(ConfirmacionActivity.this,"Seleccione un horario",Toast.LENGTH_SHORT).show();
            }
        }

    }

}
