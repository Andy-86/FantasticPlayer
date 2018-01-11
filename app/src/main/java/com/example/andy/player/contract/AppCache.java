package com.example.andy.player.contract;

import android.util.LongSparseArray;

import com.example.andy.player.bean.DownloadInfo;

/**
 * Created by andy on 2018/1/11.
 */

public class AppCache {
    public static LongSparseArray<DownloadInfo> downloadInfoList=new LongSparseArray<>();

    public static LongSparseArray<DownloadInfo> getDownloadInfoList() {
        return downloadInfoList;
    }

    public static void setDownloadInfoList(LongSparseArray<DownloadInfo> downloadInfoList) {
        AppCache.downloadInfoList = downloadInfoList;
    }
}
