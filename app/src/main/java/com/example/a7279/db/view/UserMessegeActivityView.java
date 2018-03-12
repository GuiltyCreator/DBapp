package com.example.a7279.db.view;

import android.net.Uri;

import java.io.File;

/**
 * Created by a7279 on 2018/3/8.
 */

public interface UserMessegeActivityView {

    void show_Networkerror();

    void getSuccess(int id, String name, String avatar, String token,String avatar_id);

    void changeAvatarSuccess();

    void startUcrop(Uri uri);

    void cameraFunction();

    void alubmFunction();

    void openAlbum();

    void displayImage(String picPath);

    void navigateToLogin();

    void navigateToHome();

    void navigateToResetPassword();

}
