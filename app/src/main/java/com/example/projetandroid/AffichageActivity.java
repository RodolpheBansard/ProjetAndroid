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



        //Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String[] snowtamData = {"SWEN0311 ENSB 10130958\n" +
                "(SNOWTAM 0311\n" +
                "A) ENSB\n" +
                "B) 10130958 C) 10\n" +
                "F) 7/7/7 G) XX/XX/XX H) 4/4/3\n" +
                "N) ALL REPORTED TWYS/2\n" +
                "R) ALL REPORTED APRONS/2\n" +
                "T) CONTAMINATION/100/100/100/PERCENT.\n" +
                ")","SWEN0466 ENVA 10130330\n" +
                "(SNOWTAM 0466\n" +
                "A) ENVA\n" +
                "B) 10130330 C) 09\n" +
                "F) NIL/NIL/NIL H) 5/5/5\n" +
                "N) A1 A2 A3 A4 A5 A6 A8 B1 B2 G C G W Y/NIL\n" +
                "R) APRON EAST APRON NORTH APRON WEST M1 M2 M3/NIL\n" +
                ")\n" , "SWEN0218 ENBO 10121227\n" +
                "(SNOWTAM 0218\n" +
                "A) ENBO\n" +
                "B) 10121227 C) 07\n" +
                "F) 2/2/2 G) XX/XX/XX H) 5/5/5\n" +
                "R) MIL RWY OVERRUN EAST OVERRUN WEST/2\n" +
                "T) CONTAMINATION/100/100/100/PERCENT.\n" +
                ")" , "SWBG0037 BGUK 10121300\n" +
                "(SNOWTAM 0037\n" +
                "A) BGUK\n" +
                "B) 10121300 C) 05 F) 7/7/7 G) 1/1/1 H) 5/5/5\n" +
                "N) 7/GOOD\n" +
                "R) 7/POOR\n" +
                "T) RWY CONTAMINATION 50 PERCENT 1MM ICE  -\n" +
                "MEASURED FRICTION COEFFICIENT 71/69/74 TAP\n" +
                ")"};

        Snowtam[] snowtams = new Snowtam[snowtamData.length];
        for(int i = 0; i <snowtamData.length; i++){
            try {
                snowtams[i] = new Snowtam(snowtamData[i], getAssets().open("airport"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        // Capture the layout's TextView and set the string as its text
        //TextView textView = findViewById(R.id.textView);
        //textView.setText(snowtam.toString());

        viewPager = findViewById(R.id.pager);
        adapter = new FragmentCollectionAdapter(getSupportFragmentManager(),snowtams);
        viewPager.setAdapter(adapter);


    }
}
