package com.sunday.wallpapers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class Carga extends AppCompatActivity {

    /* declaration */
    TextView app_name, developer; // android.widget.TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carga);

        /* initialization */
        app_name = findViewById(R.id.app_name);
        developer = findViewById(R.id.developer);

        /* Changing font */
        String fontLocation = "fonts/sans_negrita.ttf";
        Typeface tf = Typeface.createFromAsset(Carga.this.getAssets(), fontLocation);
        /* Changing font */

        // loading page duration
        final int DURATION = 3000; // 3 seconds
        // executes code in a specified time
        new Handler().postDelayed(() -> {
            // Code to be executed passed DURATION (3 seconds)
            // arg-1: context: class where the call is made
            // arg-2: destiny class
            Intent intent = new Intent(Carga.this, MainActivityAdmin.class);
            startActivity(intent);
            finish();
        }, DURATION);

        /* set the font */
        app_name.setTypeface(tf);
        developer.setTypeface(tf);
    }
}