package com.example.a7279.db.presenter;

import com.example.a7279.db.bean.DataBeans;
import com.example.a7279.db.callback.OnAnswerUserTendenciesFinishedListener;
import com.example.a7279.db.callback.OnGetAnswerFinishedListener;
import com.example.a7279.db.callback.OnGetMessegeSuccessListener;
import com.example.a7279.db.callback.OnSendAnswerFinishedListener;
import com.example.a7279.db.callback.OnUserTendenciesFinishedListener;
import com.example.a7279.db.commons.Urls;
import com.example.a7279.db.model.QuestionDetaiModel;
import com.example.a7279.db.model.QuestionDetaiModelImpl;
import com.example.a7279.db.utils.LogUtil;
import com.example.a7279.db.view.QuestionDetaiView;

import java.util.List;

/**
 * Created by a7279 on 2018/3/11.
 */

public class QuestionDetaiPresenterImpl implements QuestionDetaiPresenter,OnGetAnswerFinishedListener,OnAnswerUserTendenciesFinishedListener,OnGetMessegeSuccessListener,OnSendAnswerFinishedListener {
    private static final String TAG = "QuestionDetaiPresenterI";
    private QuestionDetaiView view;
    private QuestionDetaiModel model;


    public QuestionDetaiPresenterImpl(QuestionDetaiView view){
        this.view=view;
        model=new QuestionDetaiModelImpl();
    }

    @Override
    public void onDestroy() {
    view=null;
    }

    @Override
    public void refresh(int page,int qid) {
        model.refresh(page,qid,this);
    }

    @Override
    public void excitingFunction(int type, int ID, boolean isexciting) {
        if(isexciting){
            model.User_tendencies_Function(Urls.CANCELEXCITING,type,ID,this);
        }
        else {
            model.User_tendencies_Function(Urls.EXCITING,type,ID,this);
        }
    }

    @Override
    public void naiveFunction(int type, int ID, boolean isnaive) {
        if(isnaive){
            model.User_tendencies_Function(Urls.CANCELNAIVE,type,ID,this);
        }
        else {
            model.User_tendencies_Function(Urls.NAIVE,type,ID,this);
        }
    }

    public void favoriteFunction(int ID,boolean isfavorite) {
        if(isfavorite){
            model.User_tendencies_Function(Urls.CANCELFAVORITE,3,ID,this);
        }
        else {
            model.User_tendencies_Function(Urls.FAVORITE,3,ID,this);
        }
    }

    @Override
    public void onNetworkError() {
        if(view!=null){
            view.show_Networkerror();
        }
    }

    @Override
    public void onSendSuccess() {
        if(view!=null){
            view.sendAnswerSuccess();
        }
    }

    @Override
    public void useronSuccess(int Function_id,boolean isQuestion) {
        if(view!=null) {
            view.User_Tendencies_Success(Function_id,isQuestion);
        }
    }

    @Override
    public void getUermessege() {
        model.getUserMessege(this);
    }

    @Override
    public void sendAnswer(int qid, String content, String image, String token) {
        model.sendAnswer(qid,content,image,token,this);
    }

    @Override
    public void onSuccess(List<DataBeans.DataBean.AnswersBean> answersBeans,boolean isRefresh,int page) {
        if(view!=null){
            view.getAnswerSuccess(answersBeans,isRefresh,page);
        }
    }

    @Override
    public void onSuccessGet(int id, String name, String avatar, String token,String avatar_id) {
        view.getSuccess(id,name,avatar,token,avatar_id);
    }
}
