package com.hyc.one.view;

import com.hyc.one.base.BaseView;
import com.hyc.one.beans.music.MusicMonthItem;

import java.util.List;

/**
 * Created by ray on 16/5/25.
 */
public interface MusicMonthView extends BaseView {
    void showList(List<MusicMonthItem> items);
}
