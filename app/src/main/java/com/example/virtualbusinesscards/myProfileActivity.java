package com.example.virtualbusinesscards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class myProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView profileMainNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        profileMainNav = findViewById(R.id.main_nav);

        profileMainNav.getMenu().findItem(R.id.profile_nav_item).setChecked(true);

        profileMainNav.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){
            case R.id.contacts_nav_item:
                //setFragment(contactsFragment);
                Intent contactsIntent = new Intent(myProfileActivity.this, contactsActivity.class);
                startActivity(contactsIntent);
                return true;
            case R.id.scan_nav_item:
                //setFragment(fragment_scan);
                Intent scanIntent = new Intent(myProfileActivity.this, homePageActivity.class);
                startActivity(scanIntent);

                return true;
            case R.id.profile_nav_item:
                //setFragment(myProfileFragment);
                Toast.makeText(this, "already on this activity", Toast.LENGTH_SHORT).show();

                return true;
            default:
                return false;
        }
    }
}
