package com.example.andy.player.mvp.remote;

import com.example.andy.player.base.AbstractResultUtil;
import com.example.andy.player.base.HotSongResult;
import com.example.andy.player.mvp.base.BasePresenter;
import com.example.andy.player.tools.LogUtil;

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

    public void getTheMainlandHotSearchList(){
        mModel.getTheMainlandHotSongList(new Observer<AbstractResultUtil<HotSongResult>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(AbstractResultUtil<HotSongResult> value) {
                LogUtil.doLog("onNext",""+value.getShowapi_res_body().getPagebean());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
