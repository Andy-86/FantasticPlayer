package com.example.andy.player.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.andy.player.R;
import com.example.andy.player.adapter.HotSongListAdapter;
import com.example.andy.player.aidl.SongBean;
import com.example.andy.player.bean.BaseActivity;
import com.example.andy.player.bean.SongListInfo;
import com.example.andy.player.mvp.remote.RemoteMusicFragment;
import com.example.andy.player.tools.LogUtil;
import com.example.andy.player.tools.SongEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotSonglistDetailAcitvity extends BaseActivity {

    List<SongBean> songBeanList;
    List<SongBean> recycleList;
    SongListInfo info;
    @BindView(R.id.disease_detail_image)
    ImageView detailImage;
    @BindView(R.id.hotsong_songList)
    RecyclerView hotsongRecycleList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    HotSongListAdapter hotSongListAdapter;
    HotSongListAdapter.OnRItemClickListner listner;
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_hot_songlist_detail_acitvity;
    }

    @Override
    protected void initData() {
        super.initData();

        //隐藏状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
           getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }


        ButterKnife.bind(this);
        songBeanList = (ArrayList<SongBean>) getIntent().getSerializableExtra(RemoteMusicFragment.HOTSONG_EXTRA);
        info = getIntent().getParcelableExtra(RemoteMusicFragment.SONG_INFO);
        if (info!=null)
        initToolbar(info.Title,true,null);
        recycleList=new ArrayList<>();
        if(songBeanList.size()>=20){
        for(int i=0;i<20;i++){
            recycleList.add(songBeanList.get(i));
        }}else {
            for(int i=0;i<songBeanList.size();i++){
                recycleList.add(songBeanList.get(i));
            }
        }
        Glide.with(this)
                .load(info.imageUrl)
                .into(detailImage);
        hotSongListAdapter=new HotSongListAdapter(this,recycleList);
        hotsongRecycleList.setLayoutManager(new LinearLayoutManager(this));
        hotsongRecycleList.setAdapter(hotSongListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        listner=new HotSongListAdapter.OnRItemClickListner() {
            @Override
            public void onClick(SongBean songBean) {
                LogUtil.doLog("onClick","doClick");
                EventBus.getDefault().post(new SongEvent(songBean));
                fin();
            }
        };
        hotSongListAdapter.setListner(listner);
    }

    public void fin(){
        finish();
    }
}
