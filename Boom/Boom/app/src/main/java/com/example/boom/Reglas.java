package com.example.boom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Reglas extends AppCompatActivity {

    Button botonOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reglas);

        botonOk = findViewById(R.id.buttonOkRules);

        botonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Redirige a la pantalla principal
                Intent intentActivity = new Intent(Reglas.this, MainActivity.class);
                startActivity(intentActivity);
            }
        });

    }
}