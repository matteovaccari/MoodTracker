/*
 *
 *   Created by Matteo VACCARI - 2019
 *
 *   https://github.com/matteovaccari/MoodTracker
 *
 */

package com.matt.android.moodtracker_v2.controllers;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.matt.android.moodtracker_v2.R;
import com.matt.android.moodtracker_v2.models.HistoryItem;
import com.matt.android.moodtracker_v2.storage.SharedPreferencesManager;

import java.util.Calendar;
import java.util.Date;

import static com.matt.android.moodtracker_v2.storage.Constants.PREF_KEY_EMPTY_COMMENT;

public class MoodHistoryActivity extends AppCompatActivity {

    private SharedPreferencesManager mPreferences;
    private HistoryItem todayHistoryItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);

        mPreferences = new SharedPreferencesManager(getApplicationContext());

        //Each layout is a bar for each day
        RelativeLayout mDayOne = findViewById(R.id.activity_historic_day_one);
        RelativeLayout mDayTwo = findViewById(R.id.activity_historic_day_two);
        RelativeLayout mDayThree = findViewById(R.id.activity_historic_day_three);
        RelativeLayout mDayFour = findViewById(R.id.activity_historic_day_four);
        RelativeLayout mDayFive = findViewById(R.id.activity_historic_day_five);
        RelativeLayout mDaySix = findViewById(R.id.activity_historic_day_six);
        RelativeLayout mDaySeven = findViewById(R.id.activity_historic_day_seven);

        //Buttons for displaying comments associated
        ImageButton mButtonOne = findViewById(R.id.activity_historic_btn_one);
        ImageButton mButtonTwo = findViewById(R.id.activity_historic_btn_two);
        ImageButton mButtonThree = findViewById(R.id.activity_historic_btn_three);
        ImageButton mButtonFour = findViewById(R.id.activity_historic_btn_four);
        ImageButton mButtonFive = findViewById(R.id.activity_historic_btn_five);
        ImageButton mButtonSix = findViewById(R.id.activity_historic_btn_six);
        ImageButton mButtonSeven = findViewById(R.id.activity_historic_btn_seven);

        //Each textview is a "bar" title for each day (yesterday, 3 days ago, etc)
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

        //Tabs for last 7 days + comments
        RelativeLayout[] layouts = {mDayOne, mDayTwo, mDayThree, mDayFour, mDayFive, mDaySix, mDaySeven};
        ImageButton[] buttons = {mButtonOne, mButtonTwo, mButtonThree, mButtonFour, mButtonFive, mButtonSix, mButtonSeven};

        //Pick up today's HistoryItem via preferences
        Date today = Calendar.getInstance().getTime();
        todayHistoryItem = mPreferences.getHistoryItem(today);

        // Loop to display last 7 moods
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_WEEK, -1); //Subtract one day from calendar (yesterday)
            this.displayMood(calendar.getTime(), layouts[i]);
            this.displayComment(calendar.getTime(), buttons[i]);
        }
    }

    public void displayMood(Date date, RelativeLayout relativeLayout) {
        // Change the width of the layout
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        try {
            display.getRealSize(size);
        } catch (NoSuchMethodError err) {
            display.getSize(size);
        }
        int width = size.x;

        //Get a HistoryItem from prefs to be displayed
        todayHistoryItem = mPreferences.getHistoryItem(date);

        if (todayHistoryItem == null || todayHistoryItem.getMood() == null) {
            relativeLayout.setBackgroundColor(0);
        } else {
            // Set background color and fraction for each mood case
            switch (todayHistoryItem.getMood()) {
                case Sad:
                    relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(width / 5,
                            LinearLayout.LayoutParams.MATCH_PARENT, 1));
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.faded_red));
                    break;
                case Disappointed:
                    relativeLayout.setLayoutParams(new LinearLayout.LayoutParams((width / 5) * 2,
                            LinearLayout.LayoutParams.MATCH_PARENT, 1));
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.warm_grey));
                    break;
                case Normal:
                    relativeLayout.setLayoutParams(new LinearLayout.LayoutParams((width / 5) * 3,
                            LinearLayout.LayoutParams.MATCH_PARENT, 1));
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.cornflower_blue_65));
                    break;
                case Happy:
                    relativeLayout.setLayoutParams(new LinearLayout.LayoutParams((width / 5) * 4,
                            LinearLayout.LayoutParams.MATCH_PARENT, 1));
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.light_sage));
                    break;
                case SuperHappy:
                    relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(width,
                            LinearLayout.LayoutParams.MATCH_PARENT, 1));
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.banana_yellow));
                    break;
                default:
                    relativeLayout.setBackgroundColor(0);
                    break;
            }
        }
    }

    public void displayComment(Date date, ImageButton button) {
        final String comment = getEmptyOrNotComment(date);

        //If there's a comment, show comment button + can display message with Toast message
        if (comment.equalsIgnoreCase(PREF_KEY_EMPTY_COMMENT)) {
            button.setVisibility(View.INVISIBLE);
            //Else, button stay invisible

        } else {
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), comment, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //This is for preventing NullPointerException is comment is empty : PREF_KEY_EMPTY_COMMENT is replacing null
    public String getEmptyOrNotComment(Date date) {

        try {
            mPreferences.getHistoryItem(date).getComment();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return PREF_KEY_EMPTY_COMMENT;
        }

        return mPreferences.getHistoryItem(date).getComment();

    }

}
