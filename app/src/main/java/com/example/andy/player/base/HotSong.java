package com.example.andy.player.base;

/**
 * Created by andy on 2017/12/14.
 */

public class HotSong {

    /**
     * songname : 红蔷薇
     * seconds : 213
     * albummid : 002K1Euv1vaRO8
     * songid : 212113504
     * singerid : 4607
     * albumpic_big : http://i.gtimg.cn/music/photo/mid_album_300/O/8/002K1Euv1vaRO8.jpg
     * albumpic_small : http://i.gtimg.cn/music/photo/mid_album_90/O/8/002K1Euv1vaRO8.jpg
     * downUrl : http://dl.stream.qqmusic.qq.com/212113504.mp3?vkey=6E60D32A1D57FD430EA4C3283BD7A7F186EEDC3B9594913A9EE9926B321CF563E0A1EA7F5A6C877F65639BCF1B17C71F32D8D467666A3EB3&guid=2718671044
     * url : http://ws.stream.qqmusic.qq.com/212113504.m4a?fromtag=46
     * singername : 张靓颖
     * albumid : 3753676
     */

    private String songname;
    private int seconds;
    private String albummid;
    private int songid;
    private int singerid;
    private String albumpic_big;

    @Override
    public String toString() {
        return "HotSong{" +
                "songname='" + songname + '\'' +
                ", seconds=" + seconds +
                ", albummid='" + albummid + '\'' +
                ", songid=" + songid +
                ", singerid=" + singerid +
                ", albumpic_big='" + albumpic_big + '\'' +
                ", albumpic_small='" + albumpic_small + '\'' +
                ", downUrl='" + downUrl + '\'' +
                ", url='" + url + '\'' +
                ", singername='" + singername + '\'' +
                ", albumid=" + albumid +
                '}';
    }

    private String albumpic_small;
    private String downUrl;
    private String url;
    private String singername;
    private int albumid;

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getAlbummid() {
        return albummid;
    }

    public void setAlbummid(String albummid) {
        this.albummid = albummid;
    }

    public int getSongid() {
        return songid;
    }

    public void setSongid(int songid) {
        this.songid = songid;
    }

    public int getSingerid() {
        return singerid;
    }

    public void setSingerid(int singerid) {
        this.singerid = singerid;
    }

    public String getAlbumpic_big() {
        return albumpic_big;
    }

    public void setAlbumpic_big(String albumpic_big) {
        this.albumpic_big = albumpic_big;
    }

    public String getAlbumpic_small() {
        return albumpic_small;
    }

    public void setAlbumpic_small(String albumpic_small) {
        this.albumpic_small = albumpic_small;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public int getAlbumid() {
        return albumid;
    }

    public void setAlbumid(int albumid) {
        this.albumid = albumid;
    }
}
