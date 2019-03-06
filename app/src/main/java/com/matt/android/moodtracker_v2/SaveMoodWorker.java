package com.matt.android.moodtracker_v2;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;

public class SaveMoodWorker extends Worker {
    @NonNull

        //Worker class with doWork overrided method
    @Override
    public Worker.Result doWork() {
        Context context = getApplicationContext();
                //Do Work
        return Result.SUCCESS;

    }
}
