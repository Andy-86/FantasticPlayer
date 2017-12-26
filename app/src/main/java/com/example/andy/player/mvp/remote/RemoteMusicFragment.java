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
import com.example.andy.player.bean.HotSong;
import com.example.andy.player.bean.SongListInfo;
import com.example.andy.player.mvp.base.MvpFragment;
import com.example.andy.player.tools.Transformer;

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
    List<SongBean> popularMusicList;
    List<SongBean> hotMusicList;
    List<SongBean> newSongList;
    List<SongBean> theMainlandMusicList;
    List<SongBean> europeanAndAmericanMusic;
    List<SongBean> hongKongTableMusic;
    List<SongBean> southKoreaSMusicList;
    List<SongBean> japanSMusic;
    List<SongBean> netSongs;
    List<SongBean> kSongMusicList;
    List<SongBean> musician;
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
       mPresenter.getSonglit(4);
       mPresenter.getSonglit(27);
       mPresenter.getSonglit(26);
       mPresenter.getSonglit(5);
       mPresenter.getSonglit(3);
       mPresenter.getSonglit(6);
       mPresenter.getSonglit(16);
       mPresenter.getSonglit(17);
       mPresenter.getSonglit(28);
       mPresenter.getSonglit(36);
       mPresenter.getSonglit(32);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 榜行榜id
     3=欧美
     4=流行榜
     5=内地
     6=港台
     16=韩国
     17=日本
     26=热歌
     27=新歌
     28=网络歌曲
     32=音乐人
     36=K歌金曲
     * @param tipid
     * @param list
     */
    public void returnsonglist(int tipid, List<HotSong> list){
        switch (tipid){
            case 4:
                //转换
                popularMusicList= Transformer.transfromToSongBean(list);
                insertIntoListInfo(4,popularMusicList);
                break;
            case 27:
                //转换
                hotMusicList= Transformer.transfromToSongBean(list);
                insertIntoListInfo(27,hotMusicList);
                break;
            case 26:
                //转换
                newSongList= Transformer.transfromToSongBean(list);
                insertIntoListInfo(26,newSongList);
                break;
            case 5:
                //转换
                theMainlandMusicList= Transformer.transfromToSongBean(list);
                insertIntoListInfo(5,theMainlandMusicList);
                break;
            case 3:
                //转换
                europeanAndAmericanMusic= Transformer.transfromToSongBean(list);
                insertIntoListInfo(3,europeanAndAmericanMusic);
                break;
            case 6:
                //转换
                hongKongTableMusic= Transformer.transfromToSongBean(list);
                insertIntoListInfo(6,hongKongTableMusic);
                break;
            case 16:
                //转换
                southKoreaSMusicList= Transformer.transfromToSongBean(list);
                insertIntoListInfo(16,southKoreaSMusicList);
                break;
            case 17:
                //转换
                japanSMusic= Transformer.transfromToSongBean(list);
                insertIntoListInfo(17,japanSMusic);
                break;
            case 28:
                //转换
                netSongs= Transformer.transfromToSongBean(list);
                insertIntoListInfo(28,netSongs);
                break;
            case 36:
                //转换
                kSongMusicList= Transformer.transfromToSongBean(list);
                insertIntoListInfo(36,kSongMusicList);
                break;
            case 32:
                //转换
                musician= Transformer.transfromToSongBean(list);
                insertIntoListInfo(32,musician);
                break;

        }
        loadcoutn++;
        if(loadcoutn==11)
            finishload();

    }

    public List<SongBean> getHotSongList(int tipid) {
        switch (tipid) {
            case 4:
                //转换
                return popularMusicList;


            case 27:
                //转换
                return hotMusicList;
            case 26:
                //转换
                return newSongList;

            case 5:
                //转换
                return theMainlandMusicList;

            case 3:
                //转换
                return europeanAndAmericanMusic;

            case 6:
                //转换
                return hongKongTableMusic;

            case 16:
                //转换
                return southKoreaSMusicList;

            case 17:
                //转换
                return japanSMusic;

            case 28:
                //转换
                return netSongs;

            case 36:
                //转换
                return kSongMusicList;

            case 32:
                //转换
                return musician;

            default:
                return null;

        }
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
