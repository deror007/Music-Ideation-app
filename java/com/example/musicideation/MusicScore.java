package com.example.musicideation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


public class MusicScore extends View {

    private static final float TOUCH_TOLERANCE = 4;
    private float mX, mY; //finger location
    private Path mPath; //stroke paths

    private Paint mPaint; //drawing style information

    //Store pen strokes of particular image
    private ArrayList<Pen> paths = new ArrayList<>();

    private int currentColor;
    private int penWidth;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    private int canvasWidth;
    private int canvasHeight;


    //Constructors
    public MusicScore(Context context) {
        this(context, null);
    }

    public MusicScore(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();

        //smoothen drawn paths
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //0xff=255 in decimal
        mPaint.setAlpha(0xff);

    }

    //get the canvas size for producing appropriate stave lines
    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        this.canvasWidth = width;
        this.canvasHeight = height;
        super.onSizeChanged(width, height, oldWidth, oldHeight);
    }

    //instantiate bitmap and canvas called in DrawNotation.java
    public void init(int height, int width) {

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        //Set to default colour to black like in normal music notation
        currentColor = Color.BLACK;
        //set starting pen width
        penWidth = 20;
    }

    //set pen colour
    public void setColor(int color) {
        currentColor = color;
    }

    //set pen width
    public void setPenWidth(int width) {
        penWidth = width;
    }

    //undo last drawing stroke from saved list.
    public void undo() {
        if (paths.size() != 0) {
            paths.remove(paths.size() - 1);
            invalidate();
        }
    }

    //return bitmap
    public Bitmap save() {
        return mBitmap;
    }

    //allow drawing functionality on canvas
    @Override
    protected void onDraw(Canvas canvas) {

        //initialise background of canvas
        canvas.save();
        int backgroundColor = Color.WHITE;
        mCanvas.drawColor(backgroundColor);

        //create staves for music notes
        createStaves(3);

        //iterate and draw paths made by pen onto canvas
        for (Pen fp : paths) {
            mPaint.setColor(fp.color);
            mPaint.setStrokeWidth(fp.penWidth); //setStrokeWidth inbuilt method of android paint class!!!!!!!!!!!

            mCanvas.drawPath(fp.path, mPaint);
        }
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    //Create stave lines between canvas background and user drawings
    private void createStaves(int staveNum){


        Paint staveAttr = new Paint();
        staveAttr.setColor(Color.BLACK);
        staveAttr.setStrokeWidth(10f);

        //start and end position of the top (first) stave line
        int xOne =  canvasWidth/15; int yOne = canvasHeight/15;
        int xTwo =  canvasWidth - canvasWidth/15; int yTwo = canvasHeight/15;

        //Create stave bars
        for (int stave = 0; stave < staveNum; stave++){

            //Create 5 lines per stave bar
            for (int line = 0; line < 5; line++) {

                mCanvas.drawLine(xOne, yOne, xTwo, yTwo, staveAttr);
                //increment y positions and keep x positions to keep order of Staves
                yOne += canvasHeight/20; yTwo += canvasHeight/20;
            }
            yOne += canvasHeight/15; yTwo += canvasHeight/15; //Produce gap between staves.
        }


    }

    //allow various touch gestures.

    //add touch to pen paths array list.
    private void touchStart(float x, float y) {
        mPath = new Path();
        Pen fp = new Pen(currentColor, penWidth, mPath);
        paths.add(fp);

        //reset paths
        mPath.reset();
        //set starting position of path
        mPath.moveTo(x, y);
        //save current finger position
        mX = x;
        mY = y;
    }

    //check if pen moved on canvas
    private void touchMove(float x, float y) {
        //calc gradient between last and current position of pen
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        //check movement is greater than tolerance constant
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            //smoothen finger drawing
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    //draw path to current position
    private void touchUp() {
        mPath.lineTo(mX, mY);
    }

    //allow touch events in particular directions
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }

        return true;
    }

}




