package com.example.virtualbusinesscards;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import java.util.List;


public class cameraInputActivity extends AppCompatActivity implements View.OnClickListener {

    TextureView cameraInputTextureView;
    Button buttonCameraInputBack, buttonScan;
    ImageView imageViewTest;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_input);

        cameraInputTextureView = findViewById(R.id.cameraInputTextureView);
        buttonCameraInputBack = findViewById(R.id.buttonCameraInputBack);
        buttonScan = findViewById(R.id.buttonScan);

        imageViewTest = (ImageView) findViewById(R.id.imageViewTest);
        buttonCameraInputBack.setOnClickListener(this);
        buttonScan.setOnClickListener(this);

        
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(cameraInputActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(cameraInputActivity.this,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(cameraInputActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            startCamera();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // camera-related task you need to do.
                    //Toast.makeText(this, "test1", Toast.LENGTH_SHORT).show();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    //TODO For some reason denying permissions doesn't show anything. Will need to be fixed.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public void startCamera(){
        PreviewConfig config = new PreviewConfig.Builder().build();
        Preview preview = new Preview(config);

        preview.setOnPreviewOutputUpdateListener(
                new Preview.OnPreviewOutputUpdateListener() {
                    @Override
                    public void onUpdated(Preview.PreviewOutput previewOutput) {

                        cameraInputTextureView.setSurfaceTexture(previewOutput.getSurfaceTexture());

                    };
                });

        CameraX.bindToLifecycle((LifecycleOwner) this, preview);

    }

    @Override
    public void onClick(View view) {
        if (view == buttonCameraInputBack){
            Intent homepageIntent = new Intent(cameraInputActivity.this, homePageActivity.class);
            startActivity(homepageIntent);

        } else if (view == buttonScan){
            Bitmap item = cameraInputTextureView.getBitmap();
            runDetector(item);

        }
    }

    private void runDetector(Bitmap bitmap) {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionBarcodeDetectorOptions options =
                new FirebaseVisionBarcodeDetectorOptions.Builder()
                        .setBarcodeFormats(
                                FirebaseVisionBarcode.FORMAT_QR_CODE
                        )
                        .build();
        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance()
                .getVisionBarcodeDetector(options);

        detector.detectInImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
                        processResult(firebaseVisionBarcodes);
                        Toast.makeText(cameraInputActivity.this, "Scanning", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(cameraInputActivity.this, "Scanning failed," +
                                " try again", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void processResult(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
        for(FirebaseVisionBarcode item : firebaseVisionBarcodes){
            int value_type = item.getValueType();

            switch(value_type){
                case FirebaseVisionBarcode.TYPE_TEXT:
                {
                    addScannersInfoToQRSnapshot(item.getRawValue());
                    addContact(item.getRawValue());

                }
                break;

                default:
                    break;
            }
        }
    }

    public QRSnapshot addScannersInfoToQRSnapshot(String QRReference){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = database.getReference("Users");
        final DatabaseReference QRSnapshotRef = database.getReference("QRSnapshot");

        String currentUser;
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

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

                QRSnapshot newQRSnapshotChild = new QRSnapshot(
                        currentUser,foundUserName,foundUserEmail,
                        foundUserPhone, foundUserRole, foundUserOrg,
                        foundUserLocation,foundUserBio);

                QRSnapshotRef.child(QRReference).child("userToBeAddedSnapshot").setValue(newQRSnapshotChild);

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
        return null;
    }

    public void addContact(String QRReference){ //QRSnapshot snapshot
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference contactListRef = database.getReference("ContactList");
        final DatabaseReference QRSnapshotRef = database.getReference("QRSnapshot");

        String currentUser;
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        QRSnapshotRef.orderByChild("userID").equalTo(QRReference).addChildEventListener(new ChildEventListener() {
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

                QRSnapshot newQRSnapshotChild = new QRSnapshot(
                        currentUser,foundUserName,foundUserEmail,
                        foundUserPhone, foundUserRole, foundUserOrg,
                        foundUserLocation,foundUserBio);

                contactListRef.child(currentUser).child(QRReference).setValue(newQRSnapshotChild);
                Intent newContactIntent = new Intent(cameraInputActivity.this,
                        contactsActivity.class);
                startActivity(newContactIntent);

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
