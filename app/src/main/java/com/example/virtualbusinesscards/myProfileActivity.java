package com.example.virtualbusinesscards;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class myProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri; //for profile image selection

    private BottomNavigationView profileMainNav;
    EditText editTextProfileName, editTextProfileEmail, editTextProfilePhone,
             editTextProfileRole, editTextProfileOrg, editTextProfileLocation,
             editTextProfileBio;

    Switch switchProfileEdit;

    ImageView profilePic;
    Button buttonChooseImage;

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

        profilePic = findViewById((R.id.profilePic));
        buttonChooseImage = findViewById((R.id.buttonChooseImage));

        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        renderUI();

        switchProfileEdit = findViewById(R.id.switchProfileEdit);
        switchProfileEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked == true){

                    for (EditText profilePiece : profilePieces){
                        profilePiece.setFocusableInTouchMode(true);
                    }

                }

                if (isChecked == false){
                    Toast.makeText(myProfileActivity.this, "Changes Saved", Toast.LENGTH_SHORT).show();

                    for (EditText profilePiece : profilePieces){
                        profilePiece.setFocusable(false);
                        profilePiece.setClickable(false);
                    }

                    updateProfile();
                    renderUI();
                }
            }

        });

        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
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


    public void renderUI(){
        String currentUser;
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = database.getReference("Users");

        userRef.orderByChild("userID").equalTo(currentUser).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User foundUser = dataSnapshot.getValue(User.class);

                String foundUserName = foundUser.userName;
                String foundUserEmail = foundUser.userEmail;
                String foundUserPhone = foundUser.userPhone;
                String foundUserRole = foundUser.userRole;
                String foundUserOrg = foundUser.userOrg;
                String foundUserLocation = foundUser.userLocation;
                String foundUserBio = foundUser.userBio;

                editTextProfileName.setText(foundUserName);
                editTextProfileEmail.setText(foundUserEmail);
                editTextProfilePhone.setText(foundUserPhone);
                editTextProfileRole.setText(foundUserRole);
                editTextProfileOrg.setText(foundUserOrg);
                editTextProfileLocation.setText(foundUserLocation);
                editTextProfileBio.setText(foundUserBio);
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

    public void updateProfile(){
        String currentUser;
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = database.getReference("Users");

        User updatedUser = new User(
                currentUser, editTextProfileName.getText().toString(),
                editTextProfileEmail.getText().toString(), editTextProfilePhone.getText().toString(),
                editTextProfileRole.getText().toString(), editTextProfileOrg.getText().toString(),
                editTextProfileLocation.getText().toString(), editTextProfileBio.getText().toString()
        );

        userRef.child(currentUser).setValue(updatedUser);
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
        }
    }
}
