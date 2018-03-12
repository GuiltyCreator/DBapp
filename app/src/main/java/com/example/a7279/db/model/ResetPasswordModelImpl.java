package com.example.a7279.db.model;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

import com.example.a7279.db.base.BaseApp;
import com.example.a7279.db.bean.DataBeans;
import com.example.a7279.db.callback.OnRegisterFinishedListener;
import com.example.a7279.db.callback.OnResetPasswordFinishedListener;
import com.example.a7279.db.commons.Urls;
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
 * Created by a7279 on 2018/2/14.
 */

public class ResetPasswordModelImpl implements ResetPasswordModel {

    private List<OkHttpUtil.Param> list=new ArrayList<>();
    private OkHttpUtil.Param param1=new OkHttpUtil.Param();
    private OkHttpUtil.Param param2=new OkHttpUtil.Param();
    private int isSuccess=0;
    private OnResetPasswordFinishedListener listener;
    public DataBeans dataBeans;


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
                    listener.onSuccess();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void register(String password, OnResetPasswordFinishedListener listener) {

        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(BaseApp.getContext());
        String token=pref.getString("token","");


        param1.setKey("password");
        param1.setValue(password);
        list.add(param1);
        param2.setKey("token");
        param2.setValue(token);
        list.add(param2);

        this.listener=listener;

        OkHttpUtil.httpFunction(Urls.CHANGEPASSWORD,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //网络请求失败的操作
                isSuccess=0;
                Message message=new Message();
                message.what=0;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //网络请求成功的操作
                String  data=response.body().string();
                //LogUtil.d(TAG,data);
                Gson gson=new Gson();
                dataBeans=gson.fromJson(data,new TypeToken<DataBeans>(){}.getType());
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        },list);
    }

}
