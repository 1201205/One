package com.hyc.zhihu.ui.adpter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hyc.zhihu.MainApplication;
import com.hyc.zhihu.R;
import com.hyc.zhihu.beans.Comment;
import com.hyc.zhihu.beans.User;
import com.hyc.zhihu.helper.FrescoHelper;
import com.hyc.zhihu.utils.AppUtil;
import com.hyc.zhihu.widget.CircleImageView;
import com.hyc.zhihu.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ray on 16/5/18.
 */
public class CommentAdapter extends BaseAdapter {
    private List<Comment> mComments;
    private Context mContext;
    private CircleTransform mTransform = new CircleTransform();

    public CommentAdapter() {
//        mContext = context;
        mComments = new ArrayList<>();
    }

    public void refreshComments(List<Comment> comments) {
        mComments.addAll(comments);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mComments == null ? 0 : mComments.size();
    }

    @Override
    public Comment getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, null);
            holder = new ViewHolder();
            holder.head = (SimpleDraweeView) convertView.findViewById(R.id.head_iv);
            holder.name = (TextView) convertView.findViewById(R.id.name_tv);
            holder.num = (TextView) convertView.findViewById(R.id.num_tv);
            holder.date = (TextView) convertView.findViewById(R.id.date_tv);
            holder.other = (TextView) convertView.findViewById(R.id.other_tv);
            holder.content = (TextView) convertView.findViewById(R.id.content_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Comment comment = mComments.get(position);
        User user=comment.getUser();
        if (!TextUtils.isEmpty(user.getWeb_url())) {
            FrescoHelper.loadImage(holder.head,user.getWeb_url());
//            Picasso.with(mContext).load(user.getWeb_url()).placeholder(R.drawable.head).into(holder.head);
        }
        holder.name.setText(user.getUser_name());
        holder.date.setText(comment.getInput_date());
        holder.num.setText(comment.getPraisenum()+"");
        if (TextUtils.isEmpty(comment.getQuote())) {
            holder.other.setVisibility(View.GONE);
        } else {
            String s=null;
            if(comment.getTouser()!=null){
                s=comment.getTouser().getUser_name()+":";
            }
            String all=s+comment.getQuote();
            holder.other.setVisibility(View.VISIBLE);
            holder.other.setText(all);
            holder.other.setText(getClickableSpan(all,s.length()));
            holder.other.setMovementMethod(LinkMovementMethod.getInstance());
        }
//        if (TextUtils.equals("茉莉",user.getUser_name())) {
//            Log.e("test---",comment.getQuote()+"----"+(comment.getTouser()==null));
//        }
        holder.content.setText(comment.getContent());
        return convertView;
    }

    static class ViewHolder {
        private SimpleDraweeView head;
        private TextView name;
        private TextView date;
        private TextView num;
        private TextView other;
        private TextView content;
    }
    private SpannableString getClickableSpan(String text,int index) {
        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("test","click");
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
