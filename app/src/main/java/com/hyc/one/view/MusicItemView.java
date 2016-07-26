package com.hyc.one.view;

import com.hyc.one.base.BaseView;
import com.hyc.one.beans.Comment;
import com.hyc.one.beans.music.Music;
import com.hyc.one.beans.music.MusicRelate;

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
