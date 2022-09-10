package com.example.musicideation;

// drawing image class
public class Notation {

    private int ImgId;

    private String Uri, Property, Title;

    //constructor
    public Notation(String uri, String property, String title) {

        this.Uri = uri;
        this.Property = property;
        this.Title = title;

    }

    public int getImgId() {
        return ImgId;
    }

    public void setImgId(int id) {
        ImgId = id;
    }

    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        Uri = uri;
    }

    public String getProperty() {
        return Property;
    }

    public void setProperty(String property) {
        Property = property;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
