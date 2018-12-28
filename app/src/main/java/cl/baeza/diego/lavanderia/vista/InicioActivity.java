package cl.baeza.diego.lavanderia.vista;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.VideoView;

import cl.baeza.diego.lavanderia.R;

public class InicioActivity extends AppCompatActivity implements View.OnClickListener {

    VideoView vvInicio;
    Button btnEntrar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        vvInicio = findViewById(R.id.vvInicio);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(this);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video_prueba1);
        vvInicio.setVideoURI(uri);
        vvInicio.start();


        vvInicio.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v == btnEntrar){
            Intent i = new Intent(InicioActivity.this,NumeroActivity.class);
            startActivity(i);
            finish();
        }
    }
}
