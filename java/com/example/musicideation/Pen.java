package com.example.musicideation;

import android.graphics.Path;

public class Pen {

    //color of the stroke
    public int color;
    //width of the stroke
    public int penWidth;
    //a Path object to represent the path drawn
    public Path path;

    //constructor to initialise the attributes
    public Pen(int color, int penWidth, Path path) {
        this.color = color;
        this.penWidth = penWidth;
        this.path = path;
    }
}
