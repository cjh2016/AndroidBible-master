package com.cjh.lib_network.rxeasyhttp.exception;

import androidx.annotation.Nullable;

/**
 * @author: caijianhui
 * @date: 2020/7/14 9:35
 * @description:
 */
public class ServerException extends RuntimeException {

    private int errCode;
    private String message;

    public ServerException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
        this.message = msg;
    }

    public int getErrCode() {
        return errCode;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }
}
