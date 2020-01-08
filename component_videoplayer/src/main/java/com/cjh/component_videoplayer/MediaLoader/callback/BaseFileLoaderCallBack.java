package com.cjh.component_videoplayer.MediaLoader.callback;

import android.net.Uri;
import android.provider.MediaStore;
import com.cjh.component_videoplayer.MediaLoader.bean.FileProperty;
import com.cjh.component_videoplayer.MediaLoader.bean.FileType;

/**
 * @author: caijianhui
 * @date: 2019/9/19 19:44
 * @description:
 */
public abstract class BaseFileLoaderCallBack<T> extends BaseLoaderCallBack<T> {

    public static final String VOLUME_NAME = "external";

    private FileProperty mProperty;

    public BaseFileLoaderCallBack(){
        this(new FileProperty(null,null));
    }

    public BaseFileLoaderCallBack(FileType type){
        this(type.getProperty());
    }

    public BaseFileLoaderCallBack(FileProperty property){
        this.mProperty = property;
    }

    @Override
    public Uri getQueryUri() {
        return MediaStore.Files.getContentUri(VOLUME_NAME);
    }

    @Override
    public String getSelections() {
        if(mProperty!=null)
            return mProperty.createSelection();
        return null;
    }

    @Override
    public String[] getSelectProjection() {
        return new String[]{
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.DATE_MODIFIED
        };
    }

    @Override
    public String[] getSelectionsArgs() {
        if(mProperty!=null)
            return mProperty.createSelectionArgs();
        return null;
    }

}
