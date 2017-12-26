package com.example.andy.player.mvp.paly.Api;

import com.example.andy.player.aidl.SongBean;
import com.example.andy.player.bean.AbstractResultUtil;
import com.example.andy.player.bean.Lyric;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by andy on 2017/9/27.
 */

public interface PlayService {
    @GET("/songs")
    public Observable<SongBean> getSongs();
    @POST("213-2/")
    @FormUrlEncoded
    Observable<AbstractResultUtil<Lyric>> getTheLyrics(@Field("showapi_appid") int appid,
                                                       @Field("showapi_sign")String sign,
                                                       @Field("showapi_timestamp")long timestamp,
                                                       @Field("musicid") int musicid);

}
