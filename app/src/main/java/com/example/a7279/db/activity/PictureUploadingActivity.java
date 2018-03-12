package com.example.a7279.db.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a7279.db.R;
import com.example.a7279.db.base.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PictureUploadingActivity extends BaseActivity {
    private ImageView imageView;
    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;
    private Uri imageUri;
    String imagePath=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_uploading);
        imageView=findViewById(R.id.camera_image);
    }


    private  void cameraFunction()
    {
        File image=new File(getExternalCacheDir(),"output_image.jpg");
        try{
            if(image.exists())
                image.delete();
            else
                image.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
                /*这里创建了一个File对象，用于存放摄像头拍下的图片，
                将他命名为"output_image.jpg"，
                并将他放在SD卡的应用关联缓存目录下。
                应用关联缓存目录：用于存放当前应用缓存数据的位置，调用getExternalCacheDir()可以得到这个目录。
                */
        if(Build.VERSION.SDK_INT>=24)
        {
            imageUri= FileProvider.getUriForFile(this,"com.example.a7279.cameraalbumtest.fileprovider",image);
        }
        else
        {
            imageUri=Uri.fromFile(image);
        }
                /*
                进行判断，如果系统版本低于7.0就调用Uri的fromFile()方法将File对象转换成Uri对象。
                这个Uri对象标识着这张图片的本地真实路径
                如果大于7.0的系统，就调用FileProvider的getUriForFile()方法将一个File对象转换成一个封装过的Uri对象。
                因为自从7.0系统开始，直接使用本地的真实路径的Uri被认为是不安全的，会抛出一个异常。
                而FileProvider是一个特殊的内容提供器，它使用了和内容提供器类似的机制来对数据进行保护。
                 */
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
        //启动摄像机
    }

    private  void alubmFunction()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else {
            openAlbum();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
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
    private void openAlbum()
    {
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        /*4.4以上的系统使用这个方式处理图片
                        因为4.4以上的系统选取图册中的图片不再返回真实的Uri了
                        而是返回一个封装好的Uri*/
                        handleImageOnKitKat(data);
                    } else {
                        //4.4以下的系统用这个方式处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

        @TargetApi(19)
        private void handleImageOnKitKat(Intent data){
            Uri uri=data.getData();
            if(DocumentsContract.isDocumentUri(this,uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    //如果是document类型的Uri，则通过document id处理
                    String id = docId.split(":")[1];//解析出数字格式的id
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(contentUri, null);
                }
            }
            else if("content".equalsIgnoreCase(uri.getScheme()))
            {//如果是content类型的Uri，则使用普通方式处理
                imagePath=getImagePath(uri,null);
            }
            else if("file".equalsIgnoreCase(uri.getScheme()))
            {//如果是file类型的Uri，直接获取图片路径即可
                imagePath=uri.getPath();
            }
            displayImage(imagePath);
        }

    private void handleImageBeforeKitKat(Intent data)
    {
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri,String selection)
    {
        String path=null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return  path;
    }
    //显示图片的方法
    private void displayImage(String imagePath)
    {
        if(imagePath!=null)
        {
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);
        }
        else
        {
            Toast.makeText(this, "failed to get iamge", Toast.LENGTH_SHORT).show();
        }


    }




}
