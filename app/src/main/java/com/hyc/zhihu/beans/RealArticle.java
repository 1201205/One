package com.hyc.zhihu.beans;

import java.util.List;

/**
 * 短篇
 */
public class RealArticle implements java.io.Serializable {
    private static final long serialVersionUID = 8199897490716043336L;
    private String hp_makettime;
    private String guide_word;
    private boolean has_audio;
    private String content_id;
    private String hp_title;
    private List<RealArticleAuthor> author;

    public String getHp_makettime() {
        return this.hp_makettime;
    }

    public void setHp_makettime(String hp_makettime) {
        this.hp_makettime = hp_makettime;
    }

    public String getGuide_word() {
        return this.guide_word;
    }

    public void setGuide_word(String guide_word) {
        this.guide_word = guide_word;
    }

    public boolean getHas_audio() {
        return this.has_audio;
    }

    public void setHas_audio(boolean has_audio) {
        this.has_audio = has_audio;
    }

    public String getContent_id() {
        return this.content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public String getHp_title() {
        return this.hp_title;
    }

    public void setHp_title(String hp_title) {
        this.hp_title = hp_title;
    }

    public List<RealArticleAuthor> getAuthor() {
        return this.author;
    }

    public void setAuthor(List<RealArticleAuthor> author) {
        this.author = author;
    }
}
