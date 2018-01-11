package com.example.andy.player.tools;

import android.content.Context;
import android.widget.Toast;

import com.example.andy.player.application.MyApplication;

/**
 * Toast工具类
 * Created by wcy on 2015/12/26.
 */
public class ToastUtils {
    private static Context sContext;
    private static Toast sToast;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    public static void show(int resId) {
        show(sContext.getString(resId));
    }

    public static void show(String text) {
        if (sToast == null) {
            sToast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(text);
        }
        sToast.show();
    }
}
