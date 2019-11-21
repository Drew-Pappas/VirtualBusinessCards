package com.example.virtualbusinesscards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class settingsActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonBackSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        buttonBackSettings = findViewById(R.id.buttonBackSettings);

        buttonBackSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonBackSettings){
            Intent homepageIntent = new Intent(settingsActivity.this, homePageActivity.class);
            startActivity(homepageIntent);
        }
    }
}
