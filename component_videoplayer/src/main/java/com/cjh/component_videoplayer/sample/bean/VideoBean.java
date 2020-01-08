package com.cjh.component_videoplayer.sample.bean;

import java.io.Serializable;

/**
 * @author: caijianhui
 * @date: 2019/9/5 13:40
 * @description:
 */
public class VideoBean implements Serializable {

    private String displayName;
    private String cover;
    private String path;

    public VideoBean() {
    }

    public VideoBean(String displayName, String cover, String path) {
        this.displayName = displayName;
        this.cover = cover;
        this.path = path;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
