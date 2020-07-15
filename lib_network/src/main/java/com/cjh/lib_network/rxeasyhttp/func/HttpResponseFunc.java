package com.cjh.lib_network.rxeasyhttp.func;

import com.cjh.lib_network.rxeasyhttp.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 异常转换处理
 * @author: caijianhui
 * @date: 2020/7/14 9:58
 * @description:
 */
public class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {

    @Override
    public Observable<T> apply(@NonNull Throwable throwable) throws Exception {
        return Observable.error(ApiException.handleException(throwable));
    }
}
