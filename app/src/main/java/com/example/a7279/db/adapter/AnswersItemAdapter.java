package com.example.a7279.db.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a7279.db.R;
import com.example.a7279.db.bean.DataBeans;
import com.example.a7279.db.commons.Listener.OnItemClickListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by a7279 on 2018/3/11.
 */

public class AnswersItemAdapter extends   RecyclerView.Adapter<AnswersItemAdapter.ViewHolder> {

    private List<DataBeans.DataBean.AnswersBean> mlist;
    private Context mContext;
    private OnItemClickListener mClickListener;


    public AnswersItemAdapter(List<DataBeans.DataBean.AnswersBean> list){
        this.mlist=list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }

    public DataBeans.DataBean.AnswersBean getItem(int position) {
        return mlist.get(position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item,parent,false);
        final ViewHolder holder=new ViewHolder(view,mClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        DataBeans.DataBean.AnswersBean item=mlist.get(position);
        if(item.isIs_exciting()){
            Glide.with(mContext).load(R.drawable.ic_exciting_filled).into(holder.excitingImageView);
        }
        else {
            Glide.with(mContext).load(R.drawable.ic_exciting).into(holder.excitingImageView);
        }
        holder.excitingCountTextView.setText(item.getExciting()+"");

        if(item.isIs_naive()){
            Glide.with(mContext).load(R.drawable.ic_naive_filled).into(holder.naiveImageView);
        }
        else {
            Glide.with(mContext).load(R.drawable.ic_naive).into(holder.naiveImageView);
        }
        holder.naiveCountTextView.setText(item.getNaive()+"");


        if(item.getAuthorAvatar()==null){
            holder.avatarImageView.setImageResource(R.drawable.nobody_image);
        }
        else {
            Glide.with(mContext).load(item.getAuthorAvatar()).into(holder.avatarImageView);
        }
        holder.nameTextView.setText(item.getAuthorName());
        holder.timeTextView.setText(item.getDate());
        holder.contentTextView.setText(item.getContent());
        if(item.getImages()!=null&&item.getImages().length()>4){
            holder.answerImageView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getImages()).into(holder.answerImageView);
        }
        else {
            holder.answerImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        View view;
        CircleImageView avatarImageView;
        TextView nameTextView;
        TextView timeTextView;
        TextView contentTextView;
        TextView excitingCountTextView;
        TextView naiveCountTextView;
        ImageView excitingImageView;
        ImageView naiveImageView;
        ImageView answerImageView;


        private OnItemClickListener mListener;
        public ViewHolder(View itemView,OnItemClickListener listener) {
            super(itemView);
            this.view=itemView;
            this.mListener=listener;
            this.avatarImageView=view.findViewById(R.id.answer_avatar);
            this.nameTextView=view.findViewById(R.id.answer_name);
            this.timeTextView=view.findViewById(R.id.answer_time);
            this.contentTextView=view.findViewById(R.id.answer_content);
            this.excitingCountTextView=view.findViewById(R.id.answer_exciting_count);
            this.naiveCountTextView=view.findViewById(R.id.answer_naive_count);
            this.excitingImageView=view.findViewById(R.id.answer_exciting);
            this.naiveImageView=view.findViewById(R.id.answer_naive);
            this.answerImageView=view.findViewById(R.id.answer_image);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(view, getPosition());
        }
    }
}
