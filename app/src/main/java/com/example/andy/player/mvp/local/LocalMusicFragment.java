package com.example.andy.player.mvp.local;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andy.player.R;
import com.example.andy.player.mvp.base.MvpFragment;

/**
 * Created by andy on 2017/12/1.
 */

public class LocalMusicFragment extends MvpFragment<LocalPresenter>{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_local_music,container,false);
        return view;
    }

    @Override
    public LocalPresenter createPresenter() {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }
}
