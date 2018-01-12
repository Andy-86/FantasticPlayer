package com.example.andy.player.mvp.local;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import com.example.andy.player.bean.UpdataEvetn;
import com.example.andy.player.interfaces.ClickMoreListner;
import com.example.andy.player.mvp.base.MvpFragment;
import com.example.andy.player.mvp.paly.PlayFragment;
import com.example.andy.player.service.MusicService;
import com.example.andy.player.tools.LogUtil;
import com.example.andy.player.tools.MusicUtil;
import com.example.andy.player.tools.ToastUtil;
import com.example.andy.player.tools.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
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
    ClickMoreListner<SongBean> clickMoreListner;
    List<SongBean> mlist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_music, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        clickMoreListner=new ClickMoreListner<SongBean>() {
            @Override
            public void ClickMore(final SongBean songBean) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle(songBean.getSongname());
                dialog.setItems(R.array.local_music_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:// 分享
                                shareMusic(songBean);
                                break;
                            case 1:// 设为铃声
                                requestSetRingtone(songBean);
                                break;
                            case 2:// 删除
                                deleteMusic(songBean);
                                break;
                        }
                    }
                });
                dialog.show();
            }
        };

    }
    /**
     * 分享音乐
     */
    private void shareMusic(SongBean music) {
        File file = new File(music.getPath());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("audio/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        startActivity(Intent.createChooser(intent, getString(R.string.share)));
    }
    private void requestSetRingtone(final SongBean music) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(getContext())) {
            ToastUtils.show(R.string.no_permission_setting);
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + getContext().getPackageName()));
            startActivityForResult(intent,0 );
        } else {
            setRingtone(music);
        }
    }
    /**
     * 设置铃声
     */
    private void setRingtone(SongBean music) {
        Uri uri = MediaStore.Audio.Media.getContentUriForPath(music.getPath());
        // 查询音乐文件在媒体库是否存在
        Cursor cursor = getContext().getContentResolver().query(uri, null,
                MediaStore.MediaColumns.DATA + "=?", new String[]{music.getPath()}, null);
        if (cursor == null) {
            return;
        }
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            String _id = cursor.getString(0);
            ContentValues values = new ContentValues();
            values.put(MediaStore.Audio.Media.IS_MUSIC, true);
            values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
            values.put(MediaStore.Audio.Media.IS_ALARM, false);
            values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
            values.put(MediaStore.Audio.Media.IS_PODCAST, false);

            getContext().getContentResolver().update(uri, values, MediaStore.MediaColumns.DATA + "=?",
                    new String[]{music.getPath()});
            Uri newUri = ContentUris.withAppendedId(uri, Long.valueOf(_id));
            RingtoneManager.setActualDefaultRingtoneUri(getContext(), RingtoneManager.TYPE_RINGTONE, newUri);
            ToastUtils.show(R.string.setting_ringtone_success);
        }
        cursor.close();
    }

    /**
     * 删除音乐
     */
    private void deleteMusic(final SongBean music) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        String title = music.getSongname();
        String msg = getActivity().getString(R.string.delete_music);
        dialog.setMessage(msg);
        dialog.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File file = new File(music.getPath());
                if (file.delete()) {
                    boolean playing = (music == PlayFragment.getNowPlaying());
                    mlist.remove(music);
                    if (playing) {
                        try {
                            MyApplication.myApplication.getMusicPlayerService().action(MusicService.MUSIC_ACTION_STOP,music);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    ToastUtil.Toast("无法删除");
                }
            }
        });
        dialog.setNegativeButton(R.string.cancel, null);
        dialog.show();
    }
    @Override
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateMusicList(UpdataEvetn evetn) {
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
                    adapter.setClickMoreListner(clickMoreListner);
                }else {
                    mlist.removeAll(mlist);
                    for(SongBean songBean:list)
                        mlist.add(songBean);
                    adapter.notifyDataSetChanged();
                }

            }
        }.execute();
    }

    public List<SongBean> getList(){
        return mlist;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
