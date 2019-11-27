package com.example.virtualbusinesscards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class myProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView profileMainNav;
    EditText editTextProfileName, editTextProfileEmail, editTextProfilePhone,
             editTextProfileRole, editTextProfileOrg, editTextProfileLocation,
             editTextProfileBio;

    Switch switchProfileEdit;

    ArrayList<EditText> profilePieces = new ArrayList<EditText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        editTextProfileName = findViewById(R.id.editTextProfileName);
        editTextProfileEmail = findViewById(R.id.editTextProfileEmail);
        editTextProfilePhone = findViewById(R.id.editTextProfilePhone);
        editTextProfileRole = findViewById(R.id.editTextProfileRole);
        editTextProfileOrg = findViewById(R.id.editTextProfileOrg);
        editTextProfileLocation = findViewById(R.id.editTextProfileLocation);
        editTextProfileBio = findViewById(R.id.editTextProfileBio);

        profilePieces.add(editTextProfileName);
        profilePieces.add(editTextProfileEmail);
        profilePieces.add(editTextProfilePhone);
        profilePieces.add(editTextProfileRole);
        profilePieces.add(editTextProfileOrg);
        profilePieces.add(editTextProfileLocation);
        profilePieces.add(editTextProfileBio);

        switchProfileEdit = findViewById(R.id.switchProfileEdit);
        switchProfileEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked == true){
                    Toast.makeText(myProfileActivity.this, "Edit profile", Toast.LENGTH_SHORT).show();

                    for (EditText profilePiece : profilePieces){
                        profilePiece.setFocusableInTouchMode(true);
                    }

                }

                if (isChecked == false){
                    Toast.makeText(myProfileActivity.this, "save changes", Toast.LENGTH_SHORT).show();

                    for (EditText profilePiece : profilePieces){
                        profilePiece.setFocusable(false);
                        profilePiece.setClickable(false);
                    }
                }
            }

        });


        //Connect and set navigation UI
        profileMainNav = findViewById(R.id.main_nav);
        profileMainNav.getMenu().findItem(R.id.profile_nav_item).setChecked(true);
        profileMainNav.setOnNavigationItemSelectedListener(this);






    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){
            case R.id.contacts_nav_item:
                Intent contactsIntent = new Intent(myProfileActivity.this, contactsActivity.class);
                startActivity(contactsIntent);
                return true;
            case R.id.scan_nav_item:
                Intent scanIntent = new Intent(myProfileActivity.this, homePageActivity.class);
                startActivity(scanIntent);

                return true;
            case R.id.profile_nav_item:

                return true;
            default:
                return false;
        }
    }

}
