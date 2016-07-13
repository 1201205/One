package com.hyc.zhihu.ui.adpter;

import android.content.Context;
import android.view.View;

import com.hyc.zhihu.R;
import com.hyc.zhihu.base.BaseRecyclerAdapter;
import com.hyc.zhihu.base.RecyclerViewHolder;
import com.hyc.zhihu.beans.Essay;
import com.hyc.zhihu.beans.Question;
import com.hyc.zhihu.ui.MovieContentActivity;
import com.hyc.zhihu.ui.QuestionActivity;
import com.hyc.zhihu.utils.AppUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public class OtherQuestionAdapter extends BaseRecyclerAdapter<Question> {
    public OtherQuestionAdapter(Context ctx, List<Question> list) {
        super(ctx, list);
    }
    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.other_movie_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, final Question item) {
        holder.setBackground(R.id.movie_iv,R.drawable.question_image).setText(R.id.name_tv, item.getQuestion_title()).withItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.startActivityWithID(item.getQuestion_id(),v.getContext(),QuestionActivity.class);
            }
        });
    }
}
