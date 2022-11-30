package com.example.boom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Nombres extends AppCompatActivity {

    Button botonInsertar, botonJugar;
    EditText textNombre;
    AlertDialog.Builder builder;

    int cantidadJugadores, vidasJugadores, contador;
    String textoConfiguracion;
    String filename = "Config.txt";
    String filenamePlayers = "Players.txt";

    ArrayList<JugadorOB> arrayJugadores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nombres);

        botonInsertar = findViewById(R.id.buttonInsertName);
        botonJugar = findViewById(R.id.buttonPlayName);
        textNombre = findViewById(R.id.textName);
        builder = new AlertDialog.Builder(this);

        contador = 0;
        cantidadJugadores = leerConfig(1);
        vidasJugadores = leerConfig(3);

        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreJugador = String.valueOf(textNombre.getText());
                if(cantidadJugadores > contador){
                    //Añadir los nuevos jugadores al array
                    JugadorOB nuevoJugador = new JugadorOB(contador, vidasJugadores, nombreJugador);
                    arrayJugadores.add(nuevoJugador);
                    contador++;
                    escribirJugadores();
                } else {
                    //Contar si el usuario inserta mas nombres que cantidad de jugadores indicada
                    crearAlertConfirmacion("You have indicated more names than the number of players indicated.");
                }
                textNombre.setText("");
            }
        });

        botonJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cantidadJugadores > contador){
                    //Indica la falta de nombres
                    crearAlertConfirmacion("Missing names to indicate of the players.");
                } else {
                    //Inicia el juego
                    Toast.makeText(getApplicationContext(),"Starting Game...",
                            Toast.LENGTH_SHORT).show();
                    Intent intentActivity = new Intent( Nombres.this, Partida.class);
                    startActivity(intentActivity);
                }
            }
        });
    }

    public int leerConfig(int eleccionConfig) {
        int devolverConfig = 0;
        try {
            FileInputStream fin = openFileInput(filename);
            int a;
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1) {
                temp.append((char) a);
            }
            textoConfiguracion = temp.toString();
            String[] partesConfiguracion = textoConfiguracion.split(";");
            String cantidadJugadores = partesConfiguracion[0];
            String prompt = partesConfiguracion[1];
            String vidas = partesConfiguracion[2];
            String dificultad = partesConfiguracion[3];

            if(eleccionConfig == 1){
                devolverConfig = Integer.parseInt(String.valueOf(cantidadJugadores));
            } else if(eleccionConfig == 2){
                devolverConfig = Integer.parseInt(String.valueOf(prompt));
            } else if(eleccionConfig == 3){
                devolverConfig = Integer.parseInt(String.valueOf(vidas));
            } else if(eleccionConfig == 4){
                devolverConfig = Integer.parseInt(String.valueOf(dificultad));
            }
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return devolverConfig;
    }

    public void crearAlertConfirmacion(String mensaje){
        builder.setMessage(mensaje)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),"Try Again...",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("¡Alert!");
        alert.show();
    }

    public void escribirJugadores() {
        String data = "";
        for(JugadorOB j: arrayJugadores){
            data += j.getNombres() + ";";
        }
        try {
            FileOutputStream fos = openFileOutput(filenamePlayers, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}