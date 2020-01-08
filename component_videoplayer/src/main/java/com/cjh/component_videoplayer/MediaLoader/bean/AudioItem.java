package com.cjh.component_videoplayer.MediaLoader.bean;

/**
 * @author: caijianhui
 * @date: 2019/9/19 11:54
 * @description:
 */
public class AudioItem extends BaseItem {
    private long duration;
    private boolean checked;

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
