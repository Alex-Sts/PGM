package com.example.intentsactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonActividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonActividad = findViewById(R.id.buttonAct);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intentActivity = new Intent(MainActivity.this,CalculadoraTiempo.class);
                startActivity(intentActivity);
            }
        }, 5000);

        buttonActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentActivity = new Intent(MainActivity.this,CalculadoraTiempo.class);
                startActivity(intentActivity);
            }
        });

    }
}