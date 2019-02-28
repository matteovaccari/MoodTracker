package com.matt.android.moodtracker_v2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    public static final String PREF_KEY_CURRENT_SMILEY ="PREF_KEY_CURRENT_SMILEY";
    private int currentSmiley;
    VerticalViewPager verticalViewPager;
    private Button historyButton;
    CustomSwipeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historyButton = (Button) findViewById(R.id.main_activity_history_button);
        verticalViewPager = (VerticalViewPager) findViewById(R.id.verticalviewpager);

            //Instanciate adapter then set it to verticalViewPager adapter attribute
        adapter = new CustomSwipeAdapter(this);
        verticalViewPager.setAdapter(adapter);

            //Set default position when launching app to Happy Smiley (3)
        verticalViewPager.setCurrentItem(3);

        mPreferences = getPreferences(MODE_PRIVATE);

        verticalViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               currentSmiley = verticalViewPager.getCurrentItem();
               mPreferences.edit().putInt(PREF_KEY_CURRENT_SMILEY,currentSmiley);
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivityIntent = new Intent(MainActivity.this,MoodHistoryActivity.class);
                startActivity(historyActivityIntent);
            }
        });
    }


}
