package com.example.a7279.db.presenter;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.a7279.db.base.BaseApp;
import com.example.a7279.db.callback.OnResetPasswordFinishedListener;
import com.example.a7279.db.model.ResetPasswordModel;
import com.example.a7279.db.model.ResetPasswordModelImpl;
import com.example.a7279.db.view.ResetPasswordView;

/**
 * Created by a7279 on 2018/2/14.
 */

public class ResetPasswordPresenterImpl implements ResetPasswordPresenter,OnResetPasswordFinishedListener {
    private ResetPasswordView resetPasswordView;
    private ResetPasswordModel resetPasswordModel;

     public ResetPasswordPresenterImpl(ResetPasswordView resetPasswordView){
         this.resetPasswordView=resetPasswordView;
         resetPasswordModel=new ResetPasswordModelImpl();
     }

    @Override
    public void onDestroy() {
        resetPasswordView=null;
    }

    @Override
    public void validateCredentials( String password) {
        if(resetPasswordView!=null){
            resetPasswordView.showProgress();
        }
        resetPasswordModel.register(password,this);
    }


    @Override
    public void onNetworkError() {
         if(resetPasswordView!=null){
            resetPasswordView.show_Networkerror();
            resetPasswordView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if(resetPasswordView!=null){
            SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(BaseApp.getContext()).edit();
            editor.clear();
            resetPasswordView.hideProgress();
            resetPasswordView.showSuccess();
            resetPasswordView.navigateToLogin();
        }
    }
}
