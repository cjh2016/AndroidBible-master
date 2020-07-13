package com.cjh.lib_basissdk.database.impl;

import android.content.Context;

import androidx.annotation.NonNull;

import com.cjh.lib_basissdk.database.DataCallback;
import com.cjh.lib_basissdk.database.DataSource;
import com.cjh.lib_basissdk.database.bean.DBDataBean;

import org.greenrobot.greendao.database.DatabaseOpenHelper;

import java.util.List;

/**
 * @author: caijianhui
 * @date: 2020/5/4 14:28
 * @description:
 */
public class GreenDaoImpl<T extends DBDataBean> implements DataSource<T> {

    public GreenDaoImpl(Context context) {


    }

    @Override
    public void add(String id, @NonNull T data) {

    }

    @Override
    public void addAll(@NonNull List<T> datas) {

    }

    @Override
    public void getAll(@NonNull DataCallback<List<T>> callback) {

    }

    @Override
    public void get(String id, @NonNull DataCallback<T> callback) {

    }

    @Override
    public void update(@NonNull T data) {

    }

    @Override
    public void delete(@NonNull String id) {

    }

    @Override
    public void clear() {

    }
}
