package com.example.a7279.db.callback;

/**
        * Created by Anthony on 2016/2/15.
        * Class Note:登陆事件监听
        */
public interface OnLoginFinishedListener {

    void onDataeError();

    void onNetworkError();

    void onSuccess();
}
