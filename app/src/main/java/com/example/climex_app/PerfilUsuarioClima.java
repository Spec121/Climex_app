package com.example.climex_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.climex_app.db.DbHelper;

public class PerfilUsuarioClima extends AppCompatActivity {

    private EditText txtUsuarioPerfiles, txtPassPerfiles, txtEmailPerfiles;
    private Button btnActualizar, btnEliminar;
    private DbHelper dbHelper;
    private String usuario, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_usuario_clima);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vincular vistas
        txtUsuarioPerfiles = findViewById(R.id.txtUsuarioPerfiles);
        txtPassPerfiles = findViewById(R.id.txtPassPerfiles);
        txtEmailPerfiles = findViewById(R.id.txtEmailPerfiles);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        dbHelper = new DbHelper(this);

        // Obtener datos del Intent
        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        password = intent.getStringExtra("password");
        email = intent.getStringExtra("email");

        // Mostrar datos en los campos de texto
        txtUsuarioPerfiles.setText(usuario);
        txtPassPerfiles.setText(password);
        txtEmailPerfiles.setText(email);

        configurarBotones();
    }

    private void configurarBotones() {
        // Botón para actualizar datos
        btnActualizar.setOnClickListener(v -> {
            String nuevoUsuario = txtUsuarioPerfiles.getText().toString().trim();
            String nuevoPassword = txtPassPerfiles.getText().toString().trim();
            String nuevoEmail = txtEmailPerfiles.getText().toString().trim();

            if (nuevoUsuario.isEmpty() || nuevoPassword.isEmpty() || nuevoEmail.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return; // Sale del método si los campos están vacíos
            }

            if (dbHelper.actualizarUsuario(usuario, nuevoUsuario, nuevoEmail, nuevoPassword)) {
                Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                usuario = nuevoUsuario; // Actualiza los datos en el Intent actual
            } else {
                Toast.makeText(this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón para eliminar usuario
        btnEliminar.setOnClickListener(v -> {
            if (dbHelper.eliminarUsuario(usuario)) {
                Toast.makeText(this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
                finish(); // Finaliza la actividad después de la eliminación
            } else {
                Toast.makeText(this, "Error al eliminar el usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }
}