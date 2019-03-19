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
import com.matt.android.moodtracker_v2.storage.MySharedPreferences;

import java.util.Calendar;
import java.util.Date;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class MainActivity extends AppCompatActivity {

    public String comment;
    public static int currentSmileyPosition;
    VerticalViewPager verticalViewPager;
    private ImageButton historyButton;
    private ImageButton shareButton;
    private ImageButton commentButton;
    CustomSwipeAdapter adapter;
    private MySharedPreferences mPreferences;
    public static Mood sadMood;
    public static Mood disappointedMood;
    public static Mood normalMood;
    public static Mood happyMood;
    public static Mood superHappyMood;

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

            //Set default position when launching app to Happy Smiley (3)
        verticalViewPager.setCurrentItem(3);

        sadMood = new Mood("Sad", 0);
        disappointedMood = new Mood("Dissapointed",1);
        normalMood = new Mood("Normal", 2);
        happyMood = new Mood ("Happy",3);
        superHappyMood = new Mood ("Super Happy", 4);

        mPreferences = new MySharedPreferences(getApplicationContext());

            //Listener to get informed when user switch between smileys
        verticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                currentSmileyPosition = position;
          // Toast.makeText(MainActivity.this, "position: "+position, Toast.LENGTH_SHORT).show();  //Display currentPos, can be removed
                changeBackGround();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //Launch MoodHistoryActivity when history button is clicked
            historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivityIntent = new Intent(MainActivity.this,MoodHistoryActivity.class);
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
                   Intent shareIntent = new Intent (Intent.ACTION_SEND);
                   shareIntent.setType("text/plain");
                   Date today = Calendar.getInstance().getTime();
                   String shareBody = "I'm feeling " + mPreferences.getMoodNameForSharing(currentSmileyPosition) + " today!";
                   shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                   shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                   startActivity(Intent.createChooser(shareIntent, "Share via"));
               }
           });

    }
        //Method who change background color and add little music note
    void changeBackGround() {
        ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout_id);

        switch (currentSmileyPosition) {

            case 0:
                constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, HistoryItem.sadSmileyBackground));
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.pop);
                mp.start();
                break;

            case 1:
               constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, HistoryItem.disappointedSmileyBackground));
                MediaPlayer mp2 = MediaPlayer.create(getApplicationContext(), R.raw.pop);
                mp2.start();
                break;

            case 2:
                constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, HistoryItem.normalSmileyBackground));
                MediaPlayer mp3 = MediaPlayer.create(getApplicationContext(), R.raw.pop);
                mp3.start();
                break;

            case 3:
                constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this,  HistoryItem.happySmileyBackground));
                MediaPlayer mp4 = MediaPlayer.create(getApplicationContext(), R.raw.pop);
                mp4.start();
                break;

            case 4:
                constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, HistoryItem.superHappySmileyBackground));
                MediaPlayer mp5 = MediaPlayer.create(getApplicationContext(), R.raw.pop);
                mp5.start();
                break;
        }

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
                mPreferences.saveComment(today,comment);
                Toast.makeText(MainActivity.this,"Comment saved",Toast.LENGTH_SHORT).show();
            }
        });
        addcomment.setNegativeButton("Cancel",null);
        addcomment.create();
        addcomment.show();
    }


}
