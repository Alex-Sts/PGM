package com.example.urlweb;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class PantallaAyuda extends AppCompatActivity {

    TabLayout tabLayoutBotones;
    ViewPager viewPantallas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ayuda);

        tabLayoutBotones = findViewById(R.id.tabLayoutBotones);
        viewPantallas = findViewById(R.id.viewPagerPantallas);

        tabLayoutBotones.setupWithViewPager(viewPantallas);

        VPAdaptador vpAdaptador = new VPAdaptador(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdaptador.addFragment(new BlankFragmentFAQ(),"FAQ");
        vpAdaptador.addFragment(new BlankFragmentIntroduction(),"Introduction");
        vpAdaptador.addFragment(new BlankFragmentVideoTutorial(),"VideoTutorial");
        viewPantallas.setAdapter(vpAdaptador);
    }
}