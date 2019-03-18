package com.matt.android.moodtracker_v2.models;

public class Mood {

    private String title;
    private int position;

    public Mood (String title, int position) {
        setTitle(title);
        setPosition(position);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
