package com.cjh.component_videoplayer.MediaLoader.bean;

import java.util.List;

/**
 * @author: caijianhui
 * @date: 2019/9/19 11:59
 * @description:
 */
public class FileResult extends BaseResult {

    private List<FileItem> items;

    public FileResult() {
    }

    public FileResult(long totalSize, List<FileItem> items) {
        super(totalSize);
        this.items = items;
    }

    public List<FileItem> getItems() {
        return items;
    }

    public void setItems(List<FileItem> items) {
        this.items = items;
    }
}

