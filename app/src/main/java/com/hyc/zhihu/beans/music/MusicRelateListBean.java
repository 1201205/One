package com.hyc.zhihu.beans.music;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hyc.zhihu.beans.Comment;

import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public class MusicRelateListBean {
    public boolean hasRequested() {
        return hasRequested;
    }

    public void setHasRequested(boolean hasRequested) {
        this.hasRequested = hasRequested;
    }

    private boolean hasRequested;
    public SwipeToLoadLayout getLayout() {
        return layout;
    }

    public void setLayout(SwipeToLoadLayout layout) {
        this.layout = layout;
    }

    private SwipeToLoadLayout layout;
    private String id;
    private List<MusicRelate> musics;
    private List<Comment> hotComment;

    public String getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(String lastIndex) {
        this.lastIndex = lastIndex;
    }

    private String lastIndex;

    public MusicRelateListBean(String id, List<MusicRelate> musics, List<Comment> hotComment,String lastIndex) {
        this.id = id;
        this.musics = musics;
        this.hotComment = hotComment;
        this.lastIndex=lastIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public List<Comment> getHotComment() {
        return hotComment;
    }

    public void setHotComment(List<Comment> hotComment) {
        this.hotComment = hotComment;
    }

    public List<MusicRelate> getMusics() {
        return musics;
    }

    public void setMusics(List<MusicRelate> musics) {
        this.musics = musics;
    }


}
