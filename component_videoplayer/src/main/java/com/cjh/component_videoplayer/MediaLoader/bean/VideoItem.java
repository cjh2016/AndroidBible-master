package com.cjh.component_videoplayer.MediaLoader.bean;

/**
 * @author: caijianhui
 * @date: 2019/9/19 11:50
 * @description:
 */
public class VideoItem extends BaseItem {

    private long duration;
    private boolean checked;

    public VideoItem() {
    }

    public VideoItem(int id, String displayName, String path, long size, long modified, long duration) {
        super(id, displayName, path, size, modified);
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
