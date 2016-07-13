package com.hyc.zhihu.ui.adpter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseRecyclerAdapter;
import com.hyc.zhihu.base.RecyclerViewHolder;
import com.hyc.zhihu.beans.Essay;
import com.hyc.zhihu.beans.movie.Movie;
import com.hyc.zhihu.ui.EssayActivity;
import com.hyc.zhihu.ui.MovieContentActivity;
import com.hyc.zhihu.utils.AppUtil;
import com.hyc.zhihu.utils.S;
import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public class MovieListAdapter2 extends BaseRecyclerAdapter<Movie> {
    public MovieListAdapter2(Context ctx, List<Movie> list) {
        super(ctx, list);
    }
    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_movie_list;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, final Movie movie) {
        holder.loadImageByPicasso(R.id.image,movie.getCover()).withItemClickListener(
            new View.OnClickListener() {
                @Override public void onClick(View v) {
                    AppUtil.startActivityWithID(movie.getId(),v.getContext(),MovieContentActivity.class);
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
