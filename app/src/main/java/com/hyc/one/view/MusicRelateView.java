package com.hyc.one.view;

import com.hyc.one.base.BaseView;
import com.hyc.one.beans.Comment;
import com.hyc.one.beans.music.Music;

import java.util.List;

/**
 * Created by ray on 16/5/25.
 */
public interface MusicRelateView extends BaseView {
    void showList(List<Comment> comments);
    void showContent(Music music);
}
