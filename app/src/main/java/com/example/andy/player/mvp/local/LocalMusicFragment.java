package com.example.andy.player.mvp.local;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andy.player.R;
import com.example.andy.player.adapter.LocalMusicAdapter;
import com.example.andy.player.aidl.SongBean;
import com.example.andy.player.application.MyApplication;
import com.example.andy.player.mvp.base.MvpFragment;
import com.example.andy.player.tools.LogUtil;
import com.example.andy.player.tools.MusicUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by andy on 2017/12/1.
 */

public class LocalMusicFragment extends MvpFragment<LocalPresenter> {
    @BindView(R.id.lv_local_music)
    RecyclerView lvLocalMusic;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    Unbinder unbinder;
    LocalMusicAdapter adapter;
    List<SongBean> mlist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_music, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public LocalPresenter createPresenter() {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void updateMusicList() {
        LogUtil.doLog("granted","开始扫描");
        new AsyncTask<Void,Void,List<SongBean>>(){
            @Override
            protected List<SongBean> doInBackground(Void... parme) {
                return MusicUtil.scanMusic(MyApplication.getContext());
            }

            @Override
            protected void onPostExecute(List<SongBean> list) {
                LogUtil.doLog("onPostExecute",""+list.get(0));
                if(adapter==null) {
                    adapter = new LocalMusicAdapter(getActivity(), list);
                    mlist = list;
                    lvLocalMusic.setLayoutManager(new LinearLayoutManager(getActivity()));
                    lvLocalMusic.setAdapter(adapter);
                }

            }
        }.execute();
    }

    public List<SongBean> getList(){
        return mlist;
    }
}
