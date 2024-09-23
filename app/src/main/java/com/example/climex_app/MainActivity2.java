package com.example.climex_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    Button botonpestaña3;

    private TextView cityTextView, tempTextView, weatherTextView;
    private Button agregarCiudadesButton;


    // Define tu API_KEY y la ciudad
    private final String API_KEY = "cae014a9bfcd25eeedf20c545ad3b963";  // Reemplaza con tu clave
    private final String CITY = "Ovalle";         // Reemplaza con la ciudad
    private final String URL_STRING = "https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&appid=" + API_KEY + "&units=metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        botonpestaña3=(Button)findViewById(R.id.button);

        botonpestaña3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity2.this, MainActivity3.class);

                startActivity(i);
            }
        });

        // Asocia los TextViews del layout
        cityTextView = findViewById(R.id.cityTextView);
        tempTextView = findViewById(R.id.tempTextView);
        weatherTextView = findViewById(R.id.weatherTextView);

        // Ejecuta la tarea para obtener datos del clima
        new GetWeatherTask().execute();
    }



    private class GetWeatherTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // Conectar a la API
                URL url = new URL(URL_STRING);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                // Leer la respuesta
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                return result.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                try {
                    // Procesar el JSON
                    JSONObject jsonResponse = new JSONObject(result);
                    String cityName = jsonResponse.getString("name");
                    JSONObject mainObject = jsonResponse.getJSONObject("main");
                    double temperature = mainObject.getDouble("temp");
                    JSONArray weatherArray = jsonResponse.getJSONArray("weather");
                    JSONObject weatherObject = weatherArray.getJSONObject(0);
                    String weatherDescription = weatherObject.getString("description");

                    // Actualizar los TextViews
                    cityTextView.setText(" " + cityName);
                    tempTextView.setText(" " + temperature + "°C");
                    weatherTextView.setText("Clima: " + weatherDescription);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Manejar el caso en que no se recibe respuesta
                cityTextView.setText("Error al obtener datos");
            }
        }
    }
}
