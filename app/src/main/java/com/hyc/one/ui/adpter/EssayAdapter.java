package com.hyc.one.ui.adpter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyc.one.R;
import com.hyc.one.beans.RealArticle;
import com.hyc.one.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 */
public class EssayAdapter extends BaseAdapter {
    private Context mContext;
    private List<RealArticle> mRealArticles;

    public void setItemClickListener(OnReadingItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    private OnReadingItemClickListener mClickListener;

    public EssayAdapter(Context context, List<RealArticle> articles) {
        this.mContext = context;
        mRealArticles = articles;
    }

    public EssayAdapter(Context context) {
        this.mContext = context;
        mRealArticles = new ArrayList<>();
    }

    public void refreshList(List<RealArticle> realReadings) {
        mRealArticles.addAll(realReadings);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mRealArticles.size();
    }

    @Override
    public RealArticle getItem(int position) {
        return mRealArticles.get(position);
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
        final RealArticle s = mRealArticles.get(position);
        holder.tag.setImageResource(R.drawable.essay_image);
        holder.titleTV.setText(s.getHp_title());
        if (mClickListener != null) {
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClicked(s);
                }
            });
        }
        holder.contentTV.setText(s.getGuide_word());
        if (s.getHas_audio()) {
            Drawable music = AppUtil.getDrawable(R.drawable.audio_image);
            music.setBounds(0, 0, music.getMinimumWidth(), music.getMinimumHeight());
            holder.titleTV.setCompoundDrawables(null, null, music, null);
            holder.titleTV.setCompoundDrawablePadding(AppUtil.dip2px(3));
        } else {
            holder.titleTV.setCompoundDrawables(null, null, null, null);

        }
        holder.authorTV.setText(s.getAuthor().get(0).getUser_name());
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
        void onItemClicked(RealArticle s);
    }


}
