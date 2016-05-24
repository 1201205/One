package com.hyc.zhihu.beans.music;

import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public class MusicCommentListBean {
    private String id;
    private List<MusicRelate> musics;

    public String getId() {
        return id;
    }

    public MusicCommentListBean(String id, List<MusicRelate> musics) {
        this.id = id;
        this.musics = musics;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MusicRelate> getMusics() {
        return musics;
    }

    public void setMusics(List<MusicRelate> musics) {
        this.musics = musics;
    }
}
