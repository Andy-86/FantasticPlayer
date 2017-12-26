package com.example.andy.player.mvp.search.api;

import com.example.andy.player.bean.AbstractResultUtil;
import com.example.andy.player.bean.SearchResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by andy on 2017/12/25.
 */

public interface SearchApi {
    @POST("213-1/")
    @FormUrlEncoded
    Observable<AbstractResultUtil<SearchResult>> searchResult(@Field("showapi_appid") int appid,
                                                            @Field("showapi_sign")String sign,
                                                            @Field("showapi_timestamp")long timestamp,
                                                            @Field("keyword") String keyword,
                                                            @Field("page") int page);
}
