package com.example.boom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.IOException;

public class Winner extends AppCompatActivity {

    Button botonInicio;
    TextView textViewGanador;

    String textoGanador = "";
    String filenameWinner = "Winner.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_winner);

        botonInicio = findViewById(R.id.buttonHome);
        textViewGanador = findViewById(R.id.textViewWinner);

        textViewGanador.setText(leerGanador() + " Has Won This Game!");

        botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentActivity = new Intent( Winner.this, MainActivity.class);
                startActivity(intentActivity);
            }
        });

    }

    public String leerGanador() {
        String nombreGanador = "";
        try {
            FileInputStream fin = openFileInput(filenameWinner);
            int a;
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1) {
                temp.append((char) a);
            }
            textoGanador = temp.toString();
            String[] ganador = textoGanador.split(";");
            nombreGanador = ganador[0];

            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nombreGanador;
    }
}