package com.example.andy.player.bean;

import com.example.andy.player.aidl.SongBean;

import java.util.List;

/**
 * Created by andy on 2017/12/25.
 */

public class SearchResult extends AbstractClass {

    /**
     * ret_code : 0
     * pagebean : {"w":"海阔天空","allPages":8,"ret_code":0,"contentlist":[]}
     */

    private int ret_code;
    private PagebeanBean pagebean;

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public PagebeanBean getPagebean() {
        return pagebean;
    }

    public void setPagebean(PagebeanBean pagebean) {
        this.pagebean = pagebean;
    }

    public static class PagebeanBean {
        /**
         * w : 海阔天空
         * allPages : 8
         * ret_code : 0
         * contentlist : []
         */

        private String w;
        private int allPages;
        private int ret_code;
        private List<SongBean> contentlist;

        public String getW() {
            return w;
        }

        public void setW(String w) {
            this.w = w;
        }

        public int getAllPages() {
            return allPages;
        }

        public void setAllPages(int allPages) {
            this.allPages = allPages;
        }

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public List<SongBean> getContentlist() {
            return contentlist;
        }

        public void setContentlist(List<SongBean> contentlist) {
            this.contentlist = contentlist;
        }
    }
}
