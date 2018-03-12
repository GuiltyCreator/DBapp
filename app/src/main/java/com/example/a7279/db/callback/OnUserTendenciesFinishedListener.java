package com.example.a7279.db.callback;

/**
 * Created by a7279 on 2018/3/6.
 */

public interface OnUserTendenciesFinishedListener {
    void onNetworkError();
/*
* 成功执行用户倾向功能的监听器
* @param Function_id 功能id：赞/取消赞->1,2  踩/取消踩->3,4  收藏/取消收藏->5/6
* */
    void useronSuccess(int Function_id);
}
