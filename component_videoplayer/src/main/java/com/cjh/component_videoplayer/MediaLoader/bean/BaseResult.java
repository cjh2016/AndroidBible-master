package com.cjh.component_videoplayer.MediaLoader.bean;

import java.io.Serializable;

/**
 * @author: caijianhui
 * @date: 2019/9/19 11:55
 * @description:
 */
public class BaseResult implements Serializable {

    private long totalSize;

    public BaseResult() {
    }

    public BaseResult(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }
}
