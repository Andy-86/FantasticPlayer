package com.example.andy.player.tools;

import android.util.Log;

import com.example.andy.player.application.MyApplication;
import com.example.andy.player.contract.Contract;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017-7-19.
 */

public class RetrofitUtil {
    private RetrofitUtil() {
    }

    private static volatile Retrofit mRetrofit;
    private static File cacheDir = MyApplication.getApplication().getCacheDir();
    private static File cacheFile = new File(cacheDir, "cache");
    private static HttpLoggingInterceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor(
            new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Log.d("RetrofitUtil", "message:" + message);
        }
    });

    private static long fileSize = 10 * 1024 * 1024; //10MB

    private static OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .connectTimeout(8, TimeUnit.SECONDS)
            .addInterceptor(new OkHttpLoggingInterceptor())
            .cache(new Cache(cacheFile, fileSize))
            .build();

    public static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            synchronized (RetrofitUtil.class) {
                if (mRetrofit == null) {
                    mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    mRetrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(mOkHttpClient)
                            .baseUrl(Contract.BASE_URL)
                            .build();

                }
            }
        }
        return mRetrofit;
    }
}
