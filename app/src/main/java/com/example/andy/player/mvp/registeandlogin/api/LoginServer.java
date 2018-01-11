package com.example.andy.player.mvp.registeandlogin.api;

import com.example.andy.player.bean.LoginBean;
import com.example.andy.player.bean.RegisterBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by andy on 2017/12/31.
 */

public interface LoginServer {
    @POST("/api/user/add")
    @FormUrlEncoded
    public Observable<RegisterBean> regist(@Field("name") String name,@Field("password") String password);
    @POST("/api/authentication")
    @FormUrlEncoded
    public Observable<LoginBean> login(@Field("name") String name,@Field("password") String password);
}
