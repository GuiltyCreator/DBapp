package com.example.a7279.db.model;

import android.os.Handler;
import android.os.Message;

import com.example.a7279.db.callback.OnSendAnswerFinishedListener;
import com.example.a7279.db.commons.Urls;
import com.example.a7279.db.utils.OkHttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by a7279 on 2018/3/12.
 */

public class SendQuestionModelImpl implements SendQuestionModel {

    private List<OkHttpUtil.Param> list=new ArrayList<>();
    private OkHttpUtil.Param param1=new OkHttpUtil.Param();
    private OkHttpUtil.Param param2=new OkHttpUtil.Param();
    private OkHttpUtil.Param param3=new OkHttpUtil.Param();
    private OkHttpUtil.Param param4=new OkHttpUtil.Param();
    private OnSendAnswerFinishedListener listener;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch ( msg.what)
            {
                case 0:
                    //在此进行UI操作
                    listener.onNetworkError();
                    break;
                case 1:
                    listener.onSendSuccess();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void sendQuestion(String title, String content, String image, String token, OnSendAnswerFinishedListener listener) {
        this.listener=listener;
        param1.setKey("title");
        param1.setValue(title);
        list.add(param1);
        param2.setKey("content");
        param2.setValue(content);
        list.add(param2);
        if(image!=null){
            param3.setKey("images");
            param3.setValue(image);
            list.add(param3);
        }
        param4.setKey("token");
        param4.setValue(token);
        list.add(param4);

        OkHttpUtil.httpFunction(Urls.QUESTION, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message=new Message();
                message.what=0;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        },list);
    }
}
