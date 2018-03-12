package com.example.a7279.db.view;

import android.support.v7.widget.RecyclerView;

import com.example.a7279.db.bean.DataBeans;

import java.util.List;

/**
 * Created by a7279 on 2018/3/12.
 */

public interface MyFavoriteView {

    void install();

    void showNetWorkError();


    void Getquestion();

    void exciting(int type,int ID,boolean is_exciting);

    void naive(int type,int ID,boolean is_naive);

    void favorite(int ID,boolean is_favorite);

    void User_Tendencies_Success(int Function_id);
    /*
    * 改变item的属性
    * @param Function_id 功能id：赞/取消赞->1,2  踩/取消踩->3,4  收藏/取消收藏->5/6
    * */
    void setItem(int Function_id);

    boolean isSlideToBottom(RecyclerView recyclerView);

    void navigateToHome();

    void refresh();

    void getQuestionSuccess(List<DataBeans.DataBean.QuestionsBean> list, int page, boolean isRefresh);

     void navigateToQuestionDetai();
}
