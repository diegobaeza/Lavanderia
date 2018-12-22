package cl.baeza.diego.lavanderia.vista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cl.baeza.diego.lavanderia.R;
import cl.baeza.diego.lavanderia.controlador.Mail;
import cl.baeza.diego.lavanderia.controlador.Utilidades;
import cl.baeza.diego.lavanderia.db.ConexionSQLiteHelper;

public class DetalleActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSolicitar;

    TextView tvNombre;
    TextView tvTelefono;
    TextView tvServicio;
    TextView tvMejora;
    TextView tvDireccion;
    TextView tvHorario;
    TextView tvTipoPago;

    ProgressDialog pdialog;
    Mail m;
    boolean enviado= false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        btnSolicitar= findViewById(R.id.btnSolicitar);
        tvNombre = findViewById(R.id.tvNombre);
        tvTelefono = findViewById(R.id.tvTelefono);
        tvServicio = findViewById(R.id.tvServicio);
        tvMejora = findViewById(R.id.tvMejora);
        tvDireccion = findViewById(R.id.tvDireccion);
        tvHorario = findViewById(R.id.tvHorario);
        tvTipoPago = findViewById(R.id.tvTipoPago);

        btnSolicitar.setOnClickListener(this);

        cargarDatos();

    }

    public void cargarDatos(){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(DetalleActivity.this,Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db = conn.getReadableDatabase();

        String[] parametros = {"1"};
        String[] campos = {Utilidades.CAMPO_TELEFONO};

        Cursor cursor = db.query(Utilidades.TABLA_USUARIO, campos, Utilidades.CAMPO_ID + "=?",parametros,null,null,null);
        cursor.moveToFirst();

        tvNombre.setText(getIntent().getExtras().getString("nombre"));
        tvTelefono.setText(cursor.getString(0));
        tvServicio.setText(getIntent().getExtras().getString("servicio"));
        tvMejora.setText(getIntent().getExtras().getString("mejora"));
        tvDireccion.setText(getIntent().getExtras().getString("direccion"));
        tvHorario.setText(getIntent().getExtras().getString("horario"));
        tvTipoPago.setText(getIntent().getExtras().getString("tipoPago"));

        cursor.close();

    }

    @Override
    public void onClick(View v) {
        if(v == btnSolicitar){

            pdialog = ProgressDialog.show(DetalleActivity.this,"Espere","Enviando...",true);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);


            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(DetalleActivity.this,"usuarios",null,1);
            SQLiteDatabase db = conn.getReadableDatabase();

            String[] parametros = {"1"};
            String[] campos = {Utilidades.CAMPO_TELEFONO};

            Cursor cursor = db.query(Utilidades.TABLA_USUARIO, campos, Utilidades.CAMPO_ID + "=?",parametros,null,null,null);
            cursor.moveToFirst();

            String mensaje = "Nombre: "+ getIntent().getExtras().getString("nombre") +
                    "\n\nTelefono: " + cursor.getString(0) +
                    "\n\nServicio: " + getIntent().getExtras().getString("servicio") +
                    "\n\nMejora: " + getIntent().getExtras().getString("mejora") +
                    "\n\nDireccion: " + getIntent().getExtras().getString("direccion") +
                    "\n\nHorario: " + getIntent().getExtras().getString("horario") +
                    "\n\nTipo de Pago: "+ getIntent().getExtras().getString("tipoPago");

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
                        Toast.makeText(DetalleActivity.this, "Hubo problemas enviado el Email", Toast.LENGTH_LONG).show();
                        Log.e("DetalleActivity", "No se pudo enviar el Email", e);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {

                            pdialog.dismiss();
                            if(enviado) {
                                Toast.makeText(DetalleActivity.this, "Servicio Solicitado", Toast.LENGTH_LONG).show();

                                Intent i = new Intent(DetalleActivity.this,GraciasActivity.class);
                                startActivity(i);

                            } else {
                                Toast.makeText(DetalleActivity.this, "Email no fue enviado.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }).start();

        }
    }
}
