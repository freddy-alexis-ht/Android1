package com.sunday.wallpapers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Carga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carga);

        // loading page duration
        final int DURATION = 3000; // 3 seconds
        // executes code in a specified time
        new Handler().postDelayed(() -> {
            // Code to be executed passed DURATION (3 seconds)
            // arg-1: context: class where the call is made
            // arg-2: destiny class
            Intent intent = new Intent(Carga.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, DURATION);
    }
}