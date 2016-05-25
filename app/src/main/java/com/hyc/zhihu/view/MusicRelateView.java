package com.hyc.zhihu.view;

import com.hyc.zhihu.base.BaseView;
import com.hyc.zhihu.beans.Comment;
import com.hyc.zhihu.beans.music.Music;

import java.util.List;

/**
 * Created by ray on 16/5/25.
 */
public interface MusicRelateView extends BaseView {
    void showList(List<Comment> comments);
    void showContent(Music music);
}
