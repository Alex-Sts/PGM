package com.example.intentsactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalculadoraTiempo extends AppCompatActivity {

    TextView textoTiempo;
    EditText contenedorTiempo;
    Button botonCalcular;

    int segundos;
    int minutos;
    int horas;
    int dias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora_tiempo);

        textoTiempo = findViewById(R.id.textTiempo);
        contenedorTiempo = findViewById(R.id.containerTiempo);
        botonCalcular = findViewById(R.id.buttonCalc);

        botonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                segundos = Integer.parseInt(String.valueOf(contenedorTiempo.getText()));
                minutos = 0;
                horas = 0;
                dias = 0;

                textoTiempo.setText("Tiempo Calculado: ");

                while(segundos >= 60){
                    minutos++;
                    segundos -= 60;
                }

                while(minutos >= 60){
                    horas++;
                    minutos -= 60;
                }

                while(horas >= 24){
                    dias++;
                    horas -= 24;
                }

                if (dias > 0){
                    textoTiempo.setText(textoTiempo.getText() +"Días: " + dias);
                }

                if (horas > 0){
                    textoTiempo.setText(textoTiempo.getText() + " Horas: " + horas);
                }

                if (minutos > 0){
                    textoTiempo.setText(textoTiempo.getText() + " Minutos: " + minutos);
                }

                if (segundos > 0){
                    textoTiempo.setText(textoTiempo.getText() + " Segundos: " + segundos);
                }

            }
        });

    }
}