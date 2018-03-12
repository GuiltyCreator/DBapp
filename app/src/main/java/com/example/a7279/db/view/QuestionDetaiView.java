package com.example.a7279.db.view;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;

import com.example.a7279.db.bean.DataBeans;

import java.util.List;

/**
 * Created by a7279 on 2018/3/11.
 */

public interface QuestionDetaiView {
    void navigateToHome();

    void show_Networkerror();

    void getQuestionData();

    void exciting(int type,int ID,boolean is_exciting);

    void naive(int type,int ID,boolean is_naive);

    void refresh();

    void install();

    void getAnswer();

    void getAnswerSuccess(List<DataBeans.DataBean.AnswersBean> answersBeans,boolean isRefresh,int page);

     boolean isSlideToBottom(RecyclerView recyclerView);

    /*
* 成功执行用户倾向功能的回调
* @param Function_id 功能id：赞/取消赞->1,2  踩/取消踩->3,4  收藏/取消收藏->5/6
* */
    void User_Tendencies_Success(int Function_id,boolean isQuestion);
    /*
    * 改变item的属性
    * @param Function_id 功能id：赞/取消赞->1,2  踩/取消踩->3,4  收藏/取消收藏->5/6
    * */
    void setItem(int Function_id,boolean isQuestion);

    void favorite(int ID,boolean is_favorite);


    void startUcrop(Uri uri);

    void cameraFunction();

    void alubmFunction();

    void openAlbum();

    void displayImage(Uri uri);

    void hideImage();

    void getSuccess(int id, String name, String avatar, String token,String avatar_id);

    void sendAnswer(int qid,String content,String image,String token);

    void sendAnswerSuccess();

    void addItem();

}
