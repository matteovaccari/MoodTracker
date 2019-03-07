package com.matt.android.moodtracker_v2.controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matt.android.moodtracker_v2.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MoodHistoryActivity extends AppCompatActivity {

    private int mood;
    private SharedPreferences mPreferences;
    public static final String PREF_KEY_CURRENT_SMILEY = "PREF_KEY_CURRENT_SMILEY";
    public static final String PREF_KEY = "PREF_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);

        mPreferences = getApplicationContext().getSharedPreferences(PREF_KEY, 0); //0 for private mode

        RelativeLayout mDayOne = findViewById(R.id.activity_historic_day_one);
        RelativeLayout mDayTwo = findViewById(R.id.activity_historic_day_two);
        RelativeLayout mDayThree = findViewById(R.id.activity_historic_day_three);
        RelativeLayout mDayFour = findViewById(R.id.activity_historic_day_four);
        RelativeLayout mDayFive = findViewById(R.id.activity_historic_day_five);
        RelativeLayout mDaySix = findViewById(R.id.activity_historic_day_six);
        RelativeLayout mDaySeven = findViewById(R.id.activity_historic_day_seven);

        Button mButtonOne = findViewById(R.id.activity_historic_btn_one);
        Button mButtonTwo = findViewById(R.id.activity_historic_btn_two);
        Button mButtonThree = findViewById(R.id.activity_historic_btn_three);
        Button mButtonFour = findViewById(R.id.activity_historic_btn_four);
        Button mButtonFive = findViewById(R.id.activity_historic_btn_five);
        Button mButtonSix = findViewById(R.id.activity_historic_btn_six);
        Button mButtonSeven = findViewById(R.id.activity_historic_btn_seven);

        TextView mTextViewOne = findViewById(R.id.activity_historic_text_one);
        TextView mTextViewTwo = findViewById(R.id.activity_historic_text_two);
        TextView mTextViewThree = findViewById(R.id.activity_historic_text_three);
        TextView mTextViewFour = findViewById(R.id.activity_historic_text_four);
        TextView mTextViewFive = findViewById(R.id.activity_historic_text_five);
        TextView mTextViewSix = findViewById(R.id.activity_historic_text_six);
        TextView mTextViewSeven = findViewById(R.id.activity_historic_text_seven);

        mTextViewOne.setText(getString(R.string.day_1));
        mTextViewTwo.setText(getString(R.string.day_2));
        mTextViewThree.setText(getString(R.string.day_3));
        mTextViewFour.setText(getString(R.string.day_4));
        mTextViewFive.setText(getString(R.string.day_5));
        mTextViewSix.setText(getString(R.string.day_6));
        mTextViewSeven.setText(getString(R.string.day_7));

        RelativeLayout[] layouts = {mDayOne, mDayTwo, mDayThree, mDayFour, mDayFive, mDaySix, mDaySeven};
        Button[] buttons = {mButtonOne, mButtonTwo, mButtonThree, mButtonFour, mButtonFive, mButtonSix, mButtonSeven};

        // Loop to display moods and comments in the seven past days
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
            this.displayMood(calendar.getTime(), layouts[i]);
           // this.displayComment(calendar.getTime(), buttons[i]);
        }

    }

    private void displayMood(Date date, RelativeLayout relativeLayout) {
        // Change the width of the layout
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        try {
            display.getRealSize(size);
        } catch (NoSuchMethodError err) {
            display.getSize(size);
        }
        int width = size.x;
        int height = size.y;

        //Get last Mood
        mood = mPreferences.getInt(PREF_KEY_CURRENT_SMILEY,-15);

        // If there is no mood, there is nothing to display
        if (mood == -15) {
            relativeLayout.setBackgroundColor(0);
        } else {
            // Display mood width and color
            switch (mood) {
                case 0:
                    relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(width / 5,
                            LinearLayout.LayoutParams.MATCH_PARENT, 1));
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.faded_red));
                    break;
                case 1:
                    relativeLayout.setLayoutParams(new LinearLayout.LayoutParams((width / 5) * 2,
                            LinearLayout.LayoutParams.MATCH_PARENT, 1));
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.warm_grey));
                    break;
                case 2:
                    relativeLayout.setLayoutParams(new LinearLayout.LayoutParams((width / 5) * 3,
                            LinearLayout.LayoutParams.MATCH_PARENT, 1));
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.cornflower_blue_65));
                    break;
                case 3:
                    relativeLayout.setLayoutParams(new LinearLayout.LayoutParams((width / 5) * 4,
                            LinearLayout.LayoutParams.MATCH_PARENT, 1));
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.light_sage));
                    break;
                case 4:
                    relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(width,
                            LinearLayout.LayoutParams.MATCH_PARENT, 1));
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.banana_yellow));
                    break;
            }
        }
    }
}
