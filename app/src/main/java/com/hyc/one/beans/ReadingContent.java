package com.hyc.one.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 2016/5/16.
 */
public class ReadingContent extends RealmObject{
    private String title;
    private String content;
    private String author;
    private boolean hasAudio;
    @PrimaryKey
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public ReadingContent(String title, boolean hasAudio, String author, String content, String id) {
        this.title = title;
        this.hasAudio = hasAudio;
        this.author = author;
        this.content = content;
        this.id=id;
    }
    public ReadingContent() {
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHasAudio() {
        return hasAudio;
    }

    public void setHasAudio(boolean hasAudio) {
        this.hasAudio = hasAudio;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
