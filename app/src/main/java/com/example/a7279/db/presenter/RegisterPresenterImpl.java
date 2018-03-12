package com.example.a7279.db.presenter;

import com.example.a7279.db.callback.OnRegisterFinishedListener;
import com.example.a7279.db.model.RegisterModel;
import com.example.a7279.db.model.RegisterModelImpl;
import com.example.a7279.db.view.RegisterView;

/**
 * Created by a7279 on 2018/2/14.
 */

public class RegisterPresenterImpl implements RegisterPresenter,OnRegisterFinishedListener {

    private RegisterView registerView;
    private RegisterModel registerModel;

    public  RegisterPresenterImpl(RegisterView registerView)
    {
        this.registerView=registerView;
        this.registerModel=new RegisterModelImpl();
    }

    @Override
    public void onDestroy() {
        registerView=null;
    }

    @Override
    public void validateCredentials(String username, String password) {
        if(registerView!=null){
            registerView.showProgress();
        }
        registerModel.register(username,password,this);
    }

    @Override
    public void onDataeError() {
        if(registerView!=null){
            registerView.hideProgress();
            registerView.show_Dataerror();
        }

    }

    @Override
    public void onNetworkError() {
        if(registerView!=null){
            registerView.hideProgress();
            registerView.show_Networkerror();
        }
    }

    @Override
    public void onSuccess() {
            registerView.showSuccess();
        registerView.navigateToLogin();
    }
}
