package com.example.andy.player.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.andy.player.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017-7-19.
 */

public abstract class BaseFragment extends Fragment {

    Unbinder mUnbinder;
    Toolbar mToolbar;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        init();
    }

    private void init() {
        initData();
        initEvent();
    }

    protected abstract void initEvent();

    protected abstract void initData();

    protected void initToolbar(String title) {
        if (mToolbar != null) {
            mToolbar.setTitle(title);
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        }
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
