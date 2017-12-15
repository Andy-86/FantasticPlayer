package com.example.andy.player.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 2017/12/1.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> list;
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        list=new ArrayList<>();
    }
    public void addFragment(Fragment fragment){
        list.add(fragment);
    }


    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
