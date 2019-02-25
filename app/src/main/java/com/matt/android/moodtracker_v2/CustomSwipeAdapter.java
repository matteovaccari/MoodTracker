package com.matt.android.moodtracker_v2;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class CustomSwipeAdapter extends PagerAdapter {
    private int [] images_ressources = {R.drawable.smiley_sad,R.drawable.smiley_disappointed,
            R.drawable.smiley_normal,R.drawable.smiley_happy,R.drawable.smiley_super_happy};
    private Context ctx;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return images_ressources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container, false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.swipe_layout_imageView_ID);
        imageView.setImageResource(images_ressources[position]);
        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
