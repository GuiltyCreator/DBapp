package com.example.a7279.db.presenter;

import com.example.a7279.db.bean.DataBeans;

import java.util.List;

/**
 * Created by a7279 on 2018/3/5.
 */

public interface MainPresenter {
    void onDestroy();
    void refresh(int page);
    List<DataBeans.DataBean.QuestionsBean> getQuestions();
    void excitingFunction(int type,int ID,boolean isexciting);
    void naiveFunction(int type,int ID,boolean isnaive);
    void favoriteFunction(int ID,boolean isfavorite);
    void getUermessege();
}
