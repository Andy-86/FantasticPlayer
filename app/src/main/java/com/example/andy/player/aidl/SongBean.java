package com.example.andy.player.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andy on 2017/9/21.
 */

public class SongBean implements Parcelable {

    /**
     * m4a : http://ws.stream.qqmusic.qq.com/498307.m4a?fromtag=46
     * media_mid : 0024MWM52SVYgg
     * songid : 498307
     * singerid : 2
     * albumname : 遥望
     * downUrl : http://dl.stream.qqmusic.qq.com/498307.m4a?vkey=C2E7D36BFA23CD4044C79D0505535A0CDD5736DC286F4A794AC56E0DA63527169D864982B674B21CD34035D294594735B70C7F82E83D3566&guid=2718671044
     * singername : BEYOND
     * songname : 海阔天空
     * strMediaMid : 0024MWM52SVYgg
     * albummid : 004Z88hS1FiU07
     * songmid : 003Dk8AU00uTPp
     * albumpic_big : http://i.gtimg.cn/music/photo/mid_album_300/0/7/004Z88hS1FiU07.jpg
     * albumpic_small : http://i.gtimg.cn/music/photo/mid_album_90/0/7/004Z88hS1FiU07.jpg
     * albumid : 40035
     */

    private String m4a;
    private String media_mid;
    private int progress;
    private int songid;
    private int singerid;
    private String albumname;
    private String downUrl;
    private String singername;
    private String songname;
    private String strMediaMid;
    private String albummid;
    private String songmid;
    private String albumpic_big;
    private String albumpic_small;
    private int albumid;

    public String getM4a() {
        return m4a;
    }

    public void setM4a(String m4a) {
        this.m4a = m4a;
    }

    public String getMedia_mid() {
        return media_mid;
    }

    public void setMedia_mid(String media_mid) {
        this.media_mid = media_mid;
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

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getStrMediaMid() {
        return strMediaMid;
    }

    public void setStrMediaMid(String strMediaMid) {
        this.strMediaMid = strMediaMid;
    }

    public String getAlbummid() {
        return albummid;
    }

    public void setAlbummid(String albummid) {
        this.albummid = albummid;
    }

    public String getSongmid() {
        return songmid;
    }

    public void setSongmid(String songmid) {
        this.songmid = songmid;
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

    public int getAlbumid() {
        return albumid;
    }

    public void setAlbumid(int albumid) {
        this.albumid = albumid;
    }

    public SongBean() {
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.m4a);
        dest.writeString(this.media_mid);
        dest.writeInt(this.progress);
        dest.writeInt(this.songid);
        dest.writeInt(this.singerid);
        dest.writeString(this.albumname);
        dest.writeString(this.downUrl);
        dest.writeString(this.singername);
        dest.writeString(this.songname);
        dest.writeString(this.strMediaMid);
        dest.writeString(this.albummid);
        dest.writeString(this.songmid);
        dest.writeString(this.albumpic_big);
        dest.writeString(this.albumpic_small);
        dest.writeInt(this.albumid);
    }

    protected SongBean(Parcel in) {
        this.m4a = in.readString();
        this.media_mid = in.readString();
        this.progress = in.readInt();
        this.songid = in.readInt();
        this.singerid = in.readInt();
        this.albumname = in.readString();
        this.downUrl = in.readString();
        this.singername = in.readString();
        this.songname = in.readString();
        this.strMediaMid = in.readString();
        this.albummid = in.readString();
        this.songmid = in.readString();
        this.albumpic_big = in.readString();
        this.albumpic_small = in.readString();
        this.albumid = in.readInt();
    }

    public static final Creator<SongBean> CREATOR = new Creator<SongBean>() {
        @Override
        public SongBean createFromParcel(Parcel source) {
            return new SongBean(source);
        }

        @Override
        public SongBean[] newArray(int size) {
            return new SongBean[size];
        }
    };
}
