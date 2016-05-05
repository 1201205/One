package com.hyc.zhihu.beans;

import java.util.List;

public class Essay implements java.io.Serializable {
    private static final long serialVersionUID = -3839665997985805420L;
    private String hp_makettime;
    private String sub_title;
    private String guide_word;
    private String content_id;
    private String hp_title;
    private Object push_id;
    private int praisenum;
    private int sharenum;
    private String hp_author;
    private String hp_content;
    private String hp_author_introduce;
    private String wb_name;
    private String wb_img_url;
    private String auth_it;
    private String web_url;
    private String audio;
    private String last_update_date;
    private int commentnum;
    private List<RealArticleAuthor> author;


    public String getHp_makettime() {
        return this.hp_makettime;
    }

    public void setHp_makettime(String hp_makettime) {
        this.hp_makettime = hp_makettime;
    }

    public String getSub_title() {
        return this.sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getGuide_word() {
        return this.guide_word;
    }

    public void setGuide_word(String guide_word) {
        this.guide_word = guide_word;
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

    public Object getPush_id() {
        return this.push_id;
    }

    public void setPush_id(Object push_id) {
        this.push_id = push_id;
    }

    public int getPraisenum() {
        return this.praisenum;
    }

    public void setPraisenum(int praisenum) {
        this.praisenum = praisenum;
    }

    public int getSharenum() {
        return this.sharenum;
    }

    public void setSharenum(int sharenum) {
        this.sharenum = sharenum;
    }

    public String getHp_author() {
        return this.hp_author;
    }

    public void setHp_author(String hp_author) {
        this.hp_author = hp_author;
    }

    public String getHp_content() {
        return this.hp_content;
    }

    public void setHp_content(String hp_content) {
        this.hp_content = hp_content;
    }

    public String getHp_author_introduce() {
        return this.hp_author_introduce;
    }

    public void setHp_author_introduce(String hp_author_introduce) {
        this.hp_author_introduce = hp_author_introduce;
    }

    public String getWb_name() {
        return this.wb_name;
    }

    public void setWb_name(String wb_name) {
        this.wb_name = wb_name;
    }

    public String getWb_img_url() {
        return this.wb_img_url;
    }

    public void setWb_img_url(String wb_img_url) {
        this.wb_img_url = wb_img_url;
    }

    public String getAuth_it() {
        return this.auth_it;
    }

    public void setAuth_it(String auth_it) {
        this.auth_it = auth_it;
    }

    public String getWeb_url() {
        return this.web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getAudio() {
        return this.audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getLast_update_date() {
        return this.last_update_date;
    }

    public void setLast_update_date(String last_update_date) {
        this.last_update_date = last_update_date;
    }

    public int getCommentnum() {
        return this.commentnum;
    }

    public void setCommentnum(int commentnum) {
        this.commentnum = commentnum;
    }
    public List<RealArticleAuthor> getAuthor() {
        return this.author;
    }

    public void setAuthor(List<RealArticleAuthor> author) {
        this.author = author;
    }
}
