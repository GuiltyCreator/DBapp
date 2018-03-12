package com.example.a7279.db.callback;

import com.example.a7279.db.bean.DataBeans;

import java.util.List;

/**
 * Created by a7279 on 2018/3/12.
 */

public interface OnGetMyFavoriteFinishedListener {
    void onNetworkError();

    void onGetMyFavoriteSuccess(List<DataBeans.DataBean.QuestionsBean> list, int page, boolean isRefresh);
}
