package com.example.a7279.db.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.a7279.db.bean.DataBeans;

import java.util.List;

/**
 * Created by a7279 on 2018/3/5.
 */

public interface MainView {
    void navigateToLogin();
    void check();//检查是否登录
    void show_Networkerror();
    boolean isSlideToBottom(RecyclerView recyclerView);
    void Getquestion();
    void refresh();
    void exciting(int type,int ID,boolean is_exciting);
    void naive(int type,int ID,boolean is_naive);
    void favorite(int ID,boolean is_favorite);
    void navigateToUserMessege();
    void getSuccess(int id, String name, String avatar, String token,String avatar_id);

    void navigateToQuestionDetai();

    void navigateToMyFavorite();

    void navigateToSendQuestion();

    /*
* 成功执行用户倾向功能的回调
* @param Function_id 功能id：赞/取消赞->1,2  踩/取消踩->3,4  收藏/取消收藏->5/6
* */
    void User_Tendencies_Success(int Function_id);
    /*
    * 改变item的属性
    * @param Function_id 功能id：赞/取消赞->1,2  踩/取消踩->3,4  收藏/取消收藏->5/6
    * */
    void setItem(int Function_id);
}
