package com.example.andy.player.mvp.base;

/**
 * Created by Administrator on 2017-7-17.
 */

public interface IView<P> {
     P createPresenter();
     void showLoading();
     void dismissLoading();
}
