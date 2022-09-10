package com.example.musicideation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

//Page that shows all current song ideas and access points to create, update and retrieve ideas.
public class SongIdeas extends AppCompatActivity {

    private Button btNewSongFolder, btStartScreen;
    private ArrayList<Song> songs;
    private Database db;
    private FolderRecyclerAdapter folderRecAdapter;
    private RecyclerView foldersRecView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_ideas);
        // initializing our all variables.
        songs = new ArrayList<>();
        db = new Database(this);
        //Get buttons from layout

        btNewSongFolder = (Button) findViewById(R.id.btOpenSongFolder);

        btStartScreen= (Button) findViewById(R.id.btOpenStartScreen);
        //Provide onClick() functionality for each button

        btNewSongFolder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openNewSongFolder();
            }
        });

        btStartScreen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openStartScreen();
            }
        });


        //get all songs data from database

        songs = db.readSongTable();

        //create and display music folders via recycler view
        folderRecAdapter = new FolderRecyclerAdapter(songs, SongIdeas.this);
        foldersRecView = findViewById(R.id.RecFolders);

        // set recycler view layout
        LinearLayoutManager lLayoutManager = new LinearLayoutManager(SongIdeas.this, RecyclerView.VERTICAL, false);
        foldersRecView.setLayoutManager(lLayoutManager);

        // setting recycler view adapter
        foldersRecView.setAdapter(folderRecAdapter);



    }

    public void openNewSongFolder(){

        Intent intent = new Intent(this, SongProperties.class);
        intent.putExtra("New_Song_Folder", "EMPTY");
        startActivity(intent);
    }

    public void openStartScreen(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



}

