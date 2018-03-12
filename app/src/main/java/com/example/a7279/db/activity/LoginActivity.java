package com.example.a7279.db.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.a7279.db.R;
import com.example.a7279.db.base.BaseActivity;
import com.example.a7279.db.base.BaseApp;
import com.example.a7279.db.presenter.LoginPresenter;
import com.example.a7279.db.presenter.LoginPresenterImpl;
import com.example.a7279.db.utils.ActivitiesCollectorUtil;
import com.example.a7279.db.utils.LogUtil;
import com.example.a7279.db.view.LoginView;
import com.example.a7279.db.widgets.My_EdiText;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {

private ProgressBar progressBar;
private LoginPresenter presenter;
private My_EdiText e1;
private My_EdiText e2;
    private static final String TAG = "LoginActivity";
    private long time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



    e1=findViewById(R.id.my_edit1);
        e2=findViewById(R.id.my_edit2);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
        progressBar=findViewById(R.id.progress_Bar);
        presenter = new LoginPresenterImpl(this);

    }

    @Override
    public void onClick(View view) {
    switch (view.getId())
    {
        case R.id.login:
            presenter.validateCredentials(e1.getText(),e2.getText());
            break;
        case R.id.register:
                navigateToRegister();
            break;
        default:
    }
    }

    @Override
    public void show_Dataerror() {
        Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void show_Networkerror() {
        Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void navigateToHome() {
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToRegister() {
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }


    /**
     * 双击返回桌面
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - time > 2000)) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            } else {
                ActivitiesCollectorUtil.finishAll();
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
