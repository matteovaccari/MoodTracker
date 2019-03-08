package com.matt.android.moodtracker_v2.workers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.matt.android.moodtracker_v2.controllers.MainActivity;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SaveMoodWorker extends Worker {

    private SharedPreferences mPreferences;
    public static final String PREF_KEY_CURRENT_SMILEY = "PREF_KEY_CURRENT_SMILEY";
    public static final String PREF_KEY = "PREF_KEY";


    public SaveMoodWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Worker.Result doWork() {
        saveMoodInPrefs();
        return Result.SUCCESS;
    }

    public void saveMoodInPrefs() {
        Context context = getApplicationContext();
        mPreferences = getApplicationContext().getSharedPreferences(PREF_KEY,0);
        mPreferences.edit().putInt(PREF_KEY_CURRENT_SMILEY,MainActivity.currentSmileyPosition).apply();
    }
}



