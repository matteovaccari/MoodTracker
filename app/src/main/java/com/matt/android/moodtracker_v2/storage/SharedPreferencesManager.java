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
import android.util.Log;

import com.matt.android.moodtracker_v2.R;
import com.matt.android.moodtracker_v2.models.HistoryItem;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.matt.android.moodtracker_v2.storage.Constants.PREF_KEY_EMPTY_COMMENT;
import static com.matt.android.moodtracker_v2.storage.Constants.PREF_KEY_IS_DEFAULT_MOOD_PRESENT;
import static com.matt.android.moodtracker_v2.storage.Constants.PREF_KEY_LAST_POS_VIEWPAGER;
import static com.matt.android.moodtracker_v2.storage.Constants.PREF_KEY_NAME;

public class SharedPreferencesManager {

    private HistoryItem historyItem;
    private SharedPreferences mPreferences;
    private String moodInStringForShare;
    private Context context;

    public SharedPreferencesManager(Context context) {
        mPreferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
        this.context = context;
    }

    public String getMoodDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        return "Mood at " + simpleDateFormat.format(date);
    }

    public void saveHistoryItem(Date date, HistoryItem historyItem) {
        Gson gson = new Gson();
        String jsonHistoryItem;

        // HistoryItem --> Json String
        if (historyItem.getComment() != null || historyItem.getComment() != PREF_KEY_EMPTY_COMMENT) {
            jsonHistoryItem = gson.toJson(historyItem, HistoryItem.class);
        }
        //If there's no comment, save PREF_KEY_EMPTY_COMMENT as comment
        else {
            historyItem.setComment(PREF_KEY_EMPTY_COMMENT);
            jsonHistoryItem = gson.toJson(historyItem, HistoryItem.class);
        }

        mPreferences.edit().putString(getMoodDate(date), jsonHistoryItem).apply();
    }

    public HistoryItem getHistoryItem(Date date) {
        Gson gson = new Gson();
        String json = mPreferences.getString(getMoodDate(date), null);
        historyItem = gson.fromJson(json, HistoryItem.class);
        return historyItem;
    }


    public String getCommentDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        return "Comment at " + simpleDateFormat.format(date);
    }

    public void saveComment(Date date, String comment) {
        mPreferences.edit().putString(getCommentDate(date), comment).apply();
    }

    public String getComment(Date date) {
        return mPreferences.getString(getCommentDate(date), PREF_KEY_EMPTY_COMMENT);

    }

    //This is for default text set when sharing mood
    public String getMoodNameForSharing(int currentPos) {

        switch (currentPos) {
            case 0:
                moodInStringForShare  = context.getResources().getString(R.string.sad_Mood);
                break;
            case 1:
                moodInStringForShare = context.getResources().getString(R.string.disappointed_Mood);
                break;
            case 2:
                moodInStringForShare = context.getResources().getString(R.string.normal_Mood);
                break;
            case 3:
                moodInStringForShare = context.getResources().getString(R.string.happy_Mood);
                break;
            case 4:
                moodInStringForShare = context.getResources().getString(R.string.super_Happy_Mood);
                break;
        }
        return moodInStringForShare;
    }

    //The next two methods are for displaying correct last page at each run
    public void saveLastPositionForViewPager(int smileyPos) {
        mPreferences.edit().putInt(PREF_KEY_LAST_POS_VIEWPAGER, smileyPos).apply();
    }

    public int getLastPositionForViewPager() {
        return mPreferences.getInt(PREF_KEY_LAST_POS_VIEWPAGER, -50);
    }

    //The next two methods are for checking if app is run for the first time
    public void saveIsDefaultMoodPresent(Boolean isDefaultMood) {
        mPreferences.edit().putBoolean(PREF_KEY_IS_DEFAULT_MOOD_PRESENT, isDefaultMood).apply();
    }

    public Boolean getIsDefaultMoodPresent() {
        return mPreferences.getBoolean(PREF_KEY_IS_DEFAULT_MOOD_PRESENT, false);
    }

}
