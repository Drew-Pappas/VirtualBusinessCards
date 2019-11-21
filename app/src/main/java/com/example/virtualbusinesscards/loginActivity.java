package com.example.virtualbusinesscards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

// Testing commit
// Testing commit without update
public class loginActivity extends AppCompatActivity {

    // declare objects
    EditText editTextEmailLogin, editTextPasswordLogin;
    Button buttonLoginLogin, buttonForgot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // connect to Ui
        editTextEmailLogin = findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin = findViewById(R.id.editTextEmailLogin);
        buttonLoginLogin = findViewById(R.id.buttonLgoinLogIn);
        buttonForgot = findViewById(R.id.buttonForgot);

        //make sure buttons are working
        buttonLoginLogin.setOnClickListener(this);
        buttonForgot.setOnClickListener(this);




    }
}
