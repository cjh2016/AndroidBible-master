package com.cjh.component_videoplayer.MediaLoader.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * @author: caijianhui
 * @date: 2019/9/20 15:15
 * @description:
 */
public class NullFilter implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        return true;
    }
}
