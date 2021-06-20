package com.example.bengkel;

import androidx.annotation.NonNull;
import androidx.annotation.StyleableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class RegisterActivity extends AppCompatActivity {

//    DatabaseHelper db;
//    Button register;
//    EditText username, email, password, password_conf;
//    TextView login;

    private Button register;
//    private EditText username;
//    private EditText phone;
//    private EditText email;
//    private EditText password;
//    private EditText password_conf;
    private TextInputLayout email, username, phone, password, password_conf;
    private TextView login;
    private ImageView back_to_login;

    private FirebaseAuth mAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("bengkel-ac9c7-default-rtdb");






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        username = (TextInputLayout)findViewById(R.id.username);
        phone = (TextInputLayout)findViewById(R.id.phone_number);
        email = (TextInputLayout)findViewById(R.id.email);
        password = (TextInputLayout)findViewById(R.id.password);
        password_conf = (TextInputLayout)findViewById(R.id.password_conf);
        register = (Button)findViewById(R.id.btn_register);
        back_to_login = (ImageView)findViewById(R.id.back_to_login);

        login = (TextView)findViewById(R.id.go_to_login);

        myRef.setValue("Hello, World!");

        /* go to login */
        back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(goLogin);
                finish();
            }
        });

        /* go to login */
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
                String strUsername = username.getEditText().getText().toString().trim();
                String strEmail = email.getEditText().getText().toString().trim();
                String strPhone = phone.getEditText().getText().toString().trim();
                String strPassword = password.getEditText().getText().toString().trim();
                String strPassword_conf = password_conf.getEditText().getText().toString().trim();

                if (strUsername.isEmpty() || strPassword.isEmpty() || strEmail.isEmpty()) {
                    StyleableToast.makeText(RegisterActivity.this, "Username/Password/Email kosong", R.style.exampleToast).show();
                } else {
//                    String usernameVal = "(?=\\s+$)";
                    String usernameVal = "\\A\\w{4,20}\\z";     // opsi lain
                    if (!strUsername.matches(usernameVal)) {
                        StyleableToast.makeText(RegisterActivity.this, "Username pendek/mengandung spasi", R.style.exampleToast).show();
                    } else {
                        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        if (!strEmail.matches(emailPattern)) {
                            StyleableToast.makeText(RegisterActivity.this, "Invalid Email", R.style.exampleToast).show();
                        } else {
                            String passwordVal = "^" +
                                    "(?=.*[0-9])" +        // at least 1 digit
                                    "(?=.*[a-z])" +            // at least 1 lower case
                                    "(?=.*[A-Z])" +            // at least 1 upper case
//                                    "(?=.*[a-zA-Z])" +        // any letter
//                                    "(?=.*[!@#$%^&*+=])" +    // at least 1 special character
//                                    "(?=\\s+$)" +            // no white space
                                    ".{4,}" +                // at least 4 character
                                    "$";
                            if (!strPassword.matches(passwordVal)) {
                                StyleableToast.makeText(RegisterActivity.this, "Password lemah", R.style.exampleToast).show();
                            } else {
                                if (strPassword.equals(strPassword_conf)) {
                                    mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if(task.isSuccessful()){
                                                        StyleableToast.makeText(RegisterActivity.this, "Registrasi Berhasil", R.style.exampleToast).show();
                                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                                    }else {
                                                        StyleableToast.makeText(RegisterActivity.this, "Registrasi Gagal", R.style.exampleToast).show();
                                                    }
                                                }
                                            });
                                } else {
                                    StyleableToast.makeText(RegisterActivity.this, "Password tidak sama", R.style.exampleToast).show();
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