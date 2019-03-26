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
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.matt.android.moodtracker_v2.R;
import com.matt.android.moodtracker_v2.models.HistoryItem;
import com.matt.android.moodtracker_v2.models.Mood;
import com.google.gson.Gson;
import com.matt.android.moodtracker_v2.models.MoodEnum;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.matt.android.moodtracker_v2.storage.Constants.PREF_KEY_COMMENT;
import static com.matt.android.moodtracker_v2.storage.Constants.PREF_KEY_EMPTY_COMMENT;
import static com.matt.android.moodtracker_v2.storage.Constants.PREF_KEY_MOODLIST;

public class MySharedPreferences {

    private HistoryItem historyItem;
    private SharedPreferences mPreferences;
    public static final String PREF_KEY_NAME = "PREF_KEY_NAME";
    public static final String PREF_KEY_LAST_POS_VIEWPAGER = "PREF_KEY_LAST_POS_VIEWPAGER";
    public static final String PREF_KEY_HISTORY_ITEM = "PREF_KEY_HISTORY_ITEM";
    public static final String PREF_KEY_BACKGROUND_VIEWPAGER = "PREF_KEY_BACKGROUND_VIEWPAGER";
    private String moodInStringForShare;

    public MySharedPreferences(Context context) {
        mPreferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
    }

    public String getMoodDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        return "Mood at " + simpleDateFormat.format(date);
    }
/**
    public void saveMood(Date date, Mood mood) {
        mPreferences.edit().putString(getMoodDate(date), mood.getTitle().toString()).apply();
    }

    public MoodEnum getMood(Date date) {
        String mood = mPreferences.getString(getMoodDate(date), null);
        if (mood != null) {
            try {
                return MoodEnum.valueOf(mood);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return null;
    } */

    public void savemood2(Date date, HistoryItem historyItem) {
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
        Log.e("TAGINPUT", jsonHistoryItem);
    }

    public HistoryItem getMood2(Date date) {
        Gson gson = new Gson();
        String json = mPreferences.getString(getMoodDate(date), null);
        //   Type type = new TypeToken<HistoryItem>() {}.getType();
        historyItem = gson.fromJson(json, HistoryItem.class);
//        Log.e("TAGOUTPUT", json);
        return historyItem;
    }

    /**
     * public void saveHistoryItem(Date date, MoodEnum mood) {
     * Gson gson = new Gson();
     * String jsonHistoryItem;
     * //If there's already a comment, save it
     * if (getComment(date) != null || getComment(date) != PREF_KEY_EMPTY_COMMENT) {
     * jsonHistoryItem = gson.toJson(new HistoryItem(date, mood, getComment(date)),HistoryItem.class);
     * }
     * //If there's no comment, save PREF_KEY_EMPTY_COMMENT as comment
     * else {
     * jsonHistoryItem = gson.toJson(new HistoryItem(date, mood, PREF_KEY_EMPTY_COMMENT),HistoryItem.class); // HistoryItem --> Json String
     * }
     * mPreferences.edit().putString(getHistoryItemDate(date), jsonHistoryItem).apply(); // Json String --> SharedPrefs
     * }
     * <p>
     * public String getHistoryItemDate(Date date) {
     * SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
     * return "Mood at " + simpleDateFormat.format(date);
     * }
     * <p>
     * //Get historyItemJson and convert it to HistoryItem, then return it
     * public HistoryItem getHistoryItem(Date date) {
     * <p>
     * Gson gson = new Gson();
     * String json = mPreferences.getString(getHistoryItemDate(date), null);
     * //   Type type = new TypeToken<HistoryItem>() {}.getType();
     * historyItem = gson.fromJson(json,HistoryItem.class);
     * return historyItem;
     * }
     */

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

}
