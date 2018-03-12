package com.example.a7279.db.activity;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a7279.db.R;
import com.example.a7279.db.adapter.QuestionsItemAdapter;
import com.example.a7279.db.base.BaseActivity;
import com.example.a7279.db.bean.DataBeans;
import com.example.a7279.db.commons.Listener.OnItemClickListener;
import com.example.a7279.db.presenter.MainPresenterImpl;
import com.example.a7279.db.presenter.MyFavoritePresenter;
import com.example.a7279.db.presenter.MyFavoritePresenterImpl;
import com.example.a7279.db.utils.LogUtil;
import com.example.a7279.db.view.MyFavoriteView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyFavoriteActivity extends BaseActivity implements View.OnClickListener,MyFavoriteView {

    private RecyclerView recyclerView;
    private int questionPage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<DataBeans.DataBean.QuestionsBean> list;
    private QuestionsItemAdapter adapter;
    private DataBeans.DataBean.QuestionsBean item;
    private int itempositon;
    private Toolbar toolbar;

    private String token;
    private View.OnClickListener clickListener;
    private final int REQUEST_TO_QUESTIONDETAI=2;
    private MyFavoritePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);

        install();
    }

    @Override
    public void onClick(View view) {


    }

    @Override
    public void install() {
        this.recyclerView=findViewById(R.id.myfavorite_recyclerview);
        this.toolbar=findViewById(R.id.myfavorite_toolbar);
        this.swipeRefreshLayout=findViewById(R.id.myfavorite_swipe_refresh);
        this.recyclerView = findViewById(R.id.myfavorite_recyclerview);
        presenter=new MyFavoritePresenterImpl(this);
        list=new ArrayList<>();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = adapter.getItem(itempositon);
                switch (view.getId()) {
                    case R.id.question_exciting:
                        exciting(1, item.getId(), item.isIs_exciting());
                        if (item.isIs_exciting()) {
                            item.setIs_exciting(false);
                        } else {
                            item.setIs_exciting(true);
                        }
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



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (isSlideToBottom(recyclerView)) {
                   refresh();
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

        refresh();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
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

            default:
        }
    }


    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(toolbar!=null){
            toolbar.setTitle("我的收藏");
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateToHome();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showNetWorkError() {
        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
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
    public void Getquestion() {
        presenter.getQuestion(questionPage);
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
    public void favorite(int ID, boolean is_favorite) {
        presenter.favoriteFunction(ID,is_favorite);
    }

    @Override
    public void User_Tendencies_Success(int Function_id) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        setItem(Function_id);
    }

    @Override
    public void setItem(int Function_id) {
        int count;

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
            list.set(itempositon, item);
            adapter.notifyDataSetChanged();
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
    public void navigateToHome() {
        Intent intent=new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void refresh() {
        questionPage=0;
        Getquestion();
    }

    @Override
    public void getQuestionSuccess(List<DataBeans.DataBean.QuestionsBean> list,int page, boolean isRefresh) {

        swipeRefreshLayout.setRefreshing(false);

        int index=0;
        for(DataBeans.DataBean.QuestionsBean i:list){
            i.setIs_favorite(true);
            list.set(index,i);
            index++;
        }

        if (!isRefresh) {
            if(list.size()!=0){
                this.list.addAll(list);
                questionPage=page+1;
                adapter.notifyDataSetChanged();
            }
        }

        else {
            this.list.clear();
            this.list.addAll(list);
            adapter.notifyDataSetChanged();
            questionPage=page+1;
        }
    }

    @Override
    public void navigateToQuestionDetai() {
        Intent intent=new Intent(this,QuestionDetaiActivity.class);
        intent.putExtra("questionDetai",item);
        startActivityForResult(intent,REQUEST_TO_QUESTIONDETAI);
    }
}
