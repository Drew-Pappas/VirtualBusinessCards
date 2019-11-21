package com.example.virtualbusinesscards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homePageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView homepageMainNav;
    private FrameLayout homepageMainFrame;

    private MyProfileFragment myProfileFragment;
    private ContactsFragment contactsFragment;
    private fragment_scan fragment_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        homepageMainNav = findViewById(R.id.main_nav);
        homepageMainFrame = findViewById(R.id.main_frame);

        myProfileFragment = new MyProfileFragment();
        contactsFragment = new ContactsFragment();
        fragment_scan = new fragment_scan();

        setFragment(fragment_scan);
        homepageMainNav.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.contacts_nav_item:
                setFragment(contactsFragment);
                return true;
            case R.id.scan_nav_item:
                setFragment(fragment_scan);
                return true;
            case R.id.profile_nav_item:
                setFragment(myProfileFragment);
                return true;
            default:
                return false;
        }

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
