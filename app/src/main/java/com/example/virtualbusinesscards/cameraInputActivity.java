package com.example.virtualbusinesscards;



import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;


import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;


import android.os.Bundle;

import android.util.Size;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Executor;


public class cameraInputActivity extends AppCompatActivity implements View.OnClickListener {

    TextureView cameraInputTextureView;
    Button buttonCameraInputBack;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_input);

        cameraInputTextureView = findViewById(R.id.cameraInputTextureView);
        buttonCameraInputBack = findViewById(R.id.buttonCameraInputBack);
        buttonCameraInputBack.setOnClickListener(this);

        
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
                    Toast.makeText(this, "test1", Toast.LENGTH_SHORT).show();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "didn't work", Toast.LENGTH_SHORT).show();
                    //TODO For some reason denying permissions doesn't show anything. Will need to be fixed.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public void startCamera(){
        Toast.makeText(this, "test2", Toast.LENGTH_SHORT).show();


        PreviewConfig config = new PreviewConfig.Builder().build();
        Preview preview = new Preview(config);

        preview.setOnPreviewOutputUpdateListener(
                new Preview.OnPreviewOutputUpdateListener() {
                    @Override
                    public void onUpdated(Preview.PreviewOutput previewOutput) {
                        // Your code here. For example, use previewOutput.getSurfaceTexture()
                        // and post to a GL renderer.

                        //TODO Does this work on an actual phone?
                        cameraInputTextureView.setSurfaceTexture(previewOutput.getSurfaceTexture());

                        Toast.makeText(cameraInputActivity.this, "rendering", Toast.LENGTH_SHORT).show();
                    };
                });

        CameraX.bindToLifecycle((LifecycleOwner) this, preview);


        //TODO Implement QRAnalyzer or VisionImage to analyze images
        




    }

    @Override
    public void onClick(View view) {
        if (view == buttonCameraInputBack){
            Intent homepageIntent = new Intent(cameraInputActivity.this, homePageActivity.class);
            startActivity(homepageIntent);
            Toast.makeText(this, "working?", Toast.LENGTH_SHORT).show();


        }
    }





}
