package com.cjh.component_videoplayer.MediaLoader.callback;

import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import androidx.loader.content.Loader;
import com.cjh.component_videoplayer.MediaLoader.bean.FileItem;
import com.cjh.component_videoplayer.MediaLoader.bean.FileProperty;
import com.cjh.component_videoplayer.MediaLoader.bean.FileResult;
import com.cjh.component_videoplayer.MediaLoader.bean.FileType;
import java.util.ArrayList;
import java.util.List;
import static android.provider.MediaStore.Files.FileColumns.MIME_TYPE;
import static android.provider.MediaStore.Files.FileColumns.DATE_MODIFIED;

/**
 * @author: caijianhui
 * @date: 2019/9/20 14:38
 * @description:
 */
public abstract class OnFileLoaderCallBack extends BaseFileLoaderCallBack<FileResult> {

    public OnFileLoaderCallBack() {

    }

    public OnFileLoaderCallBack(FileType type) {
        super(type);
    }

    public OnFileLoaderCallBack(FileProperty property) {
        super(property);
    }

    @Override
    public void onLoadFinish(Loader<Cursor> loader, Cursor data) {
        List<FileItem> result = new ArrayList<>();
        FileItem item;
        long sum_size = 0;
        while (data.moveToNext()) {
            item = new FileItem();
            int audioId = data.getInt(data.getColumnIndexOrThrow(BaseColumns._ID));
            String path = data.getString(data.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            long size = data.getLong(data.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE));
            String name = data.getString(data.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME));
            String mime = data.getString(data.getColumnIndexOrThrow(MIME_TYPE));
            long modified = data.getLong(data.getColumnIndexOrThrow(DATE_MODIFIED));
            item.setId(audioId);
            item.setDisplayName(name);
            item.setPath(path);
            item.setSize(size);
            item.setMime(mime);
            item.setModified(modified);
            result.add(item);
            sum_size += size;
        }
        onResult(new FileResult(sum_size,result));
    }

}
