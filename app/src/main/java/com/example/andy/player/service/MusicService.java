package com.example.andy.player.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.example.andy.player.aidl.IMusicPlayer;
import com.example.andy.player.aidl.MusicPlayListner;
import com.example.andy.player.aidl.SongBean;
import com.example.andy.player.tools.LogUtil;

import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service implements MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        AudioManager.OnAudioFocusChangeListener {

    public static final int MUSIC_ACTION_PLAY = 0xff001;
    public static final int MUSIC_ACTION_LAST = 0xff002;
    public static final int MUSIC_ACTION_NEXT = 0xff003;
    public static final int MUSIC_ACTION_PAUSE = 0xff004;
    public static final int MUSIC_ACTION_STOP = 0xff005;
    public static final int MUSIC_ACTION_CONTINUE_PLAY = 0xff006;
    public static final int MUSIC_ACTION_SEEK_PLAY = 0xff007;
    public static final int MUSIC_ACTION_COMPLETE = 0xff008;
    public static final  int MUSIC_ACTION_ERROR=0xff009;
    public static final int MUSIC_ACTION_QUIT=0xff00a;
    public static final String SONGBEAN="Songbean";
    private RemoteCallbackList<MusicPlayListner> remoteCallbackList = new RemoteCallbackList<>();
    private Timer timer;
    private static final String TAG="MusicService";
    private SongBean songBean;
    @Override
    public void onCreate() {
        init();
        super.onCreate();
    }

    private IBinder mybinder = new IMusicPlayer.Stub()

    {

        @Override
        public void action(int actioncode, SongBean songBean) throws RemoteException {
            switch (actioncode) {
                case MUSIC_ACTION_PLAY:
                    String path;
                    setSongBean(songBean);
                    if(songBean.getM4a()!=null)
                        path = songBean.getM4a();
                    else
                         path = songBean.getPath();
                    playSong(path);
                    LogUtil.doLog("String path", "paly:" + path);
                    Message msg=Message.obtain();
                    msg.what=MUSIC_ACTION_PLAY;
                    sendMessge(MUSIC_ACTION_PLAY,msg);
                    break;
                case MUSIC_ACTION_PAUSE:
                    pauseSong();
                    LogUtil.doLog("action", "pause");
                    Message msg2=Message.obtain();
                    msg2.what=MUSIC_ACTION_PAUSE;
                    sendMessge(MUSIC_ACTION_PAUSE,msg2);
                    break;
                case MUSIC_ACTION_STOP:
                    stopSong();
                    break;
                case MUSIC_ACTION_LAST:
                    stopSong();
                    setSongBean(songBean);
                    String path2 = (String) songBean.getM4a();
                    playSong(path2);
                    break;
                case MUSIC_ACTION_NEXT:
                    stopSong();
                    setSongBean(songBean);
                    String path3 = (String) songBean.getM4a();
                    playSong(path3);
                    break;
                case MUSIC_ACTION_CONTINUE_PLAY:
                    continuePlaySong();
                    LogUtil.doLog("action", "continue play");
                    Message msg3=Message.obtain();
                    msg3.what=MUSIC_ACTION_CONTINUE_PLAY;
                    sendMessge(MUSIC_ACTION_CONTINUE_PLAY,msg3);
                    break;
                case MUSIC_ACTION_SEEK_PLAY:
                    int progerss=songBean.getProgress();
                    seekPlaySong(progerss);
                    break;
                case MUSIC_ACTION_QUIT:
                    System.exit(0);
                    break;


            }
        }

        @Override
        public void registListner(MusicPlayListner listner) throws RemoteException {
            remoteCallbackList.register(listner);
        }

        @Override
        public void unregistListner(MusicPlayListner listner) throws RemoteException {
            remoteCallbackList.unregister(listner);
        }
    };

    public MusicService() {
    }

    private MediaPlayer mMediaPlayer;
    private MediaPlayer onlinePlayer;
    private Handler handler;

    public void init() {
        handler = new Handler();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        ((AudioManager) getSystemService(Context.AUDIO_SERVICE)).
                requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    public void initOnlinePlyer(){
        onlinePlayer=new MediaPlayer();
        onlinePlayer.setOnErrorListener(this);
        onlinePlayer.setOnPreparedListener(this);
        onlinePlayer.setOnCompletionListener(this);
    }

    @Override
    public void onAudioFocusChange(int focusChange) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Message msg=Message.obtain();
        msg.what=MUSIC_ACTION_COMPLETE;
        sendMessge(MUSIC_ACTION_COMPLETE,msg);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        LogUtil.doLog("onError",""+what+extra);
        if(songBean.getM4a()!=null) {
            Message message = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putParcelable("SongBean", songBean);
            message.setData(bundle);
            message.what = MUSIC_ACTION_ERROR;
            sendMessge(MUSIC_ACTION_ERROR, message);
        }
        mMediaPlayer.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
        if (timer == null)
            timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateProgress();
            }
        },0, 1000);
    }

    /**
     * 更新进度
     */
    private synchronized void updateProgress() {
        if(mMediaPlayer.isPlaying()) {
            Message msg = Message.obtain();
            msg.arg1 = mMediaPlayer.getCurrentPosition();
            msg.arg2 = mMediaPlayer.getDuration();
            msg.what = MUSIC_ACTION_SEEK_PLAY;
            LogUtil.doLog("updateProgress", "" + mMediaPlayer.getCurrentPosition());
            sendMessge(MUSIC_ACTION_PLAY, msg);
        }
    }

    /**
     * 向订阅的Listner发送Message
     *
     * @param msg
     */
    private synchronized void sendMessge(int action,Message msg) {
        int n = remoteCallbackList.beginBroadcast();
        for (int i = 0; i < n; i++) {
            MusicPlayListner listner = remoteCallbackList.getBroadcastItem(i);
            try {
                listner.action(action, msg,songBean);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        remoteCallbackList.finishBroadcast();
    }

    public synchronized void playSong(String path) {
        try {
            if(songBean.getM4a()!=null)
            {
                Uri uri=Uri.parse(path);
                mMediaPlayer.reset();//idle
                mMediaPlayer.setDataSource(this,uri);
                mMediaPlayer.prepareAsync();
                return;
            }
            mMediaPlayer.reset();//idle
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepareAsync();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public synchronized void stopSong() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }

    }

    public synchronized void seekPlaySong(int progress) {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.seekTo(progress);
        }
    }

    public synchronized void pauseSong() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();

        }
    }

    public synchronized void continuePlaySong() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();


        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.doLog("onBind", "return  binder-------------------");
        return mybinder;
    }


    public void setSongBean(SongBean songBean) {
        this.songBean = songBean;
    }
}
