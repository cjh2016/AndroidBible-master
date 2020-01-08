package com.cjh.component_videoplayer.MediaLoader.inter;

import java.io.File;
import java.util.List;

/**
 * @author: caijianhui
 * @date: 2019/9/19 19:26
 * @description:
 */
public interface OnRecursionListener {

    void onStart();
    void onItemAdd(File file, int counter);
    void onFinish(List<File> files);
}
