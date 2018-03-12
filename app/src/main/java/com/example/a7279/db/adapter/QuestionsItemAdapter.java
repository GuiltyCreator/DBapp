package com.example.a7279.db.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a7279.db.R;
import com.example.a7279.db.activity.MainActivity;
import com.example.a7279.db.bean.DataBeans;
import com.example.a7279.db.commons.Listener.OnItemClickListener;
import com.example.a7279.db.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by a7279 on 2018/3/5.
 */

public class QuestionsItemAdapter extends   RecyclerView.Adapter<QuestionsItemAdapter.ViewHolder> {
  private List<DataBeans.DataBean.QuestionsBean> mlist;
  private Context mContext;
    private OnItemClickListener mClickListener;



  public QuestionsItemAdapter(List<DataBeans.DataBean.QuestionsBean> list){
      this.mlist=list;
  }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }

    public DataBeans.DataBean.QuestionsBean getItem(int position) {
        return mlist.get(position);
    }
public void User_Tendencies_Success() {

}


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      mContext=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item,parent,false);
        final ViewHolder holder=new ViewHolder(view,mClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      holder.setIsRecyclable(false);
        DataBeans.DataBean.QuestionsBean item=mlist.get(position);
        if(item.isIs_exciting()){
            Glide.with(mContext).load(R.drawable.ic_exciting_filled).into(holder.excitingImageView);
        }
        else {
            Glide.with(mContext).load(R.drawable.ic_exciting).into(holder.excitingImageView);
        }
        holder.excitingcountTextView.setText(item.getExciting()+"");

        if(item.isIs_naive()){
            Glide.with(mContext).load(R.drawable.ic_naive_filled).into(holder.naiveImageView);
        }
        else {
            Glide.with(mContext).load(R.drawable.ic_naive).into(holder.naiveImageView);
        }
        holder.naivecountTextView.setText(item.getNaive()+"");

        if(item.isIs_favorite()){
            Glide.with(mContext).load(R.drawable.ic_favorite_filled).into(holder.favoriteImageView);
        }
        else {
            Glide.with(mContext).load(R.drawable.ic_favorite).into(holder.favoriteImageView);
        }


        if(item.getAuthorAvatar()==null){
            holder.circleImageView.setImageResource(R.drawable.nobody_image);
        }
        else {
            Glide.with(mContext).load(item.getAuthorAvatar()).into(holder.circleImageView);
        }
        holder.nameTextView.setText(item.getAuthorName());
        holder.timeTextView.setText(item.getDate());
        holder.titleTextView.setText(item.getTitle());
        holder.textTextView.setText(item.getContent());
        if(item.getImages()!=null&&item.getImages().length()>4){
            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getImages()).into(holder.imageView);
        }
        else {
            holder.imageView.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return mlist.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView circleImageView;
        TextView nameTextView;
        TextView timeTextView;
        TextView titleTextView;
        TextView textTextView;
        ImageView imageView;
        ImageView excitingImageView;
        TextView excitingcountTextView;
        ImageView naiveImageView;
        TextView naivecountTextView;
        ImageView favoriteImageView;
        View item_view;
        private OnItemClickListener mListener;
        public ViewHolder(View itemView,OnItemClickListener listener) {
            super(itemView);
            this.item_view=itemView;
            this.circleImageView=itemView.findViewById(R.id.question_asker);
            this.nameTextView=itemView.findViewById(R.id.question_name);
            this.timeTextView=itemView.findViewById(R.id.question_time);
            this.titleTextView=itemView.findViewById(R.id.question_title);
            this.textTextView=itemView.findViewById(R.id.question_text);
            this.imageView=itemView.findViewById(R.id.question_image);
            this.excitingImageView=itemView.findViewById(R.id.question_exciting);
            this.excitingcountTextView=itemView.findViewById(R.id.question_exciting_count);
            this.naiveImageView=itemView.findViewById(R.id.question_naive);
            this.naivecountTextView=itemView.findViewById(R.id.question_naive_count);
            this.favoriteImageView=itemView.findViewById(R.id.question_favorite);
            this.mListener=listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(view, getPosition());
        }
    }
}
