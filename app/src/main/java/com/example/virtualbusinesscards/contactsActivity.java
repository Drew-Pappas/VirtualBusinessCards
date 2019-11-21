package com.example.virtualbusinesscards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class contactsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    //test

    private BottomNavigationView contactsMainNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        contactsMainNav = findViewById(R.id.main_nav);

        contactsMainNav.getMenu().findItem(R.id.contacts_nav_item).setChecked(true);

        contactsMainNav.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.contacts_nav_item:
                //setFragment(contactsFragment);
                Toast.makeText(this, "already on this activity", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.scan_nav_item:
                //setFragment(fragment_scan);
                Intent scanIntent = new Intent(contactsActivity.this, homePageActivity.class);
                startActivity(scanIntent);

                return true;
            case R.id.profile_nav_item:
                //setFragment(myProfileFragment);
                Toast.makeText(this, "profilepage", Toast.LENGTH_SHORT).show();
                Intent profileIntent = new Intent(contactsActivity.this, myProfileActivity.class);
                startActivity(profileIntent);
                return true;
            default:
                return false;
        }

    }
    //Test Commit
}
