package com.example.virtualbusinesscards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class contactsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    List<QRSnapshot> listContacts;

    private BottomNavigationView contactsMainNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        contactsMainNav = findViewById(R.id.main_nav);

        contactsMainNav.getMenu().findItem(R.id.contacts_nav_item).setChecked(true);

        contactsMainNav.setOnNavigationItemSelectedListener(this);

        listContacts = new ArrayList<>();

        //Use for loop to dynamically create list of contacts
        generateContactList(listContacts);

        //Assign list contacts to a UI element
        RecyclerView myRecyclerView = (RecyclerView) findViewById(R.id.contactsRecycler);
        myRecyclerView.setLayoutManager(new GridLayoutManager(this,3));



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.contacts_nav_item:

                return true;

            case R.id.scan_nav_item:
                Intent scanIntent = new Intent(contactsActivity.this, homePageActivity.class);
                startActivity(scanIntent);

                return true;

            case R.id.profile_nav_item:
                Intent profileIntent = new Intent(contactsActivity.this, myProfileActivity.class);
                startActivity(profileIntent);

                return true;

            default:

                return false;

        }

    }
    public void generateContactList(List<QRSnapshot> aQRSnapshotList){
        String currentUser;
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference contactListRef = database.getReference("ContactList");
        final DatabaseReference contactListUserReference = contactListRef.child(currentUser);

        contactListUserReference.orderByChild("userName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    aQRSnapshotList.add(snapshot.getValue(QRSnapshot.class));

                }

                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(
                        contactsActivity.this,
                        listContacts);

                RecyclerView myRecyclerView = (RecyclerView) findViewById(R.id.contactsRecycler);

                myRecyclerView.setLayoutManager(new GridLayoutManager(
                        contactsActivity.this,
                        2));

                myRecyclerView.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
