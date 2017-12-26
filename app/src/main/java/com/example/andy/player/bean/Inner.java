package com.example.andy.player.bean;

import java.util.List;

/**
 * Created by andy on 2017/12/15.
 */

public class Inner {
    public int ret_code;
    List<HotSong> songlist;

    public List<HotSong> getSonglist() {
        return songlist;
    }

    public void setSonglist(List<HotSong> songlist) {
        this.songlist = songlist;
    }

    @Override
    public String toString() {
        return songlist.size()+"";
    }
}
