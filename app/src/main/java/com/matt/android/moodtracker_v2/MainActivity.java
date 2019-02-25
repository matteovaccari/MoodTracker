package com.matt.android.moodtracker_v2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class MainActivity extends AppCompatActivity {
    VerticalViewPager verticalViewPager;
    CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verticalViewPager = (VerticalViewPager) findViewById(R.id.verticalviewpager);
        adapter = new CustomSwipeAdapter(this);
        verticalViewPager.setAdapter(adapter);
    }
}
