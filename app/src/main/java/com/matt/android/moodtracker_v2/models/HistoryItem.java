/*
 *
 *   Created by Matteo VACCARI - 2019
 *
 *   https://github.com/matteovaccari/MoodTracker
 *
 */

package com.matt.android.moodtracker_v2.models;

import java.util.Date;

import static com.matt.android.moodtracker_v2.storage.Constants.PREF_KEY_EMPTY_COMMENT;

public class HistoryItem {

    private Date date;
    private Mood mood;
    private String comment;


    public HistoryItem(Date date, Mood mood) {
        setMood(mood);
        setDate(date);
    }

    public MoodEnum getMood() {
        return mood.getTitle();
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public String getComment() {
        if (comment != null) {
            return comment;
        } else {
            return PREF_KEY_EMPTY_COMMENT;
        }

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
