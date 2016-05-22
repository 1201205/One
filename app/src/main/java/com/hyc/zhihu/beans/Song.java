package com.hyc.zhihu.beans;

/**
 * Created by ray on 16/5/22.
 */
public class Song {
    private String path;

    public Song(String name, String path) {
        this.path = path;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String name;
}
