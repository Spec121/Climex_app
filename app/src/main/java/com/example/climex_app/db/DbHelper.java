package com.example.climex_app.db;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "login.db";
    private static final String TABLE_USUARIOS = "t_usuarios";
    private final Context context;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USUARIOS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "correo_electronico TEXT NOT NULL, " +
                "pass TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        onCreate(db);
    }

    // Método para registrar usuario
    public boolean registrarUsuario(String nombre, String email, String password) {
        if (nombreExiste(nombre)) {
            mostrarAlerta("Nombre de usuario ya en uso", "Por favor, elige otro nombre de usuario.");
            return false;
        } else if (correoExiste(email)) {
            mostrarAlerta("Correo electrónico ya registrado", "Por favor, usa otro correo electrónico.");
            return false;
        }

        // Si no existe, registrar el nuevo usuario
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("correo_electronico", email);
        values.put("pass", password);

        long result = db.insert(TABLE_USUARIOS, null, values);
        db.close();
        return result != -1; // Devuelve true si la inserción fue exitosa
    }

    // Verifica si el nombre de usuario ya existe
    private boolean nombreExiste(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE nombre = ?", new String[]{nombre});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return existe;
    }

    // Verifica si el correo electrónico ya existe
    private boolean correoExiste(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE correo_electronico = ?", new String[]{email});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return existe;
    }


    private void mostrarAlerta(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss())
                .show();
    }


    public boolean validarUsuario(String nombre, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USUARIOS,
                new String[]{"id"},
                "nombre=? AND pass=?",
                new String[]{nombre, password},
                null, null, null);

        boolean existe = (cursor.getCount() > 0);
        cursor.close();
        return existe;
    }

    public boolean actualizarUsuario(String usuarioAntiguo, String nuevoUsuario, String nuevoEmail, String nuevoPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nuevoUsuario);
        values.put("correo_electronico", nuevoEmail);
        values.put("pass", nuevoPassword);

        int result = db.update(TABLE_USUARIOS, values, "nombre=?", new String[]{usuarioAntiguo});
        db.close();
        return result > 0; // Devuelve true si se actualizó al menos un registro
    }

    public boolean eliminarUsuario(String usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_USUARIOS, "nombre=?", new String[]{usuario});
        db.close();
        return result > 0; // Devuelve true si se eliminó al menos un registro
    }

    // Método para obtener los datos del usuario
    public Cursor obtenerDatosUsuario(String nombre, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Consulta para obtener nombre, correo electrónico y contraseña
        return db.rawQuery("SELECT nombre, correo_electronico, pass FROM " + TABLE_USUARIOS + " WHERE nombre=? AND pass=?", new String[]{nombre, password});
    }


    public String obtenerEmailPorUsuario(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USUARIOS,
                new String[]{"correo_electronico"},
                "nombre=?",
                new String[]{nombre},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String email = cursor.getString(0); // Obtiene el primer (y único) campo, que es el correo electrónico
            cursor.close();
            return email; // Devuelve el correo electrónico
        }
        if (cursor != null) {
            cursor.close();
        }
        return null; // Devuelve null si no se encontró el usuario
    }

}


