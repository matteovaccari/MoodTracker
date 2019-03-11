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

    public String getMoodDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.FRANCE);
        return "Mood at "+ simpleDateFormat.format(date);
    }

    public void saveMood(Date date) {
            // Get actual smiley position
        temporaryPos = mPreferences.getInt(PREF_KEY_CURRENT_SMILEY_STATIC,-50);
            // Put in prefs actual smiley position as Today final position (CURRENT_SMILEY)
        mPreferences.edit().putInt(PREF_KEY_CURRENT_SMILEY,temporaryPos).apply();
            //Put final today pos in moodPosition var
        moodPosition = mPreferences.getInt(PREF_KEY_CURRENT_SMILEY,-50);
            //Put moodPos in prefs with DATE + mood (position from 0 to 4)
        mPreferences.edit().putInt(getMood(date),moodPosition).apply();
    }


}
