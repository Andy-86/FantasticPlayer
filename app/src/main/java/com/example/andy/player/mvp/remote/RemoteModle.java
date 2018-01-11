package com.example.andy.player.mvp.remote;

import com.example.andy.player.bean.RecomendSongList;
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

    public void getSongList(Observer<RecomendSongList> observer, int type,int size,int offset){
        Retrofit retrofit= RetrofitUtil.getMusicRetrofit();
        RemoteApi api=retrofit.create(RemoteApi.class);
        api.getHotList("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)","json","","webapp_music","baidu.ting.billboard.billList",type,size,offset)
        .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

}
