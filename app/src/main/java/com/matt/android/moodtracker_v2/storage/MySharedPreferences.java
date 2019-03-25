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

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.matt.android.moodtracker_v2.R;
import com.matt.android.moodtracker_v2.models.HistoryItem;
import com.matt.android.moodtracker_v2.models.Mood;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.matt.android.moodtracker_v2.storage.Constants.PREF_KEY_EMPTY_COMMENT;
import static com.matt.android.moodtracker_v2.storage.Constants.PREF_KEY_MOODLIST;

public class MySharedPreferences {

    private HistoryItem historyItem;
    private ArrayList moodList;
    private SharedPreferences mPreferences;
    public static final String PREF_KEY_NAME = "PREF_KEY_NAME";
    public static final String PREF_KEY_LAST_POS_VIEWPAGER = "PREF_KEY_LAST_POS_VIEWPAGER";
    public static final String PREF_KEY_HISTORY_ITEM = "PREF_KEY_HISTORY_ITEM";
    public static final String PREF_KEY_BACKGROUND_VIEWPAGER = "PREF_KEY_BACKGROUND_VIEWPAGER";

    private String moodInStringForShare;

    public MySharedPreferences(Context context) {
        mPreferences = context.getSharedPreferences(PREF_KEY_NAME, Context.MODE_PRIVATE);
    }
/*
    //Get a date for moods at format dd/mm/yyyy
    public String getMoodDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        return "Mood at " + simpleDateFormat.format(date);
    }

    //Save in prefs a mood, with date as key (getMoodDate)
    public void saveMood(Date date, Mood mood) {
        mPreferences.edit().putString(getMoodDate(date), mood.getTitle().name()).apply();
    }

    //Return a mood for a specific date
    public String getMood(Date date) {
        String CurrentMood = mPreferences.getString(getMoodDate(date), null);
        //Return a value only if there's one in history
        if (CurrentMood != null) {
            return CurrentMood;
        }
        return null;
    }  */

    public void saveHistoryItem(Date date, Mood mood) {
       Gson gson = new Gson();
       String jsonHistoryItem = gson.toJson(new HistoryItem(date,mood)); // HistoryItem --> Json String
       mPreferences.edit().putString(getHistoryItemDate(date), jsonHistoryItem).apply(); // Json String --> SharedPrefs
    }

    public String getHistoryItemDate (Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        return "Mood at " + simpleDateFormat.format(date);
    }

    //Get historyItemJson and convert it to HistoryItem, then return it
    public HistoryItem getHistoryItem(Date date) {

        Gson gson = new Gson();
        String json = mPreferences.getString(getHistoryItemDate(date),null);
        Type type = new TypeToken<HistoryItem>() {}.getType();
        try {
            historyItem = gson.fromJson(json,type);
        } catch (IllegalStateException | JsonSyntaxException exception){
            exception.printStackTrace();
        }

         try {
            historyItem.getComment();
         } catch (NullPointerException e){
            e.printStackTrace();

        }
        return historyItem;
      /*  else {
            historyItem.setComment(PREF_KEY_EMPTY_COMMENT);
            return historyItem;
        }   */

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
