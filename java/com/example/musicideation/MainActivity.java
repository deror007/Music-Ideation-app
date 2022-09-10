package com.example.musicideation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int EXTERNAL_STORAGE_PERMISSION_CODE = 777;
    private Button btUserGuide;
    private Button btSongIdeas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check permission is granted.
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, EXTERNAL_STORAGE_PERMISSION_CODE);

        //Get buttons from layout
        btUserGuide = (Button) findViewById(R.id.btOpenUserGuide);
        btSongIdeas = (Button) findViewById(R.id.btOpenSongIdeas);

        //Provide onClick() functionality for each button

        btUserGuide.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openUserGuide();
            }
        });

        btSongIdeas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSongIdeas();
            }
        });

    }


    //Create intent to web-view user guide
    public void openUserGuide(){
        Intent intent = new Intent(this, UserGuide.class);
        startActivity(intent);
    }

    //Create intent to song ideas page
    public void openSongIdeas(){
        Intent intent = new Intent(this, SongIdeas.class);
        startActivity(intent);
    }

    // Function to check and request permission
    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
        }

    }

    // function when user accepts or declines the permission.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CODE) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Showing the toast message
                Toast.makeText(MainActivity.this, "Access External Storage Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Access External Storage Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
