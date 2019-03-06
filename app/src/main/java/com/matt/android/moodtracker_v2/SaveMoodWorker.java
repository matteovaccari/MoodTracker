package com.matt.android.moodtracker_v2;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SaveMoodWorker extends Worker {

    int lastDayMood;
    private SharedPreferences mPreferences;
    public static final String PREF_KEY_CURRENT_SMILEY = "PREF_KEY_CURRENT_SMILEY";
    public static final String PREF_KEY = "PREF_KEY";

    public SaveMoodWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Worker.Result doWork() {
        saveMoodAtMidnight();
        return Result.SUCCESS;

    }

    public void saveMoodAtMidnight() {
        Context context = getApplicationContext();
        mPreferences = getApplicationContext().getSharedPreferences(PREF_KEY,0);
        lastDayMood = mPreferences.getInt(PREF_KEY_CURRENT_SMILEY,15);
    }
}
