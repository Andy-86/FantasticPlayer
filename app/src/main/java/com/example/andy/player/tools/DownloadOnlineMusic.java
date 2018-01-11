package com.example.andy.player.tools;

import android.app.Activity;
import android.text.TextUtils;

import com.example.andy.player.aidl.SongBean;

import java.io.File;

import com.example.andy.player.http.HttpClient;


/**
 * 下载音乐
 * Created by wcy on 2016/1/3.
 */
public abstract class DownloadOnlineMusic extends DownloadMusic {
    private SongBean mOnlineMusic;

    public DownloadOnlineMusic(Activity activity, SongBean onlineMusic) {
        super(activity);
        mOnlineMusic = onlineMusic;
    }

    @Override
    protected void download() {
        final String artist = mOnlineMusic.getSingername();
        final String title = mOnlineMusic.getSongname();

//        // 下载歌词
//        String lrcFileName = FileUtils.getLrcFileName(artist, title);
//        File lrcFile = new File(FileUtils.getLrcDir() + lrcFileName);
//        if (!TextUtils.isEmpty(mOnlineMusic.getLrclink()) && !lrcFile.exists()) {
//            HttpClient.downloadFile(mOnlineMusic.getLrclink(), FileUtils.getLrcDir(), lrcFileName, null);
//        }

        // 下载封面
        String albumFileName = FileUtils.getAlbumFileName(artist, title);
        final File albumFile = new File(FileUtils.getAlbumDir(), albumFileName);
        String picUrl = mOnlineMusic.getAlbumpic_big();
        if (TextUtils.isEmpty(picUrl)) {
            picUrl = mOnlineMusic.getAlbumpic_small();
        }
        if (!albumFile.exists() && !TextUtils.isEmpty(picUrl)) {
            HttpClient.downloadFile(picUrl, FileUtils.getAlbumDir(), albumFileName, null);
        }

        // 获取歌曲下载链接
                downloadMusic(mOnlineMusic.getM4a(), artist, title, albumFile.getPath());
        onExecuteSuccess(null);
    }
}
