package com.example.virtualbusinesscards;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.WriterException;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homePageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private BottomNavigationView homepageMainNav;
    String TAG = "GenerateQRCode";
    ImageView imageViewQRImage;
    Button buttonGenerateQR, buttonScanQr, buttonSettings;
    String currentUser;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        imageViewQRImage = (ImageView) findViewById(R.id.imageViewQRImage);
        buttonGenerateQR = (Button) findViewById(R.id.buttonGenerateQR);
        buttonScanQr = (Button) findViewById(R.id.buttonScanQR);
        buttonSettings = (Button) findViewById(R.id.buttonSettings);

        buttonGenerateQR.setOnClickListener(this);
        buttonScanQr.setOnClickListener(this);
        buttonSettings.setOnClickListener(this);

        homepageMainNav = findViewById(R.id.main_nav);

        homepageMainNav.getMenu().findItem(R.id.scan_nav_item).setChecked(true);

        homepageMainNav.setOnNavigationItemSelectedListener(this);



    }

    @Override
    public void onClick(View view) {
        if (view == buttonGenerateQR){
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference userRef = database.getReference("Users");
            final DatabaseReference QRSnapshotRef = database.getReference("QRSnapshot");

            userRef.orderByChild("userID").equalTo(currentUser).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    User foundUser = dataSnapshot.getValue(User.class);
                    String foundUserID = foundUser.userID;
                    String foundUserName = foundUser.userName;
                    String foundUserRole = foundUser.userRole;
                    String foundUserBio = foundUser.userBio;
                    Settings foundUserSettings = foundUser.userShareSettings;

                    String foundUserEmail = Settings.checkShareSetting(foundUserSettings.shareUserEmail,
                            foundUser.userEmail);

                    String foundUserLocation = Settings.checkShareSetting(foundUserSettings.shareUserLocation,
                            foundUser.userLocation);

                    String foundUserPhone = Settings.checkShareSetting(foundUserSettings.shareUserPhone,
                            foundUser.userPhone);

                    String foundUserOrg = Settings.checkShareSetting(foundUserSettings.shareUserOrg,
                            foundUser.userOrg);

                    String foundUserPhotoURI = foundUser.userPhotoURI;

                     QRSnapshot newQRSnapshot = new QRSnapshot(
                             foundUserID,foundUserName,foundUserEmail,
                             foundUserPhone, foundUserRole, foundUserOrg,
                             foundUserLocation,foundUserBio, foundUserPhotoURI);

                     QRSnapshotRef.child(currentUser).setValue(newQRSnapshot);

                    QRSnapshotRef.child(currentUser).child("userToBeAddedSnapshot").addChildEventListener(new ChildEventListener() {

                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            addContactFromScannedUser();
                            QRSnapshotRef.child(currentUser).child("userToBeAddedSnapshot")
                                    .removeEventListener(this);
                            Intent newContactIntent = new Intent(homePageActivity.this,
                                    contactsActivity.class);
                            startActivity(newContactIntent);
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


            if (currentUser.length() > 0) {
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 1 / 2;

                qrgEncoder = new QRGEncoder(
                        currentUser, null,
                        QRGContents.Type.TEXT,
                        smallerDimension);
                try {
                    bitmap = qrgEncoder.encodeAsBitmap();
                    imageViewQRImage.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    Log.v(TAG, e.toString());
                }
            }


        } else if (view == buttonScanQr){
            Intent qrIntent = new Intent(homePageActivity.this, cameraInputActivity.class);
            startActivity(qrIntent);

        } else if (view == buttonSettings){
            Intent settingsIntent = new Intent(homePageActivity.this, settingsActivity.class);
            startActivity(settingsIntent);


        }
    }

    public void addContactFromScannedUser(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference contactListRef = database.getReference("ContactList");
        final DatabaseReference QRSnapshotRef = database.getReference("QRSnapshot");

        String currentUser;
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        QRSnapshotRef.orderByChild("userID").equalTo(currentUser).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User foundUser = dataSnapshot.child("userToBeAddedSnapshot").getValue(User.class);
                String foundUserID = foundUser.userID;
                String foundUserName = foundUser.userName;
                String foundUserEmail = foundUser.userEmail;
                String foundUserPhone = foundUser.userPhone;
                String foundUserRole = foundUser.userRole;
                String foundUserOrg = foundUser.userOrg;
                String foundUserLocation = foundUser.userLocation;
                String foundUserBio = foundUser.userBio;
                String foundUserPhotoURI = foundUser.userPhotoURI;

                QRSnapshot newQRSnapshotChild = new QRSnapshot(
                        foundUserID,foundUserName,foundUserEmail,
                        foundUserPhone, foundUserRole, foundUserOrg,
                        foundUserLocation,foundUserBio, foundUserPhotoURI);

                contactListRef.child(currentUser).child(foundUserID).setValue(newQRSnapshotChild);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.contacts_nav_item:
                Intent contactsIntent = new Intent(homePageActivity.this, contactsActivity.class);
                startActivity(contactsIntent);
                return true;
            case R.id.scan_nav_item:

                return true;
            case R.id.profile_nav_item:
                Intent profileIntent = new Intent(homePageActivity.this, myProfileActivity.class);
                startActivity(profileIntent);
                return true;
            default:
                return false;
        }

    }




}
