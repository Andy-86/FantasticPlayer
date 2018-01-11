package com.example.andy.player.bean;

import com.example.andy.player.aidl.SongBean;

import java.util.List;

/**
 * Created by andy on 2018/1/7.
 */

public class RecomendSongList {

    /**
     * song_list : []
     * billboard : {"billboard_type":"2","billboard_no":"2422","update_date":"2018-01-07","billboard_songnum":"1491","havemore":1,"name":"热歌榜","comment":"该榜单是根据百度音乐平台歌曲每周播放量自动生成的数据榜单，统计范围为百度音乐平台上的全部歌曲，每日更新一次","pic_s192":"http://a.hiphotos.baidu.com/ting/pic/item/09fa513d269759ee4764e3adb1fb43166d22dfa4.jpg","pic_s640":"http://b.hiphotos.baidu.com/ting/pic/item/5d6034a85edf8db1194683910b23dd54574e74df.jpg","pic_s444":"http://d.hiphotos.baidu.com/ting/pic/item/c83d70cf3bc79f3d98ca8e36b8a1cd11728b2988.jpg","pic_s260":"http://a.hiphotos.baidu.com/ting/pic/item/838ba61ea8d3fd1f1326c83c324e251f95ca5f8c.jpg","pic_s210":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_58c1700bf56062108d8d622a95708032.jpg","web_url":"http://music.baidu.com/top/dayhot"}
     * error_code : 22000
     */

    private BillboardBean billboard;
    private int error_code;
    private List<SongBean> song_list;

    public BillboardBean getBillboard() {
        return billboard;
    }

    public void setBillboard(BillboardBean billboard) {
        this.billboard = billboard;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<SongBean> getSong_list() {
        return song_list;
    }

    public void setSong_list(List<SongBean> song_list) {
        this.song_list = song_list;
    }

    public static class BillboardBean {
        /**
         * billboard_type : 2
         * billboard_no : 2422
         * update_date : 2018-01-07
         * billboard_songnum : 1491
         * havemore : 1
         * name : 热歌榜
         * comment : 该榜单是根据百度音乐平台歌曲每周播放量自动生成的数据榜单，统计范围为百度音乐平台上的全部歌曲，每日更新一次
         * pic_s192 : http://a.hiphotos.baidu.com/ting/pic/item/09fa513d269759ee4764e3adb1fb43166d22dfa4.jpg
         * pic_s640 : http://b.hiphotos.baidu.com/ting/pic/item/5d6034a85edf8db1194683910b23dd54574e74df.jpg
         * pic_s444 : http://d.hiphotos.baidu.com/ting/pic/item/c83d70cf3bc79f3d98ca8e36b8a1cd11728b2988.jpg
         * pic_s260 : http://a.hiphotos.baidu.com/ting/pic/item/838ba61ea8d3fd1f1326c83c324e251f95ca5f8c.jpg
         * pic_s210 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_58c1700bf56062108d8d622a95708032.jpg
         * web_url : http://music.baidu.com/top/dayhot
         */

        private String billboard_type;
        private String billboard_no;
        private String update_date;
        private String billboard_songnum;
        private int havemore;
        private String name;
        private String comment;
        private String pic_s192;
        private String pic_s640;
        private String pic_s444;
        private String pic_s260;
        private String pic_s210;
        private String web_url;

        public String getBillboard_type() {
            return billboard_type;
        }

        public void setBillboard_type(String billboard_type) {
            this.billboard_type = billboard_type;
        }

        public String getBillboard_no() {
            return billboard_no;
        }

        public void setBillboard_no(String billboard_no) {
            this.billboard_no = billboard_no;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public String getBillboard_songnum() {
            return billboard_songnum;
        }

        public void setBillboard_songnum(String billboard_songnum) {
            this.billboard_songnum = billboard_songnum;
        }

        public int getHavemore() {
            return havemore;
        }

        public void setHavemore(int havemore) {
            this.havemore = havemore;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getPic_s192() {
            return pic_s192;
        }

        public void setPic_s192(String pic_s192) {
            this.pic_s192 = pic_s192;
        }

        public String getPic_s640() {
            return pic_s640;
        }

        public void setPic_s640(String pic_s640) {
            this.pic_s640 = pic_s640;
        }

        public String getPic_s444() {
            return pic_s444;
        }

        public void setPic_s444(String pic_s444) {
            this.pic_s444 = pic_s444;
        }

        public String getPic_s260() {
            return pic_s260;
        }

        public void setPic_s260(String pic_s260) {
            this.pic_s260 = pic_s260;
        }

        public String getPic_s210() {
            return pic_s210;
        }

        public void setPic_s210(String pic_s210) {
            this.pic_s210 = pic_s210;
        }

        public String getWeb_url() {
            return web_url;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }
    }
}
