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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a7279.db.R;
import com.example.a7279.db.adapter.AnswersItemAdapter;
import com.example.a7279.db.adapter.QuestionsItemAdapter;
import com.example.a7279.db.base.BaseActivity;
import com.example.a7279.db.bean.DataBeans;
import com.example.a7279.db.commons.Listener.OnItemClickListener;
import com.example.a7279.db.presenter.QuestionDetaiPresenter;
import com.example.a7279.db.presenter.QuestionDetaiPresenterImpl;
import com.example.a7279.db.utils.LogUtil;
import com.example.a7279.db.utils.QiNiuUtil;
import com.example.a7279.db.view.QuestionDetaiView;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionDetaiActivity extends BaseActivity implements QuestionDetaiView,View.OnClickListener {

    private static final String TAG = "QuestionDetaiActivity";
    private DataBeans.DataBean.QuestionsBean QuestionData;
    private QuestionDetaiPresenter presenter;
    private List<DataBeans.DataBean.AnswersBean> answersBeans;
    private DataBeans.DataBean.AnswersBean answersitem;
    private DataBeans.DataBean.AnswersBean questionitem;
    private int answerPage;
    private int itempositon;
    private boolean isLoading;
    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;
    private Uri imageUri;
    private String AccessKey="_I8ZAWk5CwCazg4uqJ5VGwQREo0O5GnJuilVXtWK";
    private String SecretKey="eBWG5WZOqZ7XVtKqMvWzP5iEIpo5PyHZPr7Hn60J";
    private String sendPicPath;
    private int type;

    private RecyclerView recyclerView;
    private AnswersItemAdapter adapter;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText sendContextEditText;
    private ImageView addImageView;
    private ImageView sendImageView;
    private ImageView picImageView;
    private ImageView canelPicImageView;
    private ImageView question_detai_favorite_ImageView;

    private int id;
    private String name;
    private String avatar;
    private String token;
    private String avatar_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detai);

        isLoading=false;
        presenter=new QuestionDetaiPresenterImpl(this);
        presenter.getUermessege();
        getQuestionData();
        install();


        LogUtil.d(TAG,sendContextEditText.getText().toString());

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
    public void sendAnswer(int qid, String content, String image, String token) {
        presenter.sendAnswer(qid,content,image,token);
    }

    @Override
    public void sendAnswerSuccess() {
        Toast.makeText(this, "Answer Success", Toast.LENGTH_SHORT).show();
        sendContextEditText.setText("");
        hideImage();
        refresh();
    }

    @Override
    public void addItem() {
        questionitem=new DataBeans.DataBean.AnswersBean();
        questionitem.setAuthorName(QuestionData.getAuthorName());
        questionitem.setAuthorAvatar(QuestionData.getAuthorAvatar());
        questionitem.setDate(QuestionData.getDate());
        questionitem.setContent(QuestionData.getContent());
        questionitem.setExciting(QuestionData.getExciting());
        questionitem.setIs_exciting(QuestionData.isIs_exciting());
        questionitem.setNaive(QuestionData.getNaive());
        questionitem.setIs_naive(QuestionData.isIs_naive());
        questionitem.setId(QuestionData.getId());
        questionitem.setImages(QuestionData.getImages());

    }

    @Override
    public void onBackPressed() {
        navigateToHome();
        super.onBackPressed();
    }

    @Override
    public void navigateToHome() {
        Intent intent=new Intent();
        intent.putExtra("return item", QuestionData);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void show_Networkerror() {
        Toast.makeText(this, "NetWork Erro", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
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

    @Override
    public void getQuestionData() {
        Intent intent=getIntent();
        QuestionData=(DataBeans.DataBean.QuestionsBean)intent.getSerializableExtra("questionDetai");
        LogUtil.d(TAG,QuestionData.getId()+"");


    }

    @Override
    public void exciting(int type, int ID, boolean is_exciting) {
        presenter.excitingFunction(type,ID,is_exciting);
    }

    @Override
    public void naive(int type, int ID, boolean is_naive) {
        presenter.naiveFunction(type,ID,is_naive);
    }

    @Override
    public void refresh() {
        swipeRefreshLayout.setRefreshing(true);
        answerPage=0;
        presenter.refresh(answerPage,QuestionData.getId());

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(toolbar!=null){
            toolbar.setTitle(QuestionData.getTitle());
        }
    }

    @Override
    public void install() {
        if (!isLoading) {

            this.swipeRefreshLayout = findViewById(R.id.answer_swipe_refresh);
            this.sendContextEditText = findViewById(R.id.answer_edit);
            this.addImageView = findViewById(R.id.answer_input_image);
            this.sendImageView = findViewById(R.id.answer_send);
            this.picImageView = findViewById(R.id.answer_image_send);
            this.canelPicImageView = findViewById(R.id.cancel_answer_image_send);
            this.question_detai_favorite_ImageView = findViewById(R.id.question_detai_favorite);
            this.toolbar = findViewById(R.id.answer_toolbar);
            this.recyclerView = findViewById(R.id.answer_recycler_view);

            answersBeans=new ArrayList<>();


            addImageView.setOnClickListener(this);
            sendImageView.setOnClickListener(this);
            canelPicImageView.setOnClickListener(this);
            question_detai_favorite_ImageView.setOnClickListener(this);

            swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refresh();
                }
            });


            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

            isLoading=true;
            refresh();
        }

        addItem();
        answersBeans.add(questionitem);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
          /*  for(DataBeans.DataBean.AnswersBean i:answersBeans){
                LogUtil.d(TAG,i.getContent());
            }*/

            adapter = new AnswersItemAdapter(answersBeans);
            adapter.setHasStableIds(true);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int postion) {
                    itempositon = postion;
                    if(itempositon==0){
                        type=1;
                    }
                    else {
                        type=2;
                    }
                    answersitem = adapter.getItem(postion);
                    view.findViewById(R.id.answer_exciting).setOnClickListener(clickListener);
                    view.findViewById(R.id.answer_naive).setOnClickListener(clickListener);
                }
            });
            recyclerView.setAdapter(adapter);
            //增加分割线
            DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_segmenting_line));
            recyclerView.addItemDecoration(divider);

        if(QuestionData.isIs_favorite()){
            Glide.with(this).load(R.drawable.ic_favorite_filled).into(question_detai_favorite_ImageView);
        }
        else {
            Glide.with(this).load(R.drawable.ic_favorite).into(question_detai_favorite_ImageView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateToHome(); //返回主页
                break;

                default:
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.answer_exciting:
                    exciting(type, answersitem.getId(), answersitem.isIs_exciting());
                    LogUtil.d(TAG,"type:"+type+"id:"+answersitem.getId()+"isexciting:"+answersitem.isIs_exciting());
                    if (answersitem.isIs_exciting()) {
                        answersitem.setIs_exciting(false);
                    } else {
                        answersitem.setIs_exciting(true);
                    }
                    break;
                case R.id.answer_naive:

                    naive(type, answersitem.getId(), answersitem.isIs_naive());
                    if (answersitem.isIs_naive()) {
                        answersitem.setIs_naive(false);
                    } else {
                        answersitem.setIs_naive(true);
                    }
                    break;
                default:
            }
        }
    };

    @Override
    public void getAnswer() {
        presenter.refresh(answerPage,QuestionData.getId());
    }

    @Override
    public void getAnswerSuccess(List<DataBeans.DataBean.AnswersBean> answersBeans,boolean isRefresh,int page) {

        swipeRefreshLayout.setRefreshing(false);

        if (!isRefresh) {
            LogUtil.d(TAG,"answersBeans.size:"+answersBeans.size()+" page:"+page);
            if(answersBeans.size()!=0){
                this.answersBeans.addAll(answersBeans);
                answerPage=page+1;
                adapter.notifyDataSetChanged();
            }
        }

        else {
            this.answersBeans.clear();
            answersBeans.add(0,questionitem);
            this.answersBeans.addAll(answersBeans);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    if (isSlideToBottom(recyclerView)) {
                        presenter.refresh(answerPage, QuestionData.getId());
                    }

                }

            });
            adapter.notifyDataSetChanged();
            answerPage=page+1;
        }
    }

    @Override
    public boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    @Override
    public void User_Tendencies_Success(int Function_id,boolean isQuestion) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        setItem(Function_id,isQuestion);
    }

    @Override
    public void setItem(int Function_id,boolean isQuestion) {
        int count;
        if (isQuestion) {

            switch (Function_id) {
                case 1:
                    QuestionData.setIs_exciting(true);
                    count = QuestionData.getExciting();
                    QuestionData.setExciting(count + 1);
                    break;
                case 2:
                    QuestionData.setIs_exciting(false);
                    count = QuestionData.getExciting();
                    QuestionData.setExciting(count - 1);
                    break;
                case 3:
                    QuestionData.setIs_naive(true);
                    count = QuestionData.getNaive();
                    QuestionData.setNaive(count + 1);
                    break;
                case 4:
                    QuestionData.setIs_naive(false);
                    count = QuestionData.getNaive();
                    QuestionData.setNaive(count - 1);
                    break;
                case 5:
                    QuestionData.setIs_favorite(true);
                    break;
                case 6:
                    QuestionData.setIs_favorite(false);
                    break;
                default:
                    break;
            }
            LogUtil.d(TAG,QuestionData.isIs_favorite()+"");
            if (QuestionData.isIs_favorite()) {
                Glide.with(this).load(R.drawable.ic_favorite_filled).into(question_detai_favorite_ImageView);
            } else {
                Glide.with(this).load(R.drawable.ic_favorite).into(question_detai_favorite_ImageView);
            }
            addItem();
            answersBeans.set(0,questionitem);
            adapter.notifyDataSetChanged();

        }

        else {
            switch (Function_id) {
                case 1:
                    answersitem.setIs_exciting(true);
                    count = answersitem.getExciting();
                    answersitem.setExciting(count + 1);
                    break;
                case 2:
                    answersitem.setIs_exciting(false);
                    count = answersitem.getExciting();
                    answersitem.setExciting(count - 1);
                    break;
                case 3:
                    answersitem.setIs_naive(true);
                    count = answersitem.getNaive();
                    answersitem.setNaive(count + 1);
                    break;
                case 4:
                    answersitem.setIs_naive(false);
                    count = answersitem.getNaive();
                    answersitem.setNaive(count - 1);
                    break;
                default:
                    break;
            }

            answersBeans.set(itempositon, answersitem);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.question_detai_favorite:
                favorite(QuestionData.getId(), QuestionData.isIs_favorite());
                LogUtil.d(TAG, "favorite Is_favorite:" + QuestionData.isIs_favorite());
                break;

            case R.id.answer_input_image:
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

            case R.id.answer_send:
                LogUtil.d(TAG,"start send");
                String content=sendContextEditText.getText().toString();
                LogUtil.d(TAG,"content:");
                if(content.equals("")){
                    Toast.makeText(this, "回答不能为空", Toast.LENGTH_SHORT).show();
                }
            else {
                if (imageUri != null) {
                    File file = new File(imageUri.getPath());
                    sendPicPath = QiNiuUtil.uploadImg2QiNiu(file, AccessKey, SecretKey, "db-pic", "qid=" + QuestionData.getId() + "_" + "aid=" + String.valueOf(System.currentTimeMillis()) + "_pic", "p57w6t1l3.bkt.clouddn.com");
                    LogUtil.d(TAG,"sendPicPath:"+sendPicPath);
                }
                sendAnswer(QuestionData.getId(), content, sendPicPath, token);
                sendPicPath=null;
            }
                break;

            case R.id.cancel_answer_image_send:
                hideImage();
                break;

            default:
        }

    }

    @Override
    public void favorite(int ID,boolean is_favorite) {
        presenter.favoriteFunction(ID,is_favorite);

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
        options.setAllowedGestures(UCropActivity.ALL, UCropActivity.ALL, UCropActivity.NONE);
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
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else {
            openAlbum();
        }
    }

    @Override
    public void openAlbum() {
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void displayImage(Uri uri) {
        picImageView.setVisibility(View.VISIBLE);
        canelPicImageView.setVisibility(View.VISIBLE);
        Glide.with(this).load(uri).into(picImageView);
       //picImageView.setImageURI(uri);
    }

    @Override
    public void hideImage() {
        picImageView.setVisibility(View.GONE);
        canelPicImageView.setVisibility(View.GONE);
        imageUri=null;
        sendPicPath = null;
    }
}
