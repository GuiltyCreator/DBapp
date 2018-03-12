package com.example.a7279.db.model;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.TextView;

import com.example.a7279.db.base.BaseApp;
import com.example.a7279.db.bean.DataBeans;
import com.example.a7279.db.callback.OnGetMessegeSuccessListener;
import com.example.a7279.db.callback.OnGetQuestionsFinishedListener;
import com.example.a7279.db.callback.OnLoginFinishedListener;
import com.example.a7279.db.callback.OnUserTendenciesFinishedListener;
import com.example.a7279.db.commons.Urls;
import com.example.a7279.db.utils.LogUtil;
import com.example.a7279.db.utils.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by a7279 on 2018/3/5.
 */

public class MainModelImpl implements MainModel {
    private static final String TAG = "MainModelImpl";
    private List<OkHttpUtil.Param> list=new ArrayList<>();
    private OkHttpUtil.Param param1=new OkHttpUtil.Param();
    private OkHttpUtil.Param param2=new OkHttpUtil.Param();
    private OnGetQuestionsFinishedListener listener;
    private OnGetMessegeSuccessListener messegeListener;
    public  DataBeans dataBeans;

    private List<OkHttpUtil.Param> user_tendencies_List=new ArrayList<>();
    private OkHttpUtil.Param user_tendencies_param1=new OkHttpUtil.Param();
    private OkHttpUtil.Param user_tendencies_param2=new OkHttpUtil.Param();
    private OkHttpUtil.Param user_tendencies_param3=new OkHttpUtil.Param();
    private OnUserTendenciesFinishedListener user_tendencies_listener;
    public  DataBeans dataBeans1;
    private int Function_id;




    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:callback(0,listener);
                    break;
                case 1:callback(1,listener);
                    break;
                case 2:user_tendencies_callback(0,user_tendencies_listener);
                break;
                case 3:user_tendencies_callback(1,user_tendencies_listener);
                default:
                    LogUtil.d(TAG,"000");
                    break;
            }
        }
    };




    @Override
    public void refresh(int page,final OnGetQuestionsFinishedListener listener) {
        this.listener=listener;
        param1.setKey("page");
        param1.setValue(page+"");
        list.add(param1);
        param2.setKey("token");
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(BaseApp.getContext());
        String token= preferences.getString("token","none");
        param2.setValue(token);
        list.add(param2);
        OkHttpUtil.httpFunction(Urls.GETQUESTIONLIST, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message=new Message();
                message.what=0;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String  data=response.body().string();
                Gson gson=new Gson();
                dataBeans=gson.fromJson(data,new TypeToken<DataBeans>(){}.getType());
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        },list);
    }

    @Override
    public void callback(int isSuccess, OnGetQuestionsFinishedListener listener) {
        if(isSuccess==1){
            listener.onSuccess();}
        else if(isSuccess==0){listener.onNetworkError();}
    }

    @Override
    public List<DataBeans.DataBean.QuestionsBean> getQuestions() {
        LogUtil.d(TAG,dataBeans.getInfo());
        if(dataBeans.getStatus()==200){
        return dataBeans.getData().getQuestions();
        }
        else return null;
    }




    @Override
    public void User_tendencies_Function(String FUNCTION, int type, int ID,final OnUserTendenciesFinishedListener listener) {
        this.user_tendencies_listener=listener;
        user_tendencies_param3.setKey("token");
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(BaseApp.getContext());
        String token= preferences.getString("token","none");

        switch (FUNCTION){
            case Urls.EXCITING:
                user_tendencies_param1.setKey("id");
                user_tendencies_param1.setValue(ID+"");
                user_tendencies_List.add(user_tendencies_param1);
                user_tendencies_param2.setKey("type");
                user_tendencies_param2.setValue(type+"");
                user_tendencies_List.add(user_tendencies_param2);
                user_tendencies_param3.setValue(token);
                user_tendencies_List.add(user_tendencies_param3);
                Function_id=1;
                OkHttpUtil.httpFunction(Urls.EXCITING, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        user_tendencies_List.clear();
                        Message message=new Message();
                        message.what=2;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        user_tendencies_List.clear();
                        Log(response.body().string());
                        Message message=new Message();
                        message.what=3;
                        handler.sendMessage(message);
                    }
                },user_tendencies_List);
                break;

            case Urls.CANCELEXCITING:
                LogUtil.d(TAG,"CANCELEXCITING");
                user_tendencies_param1.setKey("id");
                user_tendencies_param1.setValue(ID+"");
                user_tendencies_List.add(user_tendencies_param1);
                user_tendencies_param2.setKey("type");
                user_tendencies_param2.setValue(type+"");
                user_tendencies_List.add(user_tendencies_param2);
                user_tendencies_param3.setValue(token);
                user_tendencies_List.add(user_tendencies_param3);
                Function_id=2;
                OkHttpUtil.httpFunction(Urls.CANCELEXCITING, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        user_tendencies_List.clear();
                        Message message=new Message();
                        message.what=2;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        user_tendencies_List.clear();
                        Log(response.body().string());
                        Message message=new Message();
                        message.what=3;
                        handler.sendMessage(message);
                    }
                },user_tendencies_List);
                break;

            case Urls.NAIVE:
                user_tendencies_param1.setKey("id");
                user_tendencies_param1.setValue(ID+"");
                user_tendencies_List.add(user_tendencies_param1);
                user_tendencies_param2.setKey("type");
                user_tendencies_param2.setValue(type+"");
                user_tendencies_List.add(user_tendencies_param2);
                user_tendencies_param3.setValue(token);
                user_tendencies_List.add(user_tendencies_param3);
                Function_id=3;
                OkHttpUtil.httpFunction(Urls.NAIVE, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        user_tendencies_List.clear();
                        Message message=new Message();
                        message.what=2;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log(response.body().string());
                        user_tendencies_List.clear();
                        Message message=new Message();
                        message.what=3;
                        handler.sendMessage(message);
                    }
                },user_tendencies_List);
                break;

            case Urls.CANCELNAIVE:
                user_tendencies_param1.setKey("id");
                user_tendencies_param1.setValue(ID+"");
                user_tendencies_List.add(user_tendencies_param1);
                user_tendencies_param2.setKey("type");
                user_tendencies_param2.setValue(type+"");
                user_tendencies_List.add(user_tendencies_param2);
                user_tendencies_param3.setValue(token);
                user_tendencies_List.add(user_tendencies_param3);
                Function_id=4;
                OkHttpUtil.httpFunction(Urls.CANCELNAIVE, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        user_tendencies_List.clear();
                        Message message=new Message();
                        message.what=2;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log(response.body().string());
                        user_tendencies_List.clear();
                        Message message=new Message();
                        message.what=3;
                        handler.sendMessage(message);
                    }
                },user_tendencies_List);
                break;

            case Urls.FAVORITE:
                user_tendencies_param1.setKey("qid");
                user_tendencies_param1.setValue(ID+"");
                user_tendencies_List.add(user_tendencies_param1);
                user_tendencies_param3.setValue(token);
                user_tendencies_List.add(user_tendencies_param3);
                Function_id=5;
                LogUtil.d(TAG,ID+"");
                OkHttpUtil.httpFunction(Urls.FAVORITE, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        user_tendencies_List.clear();
                        Message message=new Message();
                        message.what=2;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        user_tendencies_List.clear();
                        Log(response.body().string());
                        Message message=new Message();
                        message.what=3;
                        handler.sendMessage(message);
                    }
                },user_tendencies_List);
                break;

            case Urls.CANCELFAVORITE:
               user_tendencies_List.clear();
                user_tendencies_param1.setKey("qid");
                user_tendencies_param1.setValue(ID+"");
                user_tendencies_List.add(user_tendencies_param1);
                user_tendencies_param3.setValue(token);
                user_tendencies_List.add(user_tendencies_param3);
                Function_id=6;
                OkHttpUtil.httpFunction(Urls.CANCELFAVORITE, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message message=new Message();
                        message.what=2;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        user_tendencies_List.clear();
                        Log(response.body().string());
                        Message message=new Message();
                        message.what=3;
                        handler.sendMessage(message);
                    }
                },user_tendencies_List);
                break;



            default:
                break;
        }

    }

    @Override
    public void user_tendencies_callback(int isSuccess, OnUserTendenciesFinishedListener listener) {
        if(isSuccess==1){
            listener.useronSuccess(Function_id);}
        else if(isSuccess==0){listener.onNetworkError();}
    }

    @Override
    public void getUserMessege(OnGetMessegeSuccessListener listener) {
        this.messegeListener=listener;
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(BaseApp.getContext());
        int id= preferences.getInt("id",0);
        String  username= preferences.getString("username","none");
        String  avatar=preferences.getString("avatar","none");
        String  token= preferences.getString("token","none");
        String avatar_id=preferences.getString("avatar_id",id+String.valueOf(System.currentTimeMillis()));
        this.messegeListener.onSuccessGet(id,username,avatar,token,avatar_id);
    }

    private void Log(String json) {
        String  data=json;
        Gson gson=new Gson();
        dataBeans1=gson.fromJson(data,new TypeToken<DataBeans>(){}.getType());
        LogUtil.d(TAG,"info:"+dataBeans1.getInfo());
    }

}
