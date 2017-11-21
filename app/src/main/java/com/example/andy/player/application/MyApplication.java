package com.example.andy.player.application;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.andy.player.aidl.IMusicPlayer;
import com.example.andy.player.service.MusicService;
import com.example.andy.player.tools.LogUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by andy on 2017/9/22.
 */

public class MyApplication extends Application {
    public final static String TAG = "BaseApplication";
    public final static boolean DEBUG = true;
    public static MyApplication myApplication;
    private static int mainTid;
    private IMusicPlayer mMusicPlayerService;
    public IMusicPlayer getMusicPlayerService() {

        return mMusicPlayerService;
    }
    /**
     * Activity集合，来管理所有的Activity
     */
    private static List<Activity> activities;


    public static Application getContext() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        myApplication = this;
        bindService();
        super.onCreate();
        activities = new LinkedList<>();
        mainTid = android.os.Process.myTid();
    }

    /**
     * 获取application
     *
     * @return
     */
    public static Context getApplication() {
        return myApplication;
    }

    /**
     * 获取主线程ID
     *
     * @return
     */
    public static int getMainTid() {
        return mainTid;
    }

    /**
     * 添加一个Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 结束一个Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null)
            activities.remove(activity);
    }

    /**
     * 结束当前所有Activity
     */
    public static void clearActivities() {
        ListIterator<Activity> iterator = activities.listIterator();
        Activity activity;
        while (iterator.hasNext()) {
            activity = iterator.next();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 退出应运程序
     */
    public static void quiteApplication() {
        clearActivities();
        System.exit(0);
    }

    private void bindService() {
        Intent intent = new Intent(MyApplication.myApplication, MusicService.class);
        bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE);
    }


    static boolean linkSuccess;
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mMusicPlayerService = IMusicPlayer.Stub.asInterface(service);
            LogUtil.doLog("onServiceConnected","----------------------------------"+mMusicPlayerService.toString());
            try {
                mMusicPlayerService.asBinder().linkToDeath(deathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            linkSuccess = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bindService();
        }
    };
    private IBinder.DeathRecipient deathRecipient=new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            LogUtil.doLog("binderDied","binder died");
            mMusicPlayerService.asBinder().unlinkToDeath(deathRecipient,0);
            mMusicPlayerService=null;
            bindService();
        }
    };

}

