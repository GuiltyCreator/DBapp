package com.example.a7279.db.callback;

import com.example.a7279.db.bean.DataBeans;

import java.util.List;

/**
 * Created by a7279 on 2018/3/11.
 */

public interface OnGetAnswerFinishedListener {

    void onNetworkError();

    void onSuccess(List<DataBeans.DataBean.AnswersBean> answersBeans,boolean isRefresh, int page);
}
