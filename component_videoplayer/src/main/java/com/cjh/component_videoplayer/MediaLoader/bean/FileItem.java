package com.cjh.component_videoplayer.MediaLoader.bean;

/**
 * @author: caijianhui
 * @date: 2019/9/19 11:57
 * @description:
 */
public class FileItem extends BaseItem {

    private String mime;
    private boolean checked;

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
