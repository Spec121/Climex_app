package com.example.climex_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.climex_app.db.DbHelper;

public class LoginClima extends AppCompatActivity {

    private EditText txtUsuario, txtPass;
    private Button btnIngreso;
    private TextView registroTextoXd;
    private DbHelper dbHelper;
    private Switch switchDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_clima);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referencias a los elementos de la interfaz
        txtUsuario = findViewById(R.id.txtUsuario);
        txtPass = findViewById(R.id.txtPass);
        btnIngreso = findViewById(R.id.btnIngreso);
        registroTextoXd = findViewById(R.id.textoClick);
        switchDestino = findViewById(R.id.switch1); // Nuevo Switch

        dbHelper = new DbHelper(this);

        btnIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = txtUsuario.getText().toString().trim();
                String password = txtPass.getText().toString().trim();

                if (usuario.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginClima.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Verificar credenciales en la base de datos
                    boolean inicioExitoso = dbHelper.validarUsuario(usuario, password);
                    if (inicioExitoso) {
                        Toast.makeText(LoginClima.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                        // Obtener el correo electrónico del usuario
                        String email = dbHelper.obtenerEmailPorUsuario(usuario);

                        // Crear Intent según el estado del Switch
                        Intent intent;
                        if (switchDestino.isChecked()) {
                            // Switch activado: ir a Perfilusuario
                            intent = new Intent(LoginClima.this, PerfilUsuarioClima.class);
                            intent.putExtra("usuario", usuario);
                            intent.putExtra("password", password);
                            intent.putExtra("email", email != null ? email : ""); // Pasa el correo del usuario
                        } else {
                            // Switch desactivado: ir a Climaxd
                            intent = new Intent(LoginClima.this, MainActivity2.class);
                        }

                        // Iniciar la actividad según el Intent configurado
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginClima.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Manejo del registro de usuarios
        registroTextoXd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginClima.this, RegistroClia.class);
                startActivity(intent);
            }
        });
    }


}