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



public class UpdateSongProperties extends AppCompatActivity {

    private Button btRhythmNotation,btMelodyNotation,btBeatNotation, btUpdateIdea, btDeleteIdea,
            btViewBeatNotes, btViewRhythmNotes, btViewMelodyNotes;

    private EditText updateTitle, updateGenre, updateBPM, updateKeySign, updateLyrics,
            updateInspire, updateAddInfo;

    private TextView updateRhythm, updateMelody, updateBeat;

    private Database db;

    private String title, genre, bpm, keySign, lyrics, inspires, addInfo, drawProperty;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_song_properties);

        // initializing all our variables.
        updateTitle = findViewById(R.id.editUpdateTitle);
        updateGenre = findViewById(R.id.editUpdateGenre);
        updateBPM = findViewById(R.id.editUpdateBPM);
        updateKeySign = findViewById(R.id.editUpdateKeySig);

        updateRhythm = findViewById(R.id.textUpdateRhythm);
        btViewRhythmNotes=findViewById(R.id.btViewRhythmNotes);

        updateMelody = findViewById(R.id.textUpdateMelody);
        btViewMelodyNotes=findViewById(R.id.btViewMelodyNotes);

        updateBeat = findViewById(R.id.textUpdateBeat);
        btViewBeatNotes=findViewById(R.id.btViewBeatNotes);

        updateLyrics = findViewById(R.id.editUpdateLyrics);
        updateInspire = findViewById(R.id.editUpdateInspir);
        updateAddInfo = findViewById(R.id.editUpdateAddInfo);

        btDeleteIdea = (Button) findViewById(R.id.btDeleteSongIdea);
        btUpdateIdea = findViewById(R.id.btUpdateIdea);


        // on below line we are initialing our db class.
        db = new Database(UpdateSongProperties.this);

        // on below lines we are getting data which
        // we passed in our adapter class.
        title = getIntent().getStringExtra("Title");
        genre = getIntent().getStringExtra("Genre");
        bpm = getIntent().getStringExtra("BPM");
        keySign = getIntent().getStringExtra("KeySign");
        lyrics = getIntent().getStringExtra("Lyrics");
        inspires = getIntent().getStringExtra("Inspires");
        addInfo = getIntent().getStringExtra("AddInfo");

        // setting data to edit text
        // of our update activity.
        updateTitle.setText(title);
        updateGenre.setText(genre);
        updateBPM.setText(bpm);
        updateKeySign.setText(keySign);
        updateLyrics.setText(lyrics);
        updateInspire.setText(inspires);
        updateAddInfo.setText(addInfo);

        // give functionality for each button
        btRhythmNotation = (Button) findViewById(R.id.btNewRhythmNotes);
        btRhythmNotation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setDrawProperty("rhythm");
                openDrawNotationPage();
            }
        });

        btViewRhythmNotes = (Button) findViewById(R.id.btViewRhythmNotes);
        btViewRhythmNotes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setDrawProperty("rhythm");
                openViewNotations();
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

        btViewMelodyNotes = (Button) findViewById(R.id.btViewMelodyNotes);
        btViewMelodyNotes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setDrawProperty("melody");
                openViewNotations();
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

        btViewBeatNotes = (Button) findViewById(R.id.btViewBeatNotes);
        btViewBeatNotes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setDrawProperty("beat");
                openViewNotations();
            }
        });

        // apply functionality update song button.
        btUpdateIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // inside this method we are calling an update course
                // method and passing all our edit text values.
                db.updateSongProperties(title, updateTitle.getText().toString(),
                        updateGenre.getText().toString(), updateBPM.getText().toString(),
                        updateKeySign.getText().toString(), updateLyrics.getText().toString(),
                        updateInspire.getText().toString(), updateAddInfo.getText().toString());

                // displaying a toast message that our course has been updated.
                Toast.makeText(UpdateSongProperties.this, "Song Updated.", Toast.LENGTH_SHORT).show();
                startService(new Intent(UpdateSongProperties.this, SoundService.class));
                openSongIdeas();
            }
        });

        // delete button functionality
        btDeleteIdea.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // calling a method to delete our course.
                db.deleteSongIdea(title);

                Toast.makeText(UpdateSongProperties.this, "Deleted Song Folder", Toast.LENGTH_SHORT).show();
                startService(new Intent(UpdateSongProperties.this, SoundService.class));
                openSongIdeas();
                return true;
            }
        });
    }

    //share functionality
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.share_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //check share is selected.
        if(item.getItemId()==R.id.btShare) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);

            // content type
            sharingIntent.setType("text/plain");

            // content to share
            String body = "MY SONG IDEA \n Title: "+ updateTitle.getText().toString()+
                    "\n Genre: "+updateGenre.getText().toString()+
                    "\n BPM: "+updateBPM.getText().toString()+
                    "\n Key Signature: "+updateKeySign.getText().toString()+
                    "\n Lyrics: "+updateLyrics.getText().toString()+
                    "\n Inspirations: "+updateInspire.getText().toString()+
                    "\n Additional Information: "+updateAddInfo.getText().toString();

            // subject of the content
            String subject = updateTitle +" Song Idea";

            // passing content
            sharingIntent.putExtra(Intent.EXTRA_TEXT, body);

            // passing subject
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        }
        return super.onOptionsItemSelected(item);
    }

    public void openDrawNotationPage(){
        Intent intent = new Intent(this, DrawNotation.class);
        intent.putExtra("songTitle", title);
        intent.putExtra("selected", getDrawProperty());
        startActivity(intent);
    }

    public void openSongIdeas(){
        Intent intent = new Intent(this, SongIdeas.class);
        startActivity(intent);
    }

    public void openViewNotations(){
        Intent intent = new Intent(this, ViewNotations.class);
        intent.putExtra("songTitle", title); //These intents are same as openDrawNotePage..
        intent.putExtra("selected", getDrawProperty());
        startActivity(intent);
    }

    public String getDrawProperty() {
        return drawProperty;
    }

    public void setDrawProperty(String drawProperty) {
        this.drawProperty = drawProperty;
    }

}