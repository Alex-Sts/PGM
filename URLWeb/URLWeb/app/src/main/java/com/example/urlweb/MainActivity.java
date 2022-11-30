package com.example.urlweb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText textoURL;
    Button botonBuscar;
    WebView vistaWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoURL = findViewById(R.id.editTextURL);
        botonBuscar = findViewById(R.id.buttonBuscar);
        vistaWeb = findViewById(R.id.webView);

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vistaWeb.loadUrl(String.valueOf(textoURL.getText()));
            }
        });

    }
}