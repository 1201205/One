package com.hyc.zhihu.ui.adpter;

import android.content.Context;
import android.view.View;

import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseRecyclerAdapter;
import com.hyc.zhihu.base.RecyclerViewHolder;
import com.hyc.zhihu.beans.Question;
import com.hyc.zhihu.beans.Serial;
import com.hyc.zhihu.ui.MovieContentActivity;
import com.hyc.zhihu.ui.SerialActivity;
import com.hyc.zhihu.utils.AppUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public class OtherSerialAdapter extends BaseRecyclerAdapter<Serial> {
    public OtherSerialAdapter(Context ctx, List<Serial> list) {
        super(ctx, list);
    }
    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.other_movie_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, final Serial item) {
        holder.setBackground(R.id.movie_iv,R.drawable.serial_image).setText(R.id.name_tv, item.getTitle()).withItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.startActivityWithID(item.getId(),v.getContext(),SerialActivity.class);
            }
        });
    }
}