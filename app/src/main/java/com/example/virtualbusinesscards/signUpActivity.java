package com.example.virtualbusinesscards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUpActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextSignUpFirstName, editTextSignUpLastName, editTextSignUpPhoneNumber,
             editTextSignUpEmail, editTextSignUpPassword, editTextSignUpReEnterPassword;

    Button buttonSignUpBack, buttonSignUpComplete;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        editTextSignUpFirstName = findViewById(R.id.editTextSignUpFirstName);
        editTextSignUpLastName = findViewById(R.id.editTextSignUpLastName);
        editTextSignUpPhoneNumber = findViewById(R.id.editTextSignUpPhoneNumber);
        editTextSignUpEmail = findViewById(R.id.editTextSignUpEmail);
        editTextSignUpPassword = findViewById(R.id.editTextSignUpPassword);
        editTextSignUpReEnterPassword = findViewById(R.id.editTextSignUpReEnterPassword);

        buttonSignUpBack = findViewById(R.id.buttonSignUpBack);
        buttonSignUpComplete = findViewById(R.id.buttonSignUpComplete);

        buttonSignUpBack.setOnClickListener(this);
        buttonSignUpComplete.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        if (view == buttonSignUpComplete) {
            String name = editTextSignUpFirstName.getText().toString()
                    + " "
                    + editTextSignUpLastName.getText().toString();
            String phoneNumber = editTextSignUpPhoneNumber.getText().toString()
                    .replaceAll("\\s","")
                    .replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
            String email = editTextSignUpEmail.getText().toString();
            String password = editTextSignUpPassword.getText().toString();
            String passwordConfirm = editTextSignUpReEnterPassword.getText().toString();

            if (!email.equals("")
                    && !password.equals("")
                    && !name.equals("")
                    && !phoneNumber.equals("")
                    && !passwordConfirm.equals("")){

                if (!password.equals(passwordConfirm)){
                    editTextSignUpPassword.setError("Passwords do not match");
                    editTextSignUpReEnterPassword.setError("Passwords do not match");
                }

                //Check the formatting of a phone number
                if (phoneNumber.length() != 12){
                    editTextSignUpPhoneNumber.setError("Phone number must be ten digits");

                }

                if (password.equals(passwordConfirm) && phoneNumber.length() == 12){
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(signUpActivity.this,
                                                "Account successfully created", Toast.LENGTH_SHORT).show();

                                        //String user = mAuth.getCurrentUser().getUid(); Is this necessary?
                                        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                        //Create new user object for firebase database upon successful signup
                                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        final DatabaseReference myRef = database.getReference("Users");


                                        User newVBUser = new User(
                                                userID, name, email,
                                                phoneNumber, "", "",
                                                "", ""
                                                );
                                        myRef.child(userID).setValue(newVBUser);



                                        Intent loginIntent = new Intent(signUpActivity.this, loginActivity.class);
                                        startActivity(loginIntent);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(signUpActivity.this, "Failed to create account",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }




            } else {
                Toast.makeText(this, "All fields must be entered", Toast.LENGTH_SHORT).show();
            }


        } else if (view == buttonSignUpBack){
            Intent initialActivityIntent = new Intent(signUpActivity.this, MainActivity.class);
            startActivity(initialActivityIntent);
        }
    }
}
