package com.hyc.zhihu.view;

import com.hyc.zhihu.base.BaseView;
import com.hyc.zhihu.beans.music.MusicMonthItem;

import java.util.List;

/**
 * Created by ray on 16/5/25.
 */
public interface MusicMonthView extends BaseView {
    void showList(List<MusicMonthItem> items);
}
