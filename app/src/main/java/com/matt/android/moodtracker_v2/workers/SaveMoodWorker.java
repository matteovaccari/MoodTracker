package com.matt.android.moodtracker_v2.workers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.matt.android.moodtracker_v2.controllers.MainActivity;
import com.matt.android.moodtracker_v2.models.Mood;
import com.matt.android.moodtracker_v2.models.MySharedPreferences;

import java.util.Calendar;
import java.util.Date;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

    public class SaveMoodWorker extends Worker {

        private MySharedPreferences mPreferences;
        private int todayMood;
        private Mood finalTodayMood;

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
            mPreferences = new MySharedPreferences(getApplicationContext());

           Date today = Calendar.getInstance().getTime();
            //Get Mood from today
            todayMood = MainActivity.currentSmileyPosition;
            //Save last mood into finalTodayMood
                switch (todayMood) {
                    case 0:
                        finalTodayMood = Mood.Sad;
                        break;
                    case 1:
                        finalTodayMood = Mood.Disappointed;
                        break;
                    case 2:
                        finalTodayMood = Mood.Normal;
                        break;
                    case 3:
                        finalTodayMood = Mood.Happy;
                        break;
                    case 4:
                        finalTodayMood = Mood.SuperHappy;
                        break;
                }
                //Put finalTodayMood in prefs
            mPreferences.saveMood(today, finalTodayMood);

        }

    }



