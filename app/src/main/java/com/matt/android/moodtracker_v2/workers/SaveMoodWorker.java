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
        private int temporaryPos;
        public static final String PREF_KEY_CURRENT_SMILEY = "PREF_KEY_CURRENT_SMILEY";
        public static final String PREF_KEY_CURRENT_SMILEY_STATIC = "PREF_KEY_CURRENT_SMILEY_STATIC";
        public static final String PREF_KEY_CURRENT_COMMENT = "PREF_KEY_CURRENT_COMMENT";
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
                //Take currentSmileyPos and comment (both static) and put them in SharedPrefs
            temporaryPos = mPreferences.getInt(PREF_KEY_CURRENT_SMILEY_STATIC,-50);
            mPreferences.edit().putInt(PREF_KEY_CURRENT_SMILEY,temporaryPos).apply();
            //Comment have to be in prefs to not be deleted by dead of act
            mPreferences.edit().putString(PREF_KEY_CURRENT_COMMENT,MainActivity.comment).apply();
        }
    }



