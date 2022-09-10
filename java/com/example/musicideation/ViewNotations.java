package com.example.musicideation;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewNotations extends AppCompatActivity {

    private ArrayList<Uri> notations;
    private Database db;
    private NotationsRecyclerAdapter notationRecAdapter;
    private RecyclerView notationsRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notations);

        // initializing our all variables.
        notations = new ArrayList<>();
        db = new Database(ViewNotations.this);
        String songTitle = getIntent().getStringExtra("songTitle");
        String selectedProperty= getIntent().getStringExtra("selected");


        // getting our array
        notations = db.readImagesByAttr(selectedProperty, songTitle);

        // Set up notation items
        notationRecAdapter = new NotationsRecyclerAdapter(notations,ViewNotations.this);
        notationsRecView = findViewById(R.id.RecViewNotations);

        // setting layout manager for our recycler view.
        GridLayoutManager GridLayoutManager = new GridLayoutManager(ViewNotations.this, 3);
        notationsRecView.setLayoutManager(GridLayoutManager);

        // set adapter to recycler view.
        notationsRecView.setAdapter(notationRecAdapter);
    }
}
