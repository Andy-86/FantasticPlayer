package com.example.andy.player.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.andy.player.application.MyApplication;

/**
 * Created by andy on 2017/11/21.
 */

public class BitmapDecorator {
    public static Bitmap getFitSampleBitmap(int resource, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(MyApplication.getContext().getResources(),resource, options);
        options.inSampleSize = getFitInSampleSize(width, height, options);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(MyApplication.getContext().getResources(),resource, options);
    }
    public static int getFitInSampleSize(int reqWidth, int reqHeight, BitmapFactory.Options options) {
        int inSampleSize = 1;
        if (options.outWidth > reqWidth || options.outHeight > reqHeight) {
            int widthRatio = Math.round((float) options.outWidth / (float) reqWidth);
            int heightRatio = Math.round((float) options.outHeight / (float) reqHeight);
            inSampleSize = Math.min(widthRatio, heightRatio);
        }
        return inSampleSize;
    }
}
