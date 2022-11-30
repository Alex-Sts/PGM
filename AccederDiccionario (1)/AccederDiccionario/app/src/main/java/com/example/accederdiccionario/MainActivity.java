package com.example.accederdiccionario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView textViewLetras, textViewPalabra, textViewNombre;
    ImageView imagenVida1, imagenVida2, imagenVida3;
    Button botonInsertarPalabra;
    EditText textoPalabra;

    String palabra = "";
    String letrasAnterior = "";
    String textoConfiguracion = "";
    String filename = "Config.txt";

    Chronometer chronometer;
    long pauseOffset;
    boolean running;

    int turno = 0;
    int prompt = /*leerConfig(2)*/ 2;
    int vidas = 3;
    int tiempo = 10000;
    int intentos = 0;
    int intentosUsadas = 0;

    boolean palabraUsada = false;

    JugadorOB j1 = new JugadorOB(0, vidas, "Alex");
    JugadorOB j2 = new JugadorOB(1, vidas, "Joan");
    JugadorOB j3 = new JugadorOB(2, vidas, "Pau");

    ArrayList<JugadorOB> arrayJugadores = new ArrayList<>();
    ArrayList<String> arrayPalabrasUsadas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonInsertarPalabra = findViewById(R.id.buttonInsertWord2);
        textoPalabra = findViewById(R.id.textWord2);
        textViewLetras = findViewById(R.id.textViewChar2);
        textViewPalabra = findViewById(R.id.textViewNamePlayer2);
        textViewNombre = findViewById(R.id.textViewTurn2);
        imagenVida1 = findViewById(R.id.imageLife1);
        imagenVida2 = findViewById(R.id.imageLife2);
        imagenVida3 = findViewById(R.id.imageLife3);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        letrasAnterior = recogerLetras();
        textViewLetras.setText(letrasAnterior);

        arrayJugadores.add(j1);
        arrayJugadores.add(j2);
        arrayJugadores.add(j3);

        textViewNombre.setText("It's time to: " + arrayJugadores.get(turno).getNombres() + " Vidas: " + arrayJugadores.get(turno).getVidas());

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
                    palabra = String.valueOf(textoPalabra.getText()).toUpperCase(Locale.ROOT);
                    juegoEnMarcha(palabra, letrasAnterior);
                    textViewNombre.setText("It's time to: " + arrayJugadores.get(turno).getNombres() + " Vidas: " + arrayJugadores.get(turno).getVidas());
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
                    if(arrayJugadores.size() == 1){
                        Intent intentActivity = new Intent(MainActivity.this, Winner.class);
                        startActivity(intentActivity);
                    }
                }
            }
        });

        botonInsertarPalabra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                palabra = String.valueOf(textoPalabra.getText()).toUpperCase(Locale.ROOT);
                juegoEnMarcha(palabra, letrasAnterior);
                textViewNombre.setText("It's time to: " + arrayJugadores.get(turno).getNombres() + " Vidas: " + arrayJugadores.get(turno).getVidas());
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
                if(arrayJugadores.size() == 1){
                    Intent intentActivity = new Intent(MainActivity.this, Winner.class);
                    startActivity(intentActivity);
                }
            }
        });

        botonInsertarPalabra.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                    if(i == KeyEvent.KEYCODE_DPAD_CENTER){
                        palabra = String.valueOf(textoPalabra.getText()).toUpperCase(Locale.ROOT);
                        juegoEnMarcha(palabra, letrasAnterior);
                        textViewNombre.setText("It's time to: " + arrayJugadores.get(turno).getNombres() + " Vidas: " + arrayJugadores.get(turno).getVidas());
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
                        if(arrayJugadores.size() == 1){
                            Intent intentActivity = new Intent(MainActivity.this, Winner.class);
                            startActivity(intentActivity);
                        }
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
                    textViewPalabra.setText("Palabra Usada" + intentosUsadas);
                }

            }else if(buscarPalabra(palabraJuego,letrasAnterior)){

                resetTiempo();

                textViewPalabra.setText("Palabra correcta");
                letrasAnterior = recogerLetras();
                textViewLetras.setText(letrasAnterior);
                intentosUsadas = 0;
                intentos = 0;
                turno++;
                arrayPalabrasUsadas.add(palabraJuego);
                if(turno >= arrayJugadores.size()){
                    turno = 0;
                }
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
                } else {
                    empezarTiempo();
                    intentos++;
                    textViewPalabra.setText("Palabra Incorrecta" + intentos);
                }
            }
        } else {
            arrayJugadores.remove(turno);
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

}