package com.example.andy.player.mvp.comment.api;

import com.example.andy.player.bean.Comment;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by andy on 2018/1/3.
 */

public interface CommentServer {
    @POST("/comment/add")
    @FormUrlEncoded
    public Observable<ResponseBody> add(@Header("token") String token,@Field("songid") int songId,@Field("content") String content);

    @POST("/comment/findbysongid")
    @FormUrlEncoded
    public Observable<List<Comment>> findbysongid(@Header("token") String token, @Field("songid") int songId);

    @POST("/comment/addlikebycommentid")
    @FormUrlEncoded
    public Observable<ResponseBody> addlikebycommentid(@Header("token") String token, @Field("commentid") int commentid);

    @POST("/comment/deletelikebycommentid")
    @FormUrlEncoded
    public Observable<ResponseBody> deletelikebycommentid(@Header("token") String token, @Field("commentid") int commentid);
}
