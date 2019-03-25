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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.matt.android.moodtracker_v2.R;
import com.matt.android.moodtracker_v2.adapters.CustomSwipeAdapter;
import com.matt.android.moodtracker_v2.models.HistoryItem;
import com.matt.android.moodtracker_v2.models.Mood;
import com.matt.android.moodtracker_v2.models.MoodEnum;
import com.matt.android.moodtracker_v2.storage.MySharedPreferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private MySharedPreferences mPreferences;
    private Mood todayMood;
    private Mood todayMoodForViewPager;
    public static Mood sadMood;
    public static Mood disappointedMood;
    public static Mood normalMood;
    public static Mood happyMood;
    public static Mood superHappyMood;
    public static ArrayList<Mood> moodList = new ArrayList<>();
    private Integer[] backGroundColors = {R.color.faded_red, R.color.warm_grey, R.color.cornflower_blue_65, R.color.light_sage, R.color.banana_yellow};
    private Integer[] smileysImages = {R.drawable.smiley_sad, R.drawable.smiley_disappointed, R.drawable.smiley_normal, R.drawable.smiley_happy, R.drawable.smiley_super_happy};

    @SuppressLint("ClickableViewAccessibility") //OnTouchListener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historyButton = (ImageButton) findViewById(R.id.main_activity_history_button);
        commentButton = (ImageButton) findViewById(R.id.main_activity_comment_button);
        shareButton = (ImageButton) findViewById(R.id.main_activity_share_button);
        verticalViewPager = (VerticalViewPager) findViewById(R.id.verticalviewpager);

        //Instanciate adapter then set it to verticalViewPager adapter attribute
        adapter = new CustomSwipeAdapter(this);
        verticalViewPager.setAdapter(adapter);

        createMoods();

        mPreferences = new MySharedPreferences(getApplicationContext());
        Date today = Calendar.getInstance().getTime();
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
                saveMood(currentSmileyPosition);
                mPreferences.saveLastPositionForViewPager(currentSmileyPosition);
                Log.e("TAG",mPreferences.getHistoryItem(today).getComment());
                // Toast.makeText(MainActivity.this, "position: "+position, Toast.LENGTH_SHORT).show();  //Display currentPos, can be removed
                changeBackground(todayMood);
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

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                Date today = Calendar.getInstance().getTime();
                String shareBody = "I'm feeling " + mPreferences.getMoodNameForSharing(currentSmileyPosition) + " today!";
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

    }

    public void saveComment(Context ctx) {
        //Input message
        final EditText inputComment = new EditText(ctx);

        AlertDialog.Builder addcomment = new AlertDialog.Builder(MainActivity.this);
        addcomment.setTitle("Add comment");
        addcomment.setMessage("Please enter your feelings");
        addcomment.setView(inputComment);
        addcomment.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Date today = Calendar.getInstance().getTime();
                //Save input into comment(String)
                comment = String.valueOf(inputComment.getText());
                //Save comment into prefs with date as key
               // mPreferences.saveComment(today, comment);
                mPreferences.getHistoryItem(today).setComment(comment);
                Log.e("TAG",comment);
                Toast.makeText(MainActivity.this, "Comment saved", Toast.LENGTH_SHORT).show();
            }
        });
        addcomment.setNegativeButton("Cancel", null);
        addcomment.create();
        addcomment.show();
    }

    //Take currentSmileyPosition, convert it to corresponding mood then save it in prefs
    public void saveMood(int currentSmileyPosition) {

        Date today = Calendar.getInstance().getTime();

        switch (currentSmileyPosition) {
            case 0:
                todayMood = sadMood;
                break;
            case 1:
                todayMood = disappointedMood;
                break;
            case 2:
                todayMood = normalMood;
                break;
            case 3:
                todayMood = happyMood;
                break;
            case 4:
                todayMood = superHappyMood;
                break;
        }
        //Put todayMood in prefs
       // mPreferences.saveMood(today, todayMood);
        mPreferences.saveHistoryItem(today, todayMood);
    }

    public void setDefaultMood() {
        Date today = Calendar.getInstance().getTime();
        //Set default position when launching app to HappySmiley (3), or last mood registered

        if (mPreferences.getHistoryItem(today).getMood() == null) {

            verticalViewPager.setCurrentItem(3);
            todayMood = happyMood;
            mPreferences.saveHistoryItem(today, todayMood);
        } else {
            //Get last smiley + last background color combo and show it even is app was closed.
            verticalViewPager.setCurrentItem(mPreferences.getLastPositionForViewPager());
            ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout_id);
            constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this,moodList.get(mPreferences.getLastPositionForViewPager()).getBackgroundColor()));
        }
    }

    public void changeBackground(Mood mood) {
        ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout_id);

        constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, mood.getBackgroundColor()));
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.pop);
        mp.start();
    }

    public void createMoods() {
        moodList.add(sadMood = new Mood(MoodEnum.Sad, 0, backGroundColors[0], smileysImages[0]));
        moodList.add(disappointedMood = new Mood(MoodEnum.Disappointed, 1, backGroundColors[1], smileysImages[1]));
        moodList.add(normalMood = new Mood(MoodEnum.Normal, 2, backGroundColors[2], smileysImages[2]));
        moodList.add(happyMood = new Mood(MoodEnum.Happy, 3, backGroundColors[3], smileysImages[3]));
        moodList.add(superHappyMood = new Mood(MoodEnum.SuperHappy, 4, backGroundColors[4], smileysImages[4]));
    }

}


