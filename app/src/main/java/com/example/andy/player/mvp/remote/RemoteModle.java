package com.example.andy.player.mvp.remote;

import com.example.andy.player.bean.AbstractResultUtil;
import com.example.andy.player.bean.HotSongResult;
import com.example.andy.player.contract.Contract;
import com.example.andy.player.mvp.base.BaseModel;
import com.example.andy.player.mvp.remote.api.RemoteApi;
import com.example.andy.player.tools.RetrofitUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by andy on 2017/12/1.
 */

public class RemoteModle extends BaseModel<RemotePresenter>{
    public RemoteModle(RemotePresenter presenter) {
        super(presenter);
    }

    public void getSongList(Observer<AbstractResultUtil<HotSongResult>> observer,int tipid){
        Retrofit retrofit= RetrofitUtil.getMusicRetrofit();
        RemoteApi api=retrofit.create(RemoteApi.class);
        api.getHotList(Contract.appid,Contract.sign,System.currentTimeMillis(),tipid)
        .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

}
