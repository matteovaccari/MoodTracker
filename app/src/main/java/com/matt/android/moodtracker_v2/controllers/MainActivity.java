package com.matt.android.moodtracker_v2.controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.matt.android.moodtracker_v2.R;
import com.matt.android.moodtracker_v2.adapters.CustomSwipeAdapter;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class MainActivity extends AppCompatActivity {

    public static String comment;
    public int currentSmileyPosition;
    VerticalViewPager verticalViewPager;
    private Button historyButton;
    private Button commentButton;
    CustomSwipeAdapter adapter;
    private SharedPreferences mPreferences;
    public static final String PREF_KEY_CURRENT_SMILEY = "PREF_KEY_CURRENT_SMILEY";
    public static final String PREF_KEY_CURRENT_SMILEY_STATIC = "PREF_KEY_CURRENT_SMILEY_STATIC";
    public static final String PREF_KEY = "PREF_KEY";

    @SuppressLint("ClickableViewAccessibility") //OnTouchListener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historyButton = (Button) findViewById(R.id.main_activity_history_button);
        commentButton = (Button) findViewById(R.id.main_activity_comment_button);
        verticalViewPager = (VerticalViewPager) findViewById(R.id.verticalviewpager);

            //Instanciate adapter then set it to verticalViewPager adapter attribute
        adapter = new CustomSwipeAdapter(this);
        verticalViewPager.setAdapter(adapter);

            //Set default position when launching app to Happy Smiley (3)
        verticalViewPager.setCurrentItem(3);

        mPreferences = getApplicationContext().getSharedPreferences(PREF_KEY,0); //0 for private mode

            //Listener to get informed when user switch between smileys
       verticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                currentSmileyPosition=position;
                mPreferences.edit().putInt(PREF_KEY_CURRENT_SMILEY_STATIC,currentSmileyPosition);
                Toast.makeText(MainActivity.this, "position: "+position, Toast.LENGTH_SHORT).show();  //Display currentPos, can be removed
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

           commentButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  saveComment(MainActivity.this);
               }
           });

    }
        //Method who change background color and add little music note
    void changeBackGround() {
        ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout_id);

        switch (currentSmileyPosition) {

            case 0:
                constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.faded_red));
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.pop);
                mp.start();
                break;

            case 1:
               constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.warm_grey));
                MediaPlayer mp2 = MediaPlayer.create(getApplicationContext(), R.raw.pop);
                mp2.start();
                break;

            case 2:
                constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.cornflower_blue_65));
                MediaPlayer mp3 = MediaPlayer.create(getApplicationContext(), R.raw.pop);
                mp3.start();
                break;

            case 3:
                constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.light_sage));
                MediaPlayer mp4 = MediaPlayer.create(getApplicationContext(), R.raw.pop);
                mp4.start();
                break;

            case 4:
                constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.banana_yellow));
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
                    //Save input into comment(String)
                comment = String.valueOf(inputComment.getText());
                Toast.makeText(MainActivity.this,"Comment saved",Toast.LENGTH_SHORT).show();
            }
        });
        addcomment.setNegativeButton("Cancel",null);
        addcomment.create();
        addcomment.show();
    }

}
