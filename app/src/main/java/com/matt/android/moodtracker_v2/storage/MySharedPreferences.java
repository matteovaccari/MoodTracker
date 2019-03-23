/*
 *
 *   Created by Matteo VACCARI - 2019
 *
 *   https://github.com/matteovaccari/MoodTracker
 *
 */
package com.matt.android.moodtracker_v2.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import com.matt.android.moodtracker_v2.R;
import com.matt.android.moodtracker_v2.models.Mood;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MySharedPreferences {

    private SharedPreferences mPreferences;
    public static final String PREF_KEY_NAME = "PREF_KEY_NAME";
    public static final String PREF_KEY_LAST_POS_VIEWPAGER = "PREF_KEY_LAST_POS_VIEWPAGER";
    public static final String PREF_KEY_BACKGROUND_VIEWPAGER = "PREF_KEY_BACKGROUND_VIEWPAGER";

    private String moodInStringForShare;

    public MySharedPreferences(Context context) {
        mPreferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
    }

    //Get a date for moods at format dd/mm/yyyy
    public String getMoodDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        return "Mood at " + simpleDateFormat.format(date);
    }

    //Save in prefs a mood, with date as key (getMoodDate)
    public void saveMood(Date date, Mood mood) {
        mPreferences.edit().putString(getMoodDate(date), mood.getTitle()).apply();
    }

    //Return a mood for a specific date
    public String getMood(Date date) {
        String CurrentMood = mPreferences.getString(getMoodDate(date), null);
        //Return a value only if there's one in history
        if (CurrentMood != null) {
            return CurrentMood;
        }
        return null;
    }

    //Same methods for comments
    public String getCommentDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        return "Comment at " + simpleDateFormat.format(date);
    }

    public void saveComment(Date date, String comment) {
        mPreferences.edit().putString(getCommentDate(date), comment).apply();
    }

    public String getComment(Date date) {
        return mPreferences.getString(getCommentDate(date), null);
    }

    public String getMoodNameForSharing(int currentPos) {

        switch (currentPos) {
            case 0:
                moodInStringForShare = "sad";
                break;
            case 1:
                moodInStringForShare = "dissapointed";
                break;
            case 2:
                moodInStringForShare = "normal";
                break;
            case 3:
                moodInStringForShare = "happy";
                break;
            case 4:
                moodInStringForShare = "super happy";
                break;
        }
        return moodInStringForShare;
    }


    //The next methods are for displaying correct last page at each run
    public void saveLastPositionForViewPager(int smileyPos) {
        mPreferences.edit().putInt(PREF_KEY_LAST_POS_VIEWPAGER, smileyPos).apply();
    }

    public int getLastPositionForViewPager() {
        return mPreferences.getInt(PREF_KEY_LAST_POS_VIEWPAGER, -50);
    }

}
