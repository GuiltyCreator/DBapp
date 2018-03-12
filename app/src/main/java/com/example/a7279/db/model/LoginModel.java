package com.example.a7279.db.model;

import com.example.a7279.db.callback.OnLoginFinishedListener;

/**
 * Created by a7279 on 2018/2/14.
 */

public interface LoginModel {
    void login(String username, String password, OnLoginFinishedListener listener);
    void callback(int isSuccess,OnLoginFinishedListener listener);
}
