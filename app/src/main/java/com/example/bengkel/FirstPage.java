package com.example.bengkel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstPage extends AppCompatActivity {

    private Button login;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        login = (Button)findViewById(R.id.to_login);
        register = (Button)findViewById(R.id.to_register);

        // go to register
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_register = new Intent(FirstPage.this, LoginActivity.class);
                startActivity(to_register);
                finish();
            }
        });

        // go to register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_register = new Intent(FirstPage.this, RegisterActivity.class);
                startActivity(to_register);
                finish();
            }
        });
    }
}