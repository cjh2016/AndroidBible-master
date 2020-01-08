package com.cjh.component_videoplayer.MediaLoader.bean;

import java.util.List;

/**
 * @author: caijianhui
 * @date: 2019/9/19 14:41
 * @description:
 */
public class VideoResult extends BaseResult {

    private List<VideoFolder> folders;
    private List<VideoItem> items;

    public VideoResult() {
    }

    public VideoResult(List<VideoFolder> folders, List<VideoItem> items) {
        this.folders = folders;
        this.items = items;
    }

    public VideoResult(List<VideoFolder> folders, List<VideoItem> items,long totalSize) {
        super(totalSize);
        this.folders = folders;
        this.items = items;
    }

    public List<VideoFolder> getFolders() {
        return folders;
    }

    public void setFolders(List<VideoFolder> folders) {
        this.folders = folders;
    }

    public List<VideoItem> getItems() {
        return items;
    }

    public void setItems(List<VideoItem> items) {
        this.items = items;
    }
}
