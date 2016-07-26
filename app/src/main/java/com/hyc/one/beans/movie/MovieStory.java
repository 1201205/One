package com.hyc.one.beans.movie;

import com.hyc.one.beans.User;

public class MovieStory implements java.io.Serializable {
    private static final long serialVersionUID = -4613401808978976569L;
    private String story_type;
    private String user_id;
    private int praisenum;
    private String id;
    private String sort;
    private String movie_id;
    private String title;
    private String input_date;
    private User user;
    private String content;

    public String getStory_type() {
        return this.story_type;
    }

    public void setStory_type(String story_type) {
        this.story_type = story_type;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getPraisenum() {
        return this.praisenum;
    }

    public void setPraisenum(int praisenum) {
        this.praisenum = praisenum;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getMovie_id() {
        return this.movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInput_date() {
        return this.input_date;
    }

    public void setInput_date(String input_date) {
        this.input_date = input_date;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
