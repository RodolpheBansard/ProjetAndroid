package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AffichageActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private FragmentCollectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage);

        viewPager = findViewById(R.id.pager);
        adapter = new FragmentCollectionAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        /*Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String message = "SWEN0311 ENSB 10130958\n" +
                "(SNOWTAM 0311\n" +
                "A) ENSB\n" +
                "B) 10130958 C) 10\n" +
                "F) 7/7/7 G) XX/XX/XX H) 4/4/3\n" +
                "N) ALL REPORTED TWYS/2\n" +
                "R) ALL REPORTED APRONS/2\n" +
                "T) CONTAMINATION/100/100/100/PERCENT.\n" +
                ")";

        Snowtam snowtam = null;
        try {
            snowtam = new Snowtam(message, getAssets().open("airport"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(snowtam.toString());*/


    }
}
