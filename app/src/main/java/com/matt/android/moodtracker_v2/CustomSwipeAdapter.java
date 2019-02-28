package com.matt.android.moodtracker_v2;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CustomSwipeAdapter extends PagerAdapter {

        //Int tab to store different smileys resources
    private int [] images_ressources = {R.drawable.smiley_sad,R.drawable.smiley_disappointed,
            R.drawable.smiley_normal,R.drawable.smiley_happy,R.drawable.smiley_super_happy};

    private Context ctx;
    private LayoutInflater layoutInflater;

        //Constructor taking context as parameter
    public CustomSwipeAdapter(Context ctx) {
        this.ctx = ctx;
    }

        //Method returning smiley tab length
    @Override
    public int getCount() {
        return images_ressources.length;
    }

        //Is a view created from a specified object ? (object is for exemple return value for instanciateItem)
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object) ;
    }

        //This method specify PageAdapter to create a view at a defined position then add this view to a Container
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container, false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.swipe_layout_imageView_ID);
        imageView.setImageResource(images_ressources[position]);
        container.addView(item_view);
        return item_view;
    }

        //This method delete a specified element from Container in parameter
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
