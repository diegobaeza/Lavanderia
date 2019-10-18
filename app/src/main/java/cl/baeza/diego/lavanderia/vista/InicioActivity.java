package cl.baeza.diego.lavanderia.vista;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;


import cl.baeza.diego.lavanderia.R;
import cl.baeza.diego.lavanderia.modelo.Direccion;

public class InicioActivity extends AppCompatActivity implements View.OnClickListener {

    VideoView vvInicio;
    Button btnEntrar;
    ImageView imgLogo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inicio);



        vvInicio = findViewById(R.id.vvInicio);
        btnEntrar = findViewById(R.id.btnEntrar);
        imgLogo = findViewById(R.id.logoimagen);

        btnEntrar.setOnClickListener(this);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video_prueba1);
        vvInicio.setVideoURI(uri);
        vvInicio.start();

        mostrarBoton();
        mostrarLogo();

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
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            finish();
        }
    }

    public void mostrarBoton(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //btnEntrar.setVisibility(View.VISIBLE);
                btnEntrar.setVisibility(View.VISIBLE);
                btnEntrar.setAlpha(0.0f);
                btnEntrar.animate()
                        .alpha(1f)
                        .setDuration(500);
            }
        }, 7000);
    }

    public void mostrarLogo(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //btnEntrar.setVisibility(View.VISIBLE);
                imgLogo.setVisibility(View.VISIBLE);
                imgLogo.setAlpha(0.0f);
                imgLogo.animate()
                        .alpha(1f)
                        .setDuration(500);
            }
        }, 2200);
    }
}
