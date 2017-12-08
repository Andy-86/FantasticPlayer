package com.example.andy.player.activity;

import android.Manifest;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.andy.player.R;
import com.example.andy.player.adapter.FragmentAdapter;
import com.example.andy.player.base.BaseActivity;
import com.example.andy.player.mvp.local.LocalMusicFragment;
import com.example.andy.player.mvp.remote.RemoteMusicFragment;
import com.example.andy.player.tools.LogUtil;
import com.example.andy.player.tools.PermissionUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MusicAcitivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener ,ViewPager.OnPageChangeListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tv_local_music)
    TextView tvLocalMusic;
    @BindView(R.id.tv_online_music)
    TextView tvOnlineMusic;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.iv_play_bar_cover)
    ImageView ivPlayBarCover;
    @BindView(R.id.tv_play_bar_title)
    TextView tvPlayBarTitle;
    @BindView(R.id.tv_play_bar_artist)
    TextView tvPlayBarArtist;
    @BindView(R.id.iv_play_bar_play)
    ImageView ivPlayBarPlay;
    @BindView(R.id.iv_play_bar_next)
    ImageView ivPlayBarNext;
    @BindView(R.id.pb_play_bar)
    ProgressBar pbPlayBar;
    @BindView(R.id.fl_play_bar)
    FrameLayout flPlayBar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private FragmentAdapter fragmentAdapter;

    public PermissionUtils.ComfirmListener comfirmListener=new PermissionUtils.ComfirmListener() {
        @Override
        public void granted(String prmission) {
            switch (prmission){
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:

                    ((LocalMusicFragment) fragmentAdapter.getItem(0)).updateMusicList();

            }
        }

        @Override
        public void denied(String prmission) {
            switch (prmission){
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    LogUtil.doLog("denied","手机空间不足");

            }
        }
    };


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_music_acitivity;
    }
    @Override
    public void initData(){
        fragmentAdapter=new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter.addFragment(new LocalMusicFragment());
        fragmentAdapter.addFragment(new RemoteMusicFragment());
        viewpager.setAdapter(fragmentAdapter);
        viewpager.addOnPageChangeListener(this);
        tvLocalMusic.setSelected(true);
        PermissionUtils.RequestReadAndWriteExt(this,comfirmListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.music_acitivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
       drawerLayout.closeDrawers();
        return true;
    }

    @OnClick({R.id.iv_menu, R.id.tv_local_music, R.id.tv_online_music, R.id.iv_search, R.id.iv_play_bar_play, R.id.iv_play_bar_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_menu:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.tv_local_music:
                viewpager.setCurrentItem(0);
                break;
            case R.id.tv_online_music:
                viewpager.setCurrentItem(1);
                break;
            case R.id.iv_search:
                break;
            case R.id.iv_play_bar_play:
                break;
            case R.id.iv_play_bar_next:
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position==0){
            tvLocalMusic.setSelected(true);
            tvOnlineMusic.setSelected(false);

        }else{
            tvLocalMusic.setSelected(false);
            tvOnlineMusic.setSelected(true);

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



}
