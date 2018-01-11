package com.example.andy.player.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.andy.player.bean.Lrc;
import com.example.andy.player.bean.PlayeBean;
import com.example.andy.player.mvp.remote.api.RemoteApi;
import com.example.andy.player.tools.RetrofitUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import retrofit2.Retrofit;

/**
 * Created by andy on 2017/12/28.
 */

public class HttpClient {
private static final String TAG="HttpClient";

    private static final String SPLASH_URL = "http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
    private static final String BASE_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting";
    private static final String METHOD_GET_MUSIC_LIST = "baidu.ting.billboard.billList";
    private static final String METHOD_DOWNLOAD_MUSIC = "baidu.ting.song.play";
    private static final String METHOD_ARTIST_INFO = "baidu.ting.artist.getInfo";
    private static final String METHOD_SEARCH_MUSIC = "baidu.ting.search.catalogSug";
    private static final String METHOD_LRC = "baidu.ting.song.lry";
    private static final String PARAM_METHOD = "method";
    private static final String PARAM_TYPE = "type";
    private static final String PARAM_SIZE = "size";
    private static final String PARAM_OFFSET = "offset";
    private static final String PARAM_SONG_ID = "songid";
    private static final String PARAM_TING_UID = "tinguid";
    private static final String PARAM_QUERY = "query";





    public static void getPlayerBean(int  songid, @NonNull final HttpCallback<PlayeBean> callback) {
        Retrofit retrofit= RetrofitUtil.getMusicRetrofit();
        RemoteApi api=retrofit.create(RemoteApi.class);
        api.getPlayBean("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)","json","","webapp_music",METHOD_DOWNLOAD_MUSIC,songid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PlayeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PlayeBean value) {
                        callback.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static void getLrc(int songid, @NonNull final HttpCallback<Lrc> callback) {
        Retrofit retrofit= RetrofitUtil.getMusicRetrofit();
        RemoteApi api=retrofit.create(RemoteApi.class);
        api.getLrc("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)","json","","webapp_music",METHOD_LRC,songid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Lrc>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Lrc value) {
                        callback.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

//    public static void getMusicDownloadInfo(String songId, @NonNull final HttpCallback<DownloadInfo> callback) {
//        OkHttpUtils.get().url(BASE_URL)
//                .addParams(PARAM_METHOD, METHOD_DOWNLOAD_MUSIC)
//                .addParams(PARAM_SONG_ID, songId)
//                .build()
//                .execute(new JsonCallback<DownloadInfo>(DownloadInfo.class) {
//                    @Override
//                    public void onResponse(DownloadInfo response, int id) {
//                        callback.onSuccess(response);
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        callback.onFail(e);
//                    }
//
//                    @Override
//                    public void onAfter(int id) {
//                        callback.onFinish();
//                    }
//                });
//    }
//
//    public static void getBitmap(String url, @NonNull final HttpCallback<Bitmap> callback) {
//        OkHttpUtils.get().url(url).build()
//                .execute(new BitmapCallback() {
//                    @Override
//                    public void onResponse(Bitmap bitmap, int id) {
//                        callback.onSuccess(bitmap);
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        callback.onFail(e);
//                    }
//
//                    @Override
//                    public void onAfter(int id) {
//                        callback.onFinish();
//                    }
//                });
//    }
//
//    public static void getLrc(String songId, @NonNull final HttpCallback<Lrc> callback) {
//        OkHttpUtils.get().url(BASE_URL)
//                .addParams(PARAM_METHOD, METHOD_LRC)
//                .addParams(PARAM_SONG_ID, songId)
//                .build()
//                .execute(new JsonCallback<Lrc>(Lrc.class) {
//                    @Override
//                    public void onResponse(Lrc response, int id) {
//                        callback.onSuccess(response);
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        callback.onFail(e);
//                    }
//
//                    @Override
//                    public void onAfter(int id) {
//                        callback.onFinish();
//                    }
//                });
//    }
//
//    public static void searchMusic(String keyword, @NonNull final HttpCallback<SearchMusic> callback) {
//        OkHttpUtils.get().url(BASE_URL)
//                .addParams(PARAM_METHOD, METHOD_SEARCH_MUSIC)
//                .addParams(PARAM_QUERY, keyword)
//                .build()
//                .execute(new JsonCallback<SearchMusic>(SearchMusic.class) {
//                    @Override
//                    public void onResponse(SearchMusic response, int id) {
//                        callback.onSuccess(response);
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        callback.onFail(e);
//                    }
//
//                    @Override
//                    public void onAfter(int id) {
//                        callback.onFinish();
//                    }
//                });
//    }
//
//    public static void getArtistInfo(String tingUid, @NonNull final HttpCallback<ArtistInfo> callback) {
//        OkHttpUtils.get().url(BASE_URL)
//                .addParams(PARAM_METHOD, METHOD_ARTIST_INFO)
//                .addParams(PARAM_TING_UID, tingUid)
//                .build()
//                .execute(new JsonCallback<ArtistInfo>(ArtistInfo.class) {
//                    @Override
//                    public void onResponse(ArtistInfo response, int id) {
//                        callback.onSuccess(response);
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        callback.onFail(e);
//                    }
//
//                    @Override
//                    public void onAfter(int id) {
//                        callback.onFinish();
//                    }
//                });
//    }

    public static void downloadFile(String url, final String destFileDir, final String destFileName, @Nullable final HttpCallback<File> callback) {
        Log.d(TAG, "downloadAPK: " + url);
        OkHttpUtils.get().url(url).build()
                .execute(new FileCallBack(destFileDir, destFileName) {
                    @Override
                    public void inProgress(float progress, long total, int id) {
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        if (callback != null) {
                            callback.onSuccess(file);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (callback != null) {
                            callback.onFail(e);
                        }
                    }

                    @Override
                    public void onAfter(int id) {
                        if (callback != null) {
                            callback.onFinish();
                        }
                    }
                });
    }
}
