package com.cjh.lib_network.rxeasyhttp.func;

import com.cjh.lib_network.rxeasyhttp.exception.ApiException;
import com.cjh.lib_network.rxeasyhttp.exception.ServerException;
import com.cjh.lib_network.rxeasyhttp.model.ApiResult;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * ApiResult<T>转换T
 * @author: caijianhui
 * @date: 2020/7/14 9:32
 * @description:
 */
public class HandleFuc<T> implements Function<ApiResult<T>, T> {

    @Override
    public T apply(@NonNull ApiResult<T> tApiResult) throws Exception {
        if (ApiException.isOk(tApiResult)) {
            return tApiResult.getData();// == null ? Optional.ofNullable(tApiResult.getData()).orElse(null) : tApiResult.getData();
        } else {
            throw new ServerException(tApiResult.getCode(), tApiResult.getMsg());
        }
    }
}
