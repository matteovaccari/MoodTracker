/*
 *
 *   Created by Matteo VACCARI - 2019
 *
 *   https://github.com/matteovaccari/MoodTracker
 *
 */

package com.matt.android.moodtracker_v2.models;

import java.util.Date;

public class HistoryItem {

    private Date date;
    private Mood mood;
    private String comment;


    public HistoryItem(Date date, Mood mood, String comment) {
        setComment(comment);
        setMood(mood);
        setDate(date);
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
