package com.cjh.component_videoplayer.MediaLoader.bean;

import java.io.Serializable;

/**
 * @author: caijianhui
 * @date: 2019/9/19 11:59
 * @description:
 */
public class BaseFolder implements Serializable {
    private String id;
    private String name;

    public BaseFolder() {
    }

    public BaseFolder(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
