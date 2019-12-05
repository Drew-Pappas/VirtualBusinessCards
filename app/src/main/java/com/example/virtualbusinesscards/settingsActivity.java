package com.example.virtualbusinesscards;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Set;

public class settingsActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonBackSettings, buttonSettingsLogout;
    Switch switchEmailSettings, switchPhoneSettings,
            switchLocationSettings, switchOrgSettings;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        buttonBackSettings = findViewById(R.id.buttonBackSettings);
        buttonSettingsLogout = findViewById(R.id.buttonSettingsLogout);

        switchEmailSettings = findViewById(R.id.switchEmailSettings);
        switchPhoneSettings = findViewById(R.id.switchPhoneSettings);
        switchLocationSettings = findViewById(R.id.switchLocationSettings);
        switchOrgSettings = findViewById(R.id.switchOrgSettings);

        buttonBackSettings.setOnClickListener(this);
        buttonSettingsLogout.setOnClickListener(this);

        renderSettingSwitches();

        switchEmailSettings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked == true){
                    updateSetting("emailSetting",true);
                }

                if (isChecked == false){
                    updateSetting("emailSetting",false);
                }
            }

        });

        switchPhoneSettings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked == true){
                    updateSetting("phoneSetting",true);

                }

                if (isChecked == false){
                    updateSetting("phoneSetting",false);

                }
            }

        });


        switchLocationSettings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked == true){
                    updateSetting("locationSetting",true);

                }

                if (isChecked == false){
                    updateSetting("locationSetting",false);

                }
            }

        });


        switchOrgSettings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked == true){
                    updateSetting("orgSetting",true);


                }

                if (isChecked == false){
                    updateSetting("orgSetting",false);

                }
            }

        });

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

    public void updateSetting(String settingToUpdate, boolean bool){
        String currentUser;
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = database.getReference("Users");
        switch(settingToUpdate){
            case "emailSetting":
                userRef.child(currentUser).child("userShareSettings")
                        .child("shareUserEmail").setValue(bool);
                break;

            case "phoneSetting":
                userRef.child(currentUser).child("userShareSettings")
                        .child("shareUserPhone").setValue(bool);
                break;

            case "locationSetting":
                userRef.child(currentUser).child("userShareSettings")
                        .child("shareUserLocation").setValue(bool);
                break;

            case "orgSetting":
                userRef.child(currentUser).child("userShareSettings")
                        .child("shareUserOrg").setValue(bool);
                break;

            default:
                break;
        }

    }

    public void renderSettingSwitches(){
        String currentUser;
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = database.getReference("Users");

        userRef.orderByChild("userID").equalTo(currentUser).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User foundUser = dataSnapshot.getValue(User.class);

                Settings foundSettings = foundUser.userShareSettings;
                boolean foundEmailSetting = foundSettings.shareUserEmail;
                boolean foundLocationSetting = foundSettings.shareUserLocation;
                boolean foundOrgSetting = foundSettings.shareUserOrg;
                boolean foundPhoneSetting = foundSettings.shareUserPhone;

                switchEmailSettings.setChecked(foundEmailSetting);
                switchLocationSettings.setChecked(foundLocationSetting);
                switchOrgSettings.setChecked(foundOrgSetting);
                switchPhoneSettings.setChecked(foundPhoneSetting);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
