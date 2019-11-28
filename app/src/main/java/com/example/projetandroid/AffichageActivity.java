package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AffichageActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private FragmentCollectionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage);



        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


        String[] data = message.split("#");
        Snowtam[] snowtams = new Snowtam[data.length];
        for (int i=0; i<data.length; i++){
            try {
                snowtams[i] = new Snowtam(data[i],getAssets().open("airport"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        viewPager = findViewById(R.id.pager);
        adapter = new FragmentCollectionAdapter(getSupportFragmentManager(),snowtams);
        viewPager.setAdapter(adapter);


    }




}
