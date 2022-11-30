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
    String customURL;

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
                if(String.valueOf(textoURL.getText()).contains("!y ")){
                    customURL = "https://www.youtube.com/results?search_query=" + String.valueOf(textoURL.getText()).replace("!y ", "").replace(" ", "");
                    vistaWeb.loadUrl(customURL);
                }else if(String.valueOf(textoURL.getText()).contains("!g ")){
                    customURL = "https://www.google.com/search?q=" + String.valueOf(textoURL.getText()).replace("!g ", "").replace(" ", "");
                    vistaWeb.loadUrl(customURL);
                }else if(String.valueOf(textoURL.getText()).contains("!s ")){
                    customURL = "https://open.spotify.com/search/" + String.valueOf(textoURL.getText()).replace("!s ", "").replace(" ", "");
                    vistaWeb.loadUrl(customURL);
                }else if(String.valueOf(textoURL.getText()).contains("!b ")){
                    customURL = "https://www.bing.com/search?q=" + String.valueOf(textoURL.getText()).replace("!b ", "").replace(" ", "");
                    vistaWeb.loadUrl(customURL);
                }else{
                    customURL = String.valueOf(textoURL.getText()).replace(" ", "");
                    if(customURL.contains("http://") || customURL.contains("https://")){
                        vistaWeb.loadUrl(customURL);
                    }else{
                        vistaWeb.loadUrl("https://" + customURL);
                    }
                }


            }
        });

    }
}