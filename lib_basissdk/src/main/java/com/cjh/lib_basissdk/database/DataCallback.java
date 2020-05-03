package com.cjh.lib_basissdk.database;

public interface DataCallback<T> {

    void onSuccess(T data);

    void onError();
}
