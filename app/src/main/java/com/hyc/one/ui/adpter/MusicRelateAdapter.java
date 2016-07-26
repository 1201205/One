package com.hyc.one.ui.adpter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyc.one.R;
import com.hyc.one.beans.music.MusicRelate;
import com.hyc.one.helper.PicassoHelper;
import com.hyc.one.ui.MusicRelateActivity;
import com.hyc.one.utils.S;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public class MusicRelateAdapter extends RecyclerView.Adapter<MusicRelateAdapter.ViewHolder> {
    private List<MusicRelate> mMusicRelates;

    public MusicRelateAdapter(List<MusicRelate> musicRelates) {
        mMusicRelates = musicRelates;
    }

    public void refresh(List<MusicRelate> musicRelates) {
        mMusicRelates.addAll(musicRelates);
        notifyDataSetChanged();
    }

    public MusicRelateAdapter() {
        mMusicRelates = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_relate_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MusicRelate relate = mMusicRelates.get(position);
        PicassoHelper.load(holder.image.getContext(), relate.getCover(), holder.image);
//        FrescoHelper.loadImage(holder.image, relate.getCover());
//        Picasso.with(mContext).load(relate.getCover()).into(holder.image);
        holder.title.setText(relate.getTitle());
        holder.name.setText(relate.getAuthor().getUser_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MusicRelateActivity.class);
                intent.putExtra(S.ID, relate.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMusicRelates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.cover_iv);
            title = (TextView) itemView.findViewById(R.id.title_tv);
            name = (TextView) itemView.findViewById(R.id.name_tv);
        }
    }
}
