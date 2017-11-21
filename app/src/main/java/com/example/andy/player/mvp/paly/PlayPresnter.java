package com.example.andy.player.mvp.paly;

import com.example.andy.player.MainActivity;
import com.example.andy.player.aidl.SongBean;
import com.example.andy.player.mvp.base.BasePresenter;
import com.example.andy.player.tools.LogUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by andy on 2017/9/27.
 */

public class PlayPresnter extends BasePresenter<MainActivity,PlayModle> {
    @Override
    public PlayModle createModel() {
        return new PlayModle(this);
    }

    public void getSongs(){
        Observer<SongBean> observer=new Observer<SongBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(SongBean value) {
                LogUtil.doLog("onNext",value.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        mModel.getSongs(observer);
    }
}
