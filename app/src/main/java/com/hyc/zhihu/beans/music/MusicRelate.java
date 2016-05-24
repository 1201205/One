package com.hyc.zhihu.beans.music;

import com.hyc.zhihu.beans.RealArticleAuthor;

public class MusicRelate implements java.io.Serializable {
    private static final long serialVersionUID = -3538020097299052495L;
    private String cover;
    private RealArticleAuthor author;
    private String music_id;
    private String id;
    private String title;
    private String platform;

    public String getCover() {
        return this.cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public RealArticleAuthor getAuthor() {
        return this.author;
    }

    public void setAuthor(RealArticleAuthor author) {
        this.author = author;
    }

    public String getMusic_id() {
        return this.music_id;
    }

    public void setMusic_id(String music_id) {
        this.music_id = music_id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
