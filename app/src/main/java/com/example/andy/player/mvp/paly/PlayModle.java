package com.example.andy.player.mvp.paly;

import com.example.andy.player.aidl.SongBean;
import com.example.andy.player.bean.AbstractResultUtil;
import com.example.andy.player.bean.Lyric;
import com.example.andy.player.contract.Contract;
import com.example.andy.player.mvp.base.BaseModel;
import com.example.andy.player.mvp.paly.Api.PlayService;
import com.example.andy.player.tools.RetrofitUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by andy on 2017/9/27.
 */

public class PlayModle extends BaseModel<PlayPresnter>{
    public PlayModle(PlayPresnter presenter) {
        super(presenter);
    }
    public  void getSongs(Observer<SongBean> observer) {
        Retrofit client= RetrofitUtil.getRetrofit();
        PlayService service=client.create(PlayService.class);
        service.getSongs()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void toGetTheLyrics(Observer<AbstractResultUtil<Lyric>> observer, int musicid){
        Retrofit retrofit= RetrofitUtil.getMusicRetrofit();
        PlayService api=retrofit.create(PlayService.class);
        api.getTheLyrics(Contract.appid,Contract.sign,System.currentTimeMillis(),musicid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

}
