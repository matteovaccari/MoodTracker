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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);

        currentSmileyTextView = (TextView) findViewById(R.id.currentsmiley_ID);

        mPreferences = getPreferences(MODE_PRIVATE);

       currentSmileyInt = mPreferences.getInt(PREF_KEY_CURRENT_SMILEY,-50);
        Log.d("Keyname: ", String.valueOf(currentSmileyInt));
        currentSmileyString = Integer.toString(currentSmileyInt);
        currentSmileyTextView.setText(currentSmileyString);
    }
}
