package com.example.a7279.db.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.a7279.db.R;
import com.example.a7279.db.base.BaseActivity;
import com.example.a7279.db.presenter.RegisterPresenter;
import com.example.a7279.db.presenter.RegisterPresenterImpl;
import com.example.a7279.db.utils.LogUtil;
import com.example.a7279.db.view.RegisterView;
import com.example.a7279.db.widgets.My_EdiText;

public class RegisterActivity extends BaseActivity implements RegisterView,View.OnClickListener{

    private My_EdiText username;
    private My_EdiText  password1;
    private My_EdiText password2;
    private ProgressBar progressBar;
    private RegisterPresenter presenter;
    private static final String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=findViewById(R.id.register_username);
        password1=findViewById(R.id.register_password1);
        password2=findViewById(R.id.register_password2);
        progressBar=findViewById(R.id.register_progressBar);
        presenter=new RegisterPresenterImpl(this);

        findViewById(R.id.register_button1).setOnClickListener(this);
        findViewById(R.id.register_button2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_button1:
                if(!password1.equals("")&&!password2.equals("")&&!username.getText().equals("")) {
                    if (password1.getText().equals(password2.getText())) {
                        presenter.validateCredentials(username.getText(), password1.getText());
                    } else {
                        Toast.makeText(this, "两次密码不一致，请重新输入 ", Toast.LENGTH_SHORT).show();
                        //LogUtil.d(TAG,password1.getText()+"  "+password2.getText());
                        password1.clear();
                        password2.clear();

                    }
                }
                else {
                    Toast.makeText(this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
                }
            break;
            case R.id.register_button2:
                navigateToLogin();
                break;
            default:
        }
    }

    @Override
    public void show_Dataerror() {
        Toast.makeText(this, "账号已存在", Toast.LENGTH_SHORT).show();
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
        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showSuccess() {
        Toast.makeText(this, "注册成功!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
