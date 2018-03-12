package com.example.a7279.db.presenter;

import com.example.a7279.db.callback.OnLoginFinishedListener;
import com.example.a7279.db.model.LoginModel;
import com.example.a7279.db.model.LoginModelImpl;
import com.example.a7279.db.view.LoginView;

/**
 * Created by a7279 on 2018/2/14.
 */

public class LoginPresenterImpl implements LoginPresenter,OnLoginFinishedListener {
        private LoginView loginView;
        private LoginModel loginModel;

        public  LoginPresenterImpl(LoginView loginView){
            this.loginView = loginView;
            this.loginModel = new LoginModelImpl();
        }


    @Override
    public void onDestroy() {
        loginView=null;
    }

    @Override
    public void validateCredentials(String username, String password) {
        if(loginView!=null){
            loginView.showProgress();
        }
        loginModel.login(username,password,this);
    }

    @Override
    public void onDataeError() {
            if(loginView!=null){
                loginView.show_Dataerror();
                loginView.hideProgress();
            }

    }

    @Override
    public void onNetworkError() {
        if(loginView!=null){
            loginView.show_Networkerror();
            loginView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {

            loginView.hideProgress();
            loginView.navigateToHome();
    }
}
