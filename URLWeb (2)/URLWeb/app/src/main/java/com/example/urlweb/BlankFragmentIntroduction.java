package com.example.urlweb;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BlankFragmentIntroduction extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank_introduction, container, false);
        WebView vistaWeb = (WebView) view.findViewById(R.id.webIntroduction);
        vistaWeb.loadUrl("file:///android_asset/Introduction.html");
        vistaWeb.setWebViewClient(new WebViewClient());

        return view;
    }
}