package com.girish.hackernews.extras;

/**
 * Created by Girish on 05-Dec-17.
 */

public class HackerNewsModel {

    private String title;
    private String by;
    private String url;
    private long id;
    private long time;

    public HackerNewsModel() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
