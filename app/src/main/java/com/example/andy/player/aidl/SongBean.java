package com.example.andy.player.aidl;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

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


//      "artist_id": "88",
//              "language": "国语",
//              "pic_big": "http://qukufile2.qianqian.com/data2/pic/1c2424e15b4f432d5ad65f69f0e2d11d/568328873/568328873.png@s_1,w_150,h_150",
//              "pic_small": "http://qukufile2.qianqian.com/data2/pic/1c2424e15b4f432d5ad65f69f0e2d11d/568328873/568328873.png@s_1,w_90,h_90",
//              "country": "内地",
//              "area": "0",
//              "publishtime": "2017-12-22",
//              "album_no": "1",
//              "lrclink": "http://qukufile2.qianqian.com/data2/lrc/9b90b83c17e694eaedb354fd8d19fcff/568325003/568325003.lrc",
//              "copy_type": "1",
//              "hot": "187659",
//              "all_artist_ting_uid": "2517",
//              "resource_type": "0",
//              "is_new": "1",
//              "rank_change": "0",
//              "rank": "1",
//              "all_artist_id": "88",
//              "style": "",
//              "del_status": "0",
//              "relate_status": "0",
//              "toneid": "0",
//              "all_rate": "64,128,256,320,flac",
//              "file_duration": 235,
//              "has_mv_mobile": 0,
//              "versions": "",
//              "bitrate_fee": "{\"0\":\"0|0\",\"1\":\"0|0\"}",
//              "biaoshi": "first,lossless",
//              "info": "",
//              "has_filmtv": "0",
//              "si_proxycompany": "TAIHE MUSIC GROUP",
//              "res_encryption_flag": "0",
//              "song_id": "568320992",
//              "title": "狐狸（电影《二代妖精之今生有幸》推广曲）",
//              "ting_uid": "2517",
//              "author": "薛之谦",
//              "album_id": "568320989",
//              "album_title": "狐狸（电影《二代妖精之今生有幸》推广曲）",
//              "is_first_publish": 0,
//              "havehigh": 2,
//              "charge": 0,
//              "has_mv": 1,
//              "learn": 0,
//              "song_source": "web",
//              "piao_id": "0",
//              "korean_bb_song": "0",
//              "resource_type_ext": "0",
//              "mv_provider": "0000000000",
//              "artist_name": "薛之谦",
//              "pic_radio": "http://qukufile2.qianqian.com/data2/pic/1c2424e15b4f432d5ad65f69f0e2d11d/568328873/568328873.png@s_1,w_300,h_300",
//              "pic_s500": "http://qukufile2.qianqian.com/data2/pic/1c2424e15b4f432d5ad65f69f0e2d11d/568328873/568328873.png@s_1,w_500,h_500",
//              "pic_premium": "http://qukufile2.qianqian.com/data2/pic/1c2424e15b4f432d5ad65f69f0e2d11d/568328873/568328873.png@s_1,w_500,h_500",
//              "pic_huge": "http://qukufile2.qianqian.com/data2/pic/1c2424e15b4f432d5ad65f69f0e2d11d/568328873/568328873.png@s_1,w_1000,h_1000",
//              "album_500_500": "http://qukufile2.qianqian.com/data2/pic/1c2424e15b4f432d5ad65f69f0e2d11d/568328873/568328873.png@s_1,w_500,h_500",
//              "album_800_800": "",
//              "album_1000_1000": "http://qukufile2.qianqian.com/data2/pic/1c2424e15b4f432d5ad65f69f0e2d11d/568328873/568328873.png@s_1,w_1000,h_1000"
//
    private String m4a;
    private String media_mid;
    private int progress;
    @SerializedName("song_id")
    private long songid;
    @SerializedName("artist_id")
    private long singerid;
    @SerializedName("album_title")
    private String albumname;
    private String downUrl;
    @SerializedName("artist_name")
    private String singername;
    @SerializedName("title")
    private String songname;
    private String strMediaMid;
    private String albummid;
    private String albumName;
    private long duration;
    private String path;
    private String fileName;
    private String fileSize;

    private String songmid;
    @SerializedName("pic_big")
    private String albumpic_big;
    @SerializedName("pic_small")
    private String albumpic_small;

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }


    public long getSongid() {
        return songid;
    }

    public void setSongid(long songid) {
        this.songid = songid;
    }

    public long getSingerid() {
        return singerid;
    }

    public void setSingerid(long singerid) {
        this.singerid = singerid;
    }




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



    public void setSongid(int songid) {
        this.songid = songid;
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
        dest.writeLong(this.songid);
        dest.writeLong(this.singerid);
        dest.writeString(this.albumname);
        dest.writeString(this.downUrl);
        dest.writeString(this.singername);
        dest.writeString(this.songname);
        dest.writeString(this.strMediaMid);
        dest.writeString(this.albummid);
        dest.writeString(this.albumName);
        dest.writeLong(this.duration);
        dest.writeString(this.path);
        dest.writeString(this.fileName);
        dest.writeString(this.fileSize);
        dest.writeString(this.songmid);
        dest.writeString(this.albumpic_big);
        dest.writeString(this.albumpic_small);
    }

    protected SongBean(Parcel in) {
        this.m4a = in.readString();
        this.media_mid = in.readString();
        this.progress = in.readInt();
        this.songid = in.readLong();
        this.singerid = in.readLong();
        this.albumname = in.readString();
        this.downUrl = in.readString();
        this.singername = in.readString();
        this.songname = in.readString();
        this.strMediaMid = in.readString();
        this.albummid = in.readString();
        this.albumName = in.readString();
        this.duration = in.readLong();
        this.path = in.readString();
        this.fileName = in.readString();
        this.fileSize = in.readString();
        this.songmid = in.readString();
        this.albumpic_big = in.readString();
        this.albumpic_small = in.readString();
    }

    public static final Parcelable.Creator<SongBean> CREATOR = new Parcelable.Creator<SongBean>() {
        @Override
        public SongBean createFromParcel(Parcel source) {
            return new SongBean(source);
        }

        @Override
        public SongBean[] newArray(int size) {
            return new SongBean[size];
        }
    };

    @Override
    public String toString() {
        return "SongBean{" +
                "m4a='" + m4a + '\'' +
                ", media_mid='" + media_mid + '\'' +
                ", progress=" + progress +
                ", songid=" + songid +
                ", singerid=" + singerid +
                ", albumname='" + albumname + '\'' +
                ", downUrl='" + downUrl + '\'' +
                ", singername='" + singername + '\'' +
                ", songname='" + songname + '\'' +
                ", strMediaMid='" + strMediaMid + '\'' +
                ", albummid='" + albummid + '\'' +
                ", albumName='" + albumName + '\'' +
                ", duration=" + duration +
                ", path='" + path + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", songmid='" + songmid + '\'' +
                ", albumpic_big='" + albumpic_big + '\'' +
                ", albumpic_small='" + albumpic_small + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return ((SongBean)obj).getSongid()==getSongid();
    }
}
