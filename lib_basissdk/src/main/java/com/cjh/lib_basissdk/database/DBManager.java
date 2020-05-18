package com.cjh.lib_basissdk.database;

import androidx.annotation.NonNull;
import com.cjh.lib_basissdk.database.bean.DBDataBean;
import com.cjh.lib_basissdk.util.ObjectUtils;
import java.util.List;

/**
 * 数据库管理类
 * @param <T>
 */
public final class DBManager<T extends DBDataBean> implements DataSource<T> {


    private DataSource<T> mEngine;

    public void setDataSource(DataSource<T> dataSource) {
        mEngine = dataSource;
    }

    @Override
    public void add(String id, @NonNull T data) {
        if (ObjectUtils.isEmpty(mEngine)) {
            return;
        }
        mEngine.add(id, data);
    }

    @Override
    public void addAll(@NonNull List<T> datas) {
        if (ObjectUtils.isEmpty(mEngine)) {
            return;
        }
        mEngine.addAll(datas);
    }

    @Override
    public void getAll(@NonNull DataCallback<List<T>> callback) {
        if (ObjectUtils.isEmpty(mEngine)) {
            return;
        }
        mEngine.getAll(callback);
    }

    @Override
    public void get(String id, @NonNull DataCallback<T> callback) {
        if (ObjectUtils.isEmpty(mEngine)) {
            return;
        }
        mEngine.get(id, callback);
    }

    @Override
    public void update(@NonNull T data) {
        if (ObjectUtils.isEmpty(mEngine)) {
            return;
        }
        mEngine.update(data);
    }

    @Override
    public void delete(@NonNull String id) {
        if (ObjectUtils.isEmpty(mEngine)) {
            return;
        }
        mEngine.delete(id);
    }

    @Override
    public void clear() {
        if (ObjectUtils.isEmpty(mEngine)) {
            return;
        }
        mEngine.clear();
    }



}
