package com.example.virtualbusinesscards;

import androidx.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homePageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private BottomNavigationView homepageMainNav;

    String TAG = "GenerateQRCode";
    ImageView imageViewQRImage;
    Button buttonGenerateQR, buttonScanQr, buttonSettings;
    String inputValue;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    EditText edtValue; //TODO DELETE LATER

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        edtValue = (EditText) findViewById(R.id.edt_value); //TODO DELETE LATER


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
            inputValue = edtValue.getText().toString().trim();
            if (inputValue.length() > 0) {
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 1 / 3;

                qrgEncoder = new QRGEncoder(
                        inputValue, null,
                        QRGContents.Type.TEXT,
                        smallerDimension);
                try {
                    bitmap = qrgEncoder.encodeAsBitmap();
                    imageViewQRImage.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    Log.v(TAG, e.toString());
                }
            } else {
                edtValue.setError("Required");
            }


        } else if (view == buttonScanQr){
            Toast.makeText(this, "scan QR", Toast.LENGTH_SHORT).show();

        } else if (view == buttonSettings){
            Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
            Intent settingsIntent = new Intent(homePageActivity.this, settingsActivity.class);
            startActivity(settingsIntent);


        }
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
