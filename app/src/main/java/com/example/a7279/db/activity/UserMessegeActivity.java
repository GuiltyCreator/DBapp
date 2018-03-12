package com.example.a7279.db.activity;

import android.Manifest;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.a7279.db.R;
import com.example.a7279.db.base.BaseActivity;
import com.example.a7279.db.base.BaseApp;
import com.example.a7279.db.presenter.UserMessegePresenter;
import com.example.a7279.db.presenter.UserMessegePresenterImpl;
import com.example.a7279.db.utils.LogUtil;
import com.example.a7279.db.utils.QiNiuUtil;
import com.example.a7279.db.view.UserMessegeActivityView;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;



import java.io.File;

import java.io.IOException;


public class UserMessegeActivity extends BaseActivity implements UserMessegeActivityView,View.OnClickListener {

    private ImageView avaterView;
    private Button logoutButton;
    private Button resetPasswordButton;
    private TextView nameTextView;
    private Toolbar toolbar;
    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;
    private Uri imageUri;
    private String AccessKey="_I8ZAWk5CwCazg4uqJ5VGwQREo0O5GnJuilVXtWK";
    private String SecretKey="eBWG5WZOqZ7XVtKqMvWzP5iEIpo5PyHZPr7Hn60J";
    private static final String TAG = "UserMessegeActivity";
    private UserMessegePresenter presenter;

    private int id;
    private String name;
    private String avatar;
    private String token;
    private String avatar_id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_messege);


        presenter=new UserMessegePresenterImpl(this);
        presenter.getUermessege();
       logoutButton=findViewById(R.id.logout_Button);
       resetPasswordButton=findViewById(R.id.reset_Password_Button);
        avaterView=findViewById(R.id.usermessege_image);
        nameTextView=findViewById(R.id.usermessege_name);
        toolbar=findViewById(R.id.usermessege_toolbar);
        logoutButton.setOnClickListener(this);
        resetPasswordButton.setOnClickListener(this);
        avaterView.setOnClickListener(this);
        nameTextView.setOnClickListener(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        displayImage(avatar);
        nameTextView.setText(name);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateToHome(); //返回上一个Activity
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.usermessege_image:
                AlertDialog.Builder dialog=new AlertDialog.Builder(this);
                dialog.setTitle("更换头像");
                dialog.setMessage("");
                dialog.setCancelable(true);
                dialog.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cameraFunction();
                    }
                });
                dialog.setNegativeButton("从相册选择", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alubmFunction();
                    }
                });
                dialog.show();


                break;
            case R.id.logout_Button:
                navigateToLogin();
                break;


            case R.id.reset_Password_Button:
                navigateToResetPassword();
                break;

            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        navigateToHome();
        super.onBackPressed();
    }

    @Override
    public void show_Networkerror() {
        Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome() {
        Intent intent=new Intent();
        intent.putExtra("avatar",avatar);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void navigateToResetPassword() {
        Intent intent=new Intent(this,ResetPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void getSuccess(int id, String name, String avatar, String token,String avatar_id) {
        this.id=id;
        this.name=name;
        this.avatar=avatar;
        this.token=token;
        this.avatar_id=avatar_id;
        LogUtil.d(TAG,this.id+"  "+this.name+"  "+this.avatar+"  "+this.token+"  "+avatar_id);
    }

    @Override
    public void changeAvatarSuccess() {
        Toast.makeText(this, "更改头像成功", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void startUcrop(Uri uri) {
        //Uri uri_crop = Uri.parse(path);
        Uri uri_crop=uri;
        //裁剪后保存到文件中
        //Uri destinationUri = Uri.fromFile(new File(getCacheDir(), path.substring(path.lastIndexOf("/") + 1, path.length())));
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "SampleCropImage.jpg"));
        UCrop uCrop = UCrop.of(uri_crop, destinationUri);
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        options.setAllowedGestures(UCropActivity.NONE, UCropActivity.NONE, UCropActivity.SCALE);
        options.setFreeStyleCropEnabled(false);
        options.setHideBottomControls(true);
        options.setShowCropGrid(false);
        options.setCropFrameStrokeWidth(0);
        options.setMaxBitmapSize(250);
        uCrop.withOptions(options);
        uCrop.withAspectRatio(1, 1);
        uCrop.start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    if (resultCode == RESULT_OK) {
                        startUcrop(imageUri);
                    }
                    break;

                case CHOOSE_PHOTO:
                    imageUri=data.getData();
                    startUcrop(imageUri);
                    break;
                case UCrop.REQUEST_CROP:
                        imageUri = UCrop.getOutput(data);
                        File file = new File(imageUri.getPath());
                        avatar= QiNiuUtil.uploadImg2QiNiu(file,AccessKey,SecretKey,"db-avatar",id+avatar_id+"_avatar","p57w7bg5g.bkt.clouddn.com");
                        presenter.changeAvatar(avatar,token);
                        presenter.getUermessege();
                        displayImage(avatar);

                    break;

            }
        }
    }


        @Override
    public void cameraFunction() {
        File image=new File(getExternalCacheDir(),"output_image.jpg");
        try{
            if(image.exists())
                image.delete();
            else
                image.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=24)
        {
            imageUri= FileProvider.getUriForFile(this,"com.example.a7279.db.fileprovider",image);
        }
        else
        {
            imageUri=Uri.fromFile(image);
        }

        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }




    @Override
    public void alubmFunction() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else {
            openAlbum();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    openAlbum();
                }
                else
                {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
            default:
        }
    }

    @Override
    public void openAlbum() {
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void displayImage(String picPath) {
        if(avatar!=null) {
            Glide.with(this).load(picPath).error(R.drawable.nopic).into(avaterView);
        }
        else Glide.with(this).load(R.drawable.nobody_image).into(avaterView);
    }


    @Override
    public void navigateToLogin() {
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(BaseApp.getContext()).edit();
        editor.putString("token", "none");
        editor.putString("avatar","none");
        editor.putString("username","none");
        editor.putInt("id",0);
        editor.apply();
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }



}
