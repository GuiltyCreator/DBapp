package com.example.a7279.db.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.a7279.db.R;
import com.example.a7279.db.base.BaseActivity;
import com.example.a7279.db.base.BaseApp;
import com.example.a7279.db.presenter.ResetPasswordPresenter;
import com.example.a7279.db.presenter.ResetPasswordPresenterImpl;
import com.example.a7279.db.utils.LogUtil;
import com.example.a7279.db.view.ResetPasswordView;
import com.example.a7279.db.widgets.My_EdiText;

public class ResetPasswordActivity extends BaseActivity implements ResetPasswordView,View.OnClickListener {


    private ResetPasswordPresenter presenter;
    private My_EdiText newpassword1;
    private My_EdiText newpassword2;
    private static final String TAG = "ResetPasswordActivity";

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        newpassword1=findViewById(R.id.reset_new_password1);
        newpassword2=findViewById(R.id.reset_new_password2);
        progressBar=findViewById(R.id.reset_progressBar);
        presenter=new ResetPasswordPresenterImpl(this);

        findViewById(R.id.reset_button1).setOnClickListener(this);
        findViewById(R.id.reset_button2).setOnClickListener(this);
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
    public void navigateToLogin() {
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(BaseApp.getContext()).edit();
        editor.putString("token","none");
        editor.apply();
        Intent intent=new Intent(ResetPasswordActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showSuccess() {
        Toast.makeText(this, "修改成功，请重新登陆", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reset_button1:
                if(newpassword1.getText().equals(newpassword2.getText())){
                        presenter.validateCredentials(newpassword1.getText());
                }
                else {
                    Toast.makeText(this, "两次密码不一致，请重新输入 ", Toast.LENGTH_SHORT).show();
                    //LogUtil.d(TAG,password1.getText()+"  "+password2.getText());
                    newpassword1.clear();
                    newpassword2.clear();
                }
                break;
            case R.id.reset_button2:
                navigateToLogin();
                break;
            default:
        }
    }
}
