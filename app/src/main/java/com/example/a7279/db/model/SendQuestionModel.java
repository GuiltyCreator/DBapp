package com.example.a7279.db.model;

import com.example.a7279.db.callback.OnSendAnswerFinishedListener;

/**
 * Created by a7279 on 2018/3/12.
 */

public interface SendQuestionModel {
    void sendQuestion(String title, String content, String image, String token, OnSendAnswerFinishedListener listener);
}
