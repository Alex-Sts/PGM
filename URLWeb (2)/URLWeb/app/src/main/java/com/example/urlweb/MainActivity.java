package com.example.urlweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    EditText textoURL;
    Button botonBuscar;
    WebView vistaWeb;
    String validadaURL;
    ImageView imagenIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoURL = findViewById(R.id.editTextURL);
        botonBuscar = findViewById(R.id.buttonBuscar);
        vistaWeb = findViewById(R.id.webView);
        imagenIcono = findViewById(R.id.imageIcon);

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(String.valueOf(textoURL.getText()).contains("!y ")){
                    validadaURL = "https://www.youtube.com/results?search_query=" + String.valueOf(textoURL.getText()).replace("!y ", "").replace(" ", "");
                    vistaWeb.loadUrl(validadaURL);
                }else if(String.valueOf(textoURL.getText()).contains("!g ")){
                    validadaURL = "https://www.google.com/search?q=" + String.valueOf(textoURL.getText()).replace("!g ", "").replace(" ", "");
                    vistaWeb.loadUrl(validadaURL);
                }else if(String.valueOf(textoURL.getText()).contains("!s ")){
                    validadaURL = "https://open.spotify.com/search/" + String.valueOf(textoURL.getText()).replace("!s ", "").replace(" ", "");
                    vistaWeb.loadUrl(validadaURL);
                }else if(String.valueOf(textoURL.getText()).contains("!b ")){
                    validadaURL = "https://www.bing.com/search?q=" + String.valueOf(textoURL.getText()).replace("!b ", "").replace(" ", "");
                    vistaWeb.loadUrl(validadaURL);
                }else{
                    validadaURL = String.valueOf(textoURL.getText()).replace(" ", "");
                    if(validadaURL.contains("http://") || validadaURL.contains("https://")){
                        vistaWeb.loadUrl(validadaURL);
                    }else{
                        vistaWeb.loadUrl("https://" + validadaURL);
                    }
                }
            }
        });

        imagenIcono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentActivity = new Intent(MainActivity.this,PantallaAyuda.class);
                startActivity(intentActivity);
            }
        });

    }
}