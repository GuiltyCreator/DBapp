package com.example.a7279.db.view;

import android.net.Uri;

/**
 * Created by a7279 on 2018/3/12.
 */

public interface SendQuestionView {

    void install();

    void startUcrop(Uri uri);

    void cameraFunction();

    void alubmFunction();

    void openAlbum();

    void displayImage(Uri uri);

    void hideImage();

    void sendSuccess();

    void NetworkErro();

    void send();

}
