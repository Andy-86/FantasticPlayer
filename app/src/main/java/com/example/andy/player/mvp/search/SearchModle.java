package com.example.andy.player.mvp.search;

import com.example.andy.player.bean.AbstractResultUtil;
import com.example.andy.player.bean.SearchResult;
import com.example.andy.player.contract.Contract;
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

    public void searchReuslt(Observer<AbstractResultUtil<SearchResult>> observer, String keyword,int page){
        Retrofit retrofit= RetrofitUtil.getMusicRetrofit();
        SearchApi api=retrofit.create(SearchApi.class);
        api.searchResult(Contract.appid,Contract.sign,System.currentTimeMillis(),keyword,page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
