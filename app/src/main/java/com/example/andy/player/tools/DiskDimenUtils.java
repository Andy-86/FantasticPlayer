package com.example.andy.player.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by andy on 2017/9/9.
 * 通过截屏获取网易云音乐的图片获取图片的比例在进行调整
 */

public class DiskDimenUtils {
    /*手柄起始角度*/
    public static final float ROTATION_INIT_NEEDLE = -30;

    /*截图屏幕宽高*/
    private static final float SCREENSHOT_WIDHT = (float) 1080.0;
    private static final float SCREENSHOT_HEIGHT = (float) 1920.0;

    /*唱针宽高、距离等比例*/
    public static final float SCALE_NEEDLE_WIDTH = (float) (276.0 / SCREENSHOT_WIDHT);
    public static final float SCALE_NEEDLE_MARGIN_LEFT = (float) (500.0 / SCREENSHOT_WIDHT);
    public static final float SCALE_NEEDLE_PIVOT_X= (float) (43.0 / SCREENSHOT_WIDHT);
    public static final float SCALE_NEEDLE_PIVOT_Y = (float) (43.0 / SCREENSHOT_WIDHT);
    public static final float SCALE_NEEDLE_HEIGHT = (float) (413.0 / SCREENSHOT_HEIGHT);
    public static final float SCALE_NEEDLE_MARGIN_TOP = (float) (43.0 / SCREENSHOT_HEIGHT);

    /*唱盘比例*/
    public static final float SCALE_DISC_SIZE = (float) (813.0 / SCREENSHOT_WIDHT);
    public static final float SCALE_DISC_MARGIN_TOP = (float) (190 / SCREENSHOT_HEIGHT);

    /*专辑图片比例*/
    public static final float SCALE_MUSIC_PIC_SIZE = (float) (533.0 / SCREENSHOT_WIDHT);

    /**
     * 获取屏幕的宽度
     * @param context
     * @return
     */
    public static long getScreenWidht(Context context)
    {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕的高度
     * @param context
     * @return
     */
    public static long getScreenHeight(Context context)
    {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 生成毛玻璃效果图片
     * @param bitmap
     * @param context
     * @return
     */
    public static Drawable getForegroundDrawable(Bitmap bitmap,Context context) {
        /*得到屏幕的宽高比，以便按比例切割图片一部分*/
        final float widthHeightSize = (float) (getScreenWidht(context)
                * 1.0 / getScreenHeight(context) * 1.0);

        int cropBitmapWidth = (int) (widthHeightSize * bitmap.getHeight());
        int cropBitmapWidthX = (int) ((bitmap.getWidth() - cropBitmapWidth) / 2.0);
        Log.d(TAG, "getForegroundDrawable: "+widthHeightSize+bitmap.getHeight()+"  "+bitmap.getWidth()+"    "+cropBitmapWidth);
        /*切割部分图片*/
        Bitmap cropBitmap = Bitmap.createBitmap(bitmap, cropBitmapWidthX, 0, cropBitmapWidth,
                bitmap.getHeight());
        /*缩小图片*/
        Bitmap scaleBitmap = Bitmap.createScaledBitmap(cropBitmap, bitmap.getWidth() / 50, bitmap
                .getHeight() / 50, false);
        /*模糊化*/
        final Bitmap blurBitmap = (new StackBlurUtil()).doBlur(scaleBitmap, 1, true);

        final Drawable foregroundDrawable = new BitmapDrawable(blurBitmap);
        /*加入灰色遮罩层，避免图片过亮影响其他控件*/
        foregroundDrawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        return foregroundDrawable;
    }
}
