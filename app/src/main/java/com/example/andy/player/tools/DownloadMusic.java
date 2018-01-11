package com.example.andy.player.tools;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.webkit.MimeTypeMap;

import com.example.andy.player.R;
import com.example.andy.player.bean.DownloadInfo;
import com.example.andy.player.contract.AppCache;


/**
 * Created by hzwangchenyan on 2017/1/20.
 */
public abstract class DownloadMusic implements IExecutor<DownloadInfo> {
    private Activity mActivity;

    public DownloadMusic(Activity activity) {
        mActivity = activity;
    }
    public void execute() {
        checkNetwork();
    }

    private void checkNetwork() {
//        boolean mobileNetworkDownload = Preferences.enableMobileNetworkDownload();
        if (NetworkUtils.isActiveNetworkMobile(mActivity)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle(R.string.tips);
            builder.setMessage(R.string.download_tips);
            builder.setPositiveButton(R.string.download_tips_sure, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    downloadWrapper();
                }
            });
            builder.setNegativeButton(R.string.cancel, null);
            Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } else {
            downloadWrapper();
        }
    }

    private void downloadWrapper() {
        onPrepare();
        download();
    }

    protected abstract void download();

    protected void downloadMusic(String url, String artist, String title, String coverPath) {
        try {
            String fileName = FileUtils.getMp3FileName(artist, title);
            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(FileUtils.getFileName(artist, title));
            request.setDescription("正在下载…");
            request.setDestinationInExternalPublicDir(FileUtils.getRelativeMusicDir(), fileName);
            request.setMimeType(MimeTypeMap.getFileExtensionFromUrl(url));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            request.setAllowedOverRoaming(false); // 不允许漫游
            DownloadManager downloadManager = (DownloadManager) mActivity.getSystemService(Context.DOWNLOAD_SERVICE);
            long id = downloadManager.enqueue(request);
            String musicAbsPath = FileUtils.getMusicDir().concat(fileName);
            DownloadInfo downloadMusicInfo = new DownloadInfo(title, musicAbsPath, coverPath);
            AppCache.getDownloadInfoList().put(id, downloadMusicInfo);
        } catch (Throwable th) {
            th.printStackTrace();
            ToastUtils.show("下载失败");
        }
    }
}
