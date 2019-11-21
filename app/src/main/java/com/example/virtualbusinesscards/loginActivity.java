package com.example.virtualbusinesscards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Testing commit
// Testing commit without update
public class loginActivity extends AppCompatActivity implements View.OnClickListener{

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
        buttonLoginLogin = findViewById(R.id.buttonLoginLogIn);
        buttonForgot = findViewById(R.id.buttonForgot);

        //make sure buttons are working
        buttonLoginLogin.setOnClickListener(this);
        buttonForgot.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {

        if (view == buttonLoginLogin){
            Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
            Intent homepageIntent = new Intent(loginActivity.this, homePageActivity.class);
            startActivity(homepageIntent);
        }

    }
}
