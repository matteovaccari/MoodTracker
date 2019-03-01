package com.matt.android.moodtracker_v2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MoodHistoryActivity extends AppCompatActivity {

    private TextView currentSmileyTextView;
    private int currentSmileyInt;
    private String currentSmileyString;
    private SharedPreferences mPreferences;
    public static final String PREF_KEY_CURRENT_SMILEY = "PREF_KEY_CURRENT_SMILEY";
    public static final String PREF_KEY = "PREF_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);

        currentSmileyTextView = (TextView) findViewById(R.id.currentsmiley_ID);

        mPreferences = getApplicationContext().getSharedPreferences(PREF_KEY,0); //0 for private mode

            //Get position from prefs
       currentSmileyInt = mPreferences.getInt(PREF_KEY_CURRENT_SMILEY,-50);
        Log.d("Keyname: ", String.valueOf(currentSmileyInt));
            //Convert it from int to String
        currentSmileyString = Integer.toString(currentSmileyInt);
            //Display it
        currentSmileyTextView.setText(currentSmileyString);
    }
}
