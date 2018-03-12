package com.example.a7279.db.model;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

import com.example.a7279.db.base.BaseApp;
import com.example.a7279.db.callback.OnChangetAvatarListener;
import com.example.a7279.db.callback.OnGetMessegeSuccessListener;
import com.example.a7279.db.commons.Urls;
import com.example.a7279.db.utils.LogUtil;
import com.example.a7279.db.utils.OkHttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by a7279 on 2018/3/10.
 */

public class UserMessegeModelImpl implements UserMessegeModel {
    private OnGetMessegeSuccessListener listener;
    private OnChangetAvatarListener avatarListener;
    private String avatar_id;
    private String  username;
    private String  avatar;
    private String  token;
    private int id;

    private List<OkHttpUtil.Param> list=new ArrayList<>();
    private OkHttpUtil.Param param1=new OkHttpUtil.Param();
    private OkHttpUtil.Param param2=new OkHttpUtil.Param();

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch ( msg.what)
            {
                case 0:
                    //在此进行UI操作
                    avatarListener.onNetworkError();
                    break;
                case 1:
                    avatarListener.onSuccess();
                    break;
                default:
                    break;
            }
        }
    };





    @Override
    public void getUserMessege(OnGetMessegeSuccessListener listener) {
        this.listener=listener;
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(BaseApp.getContext());
        id= preferences.getInt("id",0);
        username= preferences.getString("username","none");
        avatar=preferences.getString("avatar","none");
        token= preferences.getString("token","none");
         avatar_id=preferences.getString("avatar_id",id+String.valueOf(System.currentTimeMillis()));
        this.listener.onSuccessGet(id,username,avatar,token,avatar_id);
    }

    @Override
    public void changeAvatar(String avatar,String token,OnChangetAvatarListener listener) {
        this.avatarListener=listener;

        avatar_id=id+String.valueOf(System.currentTimeMillis());
        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(BaseApp.getContext()).edit();
        editor.putString("avatar",avatar);
        editor.putString("avatar_id",avatar_id);
        editor.apply();

        LogUtil.d("UserMessegeActivity",avatar+"  "+token);

        param1.setKey("avatar");
        param1.setValue(avatar);
        list.add(param1);
        param2.setKey("token");
        param2.setValue(token);
        list.add(param2);
        OkHttpUtil.httpFunction(Urls.REVISE_THE_HEAD_IMAGE, new Callback() {
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
