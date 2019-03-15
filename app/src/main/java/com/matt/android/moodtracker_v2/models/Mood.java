package com.matt.android.moodtracker_v2.models;

public class Mood {

    private String title;
    private int backgroundColor;
    private int position;

    public Mood (String title, int backgroundColor, int position) {
        setTitle(title);
        setBackgroundColor(backgroundColor);
        setPosition(position);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
