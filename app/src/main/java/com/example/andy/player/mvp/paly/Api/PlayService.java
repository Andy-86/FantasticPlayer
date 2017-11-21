package com.example.andy.player.mvp.paly.Api;

import com.example.andy.player.aidl.SongBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by andy on 2017/9/27.
 */

public interface PlayService {
    @GET("/songs")
    public Observable<SongBean> getSongs();
}
