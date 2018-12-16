package cl.baeza.diego.lavanderia.vista;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import cl.baeza.diego.lavanderia.R;


public class ServiciosActivity extends AppCompatActivity {

    private Button btnServicio1;
    private Button btnServicio2;
    private Button btnServicio3;
    private Button btnServicio4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        btnServicio1 = findViewById(R.id.btnServicio1);
        btnServicio2 = findViewById(R.id.btnServicio2);
        btnServicio3 = findViewById(R.id.btnServicio3);
        btnServicio4 = findViewById(R.id.btnServicio4);


    }
}
