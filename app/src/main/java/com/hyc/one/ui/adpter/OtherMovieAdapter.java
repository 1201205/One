package com.hyc.one.ui.adpter;

import android.content.Context;
import android.view.View;

import com.hyc.one.R;
import com.hyc.one.base.BaseRecyclerAdapter;
import com.hyc.one.base.RecyclerViewHolder;
import com.hyc.one.beans.movie.Movie;
import com.hyc.one.ui.MovieContentActivity;
import com.hyc.one.utils.AppUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public class OtherMovieAdapter extends BaseRecyclerAdapter<Movie> {
    public OtherMovieAdapter(Context ctx, List<Movie> list) {
        super(ctx, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.other_movie_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, final Movie item) {
        holder.setBackground(R.id.movie_iv, R.drawable.center_movie_collection).setText(R.id.name_tv, item.getTitle()).withItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.startActivityWithID(item.getId(), v.getContext(), MovieContentActivity.class);
            }
        });
    }
}
