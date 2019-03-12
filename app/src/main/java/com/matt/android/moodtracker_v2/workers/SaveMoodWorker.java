package com.matt.android.moodtracker_v2.workers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.matt.android.moodtracker_v2.controllers.MainActivity;
import com.matt.android.moodtracker_v2.controllers.MoodHistoryActivity;
import com.matt.android.moodtracker_v2.models.Mood;
import com.matt.android.moodtracker_v2.models.MySharedPreferences;

import java.util.Calendar;
import java.util.Date;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

    public class SaveMoodWorker extends Worker {

        private MySharedPreferences mPreferences;
        private Mood mood;

        public SaveMoodWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
        }

        @NonNull
        @Override
        public Worker.Result doWork() {
            saveMoodInPrefs();
            saveCommentInPrefs();
            return Result.SUCCESS;
        }

        public void saveMoodInPrefs() {
            Context context = getApplicationContext();
            mPreferences = new MySharedPreferences(getApplicationContext());

          /*  Date today = Calendar.getInstance().getTime();
            //Get Mood from today
            mood = mPreferences.getMood(today);
            //Put mood in prefs
            mPreferences.saveMood(today, mood); */

        }

        public void saveCommentInPrefs() {
            mPreferences = new MySharedPreferences(getApplicationContext());
            //Comment have to be in prefs to not be deleted by dead of act
            // mPreferences.saveComment(date)
            //  mPreferences.edit().putString(PREF_KEY_CURRENT_COMMENT,MainActivity.comment).apply();
        }

    }



