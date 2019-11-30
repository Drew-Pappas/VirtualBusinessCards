package com.example.virtualbusinesscards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class settingsActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonBackSettings, buttonSettingsLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        buttonBackSettings = findViewById(R.id.buttonBackSettings);
        buttonSettingsLogout = findViewById(R.id.buttonSettingsLogout);


        buttonBackSettings.setOnClickListener(this);
        buttonSettingsLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonBackSettings){
            Intent homepageIntent = new Intent(settingsActivity.this, homePageActivity.class);
            startActivity(homepageIntent);
        } else if (view == buttonSettingsLogout){

            FirebaseAuth.getInstance().signOut();
            Intent logoutIntent = new Intent(settingsActivity.this, MainActivity.class);
            startActivity(logoutIntent);

        }
    }
}
