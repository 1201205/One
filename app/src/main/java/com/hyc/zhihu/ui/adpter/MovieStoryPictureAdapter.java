package com.hyc.zhihu.ui.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hyc.zhihu.R;
import com.hyc.zhihu.beans.movie.Movie;
import com.hyc.zhihu.helper.FrescoHelper;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class MovieStoryPictureAdapter extends RecyclerView.Adapter<MovieStoryPictureAdapter.ViewHolder> {

    private List<String> mUrls;

    public MovieStoryPictureAdapter(List<String> urls) {
        mUrls = urls;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_picture, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FrescoHelper.loadImage(holder.image, mUrls.get(position));
    }

    @Override
    public int getItemCount() {
        return mUrls == null ? 0 : mUrls.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (SimpleDraweeView) itemView.findViewById(R.id.image);
        }
    }

}
