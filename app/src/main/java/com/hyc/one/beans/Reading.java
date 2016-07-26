package com.hyc.one.beans;

import com.google.gson.JsonElement;

/**
 * Created by ray on 16/5/4.
 */
public class Reading {
    private String time;
    private int type;
    private JsonElement content;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public JsonElement getContent() {
        return content;
    }

    public void setContent(JsonElement content) {
        this.content = content;
    }
}
