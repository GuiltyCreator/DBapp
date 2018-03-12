package com.example.a7279.db.presenter;

/**
 * Created by a7279 on 2018/3/12.
 */

public interface SendQuestionPresenter {
    void sendQuestion(String title, String content, String image, String token);

    void onDestroy();
}
