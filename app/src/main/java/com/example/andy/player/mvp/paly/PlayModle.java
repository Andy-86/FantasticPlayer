package com.example.andy.player.mvp.paly;

import com.example.andy.player.aidl.SongBean;
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

}
