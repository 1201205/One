package com.hyc.zhihu.view;

import com.hyc.zhihu.base.BaseView;
import com.hyc.zhihu.beans.Comment;
import com.hyc.zhihu.beans.music.Music;
import com.hyc.zhihu.beans.music.MusicMonthItem;
import com.hyc.zhihu.beans.music.MusicRelate;

import java.util.List;

/**
 * Created by ray on 16/5/25.
 */
public interface MusicItemView extends BaseView {
    void refreshComment(List<Comment> comments);
    void showHotComment(List<Comment> comments);
    void showRelate(List<MusicRelate> musicRelates);
    void showContent(Music music);
}
