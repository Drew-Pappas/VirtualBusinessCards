package com.example.virtualbusinesscards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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
        buttonLoginMain.setOnClickListener(this);;

    }
}
