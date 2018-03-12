package com.example.a7279.db.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.a7279.db.utils.ActivitiesCollectorUtil;

/**
 * Created by a7279 on 2018/2/10.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitiesCollectorUtil.addActivity(this);
       /* ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivitiesCollectorUtil.removeActivity(this);
    }
}
