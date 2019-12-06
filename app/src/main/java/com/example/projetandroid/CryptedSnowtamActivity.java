package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CryptedSnowtamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypted_snowtam);

        Intent intent = getIntent();
        String message = intent.getStringExtra(AffichageActivity.EXTRA_MESSAGE);

        TextView textView = findViewById(R.id.cryptedSnowtam);
        textView.setText(message);
    }
}
