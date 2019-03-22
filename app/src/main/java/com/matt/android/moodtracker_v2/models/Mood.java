/*
 *
 *   Created by Matteo VACCARI - 2019
 *
 *   https://github.com/matteovaccari/MoodTracker
 *
 */

package com.matt.android.moodtracker_v2.models;

public class Mood {

    private String title;
    private int position;
    private int backgroundColor;
    private int smiley;

    public Mood(String title, int position, int backgroundColor, int smiley) {
        setTitle(title);
        setPosition(position);
        setBackgroundColor(backgroundColor);
        setSmiley(smiley);
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getSmiley() {
        return smiley;
    }

    public void setSmiley(int smiley) {
        this.smiley = smiley;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
