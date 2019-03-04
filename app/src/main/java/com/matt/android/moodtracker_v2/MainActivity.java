package com.matt.android.moodtracker_v2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class MainActivity extends AppCompatActivity {

    private int currentSmileyPosition;
    VerticalViewPager verticalViewPager;
    private Button historyButton;
    CustomSwipeAdapter adapter;
    private SharedPreferences mPreferences;
    public static final String PREF_KEY_CURRENT_SMILEY = "PREF_KEY_CURRENT_SMILEY";
    public static final String PREF_KEY = "PREF_KEY";


    @SuppressLint("ClickableViewAccessibility")
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

        mPreferences = getApplicationContext().getSharedPreferences(PREF_KEY,0); //0 for private mode

            //Listener to get informed when user switch between smileys
       verticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                changeImage();
                currentSmileyPosition=position;
                Toast.makeText(MainActivity.this, "position: "+position, Toast.LENGTH_SHORT).show();
                mPreferences.edit().putInt(PREF_KEY_CURRENT_SMILEY,currentSmileyPosition).apply(); //Storing position

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



    }

    void changeImage() {
        ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout_id);
        switch (currentSmileyPosition) {

            case 0:
                constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red));
                break;

            case 1:
               constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.gray));
                break;

            case 2:
                constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.blue));
                break;

            case 3:
                constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.green));
                break;

            case 4:
                constraintLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.yellow));
                break;
        }

    }

}
