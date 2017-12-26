package com.example.andy.player.mvp.search;

import com.example.andy.player.bean.AbstractResultUtil;
import com.example.andy.player.bean.SearchResult;
import com.example.andy.player.mvp.base.BasePresenter;
import com.example.andy.player.tools.LogUtil;
import com.example.andy.player.tools.RetrofitUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by andy on 2017/12/25.
 */

public class SearchPresenter extends BasePresenter<SearchMusicActivity,SearchModle>{
    @Override
    public SearchModle createModel() {
        return new SearchModle(this);
    }

    public void searchResult(String keyword,int page){
        mModel.searchReuslt(new Observer<AbstractResultUtil<SearchResult>>() {
            @Override
            public void onSubscribe(Disposable d) {
                RetrofitUtil.addDisposable(d);
            }

            @Override
            public void onNext(AbstractResultUtil<SearchResult> value) {
               LogUtil.doLog("onNext",""+ value.getShowapi_res_body().getPagebean().getContentlist().get(0));
               mView.ongetSearchResult(value.getShowapi_res_body().getPagebean().getContentlist());
            }
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        },keyword,page);
    }
}
