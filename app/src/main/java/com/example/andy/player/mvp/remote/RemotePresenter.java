package com.example.andy.player.mvp.remote;

import com.example.andy.player.bean.RecomendSongList;
import com.example.andy.player.mvp.base.BasePresenter;
import com.example.andy.player.tools.LogUtil;
import com.example.andy.player.tools.RetrofitUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by andy on 2017/12/1.
 */

public class RemotePresenter extends BasePresenter<RemoteMusicFragment,RemoteModle>{
    @Override
    public RemoteModle createModel() {
        return new RemoteModle(this);
    }

    public void getSonglit(final int tipid){
       mModel.getSongList(new Observer<RecomendSongList>() {
            @Override
            public void onSubscribe(Disposable d) {
                RetrofitUtil.addDisposable(d);
            }

            @Override
            public void onNext(RecomendSongList value) {
                if(value.getSong_list()!=null) {
                    LogUtil.doLog("onNext", "" + value.getSong_list().size());
                    mView.returnsonglist(tipid, value.getSong_list());
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        },tipid,20,0);
    }
}
