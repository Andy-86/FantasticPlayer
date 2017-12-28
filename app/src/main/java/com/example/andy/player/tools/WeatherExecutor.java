package com.example.andy.player.tools;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.example.andy.player.R;
import com.example.andy.player.activity.MusicAcitivity;
import com.example.andy.player.application.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 更新天气
 * Created by wcy on 2016/1/17.
 * <p>
 * 天气现象表
 * <p>
 * 晴
 * 多云
 * 阴
 * 阵雨
 * 雷阵雨
 * 雷阵雨并伴有冰雹
 * 雨夹雪
 * 小雨
 * 中雨
 * 大雨
 * 暴雨
 * 大暴雨
 * 特大暴雨
 * 阵雪
 * 小雪
 * 中雪
 * 大雪
 * 暴雪
 * 雾
 * 冻雨
 * 沙尘暴
 * 小雨-中雨
 * 中雨-大雨
 * 大雨-暴雨
 * 暴雨-大暴雨
 * 大暴雨-特大暴雨
 * 小雪-中雪
 * 中雪-大雪
 * 大雪-暴雪
 * 浮尘
 * 扬沙
 * 强沙尘暴
 * 飑
 * 龙卷风
 * 弱高吹雪
 * 轻霾
 * 霾
 */
public class WeatherExecutor implements AMapLocationListener, WeatherSearch.OnWeatherSearchListener {
    private static final String TAG = "WeatherExecutor";
    private MusicAcitivity mPlayService;
    private Context mContext;
    LinearLayout llWeather;
    ImageView ivIcon;
    TextView tvTemp;
    TextView tvCity;
    TextView tvWind;
    WeatherSearch mweathersearch;
    WeatherSearchQuery mquery;
    LocalWeatherLive weatherlive;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                LogUtil.doLog("onLocationChanged",""+amapLocation.getCity());
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                execute(amapLocation.getCity());
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    public WeatherExecutor(MusicAcitivity playService, View navigationHeader) {
        mPlayService = playService;
        mContext = mPlayService.getApplicationContext();
        llWeather = (LinearLayout) navigationHeader.findViewById(R.id.ll_weather);
        ivIcon = (ImageView) navigationHeader.findViewById(R.id.iv_weather_icon);
        tvTemp = (TextView) navigationHeader.findViewById(R.id.tv_weather_temp);
        tvCity = (TextView) navigationHeader.findViewById(R.id.tv_weather_city);
        tvWind = (TextView) navigationHeader.findViewById(R.id.tv_weather_wind);

        mlocationClient = new AMapLocationClient(playService);
//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
//设置定位监听
        mlocationClient.setLocationListener(this);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位
        mlocationClient.startLocation();
    }


    public void execute(String city) {
        mquery = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        mweathersearch = new WeatherSearch(MyApplication.getContext());
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }


    private void updateView(LocalWeatherLive aMapLocalWeatherLive) {
        llWeather.setVisibility(View.VISIBLE);
        ivIcon.setImageResource(getWeatherIcon(aMapLocalWeatherLive.getWeather()));
        tvTemp.setText(mContext.getString(R.string.weather_temp, aMapLocalWeatherLive.getTemperature()));
        tvCity.setText(aMapLocalWeatherLive.getCity());
        tvWind.setText(mContext.getString(R.string.weather_wind, aMapLocalWeatherLive.getWindDirection(),
                aMapLocalWeatherLive.getWindPower(), aMapLocalWeatherLive.getHumidity()));
    }

    private int getWeatherIcon(String weather) {
        if (TextUtils.isEmpty(weather)) {
            return R.drawable.ic_weather_sunny;
        }

        if (weather.contains("-")) {
            weather = weather.substring(0, weather.indexOf("-"));
        }
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int resId;
        if (weather.contains("晴")) {
            if (hour >= 7 && hour < 19) {
                resId = R.drawable.ic_weather_sunny;
            } else {
                resId = R.drawable.ic_weather_sunny_night;
            }
        } else if (weather.contains("多云")) {
            if (hour >= 7 && hour < 19) {
                resId = R.drawable.ic_weather_cloudy;
            } else {
                resId = R.drawable.ic_weather_cloudy_night;
            }
        } else if (weather.contains("阴")) {
            resId = R.drawable.ic_weather_overcast;
        } else if (weather.contains("雷阵雨")) {
            resId = R.drawable.ic_weather_thunderstorm;
        } else if (weather.contains("雨夹雪")) {
            resId = R.drawable.ic_weather_sleet;
        } else if (weather.contains("雨")) {
            resId = R.drawable.ic_weather_rain;
        } else if (weather.contains("雪")) {
            resId = R.drawable.ic_weather_snow;
        } else if (weather.contains("雾") || weather.contains("霾")) {
            resId = R.drawable.ic_weather_foggy;
        } else if (weather.contains("风") || weather.contains("飑")) {
            resId = R.drawable.ic_weather_typhoon;
        } else if (weather.contains("沙") || weather.contains("尘")) {
            resId = R.drawable.ic_weather_sandstorm;
        } else {
            resId = R.drawable.ic_weather_cloudy;
        }
        return resId;
    }

    private void release() {
        mPlayService = null;
        mContext = null;
        llWeather = null;
        ivIcon = null;
        tvTemp = null;
        tvCity = null;
        tvWind = null;
    }

    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
        if (rCode == 1000) {
            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                weatherlive = weatherLiveResult.getLiveResult();
                updateView(weatherlive);
            } else {
                LogUtil.doLog("onWeatherLiveSearched", "没有结果");
            }
        } else {
            LogUtil.doLog("onWeatherLiveSearched", "请求失败");
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

    }
}
