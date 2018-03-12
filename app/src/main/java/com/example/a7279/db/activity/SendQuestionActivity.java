package com.example.a7279.db.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a7279.db.R;
import com.example.a7279.db.base.BaseActivity;
import com.example.a7279.db.presenter.SendQuestionPresenter;
import com.example.a7279.db.presenter.SendQuestionPresenterImpl;
import com.example.a7279.db.utils.LogUtil;
import com.example.a7279.db.utils.QiNiuUtil;
import com.example.a7279.db.view.SendQuestionView;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.IOException;

public class SendQuestionActivity extends BaseActivity implements SendQuestionView,View.OnClickListener {

    private Toolbar toolbar;
    private ImageView sendImageView;
    private EditText titleEditText;
    private EditText contentEditText;
    private ImageView picImageView;
    private ImageView cancelPicImageView;

    private Uri imageUri;
    private final int TAKE_PHOTO=1;
    private final int CHOOSE_PHOTO=2;
    private SendQuestionPresenter presenter;
    private String token;
    private int id;
    private String AccessKey="_I8ZAWk5CwCazg4uqJ5VGwQREo0O5GnJuilVXtWK";
    private String SecretKey="eBWG5WZOqZ7XVtKqMvWzP5iEIpo5PyHZPr7Hn60J";
    private static final String TAG = "SendQuestionActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_question);

        presenter=new SendQuestionPresenterImpl(this);
        install();
        Intent intent=getIntent();
        token=intent.getStringExtra("token");
        id=intent.getIntExtra("id",0);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(toolbar!=null){
            toolbar.setTitle("发布问题");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish(); //返回主页
                break;

            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void install() {
        toolbar=findViewById(R.id.send_question_toolbar);
        sendImageView=findViewById(R.id.send_question_botton);
        titleEditText=findViewById(R.id.send_question_title);
        contentEditText=findViewById(R.id.send_question_content);
        picImageView=findViewById(R.id.send_question_image);
        cancelPicImageView=findViewById(R.id.send_question_cancelpic);

        sendImageView.setOnClickListener(this);
        picImageView.setOnClickListener(this);
        cancelPicImageView.setOnClickListener(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void startUcrop(Uri uri) {
        Uri uri_crop=uri;
        //裁剪后保存到文件中
        //Uri destinationUri = Uri.fromFile(new File(getCacheDir(), path.substring(path.lastIndexOf("/") + 1, path.length())));
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), String.valueOf(System.currentTimeMillis())+"SampleCropImage.jpg"));
        UCrop uCrop = UCrop.of(uri_crop, destinationUri);
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        options.setAllowedGestures(UCropActivity.NONE, UCropActivity.NONE, UCropActivity.SCALE);
        options.setFreeStyleCropEnabled(false);
        options.setHideBottomControls(true);
        options.setShowCropGrid(false);
        options.setCropFrameStrokeWidth(0);
        options.setMaxBitmapSize(1200);
        uCrop.withOptions(options);
        uCrop.start(this);
    }

    @Override
    public void cameraFunction() {
        File image=new File(getExternalCacheDir(),String.valueOf(System.currentTimeMillis())+"output_image.jpg");
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
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void openAlbum() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else {
            openAlbum();
        }
    }

    @Override
    public void displayImage(Uri uri) {
        //picImageView.setImageURI(uri);
        Glide.with(this).load(uri).into(picImageView);
        cancelPicImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideImage() {
        picImageView.setImageResource(R.drawable.ic_add);
        cancelPicImageView.setVisibility(View.GONE);
        imageUri=null;
    }

    @Override
    protected void onDestroy() {

        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void sendSuccess() {
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void NetworkErro() {
        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void send() {
        String sendPicPath=null;
        if(imageUri!=null) {
            File file = new File(imageUri.getPath());
            sendPicPath = QiNiuUtil.uploadImg2QiNiu(file, AccessKey, SecretKey, "db-pic", "asker_id=" + id + "_" + String.valueOf(System.currentTimeMillis()) + "_pic", "p57w6t1l3.bkt.clouddn.com");
        }
        String title=titleEditText.getText().toString();
        String content=contentEditText.getText().toString();
        if(title!=""&&content!=""){
            presenter.sendQuestion(title,content,sendPicPath,token);
        }
        else {
            Toast.makeText(this, "内容或标题不能为空", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.send_question_image:
                AlertDialog.Builder dialog=new AlertDialog.Builder(this);
                dialog.setTitle("选择图片");
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


            case R.id.send_question_cancelpic:
                hideImage();
                break;

            case R.id.send_question_botton:
                send();
                break;


            default:
        }

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
                    displayImage(imageUri);
                    break;

            }
        }
    }
}
