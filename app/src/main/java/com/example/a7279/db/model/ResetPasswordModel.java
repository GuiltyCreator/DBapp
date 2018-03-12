package com.example.a7279.db.model;

import com.example.a7279.db.callback.OnResetPasswordFinishedListener;

/**
 * Created by a7279 on 2018/2/14.
 */

public interface ResetPasswordModel {
    void register( String password, OnResetPasswordFinishedListener listener);
}
