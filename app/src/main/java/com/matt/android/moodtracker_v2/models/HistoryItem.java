package com.matt.android.moodtracker_v2.models;

public class HistoryItem {
    private int smiley;
    private int backgroundColor;

    public HistoryItem(int smiley, int backgroundColor) {
        setBackgroundColor(backgroundColor);
        setSmiley(smiley);
    }

    public int getSmiley() {
        return smiley;
    }

    public void setSmiley(int smiley) {
        this.smiley = smiley;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
