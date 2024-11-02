package com.example.climex_app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.climex_app.db.DbHelper;

public class RegistroClia extends AppCompatActivity {

    private EditText txtUsuarioRegistro, txtPassRegistro, txtEmailRegistro;
    private Button btnRegistro;
    private TextView textoRegresoLogin;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_clia); // Asegúrate de que este sea el nombre correcto de tu layout

        // Inicializa las vistas
        txtUsuarioRegistro = findViewById(R.id.txtUsuarioRegistro);
        txtPassRegistro = findViewById(R.id.txtPassRegistro);
        txtEmailRegistro = findViewById(R.id.txtEmailRegistro);
        btnRegistro = findViewById(R.id.btnRegistro);
        textoRegresoLogin = findViewById(R.id.textoRegresoLogin);

        dbHelper = new DbHelper(this);

        // Configura el botón de registro
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = txtUsuarioRegistro.getText().toString();
                String email = txtEmailRegistro.getText().toString();
                String password = txtPassRegistro.getText().toString();

                if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegistroClia.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Intentar registrar el usuario
                    boolean exito = dbHelper.registrarUsuario(nombre, email, password);
                    if (exito) {
                        Toast.makeText(RegistroClia.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        finish(); // Cierra la actividad de registro y vuelve a la anterior
                    } else {
                        // El usuario ya existe, se muestra un alert dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroClia.this);
                        builder.setTitle("Error de registro")
                                .setMessage("Este usuario ya está registrado. Por favor, usa otro correo.")
                                .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss())
                                .show();
                    }
                }
            }
        });

        // Configura el TextView para regresar a la actividad de inicio de sesión
        textoRegresoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crea una nueva intención para regresar a la actividad de inicio de sesión
                Intent intent = new Intent(RegistroClia.this, LoginClima.class); // Asegúrate de que MainActivity es tu pantalla de login
                startActivity(intent);
                finish(); // Cierra la actividad de registro
            }
        });
    }
}