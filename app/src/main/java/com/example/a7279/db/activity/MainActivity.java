package com.example.a7279.db.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.a7279.db.R;
import com.example.a7279.db.adapter.QuestionsItemAdapter;
import com.example.a7279.db.base.BaseActivity;
import com.example.a7279.db.base.BaseApp;
import com.example.a7279.db.bean.DataBeans;
import com.example.a7279.db.commons.Listener.OnItemClickListener;
import com.example.a7279.db.presenter.MainPresenterImpl;
import com.example.a7279.db.utils.ActivitiesCollectorUtil;
import com.example.a7279.db.utils.LogUtil;
import com.example.a7279.db.view.MainView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements MainView,View.OnClickListener {

    private static final String TAG = "MainActivity";
    private long time;
    private CircleImageView circleImageView;
    private TextView nameTextView;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNnavigationView;
    private MainPresenterImpl presenter;
    private RecyclerView recyclerView;
    private int page;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<DataBeans.DataBean.QuestionsBean> list;
    private QuestionsItemAdapter adapter;
    private DataBeans.DataBean.QuestionsBean item;
    private int itempositon;
    private boolean is_login;
    private boolean is_loading=false;
    private boolean isClicking;

    private final int REQUEST_TO_USERMESSEGE=1;
    private final int REQUEST_TO_QUESTIONDETAI=2;
    private final int REQUEST_TO_SENDQUESTION=3;
    private final int REQUEST_TO_MYFAVORITE=4;

    private  int id;
    private String username;
    private String avatar;
    private String token;
    private String avatar_id;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenterImpl(this);
        check();
        page = 0;
        list = new ArrayList<>();
        if(is_login){
            presenter.refresh(page);
        }
        else {
            navigateToLogin();
        }

        mNnavigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        circleImageView= mNnavigationView.getHeaderView(0).findViewById(R.id.nav_image);
        //circleImageView=findViewById(R.id.nav_image);
        if(avatar.equals("none")){
           Glide.with(this).load(R.drawable.nobody_image).into(circleImageView);
        }
        else {
            Glide.with(this).load(avatar).error(R.drawable.nopic).into(circleImageView);
        }

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToUserMessege();
            }
        });
        nameTextView=mNnavigationView.getHeaderView(0).findViewById(R.id.nav_name);
        nameTextView.setText(username);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        mNnavigationView.setCheckedItem(R.id.nav_Sendquestion);
        mNnavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()){
                    case R.id.nav_Sendquestion:
                       navigateToSendQuestion();
                        break;
                    case R.id.nav_Myfavorite:
                        navigateToMyFavorite();
                        default:
                }
                return true;
            }
        });
        swipeRefreshLayout = findViewById(R.id.question_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

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
    public void Getquestion() {
        LogUtil.d(TAG,is_loading+"");
        if (is_loading) {
            if (page != 0) {
                //list.addAll(list.size(),presenter.getQuestions());
                for (DataBeans.DataBean.QuestionsBean data : presenter.getQuestions()) {
                    list.add(data);
                    LogUtil.d(TAG, data.getTitle());
                }
                adapter.notifyDataSetChanged();
            } else {
                list.clear();
                list.addAll(presenter.getQuestions());
                adapter.notifyDataSetChanged();
            }
        } else {
            list = presenter.getQuestions();
            if (list == null) {
                Toast.makeText(this, "已在别处登录，请重新登录", Toast.LENGTH_SHORT).show();
                navigateToLogin();
            } else {
                recyclerView = findViewById(R.id.question_recycler_view);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        if (isSlideToBottom(recyclerView)) {
                            page += 1;
                            presenter.refresh(page);
                        }

                    }

                });
                adapter = new QuestionsItemAdapter(list);
                adapter.setHasStableIds(true);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int postion) {
                        itempositon = postion;
                        view.findViewById(R.id.question_exciting).setOnClickListener(clickListener);
                        view.findViewById(R.id.question_naive).setOnClickListener(clickListener);
                        view.findViewById(R.id.question_favorite).setOnClickListener(clickListener);
                        view.findViewById(R.id.question_title).setOnClickListener(clickListener);
                        view.findViewById(R.id.question_text).setOnClickListener(clickListener);
                        view.findViewById(R.id.question_image).setOnClickListener(clickListener);

                    }
                });
                recyclerView.setAdapter(adapter);
                //增加分割线
                DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
                divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_segmenting_line));
                recyclerView.addItemDecoration(divider);

            }
            is_loading = true;
        }
    }

    @Override
    public void setItem(int Function_id) {
        int count;
        if (isClicking) {
            switch (Function_id) {
                case 1:
                    item.setIs_exciting(true);
                    count = item.getExciting();
                    item.setExciting(count + 1);
                    break;
                case 2:
                    item.setIs_exciting(false);
                    count = item.getExciting();
                    item.setExciting(count - 1);
                    break;
                case 3:
                    item.setIs_naive(true);
                    count = item.getNaive();
                    item.setNaive(count + 1);
                    break;
                case 4:
                    item.setIs_naive(false);
                    count = item.getNaive();
                    item.setNaive(count - 1);
                    break;
                case 5:
                    item.setIs_favorite(true);
                    break;
                case 6:
                    item.setIs_favorite(false);
                    break;
                default:
                    break;
            }
            isClicking=false;
            list.set(itempositon, item);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case REQUEST_TO_USERMESSEGE:
                if(resultCode==RESULT_OK)
                {
                    presenter.getUermessege();
                    Glide.with(this).load(avatar).error(R.drawable.nopic).into(circleImageView);
                }
                break;

            case REQUEST_TO_QUESTIONDETAI:
            {
                if(resultCode==RESULT_OK)
                {
                   item=(DataBeans.DataBean.QuestionsBean) data.getSerializableExtra("return item");
                   list.set(itempositon,item);
                   adapter.notifyDataSetChanged();
                   item=null;
                }
            }
            break;

            case REQUEST_TO_SENDQUESTION:
                if(resultCode==RESULT_OK)
                {
                    refresh();
                }
                break;

            case REQUEST_TO_MYFAVORITE:
                if(resultCode==RESULT_OK)
                {
                    refresh();
                }
                break;

            default:
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isClicking=true;
            item = adapter.getItem(itempositon);
                switch (view.getId()) {
                    case R.id.question_exciting:
                        exciting(1, item.getId(), item.isIs_exciting());
                        if (item.isIs_exciting()) {
                            item.setIs_exciting(false);
                        } else {
                            item.setIs_exciting(true);
                        }
                        LogUtil.d(TAG, "exciting id:" + item.getId());
                        break;

                    case R.id.question_naive:
                        naive(1, item.getId(), item.isIs_naive());
                        if (item.isIs_naive()) {
                            item.setIs_naive(false);
                        } else {
                            item.setIs_naive(true);
                        }
                        break;

                    case R.id.question_favorite:
                        favorite(item.getId(), item.isIs_favorite());
                        if (item.isIs_favorite()) {
                            item.setIs_favorite(false);
                        } else {
                            item.setIs_favorite(true);
                        }
                        LogUtil.d(TAG, "favorite Is_favorite:" + item.isIs_favorite());
                        break;

                    case R.id.question_title:
                        navigateToQuestionDetai();
                        break;

                    case R.id.question_text:
                        navigateToQuestionDetai();
                        break;

                    case R.id.question_image:
                        navigateToQuestionDetai();
                        break;


                    default:
                }
            }
    };

    @Override
    public void refresh() {
        page=0;
        is_loading=false;
        presenter.refresh(page);
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);/*打开滑动菜单，传入布局参数*/
                break;

            default:
        }
        return true;
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


    @Override
    public void navigateToLogin() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(BaseApp.getContext()).edit();
        editor.clear();
        editor.apply();
        LogUtil.d(TAG,"tologin");
        /*Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);*/
        startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish ();
    }

    @Override
    public void check() {
            presenter.getUermessege();
        if(token.equals("none"))
        {
            is_login=false;
        }
        else {
            is_login=true;
        }
    }



    @Override
    public void show_Networkerror() {
        Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }



    @Override
    public void exciting(int type,int ID,boolean is_exciting) {
      presenter.excitingFunction(type,ID,is_exciting);
    }


    @Override
    public void naive(int type,int ID,boolean is_naive) {
    presenter.naiveFunction(type,ID,is_naive);
    }


    @Override
    public void favorite(int ID,boolean is_favorite) {
        presenter.favoriteFunction(ID,is_favorite);

    }


    @Override
    public void User_Tendencies_Success(int Function_id) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        setItem(Function_id);
    }


    @Override
    public void navigateToUserMessege() {
        Intent intent=new Intent(this,UserMessegeActivity.class);
        startActivityForResult(intent,REQUEST_TO_USERMESSEGE);
    }

    @Override
    public void getSuccess(int id, String name, String avatar, String token,String avatar_id) {
        this.id=id;
        this.username=name;
        this.avatar=avatar;
        this.token=token;
        this.avatar_id=avatar_id;
        LogUtil.d(TAG,this.id+"  "+this.username+"  "+this.avatar+"  "+this.token+"  "+this.avatar_id);
    }

    @Override
    public void navigateToQuestionDetai() {
        Intent intent=new Intent(this,QuestionDetaiActivity.class);
        intent.putExtra("questionDetai",item);
        startActivityForResult(intent,REQUEST_TO_QUESTIONDETAI);

    }

    @Override
    public void navigateToMyFavorite() {
        Intent intent=new Intent(this,MyFavoriteActivity.class);
        intent.putExtra("token",token);
        startActivityForResult(intent,REQUEST_TO_MYFAVORITE);
    }

    @Override
    public void navigateToSendQuestion() {
        Intent intent=new Intent(MainActivity.this,SendQuestionActivity.class);
        intent.putExtra("token",token);
        intent.putExtra("id",id);
        startActivityForResult(intent,REQUEST_TO_SENDQUESTION);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_image:
            navigateToUserMessege();


            default:

        }


    }
}
