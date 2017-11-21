package com.example.andy.player.tools;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;

import com.example.andy.player.application.MyApplication;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by andy on 2017/8/2.
 */

public class OkHttpLoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder newBuilder = chain
                .request()
                .newBuilder();

        Request request = newBuilder
                .addHeader("User-Agent",getUserAgent() )
                .build();

        String cacheControl=request.cacheControl().toString();
        if(TextUtils.isEmpty(cacheControl)){
            cacheControl = "public, max-age=60";
        }
        Response response = chain.proceed(request);

        if(MyApplication.DEBUG){
            Log.e("ssss", "response返回参数" + response.toString());

            //添加打印服务器返回的数据
            ResponseBody responseBody = response.body();
            long contentLength = responseBody.contentLength();
            BufferedSource source = responseBody.source();
            source.request(Integer.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();


            if (contentLength != 0) {
                Log.e("服务器返回数据：", ""+buffer.clone().readString(Charset.forName("UTF-8")));
            }
        }

        return response.
                newBuilder()
                .header("Cache-Control", cacheControl)
                .build();

    }
    private static String getUserAgent() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(MyApplication.getContext());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}