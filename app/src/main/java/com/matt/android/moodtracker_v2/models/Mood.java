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
    private int position;
    private int backgroundColor;
    private int smiley;

    public Mood(MoodEnum title, int position, int backgroundColor, int smiley) {
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

    public void setSmiley(int smiley) {
        this.smiley = smiley;
    }

    public MoodEnum getTitle() {
        return title;
    }

    public void setTitle(MoodEnum title) {
        this.title = title;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public int getPosition() {
        return position;
    }

}
