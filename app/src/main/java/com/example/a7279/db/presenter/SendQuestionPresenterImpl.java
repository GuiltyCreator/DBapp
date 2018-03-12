package com.example.a7279.db.presenter;

import com.example.a7279.db.callback.OnSendAnswerFinishedListener;
import com.example.a7279.db.model.SendQuestionModel;
import com.example.a7279.db.model.SendQuestionModelImpl;
import com.example.a7279.db.view.SendQuestionView;

/**
 * Created by a7279 on 2018/3/12.
 */

public class SendQuestionPresenterImpl implements SendQuestionPresenter,OnSendAnswerFinishedListener {

    private SendQuestionView view;
    private SendQuestionModel model;

    public SendQuestionPresenterImpl(SendQuestionView view){
        this.view=view;
        this.model= new SendQuestionModelImpl();
    }


    @Override
    public void sendQuestion(String title, String content, String image, String token) {
        model.sendQuestion(title,content,image,token,this);
    }

    @Override
    public void onDestroy() {
        view=null;
    }

    @Override
    public void onNetworkError() {
        if(view!=null)
        {
            view.NetworkErro();
        }
    }

    @Override
    public void onSendSuccess() {
        if(view!=null)
        {
            view.sendSuccess();
        }
    }
}
