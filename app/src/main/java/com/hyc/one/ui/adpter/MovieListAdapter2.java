package com.hyc.one.ui.adpter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.hyc.one.R;
import com.hyc.one.base.BaseRecyclerAdapter;
import com.hyc.one.base.RecyclerViewHolder;
import com.hyc.one.beans.movie.Movie;
import com.hyc.one.ui.MovieContentActivity;
import com.hyc.one.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/7/12.
 */
public class MovieListAdapter2 extends BaseRecyclerAdapter<Movie> {
    private List<Integer> mBackGrounds;
    private Random mRandom;

    public MovieListAdapter2(Context ctx, List<Movie> list) {
        super(ctx, list);
        mRandom = new Random();
        mBackGrounds = new ArrayList<>();
        mBackGrounds.add(R.drawable.movie_placeholder_0);
        mBackGrounds.add(R.drawable.movie_placeholder_1);
        mBackGrounds.add(R.drawable.movie_placeholder_2);
        mBackGrounds.add(R.drawable.movie_placeholder_3);
        mBackGrounds.add(R.drawable.movie_placeholder_4);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_movie_list;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, final Movie movie) {
        holder.loadImageByPicasso(R.id.image, movie.getCover(), mBackGrounds.get(mRandom.nextInt(5))).withItemClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppUtil.startActivityWithID(movie.getId(), v.getContext(), MovieContentActivity.class);
                    }
                });
        if (TextUtils.isEmpty(movie.getScore())) {
            holder.setVisible(R.id.line, View.GONE)
                    .setVisible(R.id.not_tv, View.VISIBLE)
                    .setText(R.id.score, "");
        } else {
            holder.setVisible(R.id.line, View.VISIBLE)
                    .setVisible(R.id.not_tv, View.GONE)
                    .setText(R.id.score, movie.getScore());
        }
    }
}
