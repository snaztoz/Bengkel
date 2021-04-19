package com.example.bengkel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BengkelListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bengkel_list);

        Intent intent = getIntent();
        String bengkelType = intent.getStringExtra(HomeActivity.BENGKEL_TYPE);

        TextView bengkelListTitle = findViewById(R.id.bengkelListTitle);
        bengkelListTitle.setText("Ready to fix your " + bengkelType);
    }
}