package com.example.andy.player.tools;

import android.Manifest;
import android.app.Activity;

/**
 * Created by andy on 2017/12/4.
 */

public class PermissionUtils {
    public static void RequsetLocationServer(Activity activity, final ComfirmListener listener){
        PermissionReq.with(activity)
                .permissions(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .result(new PermissionReq.Result() {
                    @Override
                    public void onGranted(String permission) {
                       listener.granted(permission);
                    }

                    @Override
                    public void onDenied(String permission) {
                        listener.denied(permission);
                    }
                })
                .request();
    }

    public static void RequestReadAndWriteExt(Activity activity,final ComfirmListener listener){
        PermissionReq.with(activity)
                 .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .result(new PermissionReq.Result() {
                    @Override
                    public void onGranted(String permission) {
                        listener.granted(permission);
                    }

                    @Override
                    public void onDenied(String permission) {
                        listener.denied(permission);
                    }
                })
                .request();

    }
    public interface ComfirmListener{
        public void granted(String prmission);
        public void denied(String prmission);
    }
}
