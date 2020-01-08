package com.cjh.component_videoplayer.MediaLoader.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * @author: caijianhui
 * @date: 2019/9/20 15:16
 * @description:
 */
public class VideoFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        String name = pathname.getName();
        int i = name.lastIndexOf('.');
        if (i != -1) {
            name = name.substring(i);
            if (name.equalsIgnoreCase(".mp4")
                    || name.equalsIgnoreCase(".3gp")
                    || name.equalsIgnoreCase(".wmv")
                    || name.equalsIgnoreCase(".ts")
                    || name.equalsIgnoreCase(".rmvb")
                    || name.equalsIgnoreCase(".mov")
                    || name.equalsIgnoreCase(".m4v")
                    || name.equalsIgnoreCase(".avi")
                    || name.equalsIgnoreCase(".m3u8")
                    || name.equalsIgnoreCase(".mkv")
                    || name.equalsIgnoreCase(".flv")
                    || name.equalsIgnoreCase(".f4v")
                    || name.equalsIgnoreCase(".rm")
                    || name.equalsIgnoreCase(".mpg")
                    || name.equalsIgnoreCase(".swf")) {
                return true;
            }
        }
        return false;
    }
}
