package com.xu.sharemoreimage;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

/**
 * Created by Administrator on 2017/11/16.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //解决android N（>=24）系统以上分享 路径为file://时的 android.os.FileUriExposedException异常
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

    }
}
