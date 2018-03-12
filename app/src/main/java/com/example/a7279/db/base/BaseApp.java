package com.example.a7279.db.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by a7279 on 2018/2/10.
 */

public class BaseApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
    }

/*
*获取Context
*
* @return
* */
    public static Context getContext() {
        return mContext;
    }
}
