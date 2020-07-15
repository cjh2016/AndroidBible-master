package com.cjh.lib_basissdk.database.impl;

import android.content.Context;

import androidx.annotation.NonNull;

import com.cjh.lib_basissdk.constance.DBConstance;
import com.cjh.lib_basissdk.database.DataCallback;
import com.cjh.lib_basissdk.database.DataSource;
import com.cjh.lib_basissdk.database.bean.DBDataBean;
import com.cjh.lib_basissdk.singleton.Singleton;
import com.cjh.lib_basissdk.util.GenericsUtils;
import com.cjh.lib_basissdk.util.GsonUtils;
import com.cjh.lib_basissdk.util.SPUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: caijianhui
 * @date: 2020/5/4 10:00
 * @description: 共享内存策略
 */
public final class SharePreferenceImpl<T extends DBDataBean> extends Singleton implements DataSource<T> {

    private SPUtils mEngine;
    private Class<T> mClass;

    public SharePreferenceImpl(Context context) {
        mEngine = SPUtils.getInstance(DBConstance.DB_SP_NAME);
        mClass = (Class<T>) GenericsUtils.getSuperClassGenericType(SharePreferenceImpl.class);
    }

    @Override
    public void add(String id, @NonNull T data) {
        mEngine.put(id, GsonUtils.toJson(data));
    }

    @Override
    public void addAll(@NonNull List<T> datas) {
        for (T data : datas) {
            add(data.getId(), data);
        }
    }

    @Override
    public void getAll(@NonNull DataCallback<List<T>> callback) {
        ArrayList<T> datas = new ArrayList<>();
        Set<Map.Entry<String, String>> set = ((Map<String, String>)mEngine.getAll()).entrySet();
        Iterator<Map.Entry<String, String>> it = set.iterator();
        while (it.hasNext()) {
            datas.add(GsonUtils.fromJson(it.next().getValue(), mClass));
        }
        callback.onSuccess(datas);
    }

    @Override
    public void get(@NonNull String id, @NonNull DataCallback<T> callback) {
        callback.onSuccess(GsonUtils.fromJson(mEngine.getString(id), mClass));
    }

    @Override
    public void update(@NonNull T data) {
        mEngine.put(data.getId(), GsonUtils.toJson(data));
    }

    @Override
    public void delete(@NonNull String id) {
        mEngine.remove(id);
    }

    @Override
    public void clear() {
        mEngine.clear();
    }

}
