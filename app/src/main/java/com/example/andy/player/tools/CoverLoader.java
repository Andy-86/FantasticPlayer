package com.example.andy.player.tools;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.example.andy.player.R;
import com.example.andy.player.aidl.SongBean;

import java.io.FileNotFoundException;
import java.io.InputStream;



/**
 * 专辑封面图片加载器
 * Created by
 */
public class CoverLoader {
    public static final int THUMBNAIL_MAX_LENGTH = 500;
    private static final String KEY_NULL = "null";

    // 封面缓存
    private LruCache<String, Bitmap> mCoverCache;
    private Context mContext;

    private enum Type {
        THUMBNAIL(""),
        BLUR("#BLUR"),
        ROUND("#ROUND");

        private String value;

        Type(String value) {
            this.value = value;
        }
    }

    public static CoverLoader getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static CoverLoader instance = new CoverLoader();
    }

    private CoverLoader() {
        // 获取当前进程的可用内存（单位KB）
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 缓存大小为当前进程可用内存的1/8
        int cacheSize = maxMemory / 8;
        mCoverCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    return bitmap.getAllocationByteCount() / 1024;
                } else {
                    return bitmap.getByteCount() / 1024;
                }
            }
        };
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    public Bitmap loadThumbnail(SongBean SongBean) {
        return loadCover(SongBean, Type.THUMBNAIL);
    }

    public Bitmap loadBlur(SongBean SongBean) {
        return loadCover(SongBean, Type.BLUR);
    }

    public Bitmap loadRound(SongBean SongBean) {
        return loadCover(SongBean, Type.ROUND);
    }

    private Bitmap loadCover(SongBean SongBean, Type type) {
        Bitmap bitmap;
        String key = getKey(SongBean, type);
        if (TextUtils.isEmpty(key)) {
            bitmap = mCoverCache.get(KEY_NULL.concat(type.value));
            if (bitmap != null) {
                return bitmap;
            }

            bitmap = getDefaultCover(type);
            mCoverCache.put(KEY_NULL.concat(type.value), bitmap);
            return bitmap;
        }

        bitmap = mCoverCache.get(key);
        if (bitmap != null) {
            return bitmap;
        }

        bitmap = loadCoverByType(SongBean, type);
        if (bitmap != null) {
            mCoverCache.put(key, bitmap);
            return bitmap;
        }

        return loadCover(null, type);
    }

    private String getKey(SongBean songBean, Type type) {
        if (songBean == null) {
            return null;
        }
        if (songBean.getAlbummid()==null)
            return null;
        if (Integer.valueOf(songBean.getAlbummid()) > 0) {
            return songBean.getAlbummid().concat(type.value);
        }
        return "";
    }

    private Bitmap getDefaultCover(Type type) {
        switch (type) {
            default:
                return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.default_cover);
        }
    }

    private Bitmap loadCoverByType(SongBean songBean, Type type) {
        Bitmap bitmap;

            bitmap = loadCoverFromMediaStore(Long.parseLong(songBean.getAlbummid()));


       return bitmap;
    }

     /**
     * 从媒体库加载封面<br>
     * 本地音乐
     */
    private Bitmap loadCoverFromMediaStore(long albumId) {
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = MusicUtil.getMediaStoreAlbumCoverUri(albumId);
        InputStream is;
        try {
            is = resolver.openInputStream(uri);
        } catch (FileNotFoundException ignored) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeStream(is, null, options);
    }

    /**
     * 从下载的图片加载封面<br>
     * 网络音乐
     */
    private Bitmap loadCoverFromFile(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(path, options);
    }
}
