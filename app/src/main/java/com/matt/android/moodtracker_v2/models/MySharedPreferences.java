package com.matt.android.moodtracker_v2.models;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MySharedPreferences {

    private SharedPreferences mPreferences;
    private int temporaryPos;
    private int moodPosition;
    public static final String PREF_KEY_NAME = "PREF_KEY_NAME";
    public static final String PREF_KEY_CURRENT_SMILEY = "PREF_KEY_CURRENT_SMILEY";
    public static final String PREF_KEY_CURRENT_SMILEY_STATIC = "PREF_KEY_CURRENT_SMILEY_STATIC";

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

    


}
