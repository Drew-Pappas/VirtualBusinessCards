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

// Testing commit
// Testing commit without update
public class loginActivity extends AppCompatActivity implements View.OnClickListener{

    // declare objects
    EditText editTextEmailLogin, editTextPasswordLogin;
    Button buttonLoginLogin, buttonForgot, buttonLoginBack;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // connect to Ui
        editTextEmailLogin = findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin = findViewById(R.id.editTextPasswordLogin);
        buttonLoginLogin = findViewById(R.id.buttonLoginLogIn);
        buttonForgot = findViewById(R.id.buttonForgot);
        buttonLoginBack = findViewById(R.id.buttonLoginBack);

        //make sure buttons are working
        buttonLoginLogin.setOnClickListener(this);
        buttonForgot.setOnClickListener(this);
        buttonLoginBack.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {

        String email = editTextEmailLogin.getText().toString();
        String password = editTextPasswordLogin.getText().toString();

        if (view == buttonLoginLogin && (!email.equals("") && !password.equals(""))){

            mAuth.signInWithEmailAndPassword(email, password) //Handles signing in users
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, go to portal activity
                                Intent homepageIntent = new Intent(loginActivity.this, homePageActivity.class);
                                startActivity(homepageIntent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(loginActivity.this, "Log in failed", Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });



        } else if (view == buttonLoginLogin && (email.equals("") || password.equals(""))){
            Toast.makeText(this, "Enter your email and password", Toast.LENGTH_SHORT).show();
        }

        if (view == buttonLoginBack){
            Intent mainActivityIntent = new Intent(loginActivity.this, MainActivity.class);
            startActivity(mainActivityIntent);
        }

    }
}
