package com.hyc.one.beans;

public class Comment implements java.io.Serializable {
    private static final long serialVersionUID = -5744748445602983590L;
    private String quote;//引用
    private User touser;//被引用用户
    private int praisenum;//心数量
    private String id;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    private String score;
    private String input_date;//时间
    private int type;//0热门1，普通
    private User user;//当前用户
    private String content;//主要内容

    public String getQuote() {
        return this.quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public User getTouser() {
        return this.touser;
    }

    public void setTouser(User touser) {
        this.touser = touser;
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

    public String getInput_date() {
        return this.input_date;
    }

    public void setInput_date(String input_date) {
        this.input_date = input_date;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
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
