package com.example.musicideation;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;



import java.util.ArrayList;


public class Database extends SQLiteOpenHelper {

    // Database name
    private static final String dbName = "Music_Ideas";
    // database version
    private static final int Version = 1;

    // Song Table columns:
    private static final String SongTable = "My_Songs";

    private static final String ID = "Song_ID";

    private static final String TITLE = "Title";

    private static final String GENRE = "Genre";

    private static final String BPM = "BPM";

    private static final String KEYSIGN = "Key_Signature";

    private static final String LYRICS = "Lyrics";

    private static final String INSPIRES = "Inspirations";

    private static final String ADDINFO = "Additional_Information";

    // Image Table columns:
    private static final String ImageTable = "My_Images";

    private static final String IMAGEID = "Image_ID";

    private static final String PROPERTY = "Property";

    private static final String URI = "URI";

    //Foreign Key from Song Table.
    private static final String SONGTITLE = "Song_Title";




    // Database constructor
    public Database(Context context) {
        super(context, dbName, null, Version);
    }

    // Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.

        String createSongTable = "CREATE TABLE " + SongTable + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + " TEXT,"
                + GENRE + " TEXT,"
                + BPM + " TEXT,"
                + KEYSIGN + " TEXT,"
                + LYRICS + " TEXT,"
                + INSPIRES + " TEXT,"
                + ADDINFO + " TEXT)";

        String createImageTable = "CREATE TABLE " + ImageTable + " ("
                + IMAGEID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + URI + " TEXT,"
                + PROPERTY + " TEXT,"
                + SONGTITLE + " TEXT)";


        // execute CREATE commands
        db.execSQL(createSongTable);
        db.execSQL(createImageTable);

    }

    //Update any changes to the tables by removing and re-creating them
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + SongTable);
        db.execSQL("DROP TABLE IF EXISTS " + ImageTable);
        onCreate(db);
    }

    // Add new song record to Song Table.
    public void addNewSong(String title, String genre, String bpm, String keySign,
                           String lyrics, String inspires, String addInfo) {

        //make db writeable
        SQLiteDatabase db = this.getWritableDatabase();
        //store record in content values.
        ContentValues values = new ContentValues();

        values.put(TITLE, title);
        values.put(GENRE, genre);
        values.put(BPM, bpm);
        values.put(KEYSIGN, keySign);
        values.put(LYRICS, lyrics);
        values.put(INSPIRES, inspires);
        values.put(ADDINFO, addInfo);

        // execute Insert command
        db.insert(SongTable, null, values);

        db.close();
    }

    // RETRIEVE all Song Data
    public ArrayList<Song> readSongTable() {

        //make db readable
        SQLiteDatabase db = this.getReadableDatabase();

        // iterate through retrieved data
        Cursor cursor = db.rawQuery("SELECT * FROM " + SongTable, null);

        // initialize array of songs
        ArrayList<Song> songs = new ArrayList<>();

        // cursor starts at first position and iterates until no more items left.
        if (cursor.moveToFirst()) {
            do {
                // add cursor items to ArrayList.
                songs.add(new Song(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)));
            } while (cursor.moveToNext());

        }

        cursor.close();
        return songs;
    }


    // Update song record
    public void updateSongProperties(String updatingSongTitle, String title, String genre,
                                     String bpm, String keySign, String lyrics, String inspires, String addInfo) {

        // set db to writeable
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TITLE, title);
        values.put(GENRE, genre);
        values.put(BPM, bpm);
        values.put(KEYSIGN, keySign);
        values.put(LYRICS, lyrics);
        values.put(INSPIRES, inspires);
        values.put(ADDINFO, addInfo);

        // Update particular song with current values
        db.update(SongTable, values, "Title=?", new String[]{updatingSongTitle});
        db.close();
    }

    //Add image data of a users drawing.
    public void addNewNotation(String uri, String property, String title) {

        // set db to writeable
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(URI, uri);
        values.put(PROPERTY, property);
        values.put(SONGTITLE, title);

        // Insert drawing details
        db.insert(ImageTable, null, values);

        db.close();
    }

    // Read all saved image pointers
    public ArrayList<Uri> readImagesByAttr(String property, String title) {
        // set db to readable
        SQLiteDatabase db = this.getReadableDatabase();

        //initialise array list of uri's
        ArrayList<Uri> imagePointers = new ArrayList<>();

        // Retrieve all correct images by type as cursor items.
        Cursor cursor = db.rawQuery("select * from " + ImageTable + " where "
                + PROPERTY+" = ?  AND  " + SONGTITLE+ " = ? " , new String[]{property, title});

        // move cursor to first position and iterate through
        if (cursor.moveToFirst()) {
            do {

                //construct uri and store it into array-list by passing encoded Uri String.
                imagePointers.add(Uri.parse(cursor.getString(1)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        return imagePointers;
    }

    // below is the method for deleting our course.
    public void deleteSongIdea(String songTitle) {

        // set db to writeable
        SQLiteDatabase db = this.getWritableDatabase();

        //execute Delete sql at particular song record
        db.delete(SongTable, "Title = ?", new String[]{songTitle});
        db.close();
    }


}