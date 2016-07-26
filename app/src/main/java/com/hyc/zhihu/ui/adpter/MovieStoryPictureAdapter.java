package com.hyc.zhihu.ui.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hyc.zhihu.R;
import com.hyc.zhihu.helper.PicassoHelper;

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
        PicassoHelper.load(holder.image.getContext(), mUrls.get(position), holder.image);
    }

    @Override
    public int getItemCount() {
        return mUrls == null ? 0 : mUrls.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

}
