package com.example.a7279.db.model;


import com.example.a7279.db.bean.DataBeans;
import com.example.a7279.db.callback.OnAnswerUserTendenciesFinishedListener;
import com.example.a7279.db.callback.OnGetAnswerFinishedListener;
import com.example.a7279.db.callback.OnGetMessegeSuccessListener;
import com.example.a7279.db.callback.OnSendAnswerFinishedListener;

import java.util.List;

/**
 * Created by a7279 on 2018/3/11.
 */

public interface QuestionDetaiModel {

    void refresh(int page,int qid, OnGetAnswerFinishedListener listener);

    void callback(int isSuccess,OnGetAnswerFinishedListener listener);

    List<DataBeans.DataBean.AnswersBean> GetAnswerList();

      /*
    * 用户倾向功能
    * @param FUNCTION 功能：赞，踩，收藏
    * @param type 赞，踩的问题与回答区分，收藏为3（不用）
    * @param ID 问题或者回答的ID
    * @param listener 监听器
    * @return
    * */

    void User_tendencies_Function(String FUNCTION, int type,int ID ,OnAnswerUserTendenciesFinishedListener listener);

    void user_tendencies_callback(int isSuccess,OnAnswerUserTendenciesFinishedListener listener);

    void getUserMessege(OnGetMessegeSuccessListener listener);

    void sendAnswer(int qid, String content, String image, String token, OnSendAnswerFinishedListener listener);

    void sendAnswer_callback(boolean isSuccess,OnSendAnswerFinishedListener listener);
}
