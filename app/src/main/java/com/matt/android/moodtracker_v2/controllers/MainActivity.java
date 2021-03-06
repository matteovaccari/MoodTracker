/*
 *
 *   Created by Matteo VACCARI - 2019
 *
 *   https://github.com/matteovaccari/MoodTracker
 *
 */

package com.matt.android.moodtracker_v2.controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.matt.android.moodtracker_v2.R;
import com.matt.android.moodtracker_v2.adapters.CustomSwipeAdapter;
import com.matt.android.moodtracker_v2.models.HistoryItem;
import com.matt.android.moodtracker_v2.models.Mood;
import com.matt.android.moodtracker_v2.models.MoodEnum;
import com.matt.android.moodtracker_v2.storage.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

import static com.matt.android.moodtracker_v2.storage.Constants.PREF_KEY_EMPTY_COMMENT;

public class MainActivity extends AppCompatActivity {

    public String comment;
    public static int currentSmileyPosition;
    VerticalViewPager verticalViewPager;
    private ImageButton historyButton;
    private ImageButton shareButton;
    private ImageButton commentButton;
    CustomSwipeAdapter adapter;
    private SharedPreferencesManager mPreferences;
    private MoodEnum todayMoodEnum;
    private Mood todayMood;
    public static Mood sadMood;
    public static Mood disappointedMood;
    public static Mood normalMood;
    public static Mood happyMood;
    public static Mood superHappyMood;
    public Boolean isDefaultMoodPresent = false;
    public static ArrayList<Mood> moodList = new ArrayList<>();
    private HistoryItem todayHistoryItem;
    private Integer[] backGroundColors = {R.color.faded_red, R.color.warm_grey, R.color.cornflower_blue_65, R.color.light_sage, R.color.banana_yellow};

    @SuppressLint("ClickableViewAccessibility") //OnTouchListener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historyButton = (ImageButton) findViewById(R.id.main_activity_history_button);
        commentButton = (ImageButton) findViewById(R.id.main_activity_comment_button);
        shareButton = (ImageButton) findViewById(R.id.main_activity_share_button);
        verticalViewPager = (VerticalViewPager) findViewById(R.id.verticalviewpager);

        //Instantiate adapter then set it to verticalViewPager adapter attribute
        adapter = new CustomSwipeAdapter(this);
        verticalViewPager.setAdapter(adapter);

        //Instantiate different moods and add them to moodList
        createMoods();

        mPreferences = new SharedPreferencesManager(getApplicationContext());

        setDefaultMood();

        //Listener to get informed when user switch between smileys
        verticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Date today = Calendar.getInstance().getTime();
                currentSmileyPosition = position;
                //setMood() set todayMoodEnum and todayMood variables to corresponding values via currentSmileyPos,
                //todayMood is for changeBackground who take a Mood as argument
                //todayMoodEnum is for saving a HistoryItem (Date, MOODENUM, comment)
                setMood(currentSmileyPosition);
                //This is for retrieve last Smiley pos at each run and show it
                mPreferences.saveLastPositionForViewPager(currentSmileyPosition);
                changeBackground(todayMood);
                updateTodayMood(today, setMood(currentSmileyPosition), mPreferences.getComment(today));
                mPreferences.saveHistoryItem(today, todayHistoryItem);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //Launch MoodHistoryActivity when history button is clicked
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivityIntent = new Intent(MainActivity.this, MoodHistoryActivity.class);
                startActivity(historyActivityIntent);
            }
        });
        //Call saveComment method when comment button is clicked
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveComment(MainActivity.this);
            }
        });
        //Display share interface
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = mPreferences.getMoodNameForSharing(currentSmileyPosition);
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

    }

    public void saveComment(Context ctx) {
        //Input message
        final EditText inputComment = new EditText(ctx);

        AlertDialog.Builder addcoment = new AlertDialog.Builder(MainActivity.this);
        addcoment.setTitle("Add comment");
        addcoment.setMessage("Please enter your feelings");
        addcoment.setView(inputComment);
        addcoment.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Date today = Calendar.getInstance().getTime();
                //Save input into comment(String)
                comment = String.valueOf(inputComment.getText());
                //Save comment into HistoryItem comment attribute with date as key
                mPreferences.saveComment(today, comment);
                updateTodayMood(today, setMood(currentSmileyPosition), comment);
                Toast.makeText(MainActivity.this, "Comment saved", Toast.LENGTH_SHORT).show();
            }
        });
        addcoment.setNegativeButton("Cancel", null);
        addcoment.create();
        addcoment.show();
    }

    //Take currentSmileyPosition, convert it to corresponding moodEnum then return it
    public MoodEnum setMood(int currentSmileyPosition) {

        Date today = Calendar.getInstance().getTime();

        switch (currentSmileyPosition) {
            case 0:
                todayMoodEnum = MoodEnum.Sad;
                todayMood = sadMood;
                break;
            case 1:
                todayMoodEnum = MoodEnum.Disappointed;
                todayMood = disappointedMood;
                break;
            case 2:
                todayMoodEnum = MoodEnum.Normal;
                todayMood = normalMood;
                break;
            case 3:
                todayMoodEnum = MoodEnum.Happy;
                todayMood = happyMood;
                break;
            case 4:
                todayMoodEnum = MoodEnum.SuperHappy;
                todayMood = superHappyMood;
                break;
        }
        return todayMoodEnum;
    }

    //Set default position when launching app
    public void setDefaultMood() {

        Date today = Calendar.getInstance().getTime();

        //If it's the first app run, we set default position to Happy (3)
        if (!mPreferences.getIsDefaultMoodPresent()) {

            verticalViewPager.setCurrentItem(3);
            mPreferences.saveLastPositionForViewPager(3);
            //TodayMood (Mood) is for changeBackground method
            todayMood = happyMood;
            //TodayMoodEnum (MoodEnum) is for HistoryItem mood attribute
            todayMoodEnum = MoodEnum.Happy;
            //Instantiate + save first run HistoryItem with default Mood and empty comment
            todayHistoryItem = new HistoryItem(today, todayMoodEnum, PREF_KEY_EMPTY_COMMENT);
            mPreferences.saveHistoryItem(today, todayHistoryItem);
            //Boolean that know if it's first run for app or not
            isDefaultMoodPresent = true;
            mPreferences.saveIsDefaultMoodPresent(isDefaultMoodPresent);

            //Else we set last mood position after run
        } else {
            //Instanciate todayHistoryItem with yesterday's information
            todayHistoryItem = new HistoryItem(today, setMood(mPreferences.getLastPositionForViewPager()), mPreferences.getComment(today));
            //Get last smiley + last background color combo and show it even is app was closed.
            verticalViewPager.setCurrentItem(mPreferences.getLastPositionForViewPager());
            ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout_id);
            constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, moodList.get(mPreferences.getLastPositionForViewPager()).getBackgroundColor()));
        }
    }

    //Set background color depending on each Mood BackgroundColor attribute, then play a little "pop" sound at each slide
    public void changeBackground(Mood mood) {
        ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout_id);

        constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, mood.getBackgroundColor()));
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.pop);
        mp.start();
    }

    //Instantiate different Mood and put them in moodList
    public void createMoods() {
        moodList.add(sadMood = new Mood(MoodEnum.Sad, backGroundColors[0]));
        moodList.add(disappointedMood = new Mood(MoodEnum.Disappointed, backGroundColors[1]));
        moodList.add(normalMood = new Mood(MoodEnum.Normal, backGroundColors[2]));
        moodList.add(happyMood = new Mood(MoodEnum.Happy, backGroundColors[3]));
        moodList.add(superHappyMood = new Mood(MoodEnum.SuperHappy, backGroundColors[4]));
    }

    //This method is called at each slide
    public void updateTodayMood(Date date, MoodEnum moodEnum, String comment) {
        todayHistoryItem.setDate(date);
        todayHistoryItem.setMood(moodEnum);
        todayHistoryItem.setComment(comment);
    }
}


