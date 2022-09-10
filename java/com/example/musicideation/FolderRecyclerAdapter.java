package com.example.musicideation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FolderRecyclerAdapter extends RecyclerView.Adapter<FolderRecyclerAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<Song> Songs;
    private Context context;

    // constructor
    public FolderRecyclerAdapter(ArrayList<Song> songs, Context context) {
        this.Songs = songs;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate folder view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // set titles to folders
        Song song = Songs.get(position);
        holder.songTitle.setText(song.getTitle());

        // When folder is clicked pass appropriate data and move to update song properties page.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //pass intent values
                Intent i = new Intent(context, UpdateSongProperties.class);

                i.putExtra("Title", song.getTitle());
                i.putExtra("Genre", song.getGenre());
                i.putExtra("BPM", song.getBpm());
                i.putExtra("KeySign", song.getKeySign());
                i.putExtra("Lyrics", song.getLyrics());
                i.putExtra("Inspires", song.getInspires());
                i.putExtra("AddInfo", song.getAddInfo());

                context.startActivity(i);
            }


        });




    }

    //get number of folders in recycler view
    @Override
    public int getItemCount() {
        // returning the size of our array list
        return Songs.size();
    }

    //Folder class that gets called for each row in database in recycler view
    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView songTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            songTitle = itemView.findViewById(R.id.songTitle);

        }
    }
}
