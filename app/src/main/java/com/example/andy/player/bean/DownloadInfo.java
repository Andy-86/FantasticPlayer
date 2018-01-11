package com.example.andy.player.bean;

/**
 * JavaBean
 * Created by wcy on 2015/12/27.
 */
public class DownloadInfo {
    private String title;
    private String musicPath;
    private String coverPath;

    public DownloadInfo(String title, String musicPath, String coverPath) {
        this.title = title;
        this.musicPath = musicPath;
        this.coverPath = coverPath;
    }

    public String getTitle() {
        return title;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public String getCoverPath() {
        return coverPath;
    }
}
