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

import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseActivity;
import com.hyc.zhihu.beans.OnePictureData;
import com.hyc.zhihu.ui.PictureDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ray on 16/5/14.
 */
public class MonthPictureAdapter extends RecyclerView.Adapter<MonthPictureAdapter.ViewHolder> {
    private List<OnePictureData> mDatas;
    private Context mContext;

    public MonthPictureAdapter(List<OnePictureData> datas, Context context) {
        mDatas = datas;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.month_picture_item, parent, false);
        return new ViewHolder(v);
    }

    public void addItems(List<OnePictureData> datas) {
        int start = mDatas.size();
        mDatas.addAll(datas);
        notifyItemInserted(start);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.with(mContext).load(mDatas.get(position).getHp_img_original_url()).fit().into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PictureDetailActivity.class);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation((BaseActivity) mContext, v, "test1");
                intent.putExtra(PictureDetailActivity.PICTURE_DATA, mDatas.get(position));
                intent.putExtra("test2", mDatas.get(position).getHp_img_original_url());
//                ((MonthPictureActivity) mContext).getWindow().setSharedElementEnterTransition(new ChangeImageTransform(mContext, null));
//                mContext.startActivity(intent, compat.toBundle());
                ActivityCompat.startActivity((BaseActivity) mContext, intent, compat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.picture_iv);
        }
    }
}
