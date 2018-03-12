package com.example.a7279.db.presenter;

import com.example.a7279.db.bean.DataBeans;
import com.example.a7279.db.callback.OnGetMessegeSuccessListener;
import com.example.a7279.db.callback.OnGetQuestionsFinishedListener;
import com.example.a7279.db.callback.OnUserTendenciesFinishedListener;
import com.example.a7279.db.commons.Urls;
import com.example.a7279.db.model.MainModel;
import com.example.a7279.db.model.MainModelImpl;
import com.example.a7279.db.utils.LogUtil;
import com.example.a7279.db.view.MainView;

import java.util.List;

/**
 * Created by a7279 on 2018/3/5.
 */

public class MainPresenterImpl implements MainPresenter,OnGetQuestionsFinishedListener,OnUserTendenciesFinishedListener,OnGetMessegeSuccessListener {
    private static final String TAG = "MainPresenterImpl";
    private MainView mainView;
    private MainModel mainModel;

    public  MainPresenterImpl(MainView mainView)
    {
        this.mainView=mainView;
        mainModel=new MainModelImpl();
    }


    @Override
    public void onDestroy() {
        mainView=null;
    }

    @Override
    public void refresh(int page) {
    mainModel.refresh(page,this);
    }

    @Override
    public List<DataBeans.DataBean.QuestionsBean> getQuestions() {
        return mainModel.getQuestions();
    }

    @Override
    public void onNetworkError() {
        if(mainView!=null){
            mainView.show_Networkerror();
        }
    }

    @Override
    public void onSuccess() {
        if(mainView!=null) {
            mainView.Getquestion();
        }
    }

    @Override
    public void favoriteFunction(int ID,boolean isfavorite) {
        if(isfavorite){
            mainModel.User_tendencies_Function(Urls.CANCELFAVORITE,3,ID,this);
        }
        else {
            mainModel.User_tendencies_Function(Urls.FAVORITE,3,ID,this);
        }
    }



    @Override
    public void naiveFunction(int type,int ID, boolean isnaive) {
        if(isnaive){
            mainModel.User_tendencies_Function(Urls.CANCELNAIVE,type,ID,this);
        }
        else {
            mainModel.User_tendencies_Function(Urls.NAIVE,type,ID,this);
        }
    }

    @Override
    public void excitingFunction(int type,int ID, boolean isexciting) {
        if(isexciting){
            mainModel.User_tendencies_Function(Urls.CANCELEXCITING,type,ID,this);
        }
        else {
            mainModel.User_tendencies_Function(Urls.EXCITING,type,ID,this);
        }

    }

    @Override
    public void useronSuccess(int Function_id) {
        if(mainView!=null) {
            mainView.User_Tendencies_Success(Function_id);
        }
    }

    @Override
    public void onSuccessGet(int id, String name, String avatar, String token,String avatar_id) {
        mainView.getSuccess(id,name,avatar,token,avatar_id);
    }

    @Override
    public void getUermessege() {
        mainModel.getUserMessege(this);
    }
}
