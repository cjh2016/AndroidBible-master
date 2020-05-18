package com.cjh.lib_basissdk.bean;

import com.cjh.lib_basissdk.database.impl.TConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author: caijianhui
 * @date: 2020/5/4 14:16
 * @description:
 */
@Entity
public class Result<T> implements Serializable {

    private int code;
    private String msg;
    @Convert(columnType = String.class, converter = TConverter.class)
    private List<T> resultlist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<T> resultlist) {
        this.resultlist = resultlist;
    }
}
