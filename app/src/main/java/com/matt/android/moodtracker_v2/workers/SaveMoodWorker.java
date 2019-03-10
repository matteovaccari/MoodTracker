package com.matt.android.moodtracker_v2.workers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.matt.android.moodtracker_v2.controllers.MainActivity;
import com.matt.android.moodtracker_v2.controllers.MoodHistoryActivity;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SaveMoodWorker extends Worker {

    private SharedPreferences mPreferences;
    public static final String PREF_KEY_CURRENT_SMILEY = "PREF_KEY_CURRENT_SMILEY";
    public static final String PREF_KEY_CURRENT_COMMENT = "PREF_KEY_CURRENT_COMMENT";
    public static final String PREF_KEY = "PREF_KEY";


    public SaveMoodWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Worker.Result doWork() {
        saveMoodInPrefs();
       // Toast.makeText(getApplicationContext(),"Current Mood Saved",Toast.LENGTH_SHORT).show();
        return Result.SUCCESS;
    }

    public void saveMoodInPrefs() {
        Context context = getApplicationContext();
        mPreferences = getApplicationContext().getSharedPreferences(PREF_KEY,0);
            //Take currentSmileyPos and comment (both static) and put them in SharedPrefs
        mPreferences.edit().putInt(PREF_KEY_CURRENT_SMILEY,MainActivity.currentSmileyPosition).apply();
        mPreferences.edit().putString(PREF_KEY_CURRENT_COMMENT,MainActivity.comment).apply();
    }
}



