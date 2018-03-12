package com.example.a7279.db.presenter;

import com.example.a7279.db.bean.DataBeans;
import com.example.a7279.db.callback.OnGetMyFavoriteFinishedListener;
import com.example.a7279.db.callback.OnUserTendenciesFinishedListener;
import com.example.a7279.db.commons.Urls;
import com.example.a7279.db.model.MyFavoriteModel;
import com.example.a7279.db.model.MyFavoriteModelImpl;
import com.example.a7279.db.view.MyFavoriteView;

import java.util.List;

/**
 * Created by a7279 on 2018/3/12.
 */

public class MyFavoritePresenterImpl implements OnGetMyFavoriteFinishedListener,OnUserTendenciesFinishedListener,MyFavoritePresenter {

    private MyFavoriteView view;
    private MyFavoriteModel model;


    public MyFavoritePresenterImpl( MyFavoriteView view) {

        this.view=view;
        this.model= new MyFavoriteModelImpl();
    }


    @Override
    public void onDestroy() {
        view=null;
    }

    @Override
    public void getQuestion(int page) {
        model.getQuestion(page,this);
    }

    @Override
    public void favoriteFunction(int ID,boolean isfavorite) {
        if(isfavorite){
            model.User_tendencies_Function(Urls.CANCELFAVORITE,3,ID,this);
        }
        else {
            model.User_tendencies_Function(Urls.FAVORITE,3,ID,this);
        }
    }



    @Override
    public void naiveFunction(int type,int ID, boolean isnaive) {
        if(isnaive){
            model.User_tendencies_Function(Urls.CANCELNAIVE,type,ID,this);
        }
        else {
            model.User_tendencies_Function(Urls.NAIVE,type,ID,this);
        }
    }

    @Override
    public void excitingFunction(int type,int ID, boolean isexciting) {
        if(isexciting){
            model.User_tendencies_Function(Urls.CANCELEXCITING,type,ID,this);
        }
        else {
            model.User_tendencies_Function(Urls.EXCITING,type,ID,this);
        }

    }

    @Override
    public void useronSuccess(int Function_id) {
        if(view!=null) {
            view.User_Tendencies_Success(Function_id);
        }
    }

    @Override
    public void onNetworkError() {
        if(view!=null){
            view.showNetWorkError();
        }
    }


    @Override
    public void onGetMyFavoriteSuccess(List<DataBeans.DataBean.QuestionsBean> list, int page, boolean isRefresh) {
        if(view!=null){
            view.getQuestionSuccess(list,page,isRefresh);
        }
    }
}
