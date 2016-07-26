package com.hyc.zhihu.ui.adpter;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.beans.User;
import com.hyc.zhihu.beans.movie.MovieStory;
import com.hyc.zhihu.helper.PicassoHelper;
import com.hyc.zhihu.utils.AppUtil;
import com.hyc.zhihu.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ray on 16/5/18.
 */
public class MovieStoryAdapter extends BaseAdapter {
    private List<MovieStory> mStories;

    public MovieStoryAdapter() {
        mStories = new ArrayList<>();
    }

    public void refreshComments(List<MovieStory> comments) {
        mStories.addAll(comments);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mStories == null ? 0 : mStories.size();
    }

    @Override
    public MovieStory getItem(int position) {
        return mStories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_story, null);
            holder = new ViewHolder();
            holder.head = (CircleImageView) convertView.findViewById(R.id.head_iv);
            holder.name = (TextView) convertView.findViewById(R.id.name_tv);
            holder.num = (TextView) convertView.findViewById(R.id.num_tv);
            holder.date = (TextView) convertView.findViewById(R.id.date_tv);
            holder.content = (TextView) convertView.findViewById(R.id.content_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MovieStory comment = mStories.get(position);
        User user = comment.getUser();
        PicassoHelper.load(holder.head.getContext(), user.getWeb_url(), holder.head, R.drawable.head);
        holder.name.setText(user.getUser_name());
        holder.date.setText(comment.getInput_date());
        holder.num.setText(comment.getPraisenum() + "");
        holder.content.setText(comment.getContent());
        return convertView;
    }

    static class ViewHolder {
        private CircleImageView head;
        private TextView name;
        private TextView date;
        private TextView num;
        private TextView content;
    }

    private SpannableString getClickableSpan(String text, int index) {
        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("test", "click");
            }
        };

        SpannableString spanableInfo = new SpannableString(text);
        int start = 0;
        spanableInfo.setSpan(new Clickable(l), start, index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanableInfo;
    }

    class Clickable extends ClickableSpan {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener l) {
            mListener = l;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(AppUtil.getColor(R.color.google_blue));//文本颜色
            ds.setUnderlineText(false);//是否有下划线
        }

        @Override
        public void onClick(View v) {
            if (null != mListener) {
                mListener.onClick(v);
            }
        }
    }
}
