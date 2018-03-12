package com.example.a7279.db.model;

import com.example.a7279.db.bean.DataBeans;
import com.example.a7279.db.callback.OnGetMessegeSuccessListener;
import com.example.a7279.db.callback.OnGetQuestionsFinishedListener;
import com.example.a7279.db.callback.OnLoginFinishedListener;
import com.example.a7279.db.callback.OnUserTendenciesFinishedListener;

import java.util.List;

/**
 * Created by a7279 on 2018/3/5.
 */

public interface MainModel {
    void refresh(int page, OnGetQuestionsFinishedListener listener);
    void callback(int isSuccess,OnGetQuestionsFinishedListener listener);
    List<DataBeans.DataBean.QuestionsBean> getQuestions();
    /*
    * 用户倾向功能
    * @param FUNCTION 功能：赞，踩，收藏
    * @param type 赞，踩的问题与回答区分，收藏为3（不用）
    * @param ID 问题或者回答的ID
    * @param listener 监听器
    * @return
    * */
    void User_tendencies_Function(String FUNCTION, int type,int questionID ,OnUserTendenciesFinishedListener listener);

    void user_tendencies_callback(int isSuccess,OnUserTendenciesFinishedListener listener);

    void getUserMessege(OnGetMessegeSuccessListener listener);
}
