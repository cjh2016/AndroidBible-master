package com.cjh.component_videoplayer.MediaLoader.callback;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.loader.content.Loader;
import com.cjh.component_videoplayer.MediaLoader.bean.AudioItem;
import com.cjh.component_videoplayer.MediaLoader.bean.AudioResult;
import java.util.ArrayList;
import java.util.List;
import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.Audio.AudioColumns.DURATION;
import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.DATE_MODIFIED;
import static android.provider.MediaStore.MediaColumns.DISPLAY_NAME;
import static android.provider.MediaStore.MediaColumns.SIZE;

/**
 * @author: caijianhui
 * @date: 2019/9/19 20:01
 * @description:
 */
public abstract class OnAudioLoaderCallBack extends BaseLoaderCallBack<AudioResult> {


    @Override
    public void onLoadFinish(Loader<Cursor> loader, Cursor data) {
        List<AudioItem> result = new ArrayList<>();
        AudioItem item;
        long sum_size = 0;
        while (data.moveToNext()) {
            item = new AudioItem();
            int audioId = data.getInt(data.getColumnIndexOrThrow(_ID));
            String name = data.getString(data.getColumnIndexOrThrow(DISPLAY_NAME));
            String path = data.getString(data.getColumnIndexOrThrow(DATA));
            long duration = data.getLong(data.getColumnIndexOrThrow(DURATION));
            long size = data.getLong(data.getColumnIndexOrThrow(SIZE));
            long modified = data.getLong(data.getColumnIndexOrThrow(DATE_MODIFIED));
            item.setId(audioId);
            item.setDisplayName(name);
            item.setPath(path);
            item.setDuration(duration);
            item.setSize(size);
            item.setModified(modified);
            result.add(item);
            sum_size += size;
        }
        onResult(new AudioResult(sum_size, result));
    }

    @Override
    public Uri getQueryUri() {
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }

    @Override
    public String[] getSelectProjection() {
        String[] PROJECTION = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.MediaColumns.SIZE,
                MediaStore.Audio.Media.DATE_MODIFIED
        };
        return PROJECTION;
    }

}


