package com.example.andy.player.mvp.base;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.andy.player.base.BaseActivity;


/**
 * Created by Administrator on 2017-7-17.
 */

public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity implements IView<P> {

    public P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attach(MvpActivity.this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detach();
        }
        super.onDestroy();
    }
}
