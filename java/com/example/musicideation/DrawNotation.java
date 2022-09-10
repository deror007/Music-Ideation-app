package com.example.musicideation;

//draw music notes.

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.material.slider.RangeSlider;


import java.io.OutputStream;


import petrov.kristiyan.colorpicker.ColorPicker;



public class DrawNotation extends AppCompatActivity {

    private MusicScore writing;

    private ImageButton save,color,stroke,undo;

    private RangeSlider penWidthSlider;

    private Database db;

    //create draw notation page with canvas
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_notation);

        String songTitle = getIntent().getStringExtra("songTitle");
        String selectedProperty= getIntent().getStringExtra("selected");

        Toast.makeText(DrawNotation.this, songTitle, Toast.LENGTH_SHORT).show();
        Toast.makeText(DrawNotation.this, selectedProperty, Toast.LENGTH_SHORT).show();

        //view references
        writing=(MusicScore)findViewById(R.id.draw_view);
        penWidthSlider=(RangeSlider)findViewById(R.id.rangebar);
        undo=(ImageButton)findViewById(R.id.btn_undo);
        save=(ImageButton)findViewById(R.id.btn_save);
        color=(ImageButton)findViewById(R.id.btn_color);
        stroke=(ImageButton)findViewById(R.id.btn_stroke);

        db = new Database(DrawNotation.this);


        //Apply functions to each drawing button!

        //apply undo command
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writing.undo();
            }
        });

        //external storage save functionality for drawings
        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)

            @Override
            public void onClick(View view) {


                //getting the bitmap from DrawView class
                Bitmap bmp= writing.save();
                //opening a OutputStream to write into the file
                OutputStream imageOutStream = null;

                //provide image meta data
                ContentValues content = new ContentValues();
                String displayName = songTitle+"_"+selectedProperty+".png";

                //provide file name, type and location by USING Content Provider!
                content.put(MediaStore.Images.Media.DISPLAY_NAME, displayName);
                content.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
                content.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

                //NOTE: get the Uri of the file which is to be saved in external storage and convert to uri.toString
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content);


                try {
                    //open the output stream with the above uri
                    imageOutStream = getContentResolver().openOutputStream(uri);
                    //this method writes the files in storage
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, imageOutStream);
                    //close the output stream after use
                    imageOutStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //add uri string to database for future referencing
                String uriStr = uri.toString();
                db.addNewNotation(uriStr,selectedProperty, songTitle);
                // Showing the toast message
                Toast.makeText(DrawNotation.this, "Saved", Toast.LENGTH_SHORT).show();
            }



        });

        //apply 3rd party colour picker library
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ColorPicker colorPicker=new ColorPicker(DrawNotation.this);
                colorPicker.setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
                    @Override
                    public void setOnFastChooseColorListener(int position, int color) {
                        writing.setColor(color);

                    }

                    @Override
                    public void onCancel() {

                        colorPicker.dismissDialog();      //petrov function
                    }
                })
                        //set columns of colour in picker menu
                        .setColumns(5)
                        //provide default colour (black)
                        .setDefaultColorButton(Color.parseColor("#000000"))
                        .show();
            }
        });

        //set pen width by showing slider visibility
        stroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(penWidthSlider.getVisibility()==View.VISIBLE)
                    penWidthSlider.setVisibility(View.GONE);
                else
                    penWidthSlider.setVisibility(View.VISIBLE);
            }
        });

        //set the min and max range of slide bar
        penWidthSlider.setValueFrom(0.0f);
        penWidthSlider.setValueTo(100.0f);

        //change pen width
        penWidthSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                writing.setPenWidth((int) value);
            }
        });

        //pass custom view dimensions to music score
        ViewTreeObserver virtTreeObs = writing.getViewTreeObserver();
        virtTreeObs.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                writing.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = writing.getMeasuredWidth();
                int height = writing.getMeasuredHeight();
                writing.init(height, width);
            }
        });
    }
}