package com.example.virtualbusinesscards;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Declare objects
    Button buttonSignUpMain, buttonLoginMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connect to UI
        buttonSignUpMain = findViewById(R.id.buttonSignUpMain);
        buttonLoginMain = findViewById(R.id.buttonLogInMain);

        //Set listeners to listen for clicks on the buttons

        buttonSignUpMain.setOnClickListener(this);
        buttonLoginMain.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonLoginMain){
            Intent loginIntent = new Intent(MainActivity.this, loginActivity.class);
            startActivity(loginIntent);
        }
        else if (view == buttonSignUpMain){
            Intent signUpIntent = new Intent(MainActivity.this, signUpActivity.class);
            startActivity(signUpIntent);
        }

    }
}
