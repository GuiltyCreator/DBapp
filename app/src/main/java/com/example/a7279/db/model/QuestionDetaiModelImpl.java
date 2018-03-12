package com.example.a7279.db.model;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.a7279.db.base.BaseApp;
import com.example.a7279.db.bean.DataBeans;
import com.example.a7279.db.callback.OnAnswerUserTendenciesFinishedListener;
import com.example.a7279.db.callback.OnGetAnswerFinishedListener;
import com.example.a7279.db.callback.OnGetMessegeSuccessListener;
import com.example.a7279.db.callback.OnSendAnswerFinishedListener;
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
 * Created by a7279 on 2018/3/11.
 */

public class QuestionDetaiModelImpl implements QuestionDetaiModel {


    private static final String TAG = "QuestionDetaiModelImpl";
    private List<OkHttpUtil.Param> list=new ArrayList<>();
    private OkHttpUtil.Param param1=new OkHttpUtil.Param();
    private OkHttpUtil.Param param2=new OkHttpUtil.Param();
    private OkHttpUtil.Param param3=new OkHttpUtil.Param();
    private   DataBeans dataBeans;
    private OnGetAnswerFinishedListener listener;

    private List<OkHttpUtil.Param> user_tendencies_List=new ArrayList<>();
    private OkHttpUtil.Param user_tendencies_param1=new OkHttpUtil.Param();
    private OkHttpUtil.Param user_tendencies_param2=new OkHttpUtil.Param();
    private OkHttpUtil.Param user_tendencies_param3=new OkHttpUtil.Param();
    private OnAnswerUserTendenciesFinishedListener user_tendencies_listener;
    private int Function_id;
    private boolean isQuestion;

    private List<OkHttpUtil.Param> send_Answer_List=new ArrayList<>();
    private OkHttpUtil.Param send_Answer_param1=new OkHttpUtil.Param();
    private OkHttpUtil.Param send_Answer_param2=new OkHttpUtil.Param();
    private OkHttpUtil.Param send_Answer_param3=new OkHttpUtil.Param();
    private OkHttpUtil.Param send_Answer_param4=new OkHttpUtil.Param();
    private OnSendAnswerFinishedListener sendAnswerFinishedListener;

    private OnGetMessegeSuccessListener userListener;
    private String avatar_id;
    private String  username;
    private String  avatar;
    private String  token;
    private int id;
    private boolean isRefresh;
    private int page;


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
                break;
                case 4:sendAnswer_callback(true,sendAnswerFinishedListener);
                break;
                case 5:sendAnswer_callback(false,sendAnswerFinishedListener);
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    public void refresh(int page,int qid ,OnGetAnswerFinishedListener listener) {
        this.listener=listener;
        param1.setKey("page");
        param1.setValue(page+"");
        list.add(param1);
        param2.setKey("qid");
        param2.setValue(qid+"");
        list.add(param2);
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(BaseApp.getContext());
        String token= preferences.getString("token","none");
        param3.setKey("token");
        param3.setValue(token);
        list.add(param3);
        LogUtil.d(TAG,page+"  "+qid+"  "+token);

        if(page==0){
            isRefresh=true;
        }
        else {
            isRefresh=false;
        }
        this.page=page;

        OkHttpUtil.httpFunction(Urls.GETANSWERLIST, new Callback() {
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
                LogUtil.d(TAG,"refresh:"+dataBeans.getInfo());
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
        }},list);
    }

    @Override
    public void callback(int isSuccess, OnGetAnswerFinishedListener listener) {
        if(isSuccess==1){
            listener.onSuccess(GetAnswerList(),isRefresh,page);}
        else if(isSuccess==0){listener.onNetworkError();}
    }

    @Override
    public List<DataBeans.DataBean.AnswersBean> GetAnswerList() {
        return dataBeans.getData().getAnswers();
    }

    @Override
    public void User_tendencies_Function(String FUNCTION, int type, int ID ,OnAnswerUserTendenciesFinishedListener listener) {
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
            isQuestion=true;
        }
        else {
            if(type==1){
                isQuestion=true;
            }
            if(type==2){
                isQuestion=false;
            }
            user_tendencies_param1.setKey("id");
            user_tendencies_param1.setValue(ID+"");
            user_tendencies_List.add(user_tendencies_param1);
            user_tendencies_param2.setKey("type");
            user_tendencies_param2.setValue(type+"");
            user_tendencies_List.add(user_tendencies_param2);
            user_tendencies_param3.setKey("token");
            user_tendencies_param3.setValue(token);
            user_tendencies_List.add(user_tendencies_param3);
        }
        LogUtil.d(TAG,"type:"+type+" id:"+ID+" token:"+token);
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
    public void user_tendencies_callback(int isSuccess, OnAnswerUserTendenciesFinishedListener listener) {
        if(isSuccess==1){
            listener.useronSuccess(Function_id,isQuestion);}
        else if(isSuccess==0){listener.onNetworkError();}
    }

    @Override
    public void getUserMessege(OnGetMessegeSuccessListener listener) {
        this.userListener=listener;
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(BaseApp.getContext());
        id= preferences.getInt("id",0);
        username= preferences.getString("username","none");
        avatar=preferences.getString("avatar","none");
        token= preferences.getString("token","none");
        avatar_id=preferences.getString("avatar_id",id+String.valueOf(System.currentTimeMillis()));
        this.userListener.onSuccessGet(id,username,avatar,token,avatar_id);
    }

    @Override
    public void sendAnswer(int qid, String content, String image, String token, OnSendAnswerFinishedListener listener) {

        LogUtil.d(TAG,"qid:"+qid+" content:"+content+" image:"+image+" token:"+token);

        this.sendAnswerFinishedListener=listener;
        this.send_Answer_param1.setKey("qid");
        this.send_Answer_param1.setValue(qid+"");
        this.send_Answer_List.add( this.send_Answer_param1);
        this.send_Answer_param2.setKey("content");
        this.send_Answer_param2.setValue(content);
        this.send_Answer_List.add( this.send_Answer_param2);
        if(image!=null){
            this.send_Answer_param3.setKey("images");
            this.send_Answer_param3.setValue(image);
            this.send_Answer_List.add( this.send_Answer_param3);
        }
        this.send_Answer_param4.setKey("token");
        this.send_Answer_param4.setValue(token);
        this.send_Answer_List.add( this.send_Answer_param4);

        OkHttpUtil.httpFunction(Urls.ANSWER, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message=new Message();
                message.what=5;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message=new Message();
                message.what=4;
                handler.sendMessage(message);
            }
        },this.send_Answer_List);

    }

    @Override
    public void sendAnswer_callback(boolean isSuccess,OnSendAnswerFinishedListener listener) {
        if(isSuccess){
            listener.onSendSuccess();
        }
        else {
            listener.onNetworkError();
        }
    }
}
