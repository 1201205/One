package com.hyc.zhihu.ui.adpter;

import android.content.Context;

import com.hyc.zhihu.base.BaseRecyclerAdapter;
import com.hyc.zhihu.base.ListPresenter;
import com.hyc.zhihu.beans.music.MusicMonthItem;
import com.hyc.zhihu.presenter.OtherDairyPresenter;
import com.hyc.zhihu.presenter.OtherMoviePresenter;
import com.hyc.zhihu.presenter.OtherMusicPresenter;
import com.hyc.zhihu.utils.S;
import com.hyc.zhihu.view.OtherPictureView;

import java.util.List;

/**
 * Created by hyc on 2016/7/12.
 */
public class AdapterFactory {
    public static BaseRecyclerAdapter getAdapter(Context ctx, List list, String tag){
        switch (tag){
            case S.DAIRY:
                return new OtherDairyAdapter(ctx,list);
            case S.MOVIE:
                return new OtherMovieAdapter(ctx,list);
            case S.MUSIC:
                return new MonthMusicAdapter2(ctx,list);
        }
        return null;
    }
}
