package com.example.bengkel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    public static final String BENGKEL_TYPE = "com.example.bengkel.BENGKEL_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // menyembunyikan title bar
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException exc) {}

        setContentView(R.layout.activity_home);
    }

    public void viewBengkelList(View view) {
        Intent intent = new Intent(this, BengkelListActivity.class);
        int bengkelType = view.getId();

        if (bengkelType == R.id.buttonMotorcycleBengkelList) {
            intent.putExtra(BENGKEL_TYPE, "motorcycle");
        } else if (bengkelType == R.id.buttonCarBengkelList) {
            intent.putExtra(BENGKEL_TYPE, "car");
        }

        startActivity(intent);
    }
}