package com.example.a7279.db.model;

import com.example.a7279.db.callback.OnRegisterFinishedListener;

/**
 * Created by a7279 on 2018/2/14.
 */

public interface RegisterModel {
    void register(String username, String password, OnRegisterFinishedListener listener);
    void callback(int isSuccess,OnRegisterFinishedListener listener);
}
