package com.example.musicideation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class NotationsRecyclerAdapter extends RecyclerView.Adapter<NotationsRecyclerAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<Uri> notations;
    private Context context;

    // constructor
    public NotationsRecyclerAdapter(ArrayList<Uri> notations, Context context) {
        this.notations = notations;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate with notation cards

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notation_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // bind items with drawing images
        Uri note = notations.get(position);

        //load images into recyclerview items
        Glide.with(context)
                .load(note)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.image);

        // zoom into drawings when clicked via intent
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on below line we are calling an intent.
                Intent i = new Intent(context, SelectNotation.class);

                // below we are passing all our values.
                i.putExtra("selectedUri", note);
                Toast.makeText(context, note.toString(),Toast.LENGTH_SHORT).show();

                // starting our activity.
                context.startActivity(i);

            }


        });

    }

    //get total number of items
    @Override
    public int getItemCount() {
        // returning the size of our array list
        return notations.size();
    }

    //mini images class to bind to recycler view items
    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.

        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our image views
            image = itemView.findViewById(R.id.notationImage);

        }
    }
}
