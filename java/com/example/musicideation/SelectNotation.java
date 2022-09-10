package com.example.musicideation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

//Zoom into selected images from View Notations
public class SelectNotation extends AppCompatActivity {


    private ImageView viewImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_notation);


        // initializing our image view.
        viewImage = (ImageView)findViewById(R.id.zoomedImage);
        Uri uri = getIntent().getParcelableExtra("selectedUri");

        //load image to image view
        Glide.with(SelectNotation.this)
                .load(uri)
                .placeholder(R.drawable.ic_launcher_background)
                .into(viewImage);

    }

}