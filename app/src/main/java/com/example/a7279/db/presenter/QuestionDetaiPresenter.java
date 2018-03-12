package com.example.a7279.db.presenter;

/**
 * Created by a7279 on 2018/3/11.
 */

public interface QuestionDetaiPresenter {
    void onDestroy();

    void refresh(int page,int qid);

    void excitingFunction(int type,int ID,boolean isexciting);

    void naiveFunction(int type,int ID,boolean isnaive);

    void favoriteFunction(int ID,boolean isfavorite);

    void getUermessege();

    void sendAnswer(int qid, String content, String image, String token);
}
