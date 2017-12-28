package com.example.andy.player.tools;

import com.example.andy.player.aidl.SongBean;
import com.example.andy.player.bean.HotSong;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 2017/12/15.
 */

public class Transformer {
    public static List<SongBean> transfromToSongBean(List<HotSong> list){
        List<SongBean> songBeanList=new ArrayList<>();
        for(HotSong song:list){
            SongBean bean=new SongBean();
            bean.setM4a(song.getUrl());
            bean.setSongname(song.getSongname());
            bean.setSingername(song.getSingername());
            bean.setAlbummid(song.getAlbummid());
            bean.setAlbumpic_big(song.getAlbumpic_big());
            bean.setAlbumpic_small(song.getAlbumpic_small());
            bean.setSongid(song.getSongid());
            bean.setSingerid(song.getSingerid());
            bean.setDownUrl(song.getDownUrl());
            songBeanList.add(bean);
        }
        return songBeanList;
    }


    public static String transformToLyr(String s){
        if(s!=null){
        String s1=s.replaceAll("&#10;", "\n");
        String s2=s1.replaceAll("&#58;", ":");
        String s3=s2.replaceAll("&#46;", ".");
        String s4=s3.replaceAll("&#32;", " ");
        String s5=s4.replaceAll("&#40;", "(");
        String s6=s5.replaceAll("&#41;", ")");
        String s7=s6.replaceAll("&#38;", "&");
        String s8=s7.replaceAll("&#45;", "-");
        System.out.println(s8);
        return s8;}else {
            return null;
        }
    }
}
