package com.example.andy.player.mvp.paly;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.andy.player.R;
import com.example.andy.player.activity.MusicAcitivity;
import com.example.andy.player.aidl.IMusicPlayer;
import com.example.andy.player.aidl.MusicPlayListner;
import com.example.andy.player.aidl.SongBean;
import com.example.andy.player.application.MyApplication;
import com.example.andy.player.interfaces.IplayStatus;
import com.example.andy.player.mvp.base.MvpFragment;
import com.example.andy.player.service.MusicService;
import com.example.andy.player.tools.CoverLoader;
import com.example.andy.player.tools.DiskDimenUtils;
import com.example.andy.player.tools.LogUtil;
import com.example.andy.player.tools.SongEvent;
import com.example.andy.player.weight.Dislayout;
import com.example.andy.player.weight.DisplayLayout;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.wcy.lrcview.LrcView;

/**
 * Created by andy on 2017/12/8.
 */

@SuppressLint("ValidFragment")
public class PlayFragment extends MvpFragment<PlayPresnter> implements IplayStatus {

    private static final String TAG = "PlayFragment";
    Dislayout dislayout;
    LrcView lrcView;
    @BindView(R.id.play_disc_backgound)
    ImageView playDiscBackgound;
    @BindView(R.id.play_discContain)
    ViewPager playDiscContain;
    @BindView(R.id.play_needle)
    ImageView playNeedle;
    @BindView(R.id.tvCurrentTime)
    TextView tvCurrentTime;
    @BindView(R.id.musicSeekBar)
    SeekBar musicSeekBar;
    @BindView(R.id.tvTotalTime)
    TextView tvTotalTime;
    @BindView(R.id.rlMusicTime)
    RelativeLayout rlMusicTime;
    @BindView(R.id.ivCi)
    ImageView ivCi;
    @BindView(R.id.ivLast)
    ImageView ivLast;
    @BindView(R.id.ivPlayOrPause)
    ImageView ivPlayOrPause;
    @BindView(R.id.ivNext)
    ImageView ivNext;
    @BindView(R.id.ivMenu)
    ImageView ivMenu;
    Unbinder unbinder1;
    @BindView(R.id.set_freground)
    DisplayLayout setFreground;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_artist)
    TextView tvArtist;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.play_dis_layout)
    Dislayout playDisLayout;
    @BindView(R.id.llPlayOption)
    LinearLayout llPlayOption;


    private SongEvent songEvent;
    private List<SongBean> songList;
    private Dislayout.MusicStatus musicStatus = Dislayout.MusicStatus.PAUSE;//设置初始化状态为暂停
    private View contain;
    private Bitmap[] bitmaps = new Bitmap[3];
    private int index = 0;
    private String[] m4as = {"http://ws.stream.qqmusic.qq.com/200790315.m4a?fromtag=46",
            "http://ws.stream.qqmusic.qq.com/7416139.m4a?fromtag=46",
            "http://ws.stream.qqmusic.qq.com/7168586.m4a?fromtag=46"};
    private Unbinder unbinder;
    private IMusicPlayer mMusicService=MyApplication.myApplication.getMusicPlayerService();
    private SimpleDateFormat mFormatter = new SimpleDateFormat("mm:ss");
    private MusicPlayListner listner = new MusicPlayListner.Stub() {
        @Override
        public void action(int actioncode, Message message) throws RemoteException {
            mHandler.sendMessage(message);
        }
    };
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MusicService.MUSIC_ACTION_SEEK_PLAY:
                    if (musicSeekBar!=null&&!musicSeekBar.isPressed())
                        updateProgerss(msg);
                    break;
                case MusicService.MUSIC_ACTION_PLAY:
                    LogUtil.doLog("handleMessage","Action_play");
                    break;
                case MusicService.MUSIC_ACTION_COMPLETE:
                    LogUtil.doLog("handleMessage", "COMPLETE");
                    ivNext.performClick();
                    break;
                case MusicService.MUSIC_ACTION_PAUSE:
                    LogUtil.doLog("handleMessage","Aciton_pause");
                    break;
                case MusicService.MUSIC_ACTION_CONTINUE_PLAY:
                    LogUtil.doLog("handleMessage","Action_Continute_Play");
                default:
                    super.handleMessage(msg);
            }

        }
    };

    public PlayFragment(SongEvent songEvent){
        super();
        this.songEvent=songEvent;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            mMusicService.registListner(listner);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        View view = inflater.inflate(R.layout.activity_main, container, false);
        unbinder1 = ButterKnife.bind(this, view);
        contain = view;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(songEvent!=null){
            activityCallPlay(songEvent);
        }
    }

    //更新进度条
    private void updateProgerss(Message msg) {
        LogUtil.doLog("updateProgerssInMain", "");
        int currentPosition = msg.arg1;
        int totalDuration = msg.arg2;
        lrcView.updateTime(currentPosition);
        tvTotalTime.setText(mFormatter.format(totalDuration));
        tvCurrentTime.setText(mFormatter.format(currentPosition));
        musicSeekBar.setMax(totalDuration);
        musicSeekBar.setProgress(currentPosition);
    }

    //初始化数据
    @Override
    protected void initData() {

        //隐藏状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }

        mPresenter.getSongs();
        dislayout = (Dislayout) contain.findViewById(R.id.play_dis_layout);
        dislayout.setpStatus(this);
        lrcView = (LrcView) contain.findViewById(R.id.lrc_view);
        Log.d(TAG, "onCreate: " + System.currentTimeMillis());
        LogUtil.doLog("onCreate", "------------" + mMusicService.toString());



        lrcView.loadLrc(getLrcText("chengdu.lrc"));
        lrcView.updateTime(0);
    }




    @Override
    public void initEvent() {
        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch: " + seekBar.getProgress());
                seekPlaySong(seekBar.getProgress());
            }
        });


        ivCi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dislayout.getVisibility() == View.VISIBLE) {
                    dislayout.setVisibility(View.INVISIBLE);
                    lrcView.setVisibility(View.VISIBLE);
                } else {
                    dislayout.setVisibility(View.VISIBLE);
                    lrcView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void seekPlaySong(int progress) {
        SongBean songBean = new SongBean();
        songBean.setProgress(progress);
        try {
            mMusicService.action(MusicService.MUSIC_ACTION_SEEK_PLAY, songBean);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.ivLast, R.id.ivPlayOrPause, R.id.ivNext,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivLast:
                if (index <songList.size()) {
                    index--;
                    LogUtil.doLog("onViewClicked", "IVLAST" + index);
//                    setFreground.setForeground(DiskDimenUtils.getForegroundDrawable(bitmaps[index], getActivity()));
//                    setFreground.beginAnimation();
                    dislayout.toLast();
                    musicSeekBar.setProgress(0);
                    pause();
                    palyOrPause();

                }
                break;
            case R.id.ivPlayOrPause:
                palyOrPause();
                break;
            case R.id.ivNext:
                if (index <songList.size()-1) {
                    index++;
                    LogUtil.doLog("onViewClicked", "IVNEXT" + index);
//                    setFreground.setForeground(DiskDimenUtils.getForegroundDrawable(bitmaps[index], getActivity()));
//                    setFreground.beginAnimation();
                    dislayout.toNext();
                    musicSeekBar.setProgress(0);
                    pause();
                    palyOrPause();
                }
                break;
            case R.id.iv_back:
                ((MusicAcitivity)getActivity()).hidePlayingFragment();
                break;
        }
    }


    @Override
    public void toNext() {
        if (index <songList.size()-1) {
            index++;
            LogUtil.doLog("onViewClicked", "IVNEXT" + index);
//                    setFreground.setForeground(DiskDimenUtils.getForegroundDrawable(bitmaps[index], getActivity()));
//                    setFreground.beginAnimation();
            dislayout.toNext();
            musicSeekBar.setProgress(0);
            pause();
            palyOrPause();
        }
    }

    @Override
    public void toLast() {
        if (index >0) {
            index--;
            LogUtil.doLog("onViewClicked", "IVLAST" + index);
//                    setFreground.setForeground(DiskDimenUtils.getForegroundDrawable(bitmaps[index], getActivity()));
//                    setFreground.beginAnimation();
            dislayout.toLast();
            musicSeekBar.setProgress(0);
            pause();
            palyOrPause();
        }
    }

    @Override
    public void pause() {
        if (musicStatus == Dislayout.MusicStatus.PLAY) {
            ivPlayOrPause.setImageResource(R.drawable.ic_play);
            musicStatus = Dislayout.MusicStatus.PAUSE;
            dislayout.doPause();
        }
    }

    @Override
    public void play() {
        LogUtil.doLog("CallbackTOPlay", "" + musicStatus);
        if (musicStatus == Dislayout.MusicStatus.PAUSE) {
            ivPlayOrPause.setImageResource(R.drawable.ic_pause);
            musicStatus = Dislayout.MusicStatus.PLAY;
            dislayout.doPlay();


        }
    }

    public void palyOrPause() {
        if (musicStatus == Dislayout.MusicStatus.PLAY) {
            ivPlayOrPause.setImageResource(R.drawable.ic_play);
            musicStatus = Dislayout.MusicStatus.PAUSE;
            dislayout.doPause();
            try {
                mMusicService.action(MusicService.MUSIC_ACTION_PAUSE, null);
            } catch (RemoteException e) {
                e.printStackTrace();
            }


        } else if (musicStatus == Dislayout.MusicStatus.PAUSE) {
            ivPlayOrPause.setImageResource(R.drawable.ic_pause);
            musicStatus = Dislayout.MusicStatus.PLAY;
            dislayout.doPlay();
            if (musicSeekBar.getProgress() == 0)
                starPalyMusic(songList.get(index));
            else
                continuePlayMusic();

        }

    }

    /**
     * 假如进度是0就直接开始
     */
    public void starPalyMusic(SongBean songBean) {
        setSongbeanToTitle(songBean);
        try {
            mMusicService.action(MusicService.MUSIC_ACTION_PLAY, songBean);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        //设置背景
        Bitmap cover = CoverLoader.getInstance().loadThumbnail(songBean);
        setFreground.setForeground(DiskDimenUtils.getForegroundDrawable(cover, getActivity()));
        setFreground.beginAnimation();
    }

    /**
     * 假如进度大于0就continue
     */
    public void continuePlayMusic() {
        SongBean b = new SongBean();
        try {
            mMusicService.action(MusicService.MUSIC_ACTION_CONTINUE_PLAY, b);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找歌词文件
     *
     * @param fileName
     * @return
     */
    private String getLrcText(String fileName) {
        String lrcText = null;
        try {
            InputStream is = getActivity().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            lrcText = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.doLog("getLrcText", "" + lrcText);
        return lrcText;
    }

    @Override
    public PlayPresnter createPresenter() {
        return new PlayPresnter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();

    }


    public void activityCallPlay(SongEvent event) {
        SongBean  songBean = event.getSongBean();
        starPalyMusic(songBean);
        Log.d(TAG, "onEventMain: "+songBean.toString());
//        Bitmap cover = CoverLoader.getInstance().loadThumbnail(event.getSongBean());
//        setFreground.setForeground(DiskDimenUtils.getForegroundDrawable(cover, getActivity()));
//        setFreground.beginAnimation();

        //设置转盘的图片
        dislayout.setImageResourse((ArrayList<SongBean>) songList,songList.indexOf(songEvent.getSongBean()));
        dislayout.doPlay();
        int index=songList.indexOf(songBean);
        this.index=index;
    }

    public void activityCallPlayNext(SongBean songBean) {
        int index=songList.indexOf(songBean);
        starPalyMusic(songBean);
        Log.d(TAG, "onEventMain: "+songBean.toString());
//        Bitmap cover = CoverLoader.getInstance().loadThumbnail(songBean);
//        setFreground.setForeground(DiskDimenUtils.getForegroundDrawable(cover, getActivity()));
//        setFreground.beginAnimation();
        dislayout.setcurrentItem(index);
        this.index=index;
    }

    public void setSongList(List<SongBean> songList){
        this.songList =songList;
    }

    public void setSongbeanToTitle(SongBean songBean){
        tvArtist.setText(songBean.getSingername());
        tvTitle.setText(songBean.getSongname());
    }

    public SongBean getTheCurrentSong(){
        if(songList!=null&&index<songList.size()) {
            return songList.get(index);
        }else
            return null;
    }

}
