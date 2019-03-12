package com.matt.android.moodtracker_v2.models;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MySharedPreferences {

    private SharedPreferences mPreferences;
    public static final String PREF_KEY_NAME = "PREF_KEY_NAME";

    public MySharedPreferences(Context context) {
        mPreferences = context.getSharedPreferences(PREF_KEY_NAME,Context.MODE_PRIVATE);
    }

        //Get a date for moods at format dd/mm/yyyy
    public String getMoodDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.FRANCE);
        return "Mood at "+ simpleDateFormat.format(date);
    }
        //Save in prefs a mood, with date as key (getMoodDate)
    public void saveMood(Date date, Mood mood) {
            mPreferences.edit().putString(getMoodDate(date),mood.name()).apply();
    }

        //Return a mood for a specific date
    public Mood getMood(Date date) {
        String CurrentMood = mPreferences.getString(getMoodDate(date), null);
            //Return a value only if there's one in history
        if (CurrentMood != null) {
            return Mood.valueOf(CurrentMood);
        }
        return null;
    }
        //Same methods for comments
    public String getCommentDate (Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        return "Comment at " + simpleDateFormat.format(date);
    }

    public void saveComment (Date date, String comment) {
        mPreferences.edit().putString(getCommentDate(date),comment).apply();
    }

    public String getComment (Date date) {
        return mPreferences.getString(getCommentDate(date),null);
    }
}
