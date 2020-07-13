package com.cjh.androidbible;

import com.cjh.lib_basissdk.bean.Result;
import com.cjh.lib_basissdk.database.impl.TConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: caijianhui
 * @date: 2020/5/4 14:16
 * @description:
 */
@Entity
public class GreenDaoTest<T> extends Result<T> {

    private String test;

    @Generated(hash = 766367447)
    public GreenDaoTest(String test) {
        this.test = test;
    }

    @Generated(hash = 456386966)
    public GreenDaoTest() {
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTest() {
        return test;
    }
}
