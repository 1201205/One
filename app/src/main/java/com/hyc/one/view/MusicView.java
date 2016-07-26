package com.hyc.one.view;

import com.hyc.one.base.BaseView;
import com.hyc.one.beans.Comment;
import com.hyc.one.beans.Song;
import com.hyc.one.beans.music.Music;
import com.hyc.one.beans.music.MusicRelate;
import com.hyc.one.beans.music.MusicRelateListBean;

import java.util.List;

/**
 * Created by ray on 16/5/5.
 */
public interface MusicView extends BaseView{

    void showMusic(String id, Music data);
    void jumpToDate();
    void showNetWorkError();
    void setAdapter(List<Music> beans, List<MusicRelateListBean> listBeen);
    void setRelate(int position,List<MusicRelate> musicRelates);
    void setComment(int position, List<Comment> hot, List<Comment> normal);
    void refreshCommentList(int page,List<Comment> comments);
    void setSongList(List<Song> songs);
}
