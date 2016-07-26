package com.hyc.one.beans.movie;

import java.util.List;

/**
 * Created by ray on 16/5/30.
 */
public class MovieStoryWrapper {
    private int count;
    private List<MovieStory> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MovieStory> getData() {
        return data;
    }

    public void setData(List<MovieStory> data) {
        this.data = data;
    }
}
