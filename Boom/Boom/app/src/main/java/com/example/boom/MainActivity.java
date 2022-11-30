package com.example.boom;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    TextView textoDefault;
    Button botonPlay, botonReglas, botonConfig;

    int idCantidad, idDfificultad, prompt, vidas;
    private String filename = "Config.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoDefault = findViewById(R.id.textViewDefault);
        botonPlay = findViewById(R.id.buttonPlayMain);
        botonConfig = findViewById(R.id.buttonConfigMain);
        botonReglas = findViewById(R.id.buttonRulesMain);

        idCantidad = 2;
        prompt = 2;
        vidas = 3;
        idDfificultad = 1;

        escribirConfig(idCantidad, prompt, vidas, idDfificultad);

        botonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Redirige a nombres > partida
                Intent intentActivity = new Intent(MainActivity.this, Nombres.class);
                startActivity(intentActivity);
            }
        });

        botonReglas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Redirige a reglas
                Intent intentActivity = new Intent(MainActivity.this, Reglas.class);
                startActivity(intentActivity);
            }
        });

        botonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Redirige a configuración
                Intent intentActivity = new Intent(MainActivity.this, Configuracion.class);
                startActivity(intentActivity);
            }
        });
    }

    public void escribirConfig(int idCantidad, int prompt, int vidas, int idDfificultad) {
        try {
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            String data = idCantidad + ";" + prompt + ";" + vidas + ";" + idDfificultad + ";";
            fos.write(data.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}