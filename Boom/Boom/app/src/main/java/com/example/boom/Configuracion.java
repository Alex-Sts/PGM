package com.example.boom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Configuracion extends AppCompatActivity {

    Button botonAplicar, botonJugar, botonOk;
    Spinner spinnerDificultad, spinnerJugadores, spinnerPrompt, spinnerVidas;

    int idCantidad, idDfificultad, prompt, vidas;
    private String filename = "Config.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_config);

        botonAplicar = findViewById(R.id.buttonApplySett);
        botonJugar = findViewById(R.id.buttonPlaySett);
        botonOk = findViewById(R.id.buttonOkSettings);
        spinnerDificultad = findViewById(R.id.spinnerDifficulty);
        spinnerJugadores = findViewById(R.id.spinnerPlayers);
        spinnerPrompt = findViewById(R.id.spinnerPrompt);
        spinnerVidas = findViewById(R.id.spinnerLifes);

        idCantidad = 2;
        prompt = 2;
        vidas = 3;
        idDfificultad = 1;

        ArrayList<DificultadOB> arrayDificultadOB = new ArrayList<DificultadOB>();
        arrayDificultadOB.add(new DificultadOB(1, "Easy"));
        arrayDificultadOB.add(new DificultadOB(2, "Medium"));
        arrayDificultadOB.add(new DificultadOB(3, "Hard"));
        ArrayAdapter<DificultadOB> arrayAdaptadorDificultad = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayDificultadOB);
        spinnerDificultad.setAdapter(arrayAdaptadorDificultad);

        ArrayList<Integer> arrayJugadores = new ArrayList<Integer>();
        arrayJugadores.add(new Integer(2));
        arrayJugadores.add(new Integer(3));
        arrayJugadores.add(new Integer(4));
        arrayJugadores.add(new Integer(5));
        ArrayAdapter<Integer> arrayAdaptadorJugadores = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayJugadores);
        spinnerJugadores.setAdapter(arrayAdaptadorJugadores);

        ArrayList<Integer> arrayPrompt = new ArrayList<Integer>();
        arrayPrompt.add(new Integer(1));
        arrayPrompt.add(new Integer(2));
        arrayPrompt.add(new Integer(3));
        arrayPrompt.add(new Integer(4));
        arrayPrompt.add(new Integer(5));
        ArrayAdapter<Integer> arrayAdaptadorPrompt = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayPrompt);
        spinnerPrompt.setAdapter(arrayAdaptadorPrompt);

        ArrayList<Integer> arrayVidas = new ArrayList<Integer>();
        arrayVidas.add(new Integer(3));
        arrayVidas.add(new Integer(2));
        ArrayAdapter<Integer> arrayAdaptadorVidas = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayVidas);
        spinnerVidas.setAdapter(arrayAdaptadorVidas);

        botonAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardar atributos de la configuración
                prompt = recogerInformacionSpinnerPrompt();
                vidas = recogerInformacionSpinnerVidas();
                idCantidad = recogerInformacionSpinnerJugadores();
                idDfificultad = recogerInformacionSpinnerDificultad();
                escribirConfig(idCantidad, prompt, vidas, idDfificultad);
            }
        });

        botonJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Redirige a nombres > partida
                Intent intentActivity = new Intent(Configuracion.this, Nombres.class);
                startActivity(intentActivity);
            }
        });

        botonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentActivity = new Intent(Configuracion.this, MainActivity.class);
                startActivity(intentActivity);
            }
        });
    }

    private int recogerInformacionSpinnerDificultad() {
        int idDificultad;
        DificultadOB dificultadOBElegida = (DificultadOB) spinnerDificultad.getSelectedItem();
        idDificultad = dificultadOBElegida.getId();
        return idDificultad;
    }

    private int recogerInformacionSpinnerJugadores() {
        int totalCantidad;
        totalCantidad = Integer.parseInt(String.valueOf(spinnerJugadores.getSelectedItem()));
        return totalCantidad;
    }

    private int recogerInformacionSpinnerPrompt() {
        int totalPrompt;
        totalPrompt = Integer.parseInt(String.valueOf(spinnerPrompt.getSelectedItem()));
        return totalPrompt;
    }

    private int recogerInformacionSpinnerVidas() {
        int totalVidas;
        totalVidas = Integer.parseInt(String.valueOf(spinnerVidas.getSelectedItem()));
        return totalVidas;
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