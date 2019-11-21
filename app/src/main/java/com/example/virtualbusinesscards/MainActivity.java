package com.example.virtualbusinesscards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //declare objects
    Button buttonSignUpMain, buttonLoginMain;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //helloworld

        //connect to UI
        buttonSignUpMain = findViewById(R.id.buttonSignUpMain);
        buttonLoginMain = findViewById(R.id.buttonLogInMain);

        //make sure buttons are working

        buttonSignUpMain.setOnClickListener(this);
        buttonLoginMain.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonLoginMain){
            Toast.makeText(this, "login", Toast.LENGTH_SHORT).show();
            Intent loginIntent = new Intent(MainActivity.this, loginActivity.class);
            startActivity(loginIntent);
        }
        else if (view == buttonSignUpMain){
            Toast.makeText(this, "signup", Toast.LENGTH_SHORT).show();
            Intent signUpIntent = new Intent(MainActivity.this, signUpActivity.class);
            startActivity(signUpIntent);
        }
    }
}
