package com.example.andy.player.receiver;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.example.andy.player.R;
import com.example.andy.player.bean.DownloadInfo;
import com.example.andy.player.bean.UpdataEvetn;
import com.example.andy.player.contract.AppCache;
import com.example.andy.player.tools.LogUtil;
import com.example.andy.player.tools.ToastUtils;
import com.example.andy.player.tools.id3.ID3TagUtils;
import com.example.andy.player.tools.id3.ID3Tags;

import org.greenrobot.eventbus.EventBus;

import java.io.File;


/**
 * 下载完成广播接收器
 * Created by hzwangchenyan on 2015/12/30.
 */
public class DownloadReceiver extends BroadcastReceiver {
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
        DownloadInfo downloadMusicInfo = AppCache.getDownloadInfoList().get(id);
        if (downloadMusicInfo != null) {
            ToastUtils.show(context.getString(R.string.download_success, downloadMusicInfo.getTitle()));

            String musicPath = downloadMusicInfo.getMusicPath();
            String coverPath = downloadMusicInfo.getCoverPath();
            if (!TextUtils.isEmpty(musicPath) && !TextUtils.isEmpty(coverPath)) {
                // 设置专辑封面
                File musicFile = new File(musicPath);
                File coverFile = new File(coverPath);
                if (musicFile.exists() && coverFile.exists()) {
                    ID3Tags id3Tags = new ID3Tags.Builder()
                            .setCoverFile(coverFile)
                            .build();
                    ID3TagUtils.setID3Tags(musicFile, id3Tags, false);
                }
                LogUtil.doLog("onReceive","musicFile:"+musicFile+"\n coverPath:"+coverPath);
            }

            // 由于系统扫描音乐是异步执行，因此延迟刷新音乐列表
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanMusic();
                }
            }, 1000);

            EventBus.getDefault().post(new UpdataEvetn());
        }
    }

    private void scanMusic() {
//        PlayService playService = AppCache.getPlayService();
//        if (playService != null) {
//            playService.updateMusicList(null);
//        }
    }
}
