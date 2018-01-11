package com.example.andy.player;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.andy.player.aidl.IMusicPlayer;
import com.example.andy.player.aidl.MusicPlayListner;
import com.example.andy.player.aidl.SongBean;
import com.example.andy.player.application.MyApplication;
import com.example.andy.player.interfaces.IplayStatus;
import com.example.andy.player.mvp.base.MvpActivity;
import com.example.andy.player.mvp.paly.PlayPresnter;
import com.example.andy.player.service.MusicService;
import com.example.andy.player.tools.DiskDimenUtils;
import com.example.andy.player.tools.LogUtil;
import com.example.andy.player.weight.Dislayout;
import com.example.andy.player.weight.DisplayLayout;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.wcy.lrcview.LrcView;

public class MainActivity extends MvpActivity<PlayPresnter> implements IplayStatus {
    private static final String TAG = "MainActivity";
    public Handler handler;
    @BindView(R.id.ivCi)
    ImageView ivCi;
    @BindView(R.id.ivMenu)
    ImageView ivMenu;
    private Dislayout.MusicStatus musicStatus = Dislayout.MusicStatus.PAUSE;
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
    @BindView(R.id.ivLast)
    ImageView ivLast;
    @BindView(R.id.ivPlayOrPause)
    ImageView ivPlayOrPause;
    @BindView(R.id.ivNext)
    ImageView ivNext;
    @BindView(R.id.llPlayOption)
    LinearLayout llPlayOption;
    @BindView(R.id.set_freground)
    DisplayLayout setFreground;
    Dislayout dislayout;
    LrcView lrcView;
    private Bitmap[] bitmaps = new Bitmap[3];
    private int index = 0;
    private String[] m4as = {"com.example.andy.player.http://ws.stream.qqmusic.qq.com/200790315.m4a?fromtag=46",
            "com.example.andy.player.http://ws.stream.qqmusic.qq.com/7416139.m4a?fromtag=46",
            "com.example.andy.player.http://ws.stream.qqmusic.qq.com/7168586.m4a?fromtag=46"};
    private Unbinder unbinder;
    private IMusicPlayer mMusicService;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("mm:ss");
    private MusicPlayListner listner = new MusicPlayListner.Stub() {
        @Override
        public void action(int actioncode, Message message,SongBean songBean) throws RemoteException {
            mHandler.sendMessage(message);
        }
    };
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MusicService.MUSIC_ACTION_SEEK_PLAY:
                    if (!musicSeekBar.isPressed())
                        updateProgerss(msg);
                    break;
                case MusicService.MUSIC_ACTION_PLAY:

                    break;
                case MusicService.MUSIC_ACTION_COMPLETE:
                    LogUtil.doLog("handleMessage", "COMPLETE");
                    ivNext.performClick();
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    };

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
        }else
        {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }

        mPresenter.getSongs();
        dislayout = (Dislayout) findViewById(R.id.play_dis_layout);
        dislayout.setpStatus(this);
        lrcView = (LrcView) findViewById(R.id.lrc_view);
        Log.d(TAG, "onCreate: " + System.currentTimeMillis());
        unbinder = ButterKnife.bind(this);
        //使用异步线程加载Bitmap
        loadBitmap();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMusicService = MyApplication.myApplication.getMusicPlayerService();
                LogUtil.doLog("onCreate", "------------" + mMusicService.toString());
                try {
                    mMusicService.registListner(listner);
                    setFreground.setForeground(DiskDimenUtils.getForegroundDrawable(bitmaps[0], getApplicationContext()));
                    setFreground.beginAnimation();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 1000);

        lrcView.loadLrc(getLrcText("chengdu.lrc"));
        lrcView.updateTime(0);
    }


    public void loadBitmap(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bitmaps[0] = Glide.with(getApplicationContext()).load(R.mipmap.ic_music1).asBitmap().centerCrop().into(100,100).get();
                    bitmaps[1] = Glide.with(getApplicationContext()).load(R.mipmap.ic_music2).asBitmap().centerCrop().into(100,100).get();
                    bitmaps[2] = Glide.with(getApplicationContext()).load(R.mipmap.ic_music3).asBitmap().centerCrop().into(100,100).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }


    @Override
    public void initEvents() {
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
                if(dislayout.getVisibility()==View.VISIBLE)
                {
                    dislayout.setVisibility(View.INVISIBLE);
                    lrcView.setVisibility(View.VISIBLE);
                }else{
                    dislayout.setVisibility(View.VISIBLE);
                    lrcView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void seekPlaySong(int progress) {
        SongBean songBean = new SongBean();
        songBean.setM4a(m4as[index]);
        songBean.setProgress(progress);
        try {
            mMusicService.action(MusicService.MUSIC_ACTION_SEEK_PLAY, songBean);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.ivLast, R.id.ivPlayOrPause, R.id.ivNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivLast:
                if (index != 0) {
                    index--;
                    LogUtil.doLog("onViewClicked", "IVLAST" + index);
                    setFreground.setForeground(DiskDimenUtils.getForegroundDrawable(bitmaps[index], this));
                    setFreground.beginAnimation();
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
                if (index != 2) {
                    index++;
                    LogUtil.doLog("onViewClicked", "IVNEXT" + index);
                    setFreground.setForeground(DiskDimenUtils.getForegroundDrawable(bitmaps[index], this));
                    setFreground.beginAnimation();
                    dislayout.toNext();
                    musicSeekBar.setProgress(0);
                    pause();
                    palyOrPause();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void toNext() {
        if (index != 2) {
            index++;
            LogUtil.doLog("onViewClicked", "" + index);
            setFreground.setForeground(DiskDimenUtils.getForegroundDrawable(bitmaps[index], this));
            setFreground.beginAnimation();
            musicSeekBar.setProgress(0);
            pause();
            palyOrPause();
        }
    }

    @Override
    public void toLast() {
        if (index != 0) {
            index--;
            LogUtil.doLog("onViewClicked", "" + index);
            setFreground.setForeground(DiskDimenUtils.getForegroundDrawable(bitmaps[index], this));
            setFreground.beginAnimation();
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
                starPalyMusic();
            else
                continuePlayMusic();

        }

    }

    /**
     * 假如进度是0就直接开始
     */
    public void starPalyMusic() {
        SongBean b = new SongBean();
        b.setM4a(m4as[index]);
        try {
            mMusicService.action(MusicService.MUSIC_ACTION_PLAY, b);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 假如进度大于0就continue
     */
    public void continuePlayMusic() {
        SongBean b = new SongBean();
        b.setM4a(m4as[index]);
        try {
            mMusicService.action(MusicService.MUSIC_ACTION_CONTINUE_PLAY, b);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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

    /**
     * 查找歌词文件
     *
     * @param fileName
     * @return
     */
    private String getLrcText(String fileName) {
        String lrcText = null;
        try {
            InputStream is = getAssets().open(fileName);
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

    public void onloadLyr(String lyric){
        lrcView.loadLrc(lyric);
        lrcView.updateTime(0);
    }

}
