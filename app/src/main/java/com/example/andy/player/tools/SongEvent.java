package com.example.andy.player.tools;

import com.example.andy.player.aidl.SongBean;

/**
 * Created by andy on 2017/12/8.
 */

public class SongEvent {
    public SongBean songBean;

    /**
     *
     * @param songBean 初始化的songbean
     */
    public SongEvent(SongBean songBean) {
        this.songBean = songBean;
    }

    public SongBean getSongBean() {
        return songBean;
    }
}
