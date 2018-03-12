package com.example.a7279.db.utils;

import android.util.Log;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import org.json.JSONObject;

import java.io.File;



/**
 * Created by a7279 on 2018/3/10.
 */

public class QiNiuUtil {
    public static String picPath;
    public static String uploadImg2QiNiu(File file, final String AccessKey, final String SecretKey, final String bucket, String key, final String uri) {
        try {
            Log.d("qiniu","文件大小："+ FileUtil.FormetFileSize(FileUtil.getFileSize(file)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        UploadManager uploadManager = new UploadManager(new Configuration.Builder().zone(FixedZone.zone2).build());
        //SimpleDateFormat sdf = new SimpleDateFormat(imagePath.substring(imagePath.lastIndexOf("/") + 1, imagePath.length()));
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        //String key = "icon_" + sdf.format(new Date());
        //String key="测试图片";
        //String token= Auth.create(AccessKey, SecretKey).uploadToken("db_avatar",key,3600,new StringMap().put("insertOnly",0));
        //String key = imagePath.substring(imagePath.lastIndexOf("/"),imagePath.length())+"avatar" ;

//"db-avatar",key,3600, new StringMap().put("insertOnly", 0)
        uploadManager.put(file, key, Auth.create(AccessKey, SecretKey).uploadToken(bucket,key,3600,new StringMap().put("insertOnly",0)),new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {

                if (info.isOK()) {
                    Log.i("qiniu", "token:" + Auth.create(AccessKey, SecretKey).uploadToken(bucket));
                    Log.i("qiniu", "complete: " + picPath);
                }
                Log.i("qiniu", key +",\r\n "+ info +",\r\n "+ res);
            }
        }, null);
        picPath = "http://"+uri+"/"+ key;

        return picPath;
    }
}
