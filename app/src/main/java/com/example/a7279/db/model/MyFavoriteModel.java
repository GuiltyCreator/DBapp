package com.example.a7279.db.model;

import com.example.a7279.db.callback.OnGetMyFavoriteFinishedListener;
import com.example.a7279.db.callback.OnUserTendenciesFinishedListener;

/**
 * Created by a7279 on 2018/3/12.
 */

public interface MyFavoriteModel {

    void getQuestion(int page, OnGetMyFavoriteFinishedListener listener);

    void callback(int isSuccess,OnGetMyFavoriteFinishedListener listener);

    void User_tendencies_Function(String FUNCTION, int type,int questionID ,OnUserTendenciesFinishedListener listener);

    void user_tendencies_callback(int isSuccess,OnUserTendenciesFinishedListener listener);
}
