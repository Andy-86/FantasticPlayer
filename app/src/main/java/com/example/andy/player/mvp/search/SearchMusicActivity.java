package com.example.andy.player.mvp.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andy.player.R;
import com.example.andy.player.adapter.SearchListAdapter;
import com.example.andy.player.aidl.SongBean;
import com.example.andy.player.mvp.base.MvpActivity;
import com.example.andy.player.tools.ToastUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchMusicActivity extends MvpActivity<SearchPresenter> implements SearchView.OnQueryTextListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv_search_music_list)
    RecyclerView lvSearchMusicList;
    @BindView(R.id.tv_loading_text)
    TextView tvLoadingText;
    @BindView(R.id.tv_load_fail_text)
    TextView tvLoadFailText;


    private List<SongBean> searchResult;
    private SearchListAdapter adapter;
    int page=1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        ButterKnife.bind(this);
    }
    @Override
    public SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_search_music;
    }

    @Override
    protected void initData() {
        super.initData();
        searchResult=new ArrayList<>();
        adapter=new SearchListAdapter(this,searchResult);
        lvSearchMusicList.setLayoutManager(new LinearLayoutManager(this));
        lvSearchMusicList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_music, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.onActionViewExpanded();
        searchView.setQueryHint(getString(R.string.search_tips));
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        try {
            Field field = searchView.getClass().getDeclaredField("mGoButton");
            field.setAccessible(true);
            ImageView mGoButton = (ImageView) field.get(searchView);
            mGoButton.setImageResource(R.drawable.ic_menu_search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mPresenter.searchResult(query,page);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void initView() {
        if (toolbar == null) {
            throw new IllegalStateException("Layout is required to include a Toolbar with id 'toolbar'");
        }
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void ongetSearchResult(List<SongBean> list){
        if(tvLoadFailText.getVisibility()==View.VISIBLE){
            tvLoadFailText.setVisibility(View.GONE);
            lvSearchMusicList.setVisibility(View.VISIBLE);
        }
        searchResult.clear();
        searchResult.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void loadFail(){
        tvLoadFailText.setVisibility(View.VISIBLE);
        lvSearchMusicList.setVisibility(View.INVISIBLE);
        ToastUtil.Toast("加载失败");
    }
}
