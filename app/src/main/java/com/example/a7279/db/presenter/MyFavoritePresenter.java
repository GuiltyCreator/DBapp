package com.example.a7279.db.presenter;

/**
 * Created by a7279 on 2018/3/12.
 */

public interface MyFavoritePresenter {

    void onDestroy();

    void getQuestion(int page);

    void excitingFunction(int type,int ID,boolean isexciting);

    void naiveFunction(int type,int ID,boolean isnaive);

    void favoriteFunction(int ID,boolean isfavorite);


}
