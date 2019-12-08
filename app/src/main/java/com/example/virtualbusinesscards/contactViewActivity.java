package com.example.virtualbusinesscards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class contactViewActivity extends AppCompatActivity implements View.OnClickListener{
    Button buttonContactViewBack;
    TextView textViewContactName, textViewContactEmail, textViewContactPhone,
            textViewContactRole, textViewContactOrg, textViewContactLocation,
            textViewContactBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        //Get info from previous intent
        Intent retrieveInfo = getIntent();

        //Assign info to variables
        String contactName = retrieveInfo.getStringExtra("Name");
        String contactEmail = retrieveInfo.getStringExtra("Email");
        String contactPhone = retrieveInfo.getStringExtra("Phone");
        String contactRole = retrieveInfo.getStringExtra("Role");
        String contactOrg = retrieveInfo.getStringExtra("Org");
        String contactLocation = retrieveInfo.getStringExtra("Location");
        String contactBio = retrieveInfo.getStringExtra("Bio");

        //Find UI objects
        textViewContactName = findViewById(R.id.textViewContactName);
        textViewContactEmail = findViewById(R.id.textViewContactEmail);
        textViewContactPhone = findViewById(R.id.textViewContactPhone);
        textViewContactRole = findViewById(R.id.textViewContactRole);
        textViewContactOrg = findViewById(R.id.textViewContactOrg);
        textViewContactLocation = findViewById(R.id.textViewContactLocation);
        textViewContactBio = findViewById(R.id.textViewContactBio);

        buttonContactViewBack = findViewById(R.id.buttonContactViewBack);
        buttonContactViewBack.setOnClickListener(this);

        //Assign UI objects the variables retrieved from the data
        textViewContactName.setText(contactName);
        textViewContactEmail.setText(contactEmail);
        textViewContactPhone.setText(contactPhone);
        textViewContactRole.setText(contactRole);
        textViewContactOrg.setText(contactOrg);
        textViewContactLocation.setText(contactLocation);
        textViewContactBio.setText(contactBio);

    }

    @Override
    public void onClick(View view) {
        if (view == buttonContactViewBack){
            Intent contactsIntent = new Intent(contactViewActivity.this, contactsActivity.class);
            startActivity(contactsIntent);
        }
    }
}
