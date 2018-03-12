package com.example.a7279.db.callback;

/**
 * Created by a7279 on 2018/3/11.
 */

public interface OnAnswerUserTendenciesFinishedListener {
    void onNetworkError();
    /*
    * 成功执行用户倾向功能的监听器
    * @param Function_id 功能id：赞/取消赞->1,2  踩/取消踩->3,4  收藏/取消收藏->5/6
    * @ isQuestion 判断是否是问题
    * */
    void useronSuccess(int Function_id,boolean isQuestion);
}
