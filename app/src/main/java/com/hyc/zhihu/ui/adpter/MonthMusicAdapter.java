package com.hyc.zhihu.ui.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hyc.zhihu.R;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.beans.music.MusicMonthItem;
import com.hyc.zhihu.helper.FrescoHelper;
import com.hyc.zhihu.ui.MonthPictureActivity;
import com.hyc.zhihu.ui.MusicItemActivity;
import com.hyc.zhihu.ui.PictureDetailActivity;
import com.hyc.zhihu.utils.S;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ray on 16/5/14.
 */
public class MonthMusicAdapter extends RecyclerView.Adapter <MonthMusicAdapter.ViewHolder>{
    private List<MusicMonthItem> mDatas;
    public MonthMusicAdapter(List<MusicMonthItem> datas){
        mDatas=datas;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.music_month_item,parent,false);
        return new ViewHolder(v);
    }
    public void addItems(List<MusicMonthItem> datas){
        int start=datas.size();
        mDatas.addAll(datas);
        notifyItemInserted(start);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MusicMonthItem item=mDatas.get(position);
        FrescoHelper.loadImage(holder.image,item.getCover());
        holder.title.setText(item.getTitle());
        holder.name.setText(item.getAuthor().getUser_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(), MusicItemActivity.class);
                i.putExtra(S.ID,item.getId());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas==null?0:mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private SimpleDraweeView image;
        private TextView title;
        private TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            image= (SimpleDraweeView) itemView.findViewById(R.id.cover_iv);
            title= (TextView) itemView.findViewById(R.id.title_tv);
            name= (TextView) itemView.findViewById(R.id.name_tv);
        }
    }
}
