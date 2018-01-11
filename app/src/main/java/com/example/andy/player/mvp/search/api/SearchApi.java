package com.example.andy.player.mvp.search.api;

import com.example.andy.player.bean.SearchBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by andy on 2017/12/25.
 */

public interface SearchApi {

    @GET("ting")
    Observable<SearchBean> searchResult(@Header("User-Agent")String cookie,
                                        @Query("format") String  format,
                                        @Query("calback") String  calback,
                                        @Query("from") String  from,
                                        @Query("method") String  method,
                                        @Query("query") String  query);
}
