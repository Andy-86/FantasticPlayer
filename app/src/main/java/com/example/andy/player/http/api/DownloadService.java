package com.example.andy.player.http.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by andy on 2017/12/28.
 */

public interface DownloadService {
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
