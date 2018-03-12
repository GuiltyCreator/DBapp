package com.example.a7279.db.model;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

import com.example.a7279.db.base.BaseApp;
import com.example.a7279.db.bean.DataBeans;
import com.example.a7279.db.callback.OnGetMyFavoriteFinishedListener;
import com.example.a7279.db.callback.OnUserTendenciesFinishedListener;
import com.example.a7279.db.commons.Urls;
import com.example.a7279.db.utils.LogUtil;
import com.example.a7279.db.utils.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by a7279 on 2018/3/12.
 */

public class MyFavoriteModelImpl implements MyFavoriteModel {

    private List<OkHttpUtil.Param> list=new ArrayList<>();
    private OkHttpUtil.Param param1=new OkHttpUtil.Param();
    private OkHttpUtil.Param param2=new OkHttpUtil.Param();
    private OnGetMyFavoriteFinishedListener listener;
    private int questionPage;
    private boolean isRefresh;
    public DataBeans dataBeans;

    private List<OkHttpUtil.Param> user_tendencies_List=new ArrayList<>();
    private OkHttpUtil.Param user_tendencies_param1=new OkHttpUtil.Param();
    private OkHttpUtil.Param user_tendencies_param2=new OkHttpUtil.Param();
    private OkHttpUtil.Param user_tendencies_param3=new OkHttpUtil.Param();
    private OnUserTendenciesFinishedListener user_tendencies_listener;
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
                    break;
            }
        }
    };




    @Override
    public void getQuestion(int page, OnGetMyFavoriteFinishedListener listener) {

        this.listener=listener;
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(BaseApp.getContext());
        String token= preferences.getString("token","none");
        param1.setKey("page");
        param1.setValue(page+"");
        list.add(param1);
        param2.setKey("token");
        param2.setValue(token);
        list.add(param2);
        questionPage=page;
        if(questionPage==0){
            isRefresh=true;
        }
        else {
            isRefresh=false;
        }

        OkHttpUtil.httpFunction(Urls.GETFAVORITELIST, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message=new Message();
                message.what=0;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                Gson gson = new Gson();
                dataBeans = gson.fromJson(data, new TypeToken<DataBeans>() {
                }.getType());
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        },list);

    }

    @Override
    public void callback(int isSuccess, OnGetMyFavoriteFinishedListener listener) {
        if(dataBeans.getData().getQuestions()!=null) {
            listener.onGetMyFavoriteSuccess(dataBeans.getData().getQuestions(), questionPage, isRefresh);
        }
        else{
            return;
            /*listener.onGetMyFavoriteSuccess(null, questionPage, isRefresh)*/}
    }

    @Override
    public void User_tendencies_Function(String FUNCTION, int type, int ID, OnUserTendenciesFinishedListener listener) {
        this.user_tendencies_listener=listener;
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(BaseApp.getContext());
        String token= preferences.getString("token","none");
        if(type==3){
            user_tendencies_param1.setKey("qid");
            user_tendencies_param1.setValue(ID+"");
            user_tendencies_List.add(user_tendencies_param1);
            user_tendencies_param2.setKey("token");
            user_tendencies_param2.setValue(token);
            user_tendencies_List.add(user_tendencies_param2);
        }
        else {
            user_tendencies_param1.setKey("id");
            user_tendencies_param1.setValue(ID + "");
            user_tendencies_List.add(user_tendencies_param1);
            user_tendencies_param2.setKey("type");
            user_tendencies_param2.setValue(type + "");
            user_tendencies_List.add(user_tendencies_param2);
            user_tendencies_param3.setKey("token");
            user_tendencies_param3.setValue(token);
            user_tendencies_List.add(user_tendencies_param3);
        }

        OkHttpUtil.httpFunction(FUNCTION, new Callback() {
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
                Message message=new Message();
                message.what=3;
                handler.sendMessage(message);
            }
        },user_tendencies_List);

        switch (FUNCTION){
            case Urls.EXCITING:
                Function_id=1;
                break;
            case Urls.CANCELEXCITING:
                Function_id=2;
                break;
            case Urls.NAIVE:
                Function_id=3;
                break;
            case Urls.CANCELNAIVE:
                Function_id=4;
                break;
            case Urls.FAVORITE:
                Function_id=5;
                break;
            case Urls.CANCELFAVORITE:
                Function_id=6;
                break;

            default:
        }
    }

    @Override
    public void user_tendencies_callback(int isSuccess, OnUserTendenciesFinishedListener listener) {
        if(isSuccess==1){
            listener.useronSuccess(Function_id);}
        else if(isSuccess==0){listener.onNetworkError();}
    }
}
