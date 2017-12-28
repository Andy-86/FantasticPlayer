package com.example.andy.player.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.andy.player.mvp.local.LocalMusicFragment;
import com.example.andy.player.mvp.remote.RemoteMusicFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 2017/12/1.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private FragmentManager fManager;
    List<Fragment> list;
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        list=new ArrayList<>();
        this.fManager = fm;
        list.add(new LocalMusicFragment());
        list.add(new RemoteMusicFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        this.fManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = list.get(position);
        fManager.beginTransaction().hide(fragment).commit();
    }
}
