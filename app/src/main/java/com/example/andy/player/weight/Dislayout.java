package com.example.andy.player.weight;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.andy.player.R;
import com.example.andy.player.adapter.DisViewPageAdapter;
import com.example.andy.player.aidl.SongBean;
import com.example.andy.player.application.MyApplication;
import com.example.andy.player.interfaces.IplayStatus;
import com.example.andy.player.tools.CoverLoader;
import com.example.andy.player.tools.DiskDimenUtils;
import com.example.andy.player.tools.MusicUtil;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;

/**
 * Created by andy on 2017/9/10.
 */

public class Dislayout extends RelativeLayout {
    private int DURATION_NEEDLE_ANIAMTOR = 500;
    /*标记唱针复位后，是否需要重新偏移到唱片处*/
    private boolean mIsNeed2StartPlayAnimator = false;
    /*标记ViewPager是否处于偏移的状态*/
    private boolean mViewPagerIsOffset = false;
    public NeedleAnimatorStatus needleAnimatorStatus = NeedleAnimatorStatus.IN_FAR_END;
    private MusicStatus musicStatus = MusicStatus.STOP;
    private ImageView mIvNeedle;
    private ViewPager mVpContain;
    private DisViewPageAdapter mViewPagerAdapter;
    private boolean doNextOrLast = false;
    private List<View> mDiscLayouts = new ArrayList<>();
    private List<ObjectAnimator> disObjectAnis = new ArrayList<>();
    private ObjectAnimator mNeedleAnimator;
    private long mScreenWidth;
    private long mSreenHeight;

    private Bitmap bitmapDisc;
    private boolean isFirstload=true;
    private Context context;
    private ArrayList<SongBean > songlist;

    public IplayStatus getpStatus() {
        return pStatus;
    }

    public void setpStatus(IplayStatus pStatus) {
        this.pStatus = pStatus;
    }

    //监听状态的回调
    private IplayStatus pStatus;

    public enum MusicStatus {
        PLAY, PAUSE, STOP
    }

    public enum NeedleAnimatorStatus {
        /*移动时：从唱盘往远处移动*/
        TO_FAR_END,
        /*移动时：从远处往唱盘移动*/
        TO_NEAR_END,
        /*静止时：离开唱盘*/
        IN_FAR_END,
        /*静止时：贴近唱盘*/
        IN_NEAR_END
    }

    public Dislayout(Context context) {
        super(context);
        this.context=context;
    }

    public Dislayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        songlist=new ArrayList<>();
        mScreenWidth = DiskDimenUtils.getScreenWidht(context);
        mSreenHeight = DiskDimenUtils.getScreenHeight(context);

    }

    public Dislayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initDiscBlackground();
        initViewPager();
        initViewList();
        initNeedle();
        initNeedleAnimotion();
    }

    /**
     * 初始化底部的圆形背景
     */
    private void initDiscBlackground() {
        ImageView mDiscBlackground = (ImageView) findViewById(R.id.play_disc_backgound);
        mDiscBlackground.setImageDrawable(getDiscBlackgroundDrawable());

        int marginTop = (int) (DiskDimenUtils.SCALE_DISC_MARGIN_TOP * mSreenHeight);
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mDiscBlackground
                .getLayoutParams();
        layoutParams.setMargins(0, marginTop, 0, 0);

        mDiscBlackground.setLayoutParams(layoutParams);
    }

    /**
     * 初始化指针
     */
    private void initNeedle() {
        mIvNeedle = (ImageView) findViewById(R.id.play_needle);

        int needleWidth = (int) (DiskDimenUtils.SCALE_NEEDLE_WIDTH * mScreenWidth);
        int needleHeight = (int) (DiskDimenUtils.SCALE_NEEDLE_HEIGHT * mSreenHeight);

        /*设置手柄的外边距为负数，让其隐藏一部分*/
        int marginTop = (int) (DiskDimenUtils.SCALE_NEEDLE_MARGIN_TOP * mSreenHeight) * -1;
        int marginLeft = (int) (DiskDimenUtils.SCALE_NEEDLE_MARGIN_LEFT * mScreenWidth);

        Bitmap originBitmap = BitmapFactory.decodeResource(getResources(), R.drawable
                .ic_needle);
        Bitmap bitmap = Bitmap.createScaledBitmap(originBitmap, needleWidth, needleHeight, false);

        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mIvNeedle.getLayoutParams();
        layoutParams.setMargins(marginLeft, marginTop, 0, 0);

        int pivotX = (int) (DiskDimenUtils.SCALE_NEEDLE_PIVOT_X * mScreenWidth);
        int pivotY = (int) (DiskDimenUtils.SCALE_NEEDLE_PIVOT_Y * mScreenWidth);

        mIvNeedle.setPivotX(pivotX);
        mIvNeedle.setPivotY(pivotY);
        mIvNeedle.setRotation(DiskDimenUtils.ROTATION_INIT_NEEDLE);
        mIvNeedle.setImageBitmap(bitmap);
        mIvNeedle.setLayoutParams(layoutParams);
    }

    public void initNeedleAnimotion() {
        mNeedleAnimator = ObjectAnimator.ofFloat(mIvNeedle, View.ROTATION, DiskDimenUtils
                .ROTATION_INIT_NEEDLE, 0);
        mNeedleAnimator.setDuration(DURATION_NEEDLE_ANIAMTOR);
        mNeedleAnimator.setInterpolator(new AccelerateInterpolator());
        mNeedleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                /**
                 * 根据动画开始前NeedleAnimatorStatus的状态，
                 * 即可得出动画进行时NeedleAnimatorStatus的状态
                 * */
                if (needleAnimatorStatus == NeedleAnimatorStatus.IN_FAR_END) {
                    needleAnimatorStatus = NeedleAnimatorStatus.TO_NEAR_END;
                } else if (needleAnimatorStatus == NeedleAnimatorStatus.IN_NEAR_END) {
                    needleAnimatorStatus = NeedleAnimatorStatus.TO_FAR_END;
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {

                if (needleAnimatorStatus == NeedleAnimatorStatus.TO_NEAR_END) {
                    needleAnimatorStatus = NeedleAnimatorStatus.IN_NEAR_END;
                    int index = mVpContain.getCurrentItem();
                    playDiscAnimator(index);

                } else if (needleAnimatorStatus == NeedleAnimatorStatus.TO_FAR_END) {
                    needleAnimatorStatus = NeedleAnimatorStatus.IN_FAR_END;
                    if (musicStatus == MusicStatus.STOP) {
                        mIsNeed2StartPlayAnimator = true;
                    }
                }

                if (mIsNeed2StartPlayAnimator) {
                    mIsNeed2StartPlayAnimator = false;
                    /**
                     * 只有在ViewPager不处于偏移状态时，才开始唱盘旋转动画
                     * */
                    if (!mViewPagerIsOffset) {
                        /*延时500ms*/
                        Dislayout.this.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                playAnimator();
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    /*播放动画*/
    private void playAnimator() {
        /*唱针处于远端时，直接播放动画*/
        if (needleAnimatorStatus == NeedleAnimatorStatus.IN_FAR_END) {
            mNeedleAnimator.start();
        }
        /*唱针处于往远端移动时，设置标记，等动画结束后再播放动画*/
        else if (needleAnimatorStatus == NeedleAnimatorStatus.TO_FAR_END) {
            mIsNeed2StartPlayAnimator = true;
        }
    }


    /*暂停动画*/
    private void pauseAnimator() {
        /*播放时暂停动画*/
        if (needleAnimatorStatus == NeedleAnimatorStatus.IN_NEAR_END) {
            int index = mVpContain.getCurrentItem();
            pauseDiscAnimatior(index);

        }
        /*唱针往唱盘移动时暂停动画*/
        else if (needleAnimatorStatus == NeedleAnimatorStatus.TO_NEAR_END) {
            mNeedleAnimator.reverse();
            /**
             * 若动画在没结束时执行reverse方法，则不会执行监听器的onStart方法，此时需要手动设置
             * */
            needleAnimatorStatus = NeedleAnimatorStatus.TO_FAR_END;
        }
    }

    /**
     * 将底部的图片转为圆形
     *
     * @return
     */
    private Drawable getDiscBlackgroundDrawable() {

        RoundedBitmapDrawable roundDiscDrawable = RoundedBitmapDrawableFactory.create
                (getResources(), bitmapDisc);
        return roundDiscDrawable;
    }

    private void initViewPager() {
        mViewPagerAdapter = new DisViewPageAdapter(mDiscLayouts);
        mVpContain = (ViewPager) findViewById(R.id.play_discContain);
        mVpContain.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mVpContain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int lastPositionOffsetPixels = 0;
            int currentItem = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
                //左滑
                if (lastPositionOffsetPixels > positionOffsetPixels) {
                    if (positionOffset < 0.5) {
                        notifyMusicInfoChanged(position);
                    } else {
                        notifyMusicInfoChanged(mVpContain.getCurrentItem());

                    }
                }
                //右滑
                else if (lastPositionOffsetPixels < positionOffsetPixels) {
                    if (positionOffset > 0.5) {
                        notifyMusicInfoChanged(position + 1);

                    } else {
                        notifyMusicInfoChanged(position);
                    }
                }
                lastPositionOffsetPixels = positionOffsetPixels;
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + position);
                if (position > currentItem) {
                    notifyStartAnimotion(position);
                    if (!doNextOrLast)
                        if (pStatus != null)
                            pStatus.toNext();
                        else
                            doNextOrLast = false;
                    Log.d(TAG, "onPageSelected: ToNext");
                } else if (position < currentItem) {
                    notifyStartAnimotion(position);
                    if (!doNextOrLast)
                        if (pStatus != null)
                            pStatus.toLast();
                        else
                            doNextOrLast = false;
                    Log.d(TAG, "onPageSelected: ToLast");
                }
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged: " + state);
                switch (state) {

                    case ViewPager.SCROLL_STATE_IDLE:
                        //0 idle(空闲，挂空挡)， 理解为：只要拖动/滑动结束，无论是否安放到了目标页，则 state = ViewPager.SCROLL_STATE_IDLE
                        Log.d(TAG, "onPageScrollStateChanged: IDLE");
                    case ViewPager.SCROLL_STATE_SETTLING: {
                        //2 settling(安放、定居、解决)，理解为：通过拖动/滑动，安放到了目标页，则 state = ViewPager.SCROLL_STATE_SETTLING
                        /**
                         * 假如没有划出去的话没有调用onPageSelect的使用应该在这里调用
                         */
                        mViewPagerIsOffset = false;
                        if (musicStatus == MusicStatus.PAUSE) {
                            Log.d(TAG, "onPageScrollStateChanged: " + "play");
                            playAnimator();
                        }
                        break;
                    }
                    case ViewPager.SCROLL_STATE_DRAGGING: {
                        //1 dragging（拖动），理解为：只要触发拖动/滑动事件时，则 state = ViewPager.SCROLL_STATE_DRAGGING
                        mViewPagerIsOffset = true;
                        if (musicStatus == MusicStatus.PLAY) {
                            pauseDiscAnimatior(mVpContain.getCurrentItem());
                            Log.d(TAG, "onPageScrollStateChanged: " + "pause");
                        }

                        break;
                    }
                }
            }
        });
        mVpContain.setAdapter(mViewPagerAdapter);

        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mVpContain.getLayoutParams();
        int marginTop = (int) (DiskDimenUtils.SCALE_DISC_MARGIN_TOP * mSreenHeight);
        layoutParams.setMargins(0, marginTop, 0, 0);
        mVpContain.setLayoutParams(layoutParams);
    }

    private void notifyStartAnimotion(int position) {

    }

    private void notifyMusicInfoChanged(int position) {

    }

    public void initViewList() {

        for (SongBean songBean :songlist) {
            View discLayout = LayoutInflater.from(getContext()).inflate(R.layout.layout_disc,
                    mVpContain, false);
            ImageView disc = (ImageView) discLayout.findViewById(R.id.play_disc);
            disc.setImageDrawable(getDiscDrawable(songBean));
            mDiscLayouts.add(discLayout);
            disObjectAnis.add(getDiscObjectAnimator(disc, 1));
        }
        mViewPagerAdapter.notifyDataSetChanged();

    }


    public void addOneBitmap(SongBean songBean){
        View discLayout = LayoutInflater.from(getContext()).inflate(R.layout.layout_disc,
                mVpContain, false);
        ImageView disc = (ImageView) discLayout.findViewById(R.id.play_disc);
        disc.setImageDrawable(getDiscDrawable(songBean));
        mDiscLayouts.add(discLayout);
        disObjectAnis.add(getDiscObjectAnimator(disc, 1));
    }
    /**
     * 将圆形的底盘和图片结合形成一个Drawable返回
     *
     * @param imageResource 图片的ID
     * @return
     */
    private Drawable getDiscDrawable(SongBean songBean) {
        int discSize = (int) (mScreenWidth * DiskDimenUtils.SCALE_DISC_SIZE);
        int musicPicSize = (int) (mScreenWidth * DiskDimenUtils.SCALE_MUSIC_PIC_SIZE);
         if(isFirstload) {
             bitmapDisc = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R
                     .drawable.ic_disc), discSize, discSize, false);
             isFirstload=false;
         }
        Bitmap bitmapMusicPic = getMusicPicBitmap(musicPicSize, songBean);
        BitmapDrawable discDrawable = new BitmapDrawable(bitmapDisc);
        RoundedBitmapDrawable roundMusicDrawable = RoundedBitmapDrawableFactory.create
                (getResources(), bitmapMusicPic);
        //抗锯齿
        discDrawable.setAntiAlias(true);
        roundMusicDrawable.setAntiAlias(true);

        Drawable[] drawables = new Drawable[2];
        drawables[0] = roundMusicDrawable;
        drawables[1] = discDrawable;

        LayerDrawable layerDrawable = new LayerDrawable(drawables);
        int musicPicMargin = (int) ((DiskDimenUtils.SCALE_DISC_SIZE - DiskDimenUtils
                .SCALE_MUSIC_PIC_SIZE) * mScreenWidth / 2);
        //调整专辑图片的四周边距，让其显示在正中
        layerDrawable.setLayerInset(0, musicPicMargin, musicPicMargin, musicPicMargin,
                musicPicMargin);

        return layerDrawable;
    }


    private Drawable getDiscDrawableWithBitmap(Bitmap bitmap) {
        int discSize = (int) (mScreenWidth * DiskDimenUtils.SCALE_DISC_SIZE);
        int musicPicSize = (int) (mScreenWidth * DiskDimenUtils.SCALE_MUSIC_PIC_SIZE);
        if(isFirstload) {
            bitmapDisc = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R
                    .drawable.ic_disc), discSize, discSize, false);
            isFirstload=false;
        }
        Bitmap bitmapMusicPic = bitmap;
        BitmapDrawable discDrawable = new BitmapDrawable(bitmapDisc);
        RoundedBitmapDrawable roundMusicDrawable = RoundedBitmapDrawableFactory.create
                (getResources(), bitmapMusicPic);
        //抗锯齿
        discDrawable.setAntiAlias(true);
        roundMusicDrawable.setAntiAlias(true);

        Drawable[] drawables = new Drawable[2];
        drawables[0] = roundMusicDrawable;
        drawables[1] = discDrawable;

        LayerDrawable layerDrawable = new LayerDrawable(drawables);
        int musicPicMargin = (int) ((DiskDimenUtils.SCALE_DISC_SIZE - DiskDimenUtils
                .SCALE_MUSIC_PIC_SIZE) * mScreenWidth / 2);
        //调整专辑图片的四周边距，让其显示在正中
        layerDrawable.setLayerInset(0, musicPicMargin, musicPicMargin, musicPicMargin,
                musicPicMargin);

        return layerDrawable;
    }

    /**
     * 压缩图片防止OOM
     *
     * @param musicPicSize   真正的磁盘大小
     * @param InputStream 图片的输入流
     * @return
     */
    private Bitmap getMusicPicBitmap(final int musicPicSize, final SongBean  songBean) {
        if(songBean.getM4a()==null) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        /**
         * 预先加载图片的基本信息
         */
            ContentResolver resolver = MyApplication.getContext().getContentResolver();
            Uri uri = MusicUtil.getMediaStoreAlbumCoverUri(Long.valueOf(songBean.getAlbummid()));
            BitmapFactory.decodeFile(uri.getPath(), options);
            int imageWidth = options.outWidth;

            int sample = imageWidth / musicPicSize;
            int dstSample = 1;
            if (sample > dstSample) {
                dstSample = sample;
            }
            options.inJustDecodeBounds = false;
            //设置图片采样率
            options.inSampleSize = dstSample;
            //设置图片解码格式因为JPG假如用ARGB_8888的话大一半
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            return Bitmap.createScaledBitmap(CoverLoader.getInstance().loadThumbnail(songBean), musicPicSize, musicPicSize, true);
        }else {
            new AsyncTask<Void, Integer, Drawable>(){

                @Override
                protected Drawable doInBackground(Void... voids) {
                    try {
                         Bitmap bitmap=Glide.with(MyApplication.getContext())
                                .load(songBean.getAlbumpic_big())
                                .asBitmap() //必须
                                .centerCrop()
                                .into(musicPicSize, musicPicSize)
                                .get();

                         return getDiscDrawableWithBitmap(bitmap);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Drawable drawable) {
                    super.onPostExecute(drawable);
                    View dislayout= mDiscLayouts.get(songlist.indexOf(songBean));
                    ImageView disc = (ImageView) dislayout.findViewById(R.id.play_disc);
                    disc.setImageDrawable(drawable);
                }
            }.execute();
            Bitmap bitmap=BitmapFactory.decodeResource(MyApplication.getContext().getResources(),R.drawable.default_cover);
            return bitmap;
        }

    }

    /**
     * 设置圆盘的旋转动画
     *
     * @param disc
     * @param i
     * @return
     */
    private ObjectAnimator getDiscObjectAnimator(ImageView disc, final int i) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(disc, View.ROTATION, 0, 360);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setDuration(20 * 1000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        return objectAnimator;
    }

    /**
     * 播放属性动画
     *
     * @param index
     */
    private void playDiscAnimator(int index) {
        musicStatus = MusicStatus.PLAY;
        //通知MainAcitivity播放音乐
        pStatus.play();
        ObjectAnimator objectAnimator = disObjectAnis.get(index);
        Log.d(TAG, String.valueOf("playDiscAnimator: " + index + objectAnimator.isPaused()));
        if (objectAnimator.isPaused()) {
            objectAnimator.resume();
        } else {
            objectAnimator.start();
        }

        /*唱针处于远端时，直接播放动画*/
        if (needleAnimatorStatus == NeedleAnimatorStatus.IN_FAR_END) {
            mNeedleAnimator.start();
        }
    }

    /**
     * 暂停属性动画
     *
     * @param index
     */
    private void pauseDiscAnimatior(int index) {
        musicStatus = MusicStatus.PAUSE;

        ObjectAnimator objectAnimator = disObjectAnis.get(index);
        Log.d(TAG, "pauseDiscAnimatior: " + objectAnimator.isRunning());
        if (objectAnimator.isRunning())
            objectAnimator.pause();
        mNeedleAnimator.reverse();
        pStatus.pause();
    }

    public int toNext() {
        if (mVpContain.getCurrentItem() != songlist.size()) {
            doNextOrLast = true;
            //因为他调用sercurrent的话没用调用Dragger状态  只有setting和IDEL
            if (musicStatus == MusicStatus.PLAY) {
                pauseDiscAnimatior(mVpContain.getCurrentItem());
                Log.d(TAG, "onPageScrollStateChanged: " + "pause");
            }
            mVpContain.setCurrentItem(mVpContain.getCurrentItem() + 1, true);
            return mVpContain.getCurrentItem();
        } else {
            return -1;
        }


    }

    public int toLast() {
        if (mVpContain.getCurrentItem() != 0) {
            doNextOrLast = true;
            if (musicStatus == MusicStatus.PLAY) {
                pauseDiscAnimatior(mVpContain.getCurrentItem());
                Log.d(TAG, "onPageScrollStateChanged: " + "pause");
            }
            mVpContain.setCurrentItem(mVpContain.getCurrentItem() - 1, true);

            Log.d(TAG, "toLast: " + "start" + mVpContain.getCurrentItem());
            return mVpContain.getCurrentItem();
        } else
            return -1;

    }

    public void doPause() {
        pauseAnimator();
    }

    public void doPlay() {

        playAnimator();
    }
    //每次开始的时候要初始化唱盘的界面的图片
    public void setImageResourse(ArrayList<SongBean> bitmaps,SongBean songBean) {
        this.songlist=bitmaps;
        initViewList();
        mViewPagerAdapter.notifyDataSetChanged();
        if(songBean!=null)
        mVpContain.setCurrentItem(songlist.indexOf(songBean));
    }

    public void addbitmap(SongBean bean){
        addOneBitmap(bean);
        mViewPagerAdapter.notifyDataSetChanged();
        mVpContain.setCurrentItem(songlist.indexOf(bean));

    }

    public void setcurrentItem(int index){
        mVpContain.setCurrentItem(index);
    }

}