package com.example.bengkel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button register;
    EditText username, email, password, password_conf;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        username = (EditText)findViewById(R.id.username);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        password_conf = (EditText)findViewById(R.id.password_conf);
        register = (Button)findViewById(R.id.btn_register);

        login = (TextView)findViewById(R.id.go_to_login);

        // go to register
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(goLogin);
                finish();
            }
        });

        // register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUsername = username.getText().toString();
                String strEmail = email.getText().toString();
                String strPassword = password.getText().toString();
                String strPassword_conf = password_conf.getText().toString();

                if (strUsername.isEmpty() || strPassword.isEmpty() || strEmail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username/Password/Email kosong", Toast.LENGTH_SHORT).show();
                } else {
//                    String usernameVal = "(?=\\s+$)";
                    String usernameVal = "\\A\\w{4,20}\\z";     // opsi lain
                    if (!strUsername.matches(usernameVal)) {
                        Toast.makeText(getApplicationContext(), "Username pendek/mengandung spasi", Toast.LENGTH_SHORT).show();
                    } else {
                        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        if (!strEmail.matches(emailPattern)) {
                            Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                        } else {
                            String passwordVal = "^" +
                                    "(?=.*[0-9])" +        // at least 1 digit
                                    "(?=.*[a-z])" +            // at least 1 lower case
                                    "(?=.*[A-Z])" +            // at least 1 upper case
//                                    "(?=.*[a-zA-Z])" +        // any letter
                                    "(?=.*[!@#$%^&*+=])" +    // at least 1 special character
//                                    "(?=\\s+$)" +            // no white space
                                    ".{4,}" +                // at least 4 character
                                    "$";
                            if (!strPassword.matches(passwordVal)) {
                                Toast.makeText(getApplicationContext(), "Password lemah", Toast.LENGTH_SHORT).show();
                            } else {
                                if (strPassword.equals(strPassword_conf)) {
                                    Boolean daftar = db.insertUser(strUsername, strEmail, strPassword);
                                    if (daftar == true) {
                                        Toast.makeText(getApplicationContext(), "Register Berhasil", Toast.LENGTH_SHORT).show();
                                        Intent homeIntent = new Intent(RegisterActivity.this, HomeActivity.class);
                                        startActivity(homeIntent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Register gagal", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Password tidak sama", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent goLogin = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(goLogin);
        finish();
    }
}