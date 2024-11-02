package com.example.climex_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivityxd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mainxd);

        // Retraso de 4 segundos (4000 ms) antes de cambiar a MainActivity2
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Corregido: utilizar startActivity para iniciar una actividad
                Intent intent = new Intent(MainActivityxd.this, LoginClima.class);
                startActivity(intent); // Iniciar la nueva actividad
                finish(); // Cerrar la actividad actual
            }
        }, 3000);
    }
}
