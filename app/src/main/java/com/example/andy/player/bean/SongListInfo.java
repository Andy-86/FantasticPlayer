package com.example.andy.player.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andy on 2017/12/15.
 */

public class SongListInfo implements Parcelable {
    public int type;
    public boolean isSonglist;
    public String firsSongname;
    public String firsSingername;
    public String secondSongname;
    public String secondSingername;
    public String thirdSongname;
    public String thirdSingername;
    public String imageUrl;
    public String Title;
    public boolean isFinishload;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeByte(this.isSonglist ? (byte) 1 : (byte) 0);
        dest.writeString(this.firsSongname);
        dest.writeString(this.firsSingername);
        dest.writeString(this.secondSongname);
        dest.writeString(this.secondSingername);
        dest.writeString(this.thirdSongname);
        dest.writeString(this.thirdSingername);
        dest.writeString(this.imageUrl);
        dest.writeString(this.Title);
        dest.writeByte(this.isFinishload ? (byte) 1 : (byte) 0);
    }

    public SongListInfo() {
    }

    protected SongListInfo(Parcel in) {
        this.type = in.readInt();
        this.isSonglist = in.readByte() != 0;
        this.firsSongname = in.readString();
        this.firsSingername = in.readString();
        this.secondSongname = in.readString();
        this.secondSingername = in.readString();
        this.thirdSongname = in.readString();
        this.thirdSingername = in.readString();
        this.imageUrl = in.readString();
        this.Title = in.readString();
        this.isFinishload = in.readByte() != 0;
    }

    public static final Parcelable.Creator<SongListInfo> CREATOR = new Parcelable.Creator<SongListInfo>() {
        @Override
        public SongListInfo createFromParcel(Parcel source) {
            return new SongListInfo(source);
        }

        @Override
        public SongListInfo[] newArray(int size) {
            return new SongListInfo[size];
        }
    };
}
