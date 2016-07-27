package com.hyc.one.ui.adpter;

import android.content.Context;
import android.view.View;

import com.hyc.one.R;
import com.hyc.one.base.BaseRecyclerAdapter;
import com.hyc.one.base.RecyclerViewHolder;
import com.hyc.one.beans.music.MusicMonthItem;
import com.hyc.one.ui.MusicItemActivity;
import com.hyc.one.utils.AppUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public class MonthMusicAdapter2 extends BaseRecyclerAdapter<MusicMonthItem> {
    public MonthMusicAdapter2(Context ctx, List<MusicMonthItem> list) {
        super(ctx, list);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.music_month_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, final MusicMonthItem item) {
        holder.setText(R.id.title_tv, item.getTitle()).setText(R.id.name_tv, item.getAuthor().getUser_name()).loadImageByPicasso(R.id.cover_iv, item.getCover()).withItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.startActivityWithID(item.getId(), v.getContext(), MusicItemActivity.class);
            }
        });
    }
}
