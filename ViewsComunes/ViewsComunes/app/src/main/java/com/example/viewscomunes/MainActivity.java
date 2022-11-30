package com.example.viewscomunes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textoOcultar;
    TextView textoRating;
    TextView textoLargo;
    Button botonFondo;
    Button botonLetras;
    CheckBox checkbox;
    View vista;
    RatingBar rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoOcultar = findViewById(R.id.textViewHidden);
        textoRating = findViewById(R.id.textViewRating);
        textoLargo = findViewById(R.id.textViewLong);
        botonFondo = findViewById(R.id.buttonBackground);
        botonLetras = findViewById(R.id.buttonDark);
        checkbox = findViewById(R.id.checkBox);
        vista = findViewById(R.id.view);
        rating = findViewById(R.id.ratingBar);

        textoOcultar.setVisibility(View.INVISIBLE);

        botonFondo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vista.setBackgroundColor(Color.RED);
            }
        });

        botonLetras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonLetras.setTextColor(Color.RED);
            }
        });

        botonLetras.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                botonFondo.setTextColor(Color.RED);
                botonFondo.setBackgroundColor(Color.DKGRAY);
                return false;
            }
        });

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkbox.isChecked()){
                    textoOcultar.setVisibility(View.VISIBLE);
                } else {
                    textoOcultar.setVisibility(View.INVISIBLE);
                }
            }
        });

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                textoRating.setText(v + "/5");
                if(v == 5){
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "¡Nos Has Calificado Con 5 Estrellas!", Toast.LENGTH_SHORT);
                    toast1.show();
                    vista.setBackgroundColor(Color.YELLOW);
                }
            }
        });

        textoLargo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast toast1 = Toast.makeText(getApplicationContext(),
                                "¡Muchas Gracias!", Toast.LENGTH_SHORT);
                toast1.show();
                return false;
            }
        });
    }
}