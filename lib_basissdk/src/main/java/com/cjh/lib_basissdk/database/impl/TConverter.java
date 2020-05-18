package com.cjh.lib_basissdk.database.impl;

import com.cjh.lib_basissdk.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import org.greenrobot.greendao.converter.PropertyConverter;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author: caijianhui
 * @date: 2020/5/4 14:28
 * @description:
 */
public class TConverter<T> implements PropertyConverter<List<T>, String> {

    @Override
    public List<T> convertToEntityProperty(String databaseValue) {
        if (null == databaseValue) {
            return null;
        }
        Type type = new TypeToken<List<T>>() {}.getType();
        List<T> arrayList = GsonUtils.fromJson(databaseValue, type);
        return arrayList;
    }

    @Override
    public String convertToDatabaseValue(List<T> entityProperty) {
        if (null == entityProperty) {
            return null;
        }
        return GsonUtils.toJson(entityProperty);
    }
}
