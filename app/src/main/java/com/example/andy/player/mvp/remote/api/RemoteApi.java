package com.example.andy.player.mvp.remote.api;

import com.example.andy.player.base.AbstractResultUtil;
import com.example.andy.player.base.HotSongResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by andy on 2017/12/14.
 */

public interface RemoteApi {

    @POST("213-4/")
    @FormUrlEncoded
    Observable<AbstractResultUtil<HotSongResult>> getHotList(@Field("showapi_appid") int appid,
                                                             @Field("showapi_sign")String sign,
                                                             @Field("showapi_timestamp")long timestamp,
                                                             @Field("topid") int topid);

}
