/*
 *
 *   Created by Matteo VACCARI - 2019
 *
 *   https://github.com/matteovaccari/MoodTracker
 *
 */

package com.matt.android.moodtracker_v2.models;

public class Mood {

    private MoodEnum title;
    private int backgroundColor;

    public Mood(MoodEnum title, int backgroundColor) {
        setTitle(title);
        setBackgroundColor(backgroundColor);
    }

    //Getters and setters
    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public MoodEnum getTitle() {
        return title;
    }

    public void setTitle(MoodEnum title) {
        this.title = title;
    }

}
