package com.example.virtualbusinesscards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homePageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView homepageMainNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        homepageMainNav = findViewById(R.id.main_nav);
        //homepageMainFrame = findViewById(R.id.main_frame);

        homepageMainNav.getMenu().findItem(R.id.scan_nav_item).setChecked(true);

        //setFragment(fragment_scan);
        homepageMainNav.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.contacts_nav_item:
                //setFragment(contactsFragment);
                Toast.makeText(this, "contactspage", Toast.LENGTH_SHORT).show();
                Intent contactsIntent = new Intent(homePageActivity.this, contactsActivity.class);
                startActivity(contactsIntent);
                return true;
            case R.id.scan_nav_item:
                //setFragment(fragment_scan);
                Toast.makeText(this, "already on this activity", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.profile_nav_item:
                //setFragment(myProfileFragment);
                Toast.makeText(this, "profilepage", Toast.LENGTH_SHORT).show();
                Intent profileIntent = new Intent(homePageActivity.this, myProfileActivity.class);
                startActivity(profileIntent);
                return true;
            default:
                return false;
        }

    }


}
