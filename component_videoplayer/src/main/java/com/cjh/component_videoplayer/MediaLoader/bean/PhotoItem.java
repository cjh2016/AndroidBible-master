package com.cjh.component_videoplayer.MediaLoader.bean;

/**
 * @author: caijianhui
 * @date: 2019/9/19 11:58
 * @description:
 */
public class PhotoItem extends BaseItem {

    private boolean checked;

    public PhotoItem() {
    }

    public PhotoItem(int id, String displayName, String path) {
        super(id, displayName, path);
    }

    public PhotoItem(int id, String displayName, String path, long size) {
        super(id, displayName, path, size);
    }

    public PhotoItem(int id, String displayName, String path, long size, long modified) {
        super(id, displayName, path, size, modified);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
