package com.example.musicideation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SongProperties extends AppCompatActivity {
    private Button btRhythmNotation,btMelodyNotation,btBeatNotation, btAddIdea;

    private EditText edtTitle, edtGenre, edtBPM, edtKeySign, edtLyrics, edtInspire, edtAddInfo;

    private TextView tvRhythm, tvMelody, tvBeat;

    private Database db;

    private String drawProperty;

    boolean validInputs = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_properties);

        // initializing all our variables.
        edtTitle = findViewById(R.id.editTitle);
        edtGenre = findViewById(R.id.editGenre);
        edtBPM = findViewById(R.id.editBPM);
        edtKeySign = findViewById(R.id.editKeySig);
        tvRhythm = findViewById(R.id.textRhythm);
        tvMelody = findViewById(R.id.textMelody);
        tvBeat = findViewById(R.id.textBeat);
        edtLyrics = findViewById(R.id.editLyrics);
        edtInspire = findViewById(R.id.editInspir);
        edtAddInfo = findViewById(R.id.editAddInfo);

        //declare database
        db = new Database(SongProperties.this);

        //apply intent functions to draw notation activities for music attributes.
        btRhythmNotation = (Button) findViewById(R.id.btNewRhythmNotes);
        btRhythmNotation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setDrawProperty("rhythm");
                openDrawNotationPage();
            }
        });
        btMelodyNotation = (Button) findViewById(R.id.btNewMelodyNotes);
        btMelodyNotation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setDrawProperty("melody");
                openDrawNotationPage();
            }
        });
        btBeatNotation = (Button) findViewById(R.id.btNewBeatNotes);
        btBeatNotation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setDrawProperty("beat");
                openDrawNotationPage();
            }
        });


        // apply add song functionality to add song button
        btAddIdea = (Button) findViewById(R.id.btAddIdea);
        btAddIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // pass data from inputs
                String titleVal = edtTitle.getText().toString();
                String genreVal = edtGenre.getText().toString();
                String bpmVal = edtBPM.getText().toString();
                String keySignVal = edtKeySign.getText().toString();
                String lyricsVal = edtLyrics.getText().toString();
                String inspireVal = edtInspire.getText().toString();
                String addInfoVal = edtAddInfo.getText().toString();

                // validating text fields
                checkValidInputs();
                if (validInputs==false){
                    Toast.makeText(SongProperties.this, "Invalid inputs", Toast.LENGTH_LONG).show();
                    return;
                }

                //add song details to db

                db.addNewSong(titleVal, genreVal, bpmVal, keySignVal,lyricsVal,inspireVal,addInfoVal);

                //make bell sound
                startService(new Intent(SongProperties.this, SoundService.class));
                //confirm song is added
                Toast.makeText(SongProperties.this, "Stored New Song Idea", Toast.LENGTH_SHORT).show();

                openSongIdeas();
            }
        });
    }

    //Share button functionality
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.share_menu, menu);

        // first parameter is the file for icon and second one is menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // check if share icon is selected
        if(item.getItemId()==R.id.btShare) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);

            // content type
            sharingIntent.setType("text/plain");

            // content to be shared
            String body = "MY SONG IDEA \n Title: "+ edtTitle.getText().toString()+
                    "\n Genre: "+edtGenre.getText().toString()+
                    "\n BPM: "+edtBPM.getText().toString()+
                    "\n Key Signature: "+edtKeySign.getText().toString()+
                    "\n Lyrics: "+edtLyrics.getText().toString()+
                    "\n Inspirations: "+edtInspire.getText().toString()+
                    "\n Additional Information: "+edtAddInfo.getText().toString();

            // content's subject
            String subject = edtTitle.getText().toString()+" Song Idea";

            // pass content
            sharingIntent.putExtra(Intent.EXTRA_TEXT, body);

            // pass subject
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        }
        return super.onOptionsItemSelected(item);
    }

    public void openDrawNotationPage(){
        checkValidInputs();
        if(validInputs==true) {
            //go to draw notation page with correct data.

            Toast.makeText(SongProperties.this, "Valid inputs", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, DrawNotation.class);

            //get correct file names for saving notation.
            intent.putExtra("songTitle", edtTitle.getText().toString());
            intent.putExtra("selected", getDrawProperty());
            startActivity(intent);
        }else{
            Toast.makeText(SongProperties.this, "Invalid inputs", Toast.LENGTH_LONG).show();
        }

    }


    public void openSongIdeas(){
        Intent intent = new Intent(this, SongIdeas.class);
        startActivity(intent);
    }

    public String getDrawProperty() {
        return drawProperty;
    }

    public void setDrawProperty(String drawProperty) {
        this.drawProperty = drawProperty;
    }

    public void checkValidInputs(){
        String titleVal = edtTitle.getText().toString();

        if (titleVal.isEmpty()==false){
            validInputs = true;

        }
    }
}