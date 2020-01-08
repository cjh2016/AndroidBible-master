package com.cjh.component_videoplayer.MediaLoader.bean;

import com.cjh.component_videoplayer.MediaLoader.config.FileLoaderConfig;

/**
 * @author: caijianhui
 * @date: 2019/9/19 14:43
 * @description:
 */
public enum FileType {

    DOC(new FileProperty(null, FileLoaderConfig.documentMIME)),
    APK(new FileProperty(FileLoaderConfig.apkExtension,null)),
    ZIP(new FileProperty(FileLoaderConfig.zipExtension,null));

    FileProperty property;

    FileType(FileProperty property) {
        this.property = property;
    }

    public FileProperty getProperty() {
        return property;
    }

    public void setProperty(FileProperty property) {
        this.property = property;
    }


}
