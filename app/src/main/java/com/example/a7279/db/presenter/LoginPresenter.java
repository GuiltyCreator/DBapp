package com.example.a7279.db.presenter;

/**
 * Created by a7279 on 2018/2/14.
 */

public interface LoginPresenter {
    void onDestroy();

    void validateCredentials(String username, String password);
}
