package com.example.boom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import com.example.boom.Nombres;

public class Partida extends AppCompatActivity {
    TextView textViewLetras, textViewPalabra, textViewNombre;
    ImageView imagenVida1, imagenVida2, imagenVida3;
    Button botonInsertarPalabra;
    EditText textoPalabra;

    String palabra = "";
    String letrasAnterior = "";
    String textoConfiguracion = "";
    String textoJugadores = "";
    String filename = "Config.txt";
    String filenamePlayers = "Players.txt";
    String filenameWinner = "Winner.txt";

    Chronometer chronometer;
    long pauseOffset;
    boolean running;

    int turno = 0;
    int intentos = 0;
    int intentosUsadas = 0;
    int tiempo = 0;
    int cantidadJugadores = 2;
    int prompt = 0;
    int vidas = 0;
    int dificultad = 0;

    boolean palabraUsada = false;

    ArrayList<JugadorOB> arrayJugadores = new ArrayList<>();
    ArrayList<String> arrayPalabrasUsadas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_partida);

        botonInsertarPalabra = findViewById(R.id.buttonInsertWord);
        textoPalabra = findViewById(R.id.textWord);
        textViewLetras = findViewById(R.id.textViewChar);
        textViewPalabra = findViewById(R.id.textViewNamePlayer);
        textViewNombre = findViewById(R.id.textViewTurn);
        imagenVida1 = findViewById(R.id.imageLife1);
        imagenVida2 = findViewById(R.id.imageLife2);
        imagenVida3 = findViewById(R.id.imageLife3);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        cantidadJugadores = leerConfig(1);
        prompt = leerConfig(2);
        vidas = leerConfig(3);
        dificultad = leerConfig(4);
        arrayJugadores = leerJugadores();

        if(dificultad == 1){
            tiempo = 25000;
        } else if(dificultad == 2){
            tiempo = 20000;
        } else if(dificultad == 3){
            tiempo = 13000;
        }

        letrasAnterior = recogerLetras();
        textViewLetras.setText(letrasAnterior);

        textViewNombre.setText("It's time to: " + arrayJugadores.get(turno).getNombres());

        if(vidas == 2){
            imagenVida3.setVisibility(View.INVISIBLE);
        } else if(vidas == 1){
            imagenVida3.setVisibility(View.INVISIBLE);
            imagenVida2.setVisibility(View.INVISIBLE);
        }

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= tiempo) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    juegoFinal();
                }
            }
        });

        botonInsertarPalabra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                juegoFinal();
            }
        });

        botonInsertarPalabra.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                    if(i == KeyEvent.KEYCODE_DPAD_CENTER){
                        juegoFinal();
                        return true;
                    }
                return false;
            }
        });
    }

    public boolean leerTxtParte1(String palabraJuego, String letras) {
        BufferedReader reader = null;
        String mLine;
        boolean palabraEncontrada = false;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("DiccionarioPalabras.txt"), "UTF-8"));

            while ((mLine = reader.readLine()) != null) {
                if(palabraJuego.contains(letras)){
                    if(palabraJuego.equalsIgnoreCase(mLine)){
                        palabraEncontrada = true;
                    }
                }
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
            return palabraEncontrada;
        }
    }

    public boolean leerTxtParte2(String palabraJuego, String letras) {
        BufferedReader reader = null;
        String mLine;
        boolean palabraEncontrada = false;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("DiccionarioPalabrasParte2.txt"), "UTF-8"));

            while ((mLine = reader.readLine()) != null) {
                if(palabraJuego.contains(letras)){
                    if(palabraJuego.equalsIgnoreCase(mLine)){
                        palabraEncontrada = true;
                    }
                }
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
            return palabraEncontrada;
        }
    }

    public boolean buscarPalabra(String palabraJuego, String letras){
        boolean palabraEncontrada = false;
        if(leerTxtParte1(palabraJuego, letras)){
            palabraEncontrada = true;
        }
        if(leerTxtParte2(palabraJuego, letras)){
            palabraEncontrada = true;
        }
        return palabraEncontrada;
    }

    public String recogerLetras(){
        Random rand = new Random();
        int n = rand.nextInt(127);
        int contador = 1;
        String devolverLetras = "";
        String mLine;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("DiccionarioLetras.txt"), "UTF-8"));

            while ((mLine = reader.readLine()) != null) {
                if(contador == n){
                    devolverLetras = mLine;
                }
                contador++;
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return devolverLetras;
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

    public ArrayList<JugadorOB> leerJugadores() {
        ArrayList<JugadorOB> arrayCreacionJugadores = new ArrayList<>();
        try {
            FileInputStream fin = openFileInput(filenamePlayers);
            int a;
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1) {
                temp.append((char) a);
            }
            textoJugadores = temp.toString();
            String[] nombresJugadores = textoJugadores.split(";");

            if(cantidadJugadores == 2){
                String nombreJugador1 = nombresJugadores[0];
                String nombreJugador2 = nombresJugadores[1];
                JugadorOB j1 = new JugadorOB(0, vidas, nombreJugador1);
                JugadorOB j2 = new JugadorOB(1, vidas, nombreJugador2);
                arrayCreacionJugadores.add(j1);
                arrayCreacionJugadores.add(j2);
            } else if(cantidadJugadores == 3){
                String nombreJugador1 = nombresJugadores[0];
                String nombreJugador2 = nombresJugadores[1];
                String nombreJugador3 = nombresJugadores[2];
                JugadorOB j1 = new JugadorOB(0, vidas, nombreJugador1);
                JugadorOB j2 = new JugadorOB(1, vidas, nombreJugador2);
                JugadorOB j3 = new JugadorOB(2, vidas, nombreJugador3);
                arrayCreacionJugadores.add(j1);
                arrayCreacionJugadores.add(j2);
                arrayCreacionJugadores.add(j3);
            } else if(cantidadJugadores == 4){
                String nombreJugador1 = nombresJugadores[0];
                String nombreJugador2 = nombresJugadores[1];
                String nombreJugador3 = nombresJugadores[2];
                String nombreJugador4 = nombresJugadores[3];
                JugadorOB j1 = new JugadorOB(0, vidas, nombreJugador1);
                JugadorOB j2 = new JugadorOB(1, vidas, nombreJugador2);
                JugadorOB j3 = new JugadorOB(2, vidas, nombreJugador3);
                JugadorOB j4 = new JugadorOB(3, vidas, nombreJugador4);
                arrayCreacionJugadores.add(j1);
                arrayCreacionJugadores.add(j2);
                arrayCreacionJugadores.add(j3);
                arrayCreacionJugadores.add(j4);
            } else if(cantidadJugadores == 5){
                String nombreJugador1 = nombresJugadores[0];
                String nombreJugador2 = nombresJugadores[1];
                String nombreJugador3 = nombresJugadores[2];
                String nombreJugador4 = nombresJugadores[3];
                String nombreJugador5 = nombresJugadores[4];
                JugadorOB j1 = new JugadorOB(0, vidas, nombreJugador1);
                JugadorOB j2 = new JugadorOB(1, vidas, nombreJugador2);
                JugadorOB j3 = new JugadorOB(2, vidas, nombreJugador3);
                JugadorOB j4 = new JugadorOB(3, vidas, nombreJugador4);
                JugadorOB j5 = new JugadorOB(4, vidas, nombreJugador5);
                arrayCreacionJugadores.add(j1);
                arrayCreacionJugadores.add(j2);
                arrayCreacionJugadores.add(j3);
                arrayCreacionJugadores.add(j4);
                arrayCreacionJugadores.add(j5);
            }
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayCreacionJugadores;
    }

    public void juegoEnMarcha(String palabraJuego, String letras){

        for(String s: arrayPalabrasUsadas){
            if(s.equalsIgnoreCase(palabraJuego)){
                palabraUsada = true;
            }
        }

        if(arrayJugadores.get(turno).getVidas() > 1) {
            if (palabraUsada){
                if(intentosUsadas >= 2){
                    resetTiempo();
                    int idUpdate = arrayJugadores.get(turno).getId();
                    int vidasUpdate = arrayJugadores.get(turno).getVidas() - 1;
                    String nombreUpdate = arrayJugadores.get(turno).getNombres();
                    JugadorOB actualizacionJugador = new JugadorOB(idUpdate, vidasUpdate, nombreUpdate);
                    arrayJugadores.set(idUpdate, actualizacionJugador);
                    letrasAnterior = recogerLetras();
                    textViewLetras.setText(letrasAnterior);
                    intentosUsadas = 0;
                    intentos = 0;
                    turno++;
                    if(turno >= arrayJugadores.size()){
                        turno = 0;
                    }
                } else {
                    intentosUsadas++;
                    textViewPalabra.setText("Word Used: " + intentosUsadas);
                }

            }else if(buscarPalabra(palabraJuego,letrasAnterior)){

                resetTiempo();

                letrasAnterior = recogerLetras();
                textViewLetras.setText(letrasAnterior);
                intentosUsadas = 0;
                intentos = 0;
                turno++;
                arrayPalabrasUsadas.add(palabraJuego);
                if(turno >= arrayJugadores.size()){
                    turno = 0;
                }
                textViewPalabra.setText(arrayJugadores.get(turno).getNombres() + " Your Turn!");
            } else {
                if(intentos >= prompt-1){

                    resetTiempo();

                    int idUpdate = arrayJugadores.get(turno).getId();
                    int vidasUpdate = arrayJugadores.get(turno).getVidas() - 1;
                    String nombreUpdate = arrayJugadores.get(turno).getNombres();
                    JugadorOB actualizacionJugador = new JugadorOB(idUpdate, vidasUpdate, nombreUpdate);
                    arrayJugadores.set(idUpdate, actualizacionJugador);
                    letrasAnterior = recogerLetras();
                    textViewLetras.setText(letrasAnterior);
                    intentosUsadas = 0;
                    intentos = 0;
                    turno++;
                    if(turno >= arrayJugadores.size()){
                        turno = 0;
                    }

                    textViewPalabra.setText(arrayJugadores.get(turno).getNombres() + " Your Turn!");

                } else {
                    empezarTiempo();
                    intentos++;
                    textViewPalabra.setText("You Have Failed " + intentos + " Times");
                }
            }
        } else {
            arrayJugadores.remove(turno);
            if(comprobarGanador()){
                turno = 0;
            }
            letrasAnterior = recogerLetras();
            textViewLetras.setText(letrasAnterior);
        }

        palabraUsada = false;

        if(arrayJugadores.get(turno).getVidas() == 3){
            imagenVida3.setVisibility(View.VISIBLE);
            imagenVida2.setVisibility(View.VISIBLE);
            imagenVida1.setVisibility(View.VISIBLE);
        }
    }

    public boolean comprobarGanador(){
        if(arrayJugadores.size() == 1){
            escribirGanador(arrayJugadores.get(0).getNombres());
            Intent intentActivity = new Intent( Partida.this, Winner.class);
            startActivity(intentActivity);
            return true;
        }
        return false;
    }

    public void resetTiempo(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;

        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void empezarTiempo(){
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void juegoFinal(){
        palabra = String.valueOf(textoPalabra.getText()).toUpperCase(Locale.ROOT);
        juegoEnMarcha(palabra, letrasAnterior);
        textViewNombre.setText("It's time to: " + arrayJugadores.get(turno).getNombres());
        if(arrayJugadores.get(turno).getVidas() == 3){
            imagenVida3.setVisibility(View.VISIBLE);
            imagenVida2.setVisibility(View.VISIBLE);
            imagenVida1.setVisibility(View.VISIBLE);
        } else if(arrayJugadores.get(turno).getVidas() == 2){
            imagenVida3.setVisibility(View.INVISIBLE);
            imagenVida2.setVisibility(View.VISIBLE);
            imagenVida1.setVisibility(View.VISIBLE);
        } else if(arrayJugadores.get(turno).getVidas() == 1) {
            imagenVida3.setVisibility(View.INVISIBLE);
            imagenVida2.setVisibility(View.INVISIBLE);
            imagenVida1.setVisibility(View.VISIBLE);
        }
        textoPalabra.setText("");
    }

    public void escribirGanador(String nombre) {
        try {
            FileOutputStream fos = openFileOutput(filenameWinner, Context.MODE_PRIVATE);
            String data = nombre + ";";
            fos.write(data.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}