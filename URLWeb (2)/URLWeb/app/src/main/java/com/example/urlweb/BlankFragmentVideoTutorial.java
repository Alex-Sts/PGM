package com.example.urlweb;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BlankFragmentVideoTutorial extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank_video_tutorial, container, false);
        WebView vistaWeb = (WebView) view.findViewById(R.id.webVideoTutorial);
        vistaWeb.loadUrl("file:///android_asset/Videotutorial.html");
        vistaWeb.setWebViewClient(new WebViewClient());

        return view;
    }
}