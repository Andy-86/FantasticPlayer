package com.example.andy.player.mvp.remote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andy.player.R;
import com.example.andy.player.activity.HotSonglistDetailAcitvity;
import com.example.andy.player.adapter.RemoteListAdapter;
import com.example.andy.player.aidl.SongBean;
import com.example.andy.player.bean.SongListInfo;
import com.example.andy.player.mvp.base.MvpFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by andy on 2017/12/1.
 */

public class RemoteMusicFragment extends MvpFragment<RemotePresenter> {
    public static final String HOTSONG_EXTRA="hotsongExtract";
    public static final String SONG_INFO="songInfo";
    List<SongBean> baiduHotSongList;
    List<SongBean> baiduNewSongList;
    List<SongBean> chineseGoldenMelodyList;
    List<SongBean> euramerican;
    List<SongBean> goldenSongList;
    List<SongBean> loveSongsToTheList;
    List<SongBean> onlineSongList;
    List<SongBean> listOfClassicOldSongs;
    List<SongBean> listOfRock;
    List<SongBean> ktvHotSongList;
    List<SongBean> Billboard;
    List<SongBean> hitoChineseList;
    List<SongBean> powerSongList;
    @BindView(R.id.lv_playlist)
    RecyclerView lvPlaylist;
    @BindView(R.id.tv_loading_text)
    TextView tvLoadingText;
    @BindView(R.id.tv_load_fail_text)
    TextView tvLoadFailText;
    Unbinder unbinder;
    private List<SongListInfo> listInfos=new ArrayList<>();
    private RemoteListAdapter listAdapter;
    public int loadcoutn=0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public RemotePresenter createPresenter() {
        return new RemotePresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    protected void initEvent() {
        RemoteListAdapter.OnRItemClickListner listner=new RemoteListAdapter.OnRItemClickListner() {
            @Override
            public void onClick(SongListInfo info) {
                List<SongBean> hotlist=getHotSongList(info.type);
                if(hotlist!=null){
                    Intent intent=new Intent(getActivity(), HotSonglistDetailAcitvity.class);
                    intent.putExtra(HOTSONG_EXTRA,(Serializable)hotlist);
                    intent.putExtra(SONG_INFO,info);
                    startActivity(intent);
                }
            }
        };
        listAdapter.setListner(listner);
    }

    @Override
    protected void initData() {
        //加载歌曲榜单
        initSonglistInfo();
        lvPlaylist.setLayoutManager(new LinearLayoutManager(getContext()));
        listAdapter=new RemoteListAdapter(getContext(),listInfos);
        lvPlaylist.setAdapter(listAdapter);
       mPresenter.getSonglit(2);
       mPresenter.getSonglit(1);
       mPresenter.getSonglit(20);
       mPresenter.getSonglit(21);
       mPresenter.getSonglit(23);
       mPresenter.getSonglit(22);
       mPresenter.getSonglit(25);
       mPresenter.getSonglit(11);
       mPresenter.getSonglit(6);
       mPresenter.getSonglit(7);
       mPresenter.getSonglit(8);
        mPresenter.getSonglit(18);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//  <item>主打榜单</item>
//        <item>百度热歌榜</item>
//        <item>百度新歌榜</item>
//        <item>分类榜单</item>
//        <item>华语金曲榜</item>
//        <item>欧美金曲榜</item>
//        <item>影视金曲榜</item>
//        <item>情歌对唱榜</item>
//        <item>网络歌曲榜</item>
//        <item>经典老歌榜</item>
//        <item>摇滚榜</item>
//        <item>媒体榜单</item>
//        <item>KTV热歌榜</item>
//        <item>Billboard</item>
//        <item>Hito中文榜</item>
//        <item>叱咤歌曲榜</item>

//     <item>#</item>
//        <item>2</item>
//        <item>1</item>
//        <item>#</item>
//        <item>20</item>
//        <item>21</item>
//        <item>24</item>
//        <item>23</item>
//        <item>25</item>
//        <item>22</item>
//        <item>11</item>
//        <item>#</item>
//        <item>6</item>
//        <item>8</item>
//        <item>18</item>
//        <item>7</item>
    public void returnsonglist(int tipid, List<SongBean> list){
        switch (tipid){
            case 2:
                //转换
                baiduHotSongList=list;
                insertIntoListInfo(tipid,list);
                break;
            case 1:
                //转换
               baiduNewSongList=list;
                insertIntoListInfo(tipid,list);
                break;
            case 20:
                //转换
              chineseGoldenMelodyList=list;
                insertIntoListInfo(tipid,list);
                break;
            case 21:
                //转换
               euramerican=list;
                insertIntoListInfo(tipid,list);
                break;
            case 24:
                //转换
               goldenSongList=list;
                insertIntoListInfo(tipid,list);
                break;
            case 23:
                //转换
               loveSongsToTheList=list;
                insertIntoListInfo(tipid,list);
                break;
            case 25:
                //转换
                onlineSongList=list;
                insertIntoListInfo(tipid,list);
                break;
            case 22:
                //转换
               listOfClassicOldSongs=list;
                insertIntoListInfo(tipid,list);
                break;
            case 11:
                //转换
               listOfRock=list;
                insertIntoListInfo(tipid,list);
                break;
            case 6:
                //转换
               ktvHotSongList=list;
                insertIntoListInfo(tipid,list);
                break;
            case 8:
                //转换
                Billboard=list;
                insertIntoListInfo(tipid,list);
                break;
            case 18:
                //转换
                hitoChineseList=list;
                insertIntoListInfo(tipid,list);
                break;
            case 7:
                //转换
                powerSongList=list;
                insertIntoListInfo(tipid,list);
                break;

        }
        loadcoutn++;
        if(loadcoutn==13)
            finishload();
    }

    public List<SongBean> getHotSongList(int tipid) {
        switch (tipid){
            case 2:
                //转换
                return baiduHotSongList;

            case 1:
                //转换
                return baiduNewSongList;

            case 20:
                //转换
                return chineseGoldenMelodyList;

            case 21:
                //转换
                return euramerican;

            case 24:
                //转换
                return goldenSongList;

            case 23:
                //转换
                return loveSongsToTheList;

            case 25:
                //转换
                return onlineSongList;

            case 22:
                //转换
                return listOfClassicOldSongs;

            case 11:
                //转换
                return listOfRock;

            case 6:
                //转换
                return ktvHotSongList;

            case 8:
                //转换
                return Billboard;

            case 18:
                //转换
                return hitoChineseList;

            case 7:
                //转换
                return powerSongList;



        }
        return null;
    }
    public void finishload(){

    }

    public void initSonglistInfo(){
        int i=0;
        String[] titles = getResources().getStringArray(R.array.online_music_list_title);
        String[] types = getResources().getStringArray(R.array.online_music_list_type);
        for(String title:titles){
            SongListInfo info=new SongListInfo();
            if(!types[i].equals("#")){
                info.isSonglist=true;
                info.Title=title;
                info.type=Integer.valueOf(types[i]);
            }else {
                info.isSonglist=false;
                info.Title=title;
                info.type=0;
                info.isFinishload=false;
            }
            listInfos.add(info);
            i++;
        }
    }

    public void insertIntoListInfo(int tipid,List<SongBean> list){
        for(SongListInfo info:listInfos){
            if(info.type==tipid){
                info.firsSongname=list.get(0).getSongname();
                info.firsSingername=list.get(0).getSingername();
                info.secondSongname=list.get(1).getSongname();
                info.secondSingername=list.get(1).getSingername();
                info.thirdSongname=list.get(2).getSongname();
                info.thirdSingername=list.get(2).getSingername();
                info.imageUrl=list.get(0).getAlbumpic_big();
                listAdapter.notifyDataSetChanged();
                break;
            }
        }
    }



}
