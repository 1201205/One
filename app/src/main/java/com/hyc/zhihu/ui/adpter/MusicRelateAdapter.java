package com.hyc.zhihu.ui.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hyc.zhihu.R;
import com.hyc.zhihu.beans.music.MusicRelate;
import com.hyc.zhihu.helper.FrescoHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public class MusicRelateAdapter extends RecyclerView.Adapter<MusicRelateAdapter.ViewHolder> {
    private List<MusicRelate> mMusicRelates;

    public MusicRelateAdapter(List<MusicRelate> musicRelates) {
        mMusicRelates = musicRelates;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.music_relate_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MusicRelate relate=mMusicRelates.get(position);
        Log.e("test1111",position+"----");
        FrescoHelper.loadImage(holder.image,relate.getCover());
//        Picasso.with(mContext).load(relate.getCover()).into(holder.image);
        holder.title.setText(relate.getTitle());
        holder.name.setText(relate.getAuthor().getUser_name());
    }

    @Override
    public int getItemCount() {
        Log.e("test1111---","获取个数---"+mMusicRelates.size());
        return mMusicRelates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView image;
        private TextView title;
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (SimpleDraweeView) itemView.findViewById(R.id.cover_iv);
            title = (TextView) itemView.findViewById(R.id.title_tv);
            name = (TextView) itemView.findViewById(R.id.name_tv);
        }
    }
}
