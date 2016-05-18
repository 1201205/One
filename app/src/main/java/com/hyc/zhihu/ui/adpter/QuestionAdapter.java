package com.hyc.zhihu.ui.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.beans.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class QuestionAdapter extends BaseAdapter {
    private Context mContext;
    private List<Question> mQuestions;

    public void setItemClickListener(OnReadingItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    private OnReadingItemClickListener mClickListener;

    public QuestionAdapter(Context context, List<Question> questions) {
        this.mContext = context;
        mQuestions = questions;
    }

    public QuestionAdapter(Context context) {
        this.mContext = context;
        mQuestions = new ArrayList<>();
    }

    public void refreshList(List<Question> realReadings) {
        mQuestions.addAll(realReadings);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mQuestions.size();
    }

    @Override
    public Question getItem(int position) {
        return mQuestions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.reading_item, null);
            holder = new ViewHolder();
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);
            holder.authorTV = (TextView) convertView.findViewById(R.id.author_tv);
            holder.contentTV = (TextView) convertView.findViewById(R.id.content_tv);
            holder.tag = (ImageView) convertView.findViewById(R.id.tag);
            holder.titleTV = (TextView) convertView.findViewById(R.id.title_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Question q = mQuestions.get(position);
        holder.tag.setImageResource(R.drawable.question_image);
        holder.titleTV.setText(q.getQuestion_title());
        if (mClickListener != null) {
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClicked(q);
                }
            });
        }
        holder.contentTV.setText(q.getAnswer_content());
        holder.authorTV.setText(q.getAnswer_title());
        return convertView;
    }


    static class ViewHolder {
        private TextView authorTV;
        private TextView titleTV;
        private TextView contentTV;
        private ImageView tag;
        private LinearLayout item;
    }

    public interface OnReadingItemClickListener {
        void onItemClicked(Question question);
    }


}
