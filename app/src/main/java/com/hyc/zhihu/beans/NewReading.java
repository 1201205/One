package com.hyc.zhihu.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewReading implements java.io.Serializable {
    private static final long serialVersionUID = 4473626619047970551L;
    private String hp_makettime;
    private String maketime;
    private String guide_word;
    private boolean has_audio;
    private String content_id;
    private String hp_title;
    private String question_makettime;
    private String question_title;
    private String title;
    private String question_id;
    private String number;
    private String read_num;
    private String answer_content;
    private String serial_id;
    private String answer_title;
    private String id;
    private String excerpt;
    private RealArticleAuthor author;
    @Expose
    @SerializedName("author")
    private List<RealArticleAuthor> authors;
    public String getHp_makettime() {
        return this.hp_makettime;
    }

    public void setHp_makettime(String hp_makettime) {
        this.hp_makettime = hp_makettime;
    }

    public String getMaketime() {
        return this.maketime;
    }

    public void setMaketime(String maketime) {
        this.maketime = maketime;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion_id() {
        return this.question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRead_num() {
        return this.read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }

    public String getAnswer_content() {
        return this.answer_content;
    }

    public void setAnswer_content(String answer_content) {
        this.answer_content = answer_content;
    }

    public String getSerial_id() {
        return this.serial_id;
    }

    public void setSerial_id(String serial_id) {
        this.serial_id = serial_id;
    }

    public String getAnswer_title() {
        return this.answer_title;
    }

    public void setAnswer_title(String answer_title) {
        this.answer_title = answer_title;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExcerpt() {
        return this.excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }
    public RealArticleAuthor getAuthor() {
        return this.author;
    }

    public void setAuthor(RealArticleAuthor author) {
        this.author = author;
    }

}
