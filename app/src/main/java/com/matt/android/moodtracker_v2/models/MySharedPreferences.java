package com.matt.android.moodtracker_v2.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.matt.android.moodtracker_v2.controllers.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MySharedPreferences {

    private SharedPreferences mPreferences;
    public static final String PREF_KEY_NAME = "PREF_KEY_NAME";
    public static final String PREF_KEY_CURRENT_SMILEY = "PREF_KEY_CURRENT_SMILEY";

        //Constructor
    public MySharedPreferences(Context context) {
        mPreferences = context.getSharedPreferences(PREF_KEY_NAME,Context.MODE_PRIVATE);
    }

    private String getMood(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.FRANCE);
        return "Mood at "+ simpleDateFormat.format(date);
    }

    public void saveMood(Date date, Integer currentSmileyPos ) {
        mPreferences.edit().putInt(PREF_KEY_CURRENT_SMILEY,currentSmileyPos);
    }
}
