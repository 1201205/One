package com.hyc.zhihu.ui.adpter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hyc.zhihu.R;
import com.hyc.zhihu.beans.movie.Movie;
import com.hyc.zhihu.helper.FrescoHelper;
import com.hyc.zhihu.ui.MovieContentActivity;
import com.hyc.zhihu.utils.S;

import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private List<Movie> mMovies;

    public MovieListAdapter(List<Movie> movies) {
        mMovies = movies;
    }

    public void refresh(List<Movie> movies) {
        mMovies.addAll(movies);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie movie = mMovies.get(position);
        FrescoHelper.loadImage(holder.image, movie.getCover());
        holder.score.setText(movie.getScore());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MovieContentActivity.class);
                intent.putExtra(S.ID, movie.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView image;
        private TextView score;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (SimpleDraweeView) itemView.findViewById(R.id.image);
            score = (TextView) itemView.findViewById(R.id.score);
        }
    }

}
