package com.example.musicideation;

public class Song {

    private int id;
    private String title, genre, bpm, keySign, lyrics, inspires, addInfo;

    // constructor
    public Song(String title, String genre, String bpm, String keySign, String lyrics,
                String inspires, String addInfo) {

        this.title = title;
        this.genre = genre;
        this.bpm = bpm;
        this.keySign = keySign;
        this.lyrics = lyrics;
        this.inspires = inspires;
        this.addInfo = addInfo;
    }


    // creating getter and setter methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getBpm() {
        return bpm;
    }

    public void setBpm(String bpm) {
        this.bpm = bpm;
    }

    public String getKeySign() {
        return keySign;
    }

    public void setKeySign(String keySign) {
        this.keySign = keySign;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getInspires() {
        return inspires;
    }

    public void setInspires(String inspires) {
        this.inspires = inspires;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
}
