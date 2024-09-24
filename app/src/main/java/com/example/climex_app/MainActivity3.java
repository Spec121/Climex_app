package com.example.climex_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {

    Button buttonvuelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        buttonvuelta = (Button) findViewById(R.id.buttonvuelta);

        // Configuramos el click listener para el botón
        buttonvuelta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creamos un Intent para volver a MainActivity2
                Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                startActivity(intent); // Iniciamos MainActivity2
                finish(); // Finalizamos MainActivity3 para que no quede en el historial
            }
        });
    }
}
