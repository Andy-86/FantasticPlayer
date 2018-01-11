package com.example.andy.player.mvp.search;

import com.example.andy.player.bean.SearchBean;
import com.example.andy.player.mvp.base.BaseModel;
import com.example.andy.player.mvp.search.api.SearchApi;
import com.example.andy.player.tools.RetrofitUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by andy on 2017/12/25.
 */

public class SearchModle extends BaseModel<SearchPresenter>{
    public SearchModle(SearchPresenter presenter) {
        super(presenter);
    }

    public void searchReuslt(Observer<SearchBean> observer, String keyword, int page){
        Retrofit retrofit= RetrofitUtil.getMusicRetrofit();
        SearchApi api=retrofit.create(SearchApi.class);
        api.searchResult("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)","json","","webapp_music","baidu.ting.search.catalogSug",keyword)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
