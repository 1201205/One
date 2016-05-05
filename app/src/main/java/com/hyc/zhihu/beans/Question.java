package com.hyc.zhihu.beans;

public class Question implements java.io.Serializable {
    private static final long serialVersionUID = 3478686235289175586L;
    private String answer_content;
    private String answer_title;
    private String question_makettime;
    private String question_title;
    private String question_id;

    public String getAnswer_content() {
        return this.answer_content;
    }

    public void setAnswer_content(String answer_content) {
        this.answer_content = answer_content;
    }

    public String getAnswer_title() {
        return this.answer_title;
    }

    public void setAnswer_title(String answer_title) {
        this.answer_title = answer_title;
    }

    public String getQuestion_makettime() {
        return this.question_makettime;
    }

    public void setQuestion_makettime(String question_makettime) {
        this.question_makettime = question_makettime;
    }

    public String getQuestion_title() {
        return this.question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getQuestion_id() {
        return this.question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }
}
