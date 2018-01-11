package com.example.andy.player.bean;

import java.util.List;

/**
 * Created by andy on 2018/1/7.
 */

public class SearchBean {

    /**
     * song : [{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","weight":"1181099","songname":"海阔天空","resource_type":"0","songid":"87859296","has_mv":"0","yyr_artist":"0","resource_type_ext":"0","artistname":"Beyond","info":"","resource_provider":"1","control":"0000000000","encrypted_songid":"57081DD4AF1F0859549A7E"},{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","weight":"37099","songname":"海阔天空","resource_type":"0","songid":"797275","has_mv":"0","yyr_artist":"0","resource_type_ext":"0","artistname":"周华健","info":"","resource_provider":"1","control":"0000000000","encrypted_songid":"49081D6698300858DE4913"},{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","weight":"23099","songname":"海阔天空-现场版","resource_type":"0","songid":"19610286","has_mv":"0","yyr_artist":"0","resource_type_ext":"0","artistname":"任贤齐","info":"","resource_provider":"1","control":"0000000000","encrypted_songid":"77081D90898B0858DE4B1C"},{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","weight":"17099","songname":"海阔天空","resource_type":"0","songid":"245905834","has_mv":"0","yyr_artist":"0","resource_type_ext":"0","artistname":"刘良骏","info":"","resource_provider":"1","control":"0000000000","encrypted_songid":"3107ea839aa095621afb6L"},{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","weight":"8099","songname":"海阔天空","resource_type":"0","songid":"14880013","has_mv":"0","yyr_artist":"0","resource_type_ext":"0","artistname":"林忆莲","info":"","resource_provider":"1","control":"1100000000","encrypted_songid":"1606e30d0d095771062dL"},{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","weight":"7099","songname":"海阔天空","resource_type":"0","songid":"73896409","has_mv":"0","yyr_artist":"1","resource_type_ext":"0","artistname":"幼稚园杀手","info":"","resource_provider":"1","control":"0000000000","encrypted_songid":""},{"bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","weight":"6099","songname":"海阔天空","resource_type":"0","songid":"268425156","has_mv":"0","yyr_artist":"0","resource_type_ext":"0","artistname":"韩红","info":"","resource_provider":"1","control":"1100000000","encrypted_songid":"2107fffd7c409584153f0L"},{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","weight":"4099","songname":"海阔天空-电吉他版","resource_type":"0","songid":"73984962","has_mv":"0","yyr_artist":"1","resource_type_ext":"0","artistname":"MC雪殇","info":"","resource_provider":"1","control":"0100000000","encrypted_songid":""},{"bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","weight":"4099","songname":"海阔天空","resource_type":"0","songid":"8877990","has_mv":"0","yyr_artist":"0","resource_type_ext":"0","artistname":"林育群","info":"","resource_provider":"1","control":"1100000000","encrypted_songid":"66068777a6095848d897L"},{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","weight":"4099","songname":"海阔天空","resource_type":"0","songid":"565884031","has_mv":"0","yyr_artist":"0","resource_type_ext":"0","artistname":"三师兄","info":"","resource_provider":"1","control":"0000000000","encrypted_songid":"180821BAB472085A00414A"}]
     * error_code : 22000
     * order : song
     */

    private int error_code;
    private String order;
    private List<SongBean> song;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<SongBean> getSong() {
        return song;
    }

    public void setSong(List<SongBean> song) {
        this.song = song;
    }

    public static class SongBean {
        /**
         * bitrate_fee : {"0":"0|0","1":"0|0"}
         * weight : 1181099
         * songname : 海阔天空
         * resource_type : 0
         * songid : 87859296
         * has_mv : 0
         * yyr_artist : 0
         * resource_type_ext : 0
         * artistname : Beyond
         * info :
         * resource_provider : 1
         * control : 0000000000
         * encrypted_songid : 57081DD4AF1F0859549A7E
         */

        private String bitrate_fee;
        private String weight;
        private String songname;
        private String resource_type;
        private String songid;
        private String has_mv;
        private String yyr_artist;
        private String resource_type_ext;
        private String artistname;
        private String info;
        private String resource_provider;
        private String control;
        private String encrypted_songid;

        public String getBitrate_fee() {
            return bitrate_fee;
        }

        public void setBitrate_fee(String bitrate_fee) {
            this.bitrate_fee = bitrate_fee;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getSongname() {
            return songname;
        }

        public void setSongname(String songname) {
            this.songname = songname;
        }

        public String getResource_type() {
            return resource_type;
        }

        public void setResource_type(String resource_type) {
            this.resource_type = resource_type;
        }

        public String getSongid() {
            return songid;
        }

        public void setSongid(String songid) {
            this.songid = songid;
        }

        public String getHas_mv() {
            return has_mv;
        }

        public void setHas_mv(String has_mv) {
            this.has_mv = has_mv;
        }

        public String getYyr_artist() {
            return yyr_artist;
        }

        public void setYyr_artist(String yyr_artist) {
            this.yyr_artist = yyr_artist;
        }

        public String getResource_type_ext() {
            return resource_type_ext;
        }

        public void setResource_type_ext(String resource_type_ext) {
            this.resource_type_ext = resource_type_ext;
        }

        public String getArtistname() {
            return artistname;
        }

        public void setArtistname(String artistname) {
            this.artistname = artistname;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getResource_provider() {
            return resource_provider;
        }

        public void setResource_provider(String resource_provider) {
            this.resource_provider = resource_provider;
        }

        public String getControl() {
            return control;
        }

        public void setControl(String control) {
            this.control = control;
        }

        public String getEncrypted_songid() {
            return encrypted_songid;
        }

        public void setEncrypted_songid(String encrypted_songid) {
            this.encrypted_songid = encrypted_songid;
        }
    }
}
