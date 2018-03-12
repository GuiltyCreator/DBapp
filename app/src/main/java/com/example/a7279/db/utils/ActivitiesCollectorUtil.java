package com.example.a7279.db.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a7279 on 2018/2/10.
 */

public class ActivitiesCollectorUtil {
    public static List<Activity> activities=new ArrayList<>();
    public  static void addActivity(Activity activity)
    {
        activities.add(activity);
    }
    public  static  void  removeActivity(Activity activity)
    {
        activities.remove(activity);
    }
    public static void finishAll()
    {
        for(Activity activity:activities)
        {
            if(!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}