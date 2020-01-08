package com.cjh.component_videoplayer.MediaLoader.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * @author: caijianhui
 * @date: 2019/9/20 15:08
 * @description:
 */
public class PhotoFilter implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        if (pathname.length() <= 0)
            return false;
        String name = pathname.getName();
        int i = name.lastIndexOf('.');
        if (i != -1) {
            name = name.substring(i);
            if (name.equalsIgnoreCase(".jpg")
                    || name.equalsIgnoreCase(".jpeg")
                    || name.equalsIgnoreCase(".png")
                    || name.equalsIgnoreCase(".gif")
                    || name.equalsIgnoreCase(".bmp")) {
                return true;
            }
        }
        return false;
    }

}
