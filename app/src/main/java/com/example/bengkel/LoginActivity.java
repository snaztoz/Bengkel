package com.example.bengkel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button login;
    EditText username, password;
    TextView register, forgot_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.btn_login);

        register = (TextView)findViewById(R.id.go_to_register);
        forgot_pass = (TextView)findViewById(R.id.forgot_pass);

        // go to register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(to_register);
                finish();
            }
        });

        // go to forgot password
//        forgot_pass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent to_forgot = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(to_forgot);
//                finish();
//            }
//        });

        // login
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String strUsername = username.getText().toString();
                String strPassword = password.getText().toString();
                Boolean masuk = db.checkLogin(strUsername, strPassword);
                if (masuk == true) {
                    Boolean updateSession = db.upgradeSession("ada", 1);
                    if (updateSession == true) {
                        Toast.makeText(getApplicationContext(), "Berhasil masuk", Toast.LENGTH_SHORT).show();
                        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}