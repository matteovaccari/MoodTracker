package com.matt.android.moodtracker_v2.workers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;


import java.util.ArrayList;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SaveMoodWorker extends Worker {

    int lastDayMood;
    public static ArrayList<Integer> moodList = new ArrayList<>();
    private SharedPreferences mPreferences;
    public static final String PREF_KEY_CURRENT_SMILEY = "PREF_KEY_CURRENT_SMILEY";
    public static final String PREF_KEY = "PREF_KEY";


    public SaveMoodWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Worker.Result doWork() {
        saveMoodFromPrefs();
        putMoodOnList();
        return Result.SUCCESS;

    }

    public void saveMoodFromPrefs() {
        Context context = getApplicationContext();
        mPreferences = getApplicationContext().getSharedPreferences(PREF_KEY,0);
        lastDayMood = mPreferences.getInt(PREF_KEY_CURRENT_SMILEY,15);
    }
    public void putMoodOnList() {
        moodList.add(lastDayMood);
    }
}
