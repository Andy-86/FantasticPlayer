package com.example.andy.player.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by andy on 2017/9/10.
 */

public class DisViewPageAdapter extends PagerAdapter{
    private List<View> mDiscLayouts;
    public DisViewPageAdapter(List<View> list) {
        mDiscLayouts=list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View discLayout = mDiscLayouts.get(position);
        container.addView(discLayout);
        return discLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mDiscLayouts.get(position));
    }

    @Override
    public int getCount() {
        return mDiscLayouts.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
