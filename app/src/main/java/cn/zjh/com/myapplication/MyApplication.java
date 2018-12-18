package cn.zjh.com.myapplication;

import android.app.Application;

/**
 * Created by zhuojh on 2018/12/18.
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getApplicationInstance(){
        return instance;
    }
}
