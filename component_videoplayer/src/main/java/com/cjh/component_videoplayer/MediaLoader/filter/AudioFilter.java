package com.cjh.component_videoplayer.MediaLoader.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * @author: caijianhui
 * @date: 2019/9/20 15:16
 * @description:
 */
public class AudioFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        String name = pathname.getName();
        int i = name.lastIndexOf('.');
        if (i != -1) {
            name = name.substring(i);
            if (name.equalsIgnoreCase(".mp3")
                    || name.equalsIgnoreCase(".wma")
                    || name.equalsIgnoreCase(".wav")
                    || name.equalsIgnoreCase(".flac")
                    || name.equalsIgnoreCase(".amr")
                    || name.equalsIgnoreCase(".aac")) {
                return true;
            }
        }
        return false;
    }
}
