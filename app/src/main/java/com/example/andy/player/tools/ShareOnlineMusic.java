package com.example.andy.player.tools;

import android.content.Context;
import android.content.Intent;

import com.example.andy.player.R;
import com.example.andy.player.aidl.SongBean;


/**
 * 分享在线歌曲
 * Created by hzwangchenyan on 2016/1/13.
 */
public class ShareOnlineMusic  {
    private Context mContext;
    private String mTitle;
    private long mSongId;
    private SongBean songBean;
    public ShareOnlineMusic(Context context, SongBean songBean) {
        mContext = context;
        mTitle = songBean.getSongname();
        mSongId = songBean.getSongid();
        this.songBean=songBean;
    }


    public void execute() {
        share();
    }

    private void share() {
        // 获取歌曲播放链接

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, mContext.getString(R.string.share_music, mContext.getString(R.string.app_name),
                mTitle, songBean.getM4a()));
        mContext.startActivity(Intent.createChooser(intent, mContext.getString(R.string.share)));

    }
}
