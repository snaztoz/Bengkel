package com.example.bengkel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // menyembunyikan title bar
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException exc) {}

        setContentView(R.layout.activity_home);
    }
}