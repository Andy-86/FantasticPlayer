package com.example.andy.player.mvp.remote;

import com.example.andy.player.bean.AbstractResultUtil;
import com.example.andy.player.bean.HotSongResult;
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
       mModel.getSongList(new Observer<AbstractResultUtil<HotSongResult>>() {
            @Override
            public void onSubscribe(Disposable d) {
                RetrofitUtil.addDisposable(d);
            }

            @Override
            public void onNext(AbstractResultUtil<HotSongResult> value) {
                if(value.getShowapi_res_body().getPagebean()!=null) {
                    LogUtil.doLog("onNext", "" + value.getShowapi_res_body().getPagebean().getSonglist().size());
                    mView.returnsonglist(tipid, value.getShowapi_res_body().getPagebean().getSonglist());
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        },tipid);
    }
}
