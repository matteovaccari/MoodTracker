package com.matt.android.moodtracker_v2;

import android.support.v4.view.PagerAdapter;
import android.view.View;

public class CustomSwipeAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
