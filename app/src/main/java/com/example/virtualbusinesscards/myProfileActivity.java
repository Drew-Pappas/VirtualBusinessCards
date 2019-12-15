package com.example.virtualbusinesscards;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class myProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri; //for profile image selection

    private BottomNavigationView profileMainNav;
    EditText editTextProfileName, editTextProfileEmail, editTextProfilePhone,
             editTextProfileRole, editTextProfileOrg, editTextProfileLocation,
             editTextProfileBio;

    Switch switchProfileEdit;

    ImageView profilePic;


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



        renderUI();

        switchProfileEdit = findViewById(R.id.switchProfileEdit);
        switchProfileEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked == true){

                    for (EditText profilePiece : profilePieces){
                        profilePiece.setFocusableInTouchMode(true);
                    }
                    profilePic.setClickable(true);
                    profilePic.setFocusable(true);

                    profilePic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openFileChooser();
                        }
                    });


                }

                if (isChecked == false){
                    Toast.makeText(myProfileActivity.this, "Changes Saved", Toast.LENGTH_SHORT).show();

                    for (EditText profilePiece : profilePieces){
                        profilePiece.setFocusable(false);
                        profilePiece.setClickable(false);
                    }
                    profilePic.setClickable(false);
                    profilePic.setFocusable(false);


                    updateProfile();
                    renderUI();


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
                String foundUserPhotoURI = foundUser.userPhotoURI;

                editTextProfileName.setText(foundUserName);
                editTextProfileEmail.setText(foundUserEmail);
                editTextProfilePhone.setText(foundUserPhone);
                editTextProfileRole.setText(foundUserRole);
                editTextProfileOrg.setText(foundUserOrg);
                editTextProfileLocation.setText(foundUserLocation);
                editTextProfileBio.setText(foundUserBio);
                if (!foundUserPhotoURI.equals("")) {
                    Glide.with(myProfileActivity.this).load(foundUserPhotoURI).into(profilePic);

                } else {
                    profilePic.setImageResource(R.drawable.ic_person_icon);


                }


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

    public void getImage(StorageReference storageRef){
        String currentUser;
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = database.getReference("Users");

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'

                Glide.with(myProfileActivity.this).load(uri).into(profilePic);

                DatabaseReference hopperRef = userRef.child(currentUser);
                Map<String, Object> hopperUpdates = new HashMap<>();
                hopperUpdates.put("userPhotoURI", uri.toString());

                hopperRef.updateChildren(hopperUpdates);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public void updateProfile(){
        String currentUser;
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = database.getReference("Users");

        DatabaseReference hopperRef = userRef.child(currentUser);
        Map<String, Object> hopperUpdates = new HashMap<>();
        hopperUpdates.put("userName", editTextProfileName.getText().toString());
        hopperUpdates.put("userEmail", editTextProfileEmail.getText().toString());
        hopperUpdates.put("userPhone", editTextProfilePhone.getText().toString());
        hopperUpdates.put("userRole", editTextProfileRole.getText().toString());
        hopperUpdates.put("userOrg", editTextProfileOrg.getText().toString());
        hopperUpdates.put("userLocation", editTextProfileLocation.getText().toString());
        hopperUpdates.put("userBio", editTextProfileBio.getText().toString());

        hopperRef.updateChildren(hopperUpdates);

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
            //profilePic.setImageURI(imageUri); //TODO Add firebase storage capabilities for profile pic

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                //profilePic.setImageBitmap(bitmap);
                //API version 28 version
                //ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), imageUri);
                //Bitmap bitmap = ImageDecoder.decodeBitmap(source);

                uploadImage(bitmap);



            } catch (IOException e){

            }




        }
    }


    public void uploadImage(Bitmap bitmap) {
        String currentUser;
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://virtualbusinesscards-96adf.appspot.com");
        StorageReference imagesRef = storageRef.child("images/" + currentUser);

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                // Do what you want
                Toast.makeText(myProfileActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();

                StorageReference userReference = getImageReference(currentUser);
                //downloadImage(userReference, profilePic);
                getImage(userReference);


            }
        });
    }

    /**
    public void downloadImage(StorageReference reference, ImageView imageViewToRender){

        reference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes , 0, bytes .length);
                imageViewToRender.setImageBitmap(bitmap);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }
     **/

    public StorageReference getImageReference(String userID){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://virtualbusinesscards-96adf.appspot.com");
        StorageReference imagesRef = storageRef.child("images/" + userID);
        return imagesRef;
    }
}
