package com.cjh.lib_basissdk.database;

import androidx.annotation.NonNull;

import com.cjh.lib_basissdk.database.bean.DBDataBean;

import java.util.List;

/**
 * 基类数据接口
 * @param <T>
 */
public interface DataSource<T extends DBDataBean> extends IMemoryDataSource, ISharePreferenceDataSource,
        ILocalDataSource, IDatabaseDataSource, INetworkDataSource {

    void add(String id, @NonNull T data);

    void addAll(@NonNull List<T> datas);

    void getAll(@NonNull DataCallback<List<T>> callback);

    void get(String id, @NonNull DataCallback<T> callback);

    void update(@NonNull T data);

    void delete(@NonNull String id);

    void clear();
}
