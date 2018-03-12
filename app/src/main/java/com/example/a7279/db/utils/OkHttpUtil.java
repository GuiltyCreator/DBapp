package com.example.a7279.db.utils;

import com.example.a7279.db.commons.Urls;

import java.util.List;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by a7279 on 2018/2/10.
 * Description : OkHttp网络请求工具类
 */

public class OkHttpUtil {
    private OkHttpClient mOkHttpClient;
    private static final String TAG = "OkHttpUtil";

    /**
    * post请求
    * @param Url 功能Url
    * @param params 请求参数
    * @param callback 回调
    *
    *
    * */
    static public void httpFunction(String  Url,Callback callback,List<Param> params)
    {

        RequestBody requestBody=buildPostRequest(params);
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(Urls.BASEURL+Url).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }
    private static RequestBody buildPostRequest(List<Param> params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        //LogUtil.d(TAG,param.getKey()+"    "+param.getValue());
        }
        RequestBody requestBody = builder.build();
        return requestBody;
    }

    public static class Param {

        String key;
        String value;

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setKey(String key) {
            this.key = key;
        }



        public String getKey() {
            return key;
        }

        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

    }
}
