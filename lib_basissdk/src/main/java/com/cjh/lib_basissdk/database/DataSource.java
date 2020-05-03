package com.cjh.lib_basissdk.database;

import androidx.annotation.NonNull;
import java.util.List;

public interface DataSource<T> {

    void getAll(@NonNull DataCallback<List<T>> callback);

    void get(@NonNull String id, @NonNull DataCallback<T> callback);

    void update(@NonNull T data);

    void delete(@NonNull String id);

    void clear();
}
