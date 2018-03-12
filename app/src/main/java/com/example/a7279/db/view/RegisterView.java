package com.example.a7279.db.view;

/**
 * Created by a7279 on 2018/2/14.
 */

public interface RegisterView {
    void show_Dataerror();

    void show_Networkerror();

    void showProgress();

    void hideProgress();

    void navigateToLogin();

    void showSuccess();

}
