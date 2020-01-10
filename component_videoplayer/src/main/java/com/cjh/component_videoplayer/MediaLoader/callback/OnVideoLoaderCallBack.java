package com.cjh.component_videoplayer.MediaLoader.callback;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.loader.content.Loader;
import com.cjh.component_videoplayer.MediaLoader.bean.VideoFolder;
import com.cjh.component_videoplayer.MediaLoader.bean.VideoItem;
import com.cjh.component_videoplayer.MediaLoader.bean.VideoResult;
import java.util.ArrayList;
import java.util.List;
import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.DATE_MODIFIED;
import static android.provider.MediaStore.MediaColumns.DISPLAY_NAME;
import static android.provider.MediaStore.MediaColumns.SIZE;
import static android.provider.MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME;
import static android.provider.MediaStore.Video.VideoColumns.BUCKET_ID;
import static android.provider.MediaStore.Video.VideoColumns.DURATION;

/**
 * @author: caijianhui
 * @date: 2019/9/20 14:57
 * @description:
 */
public abstract class OnVideoLoaderCallBack extends BaseLoaderCallBack<VideoResult> {

    @Override
    public void onLoadFinish(Loader<Cursor> loader, Cursor data) {
        List<VideoFolder> folders = new ArrayList<>();
        VideoFolder folder;
        VideoItem item;
        long sum_size = 0;
        List<VideoItem> items = new ArrayList<>();
        while (data.moveToNext()) {
            String folderId = data.getString(data.getColumnIndexOrThrow(BUCKET_ID));
            String folderName = data.getString(data.getColumnIndexOrThrow(BUCKET_DISPLAY_NAME));
            int videoId = data.getInt(data.getColumnIndexOrThrow(_ID));
            String name = data.getString(data.getColumnIndexOrThrow(DISPLAY_NAME));
            String path = data.getString(data.getColumnIndexOrThrow(DATA));
            long duration = data.getLong(data.getColumnIndexOrThrow(DURATION));
            long size = data.getLong(data.getColumnIndexOrThrow(SIZE));
            long modified = data.getLong(data.getColumnIndexOrThrow(DATE_MODIFIED));
            item = new VideoItem(videoId,name,path,size,modified,duration);
            folder = new VideoFolder();
            folder.setId(folderId);
            folder.setName(folderName);
            if(folders.contains(folder)){
                folders.get(folders.indexOf(folder)).addItem(item);
            }else{
                folder.addItem(item);
                folders.add(folder);
            }
            items.add(item);
            sum_size += size;
        }
        onResult(new VideoResult(folders,items,sum_size));
    }

    @Override
    public String[] getSelectProjection() {
        String[] PROJECTION = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.BUCKET_ID,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.MediaColumns.SIZE,
                MediaStore.Video.Media.DATE_MODIFIED
        };
        return PROJECTION;
    }

    @Override
    public Uri getQueryUri() {
        return MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    }

}
