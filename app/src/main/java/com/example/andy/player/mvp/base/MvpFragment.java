package com.example.andy.player.mvp.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.example.andy.player.bean.BaseFragment;


/**
 * Created by Administrator on 2017-7-17.
 */

public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment implements IView<P> {

    public P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attach(MvpFragment.this);
        }
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detach();
        }
        super.onDestroy();
    }

    Snackbar mSnackBar;

    public void showMessage(String message) {
        if (mSnackBar == null) {
            mSnackBar = Snackbar.make(this.getView(), message,
                    Snackbar.LENGTH_SHORT);
        } else {
            mSnackBar.setText(message);
        }
        mSnackBar.show();
    }

}
